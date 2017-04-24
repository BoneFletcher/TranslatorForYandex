package frosteagle.com.yandextranslator.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import frosteagle.com.yandextranslator.FullScreenWordActivity;
import frosteagle.com.yandextranslator.R;
import frosteagle.com.yandextranslator.api.ApiInterface;
import frosteagle.com.yandextranslator.api.ServiceGenerator;
import frosteagle.com.yandextranslator.db.HistoryTranslateDataSource;
import frosteagle.com.yandextranslator.extras.Constants;
import frosteagle.com.yandextranslator.models.languageModel.LanguageCustomModel;
import frosteagle.com.yandextranslator.models.languageModel.LanguageModels;
import frosteagle.com.yandextranslator.models.languageModel.languageTranslate.TranslateModel;
import frosteagle.com.yandextranslator.realm.FavoriteHistory;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class TranslatorFragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener {
    private static final int MY_DATA_CHECK_CODE = 1;
    private EditText edtInputWord;
    private View view;
    private TextView tvLanguageFrom, tvLanguageTo, tvTranslateWord, tvOriginalWord;
    private String languageFrom = "ru", languageTo = "en";
    private final int REQ_CODE_SPEECH_INPUT = 2;
    private ImageView imgMicro, imgClear, imgSoundInput, imgSoundTranslate, imgFullScreen, imgFavorite;
    private TextToSpeech myTTS;
    private DialogChoseLanguage dialog;
    private LanguageModels languageModel;
    private List<LanguageCustomModel> languageList;
    private HistoryTranslateDataSource dbFavoriteForecasters;
    private Realm mRealm;
    private FavoriteHistory historyModel;
    private boolean isFavorite = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_translator, container, false);
        mRealm = Realm.getInstance(getContext());
        languageList = new ArrayList<>();
        tvLanguageFrom = (TextView) view.findViewById(R.id.tv_language_from);
        tvLanguageTo = (TextView) view.findViewById(R.id.tv_language_to);
        tvTranslateWord = (TextView) view.findViewById(R.id.tv_translate_word);
        tvOriginalWord = (TextView) view.findViewById(R.id.tv_original_word);
        edtInputWord = (EditText) view.findViewById(R.id.edt_input_word);
        imgMicro = (ImageView) view.findViewById(R.id.img_micro);
        imgClear = (ImageView) view.findViewById(R.id.img_clear);
        imgSoundInput = (ImageView) view.findViewById(R.id.img_sound_input);
        imgSoundTranslate = (ImageView) view.findViewById(R.id.img_sound_translate);
        imgFullScreen = (ImageView) view.findViewById(R.id.img_open_full);
        imgFavorite = (ImageView) view.findViewById(R.id.img_favorite);
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        tvLanguageFrom.setOnClickListener(this);
        tvLanguageTo.setOnClickListener(this);
        imgMicro.setOnClickListener(this);
        imgClear.setOnClickListener(this);
        imgSoundInput.setOnClickListener(this);
        imgSoundTranslate.setOnClickListener(this);
        imgFullScreen.setOnClickListener(this);
        imgFavorite.setOnClickListener(this);
        initLanguage("ru");
        dbFavoriteForecasters = new HistoryTranslateDataSource(getActivity());
        dbFavoriteForecasters.open();
        edtInputWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0) {
                    translate(s.toString());
                }
            }
        });
        edtInputWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    isFavorite = false;
                    mRealm.beginTransaction();
                    FavoriteHistory historyModelRealm = new FavoriteHistory();
                    historyModelRealm.setFirstWord(historyModel.getFirstWord());
                    historyModelRealm.setSecondWord(historyModel.getSecondWord());
                    historyModelRealm.setLocale(historyModel.getLocale());
                    historyModelRealm.setFavorite(true);
                    mRealm.copyToRealmOrUpdate(historyModelRealm);
                    mRealm.commitTransaction();
                }
                return false;
            }
        });
        return view;

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbFavoriteForecasters.close();
        mRealm.close();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }


    public void callOnActivityResultOnChildFragments(Fragment parent, int requestCode, int resultCode, Intent data) {
        FragmentManager childFragmentManager = parent.getChildFragmentManager();
        if (childFragmentManager != null) {
            List<Fragment> childFragments = childFragmentManager.getFragments();
            if (childFragments == null) {
                return;
            }
            for (Fragment child : childFragments) {
                if (child != null && !child.isDetached() && !child.isRemoving()) {
                    child.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callOnActivityResultOnChildFragments(this, requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtInputWord.setText(result.get(0));
                }
                break;
            }
            case MY_DATA_CHECK_CODE: {
                if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    myTTS = new TextToSpeech(getContext(), this);
                } else {
                    Intent installTTSIntent = new Intent();
                    installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(installTTSIntent);
                }
            }
        }

        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                languageFrom = data.getStringExtra("languageCode");
                tvLanguageFrom.setText(data.getStringExtra("language"));

            }
        } else if (requestCode == 15) {
            if (resultCode == Activity.RESULT_OK) {
                languageTo = data.getStringExtra("languageCode");
                tvLanguageTo.setText(data.getStringExtra("language"));

            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_language_from:
                FragmentManager manager = getFragmentManager();
                dialog = new DialogChoseLanguage();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("languageList", (ArrayList<? extends Parcelable>) languageList);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(this, 10);
                dialog.show(manager, "Dialog");
                break;
            case R.id.tv_language_to:
                FragmentManager managerTo = getFragmentManager();
                dialog = new DialogChoseLanguage();
                Bundle bundleTo = new Bundle();
                bundleTo.putParcelableArrayList("languageList", (ArrayList<? extends Parcelable>) languageList);
                dialog.setArguments(bundleTo);
                dialog.setTargetFragment(this, 15);
                dialog.show(managerTo, "Dialog");
                break;
            case R.id.img_micro:
                promptSpeechInput();
                break;
            case R.id.img_clear:
                historyModel=new FavoriteHistory();
                edtInputWord.setText("");
                tvTranslateWord.setText("");
                tvOriginalWord.setText("");
                break;
            case R.id.img_sound_input:
                myTTS.speak(edtInputWord.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.img_sound_translate:
                myTTS.speak(tvTranslateWord.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.img_open_full:
                if (historyModel!=null) {
                    Intent intent = new Intent(getContext(), FullScreenWordActivity.class);
                    intent.putExtra("word", historyModel.getFirstWord());
                    startActivity(intent);
                }

                break;
            case R.id.img_favorite:
                if (!isFavorite&&historyModel!=null) {
                    mRealm.beginTransaction();
                    FavoriteHistory historyModelRealm = new FavoriteHistory();
                    historyModelRealm.setFirstWord(historyModel.getFirstWord());
                    historyModelRealm.setSecondWord(historyModel.getSecondWord());
                    historyModelRealm.setLocale(historyModel.getLocale());
                    historyModelRealm.setFavorite(true);
                    mRealm.copyToRealmOrUpdate(historyModelRealm);
                    mRealm.commitTransaction();
                    imgFavorite.setImageResource(R.drawable.ic_turned_in_yellow);
                    isFavorite=true;

                }
                break;
        }
    }

    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.getDefault()) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.getDefault());
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(getContext(), "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    public void initLanguage(String language) {
        ApiInterface apiService = ServiceGenerator.getClient().create(ApiInterface.class);
        Call<LanguageModels> call = apiService.getLanguages(Constants.API_KEY, language);
        call.enqueue(new retrofit2.Callback<LanguageModels>() {
            @Override
            public void onResponse(Call<LanguageModels> call, Response<LanguageModels> response) {
                languageModel = response.body();
                for (int i = 0; i < response.body().getLangs().size(); i++) {

                }
                for (Map.Entry<String, String> entry : response.body().getLangs().entrySet()) {
                    languageList.add(new LanguageCustomModel(entry.getKey(), entry.getValue()));

                }


            }

            @Override
            public void onFailure(Call<LanguageModels> call, Throwable t) {

            }
        });
    }

    public void translate(String text) {
        ApiInterface apiService = ServiceGenerator.getClient().create(ApiInterface.class);
        Call<TranslateModel> call = apiService.getTranslate(Constants.API_KEY, text, languageFrom + "-" + languageTo);
        call.enqueue(new retrofit2.Callback<TranslateModel>() {
            @Override
            public void onResponse(Call<TranslateModel> call, Response<TranslateModel> response) {
                tvOriginalWord.setText(edtInputWord.getText().toString());
                tvTranslateWord.setText(response.body().getText().get(0));

                historyModel = new FavoriteHistory();
                historyModel.setFirstWord(response.body().getText().get(0));
                historyModel.setSecondWord(edtInputWord.getText().toString());
                historyModel.setLocale(languageFrom.toUpperCase() + "-" + languageTo.toUpperCase());




            }

            @Override
            public void onFailure(Call<TranslateModel> call, Throwable t) {

            }
        });
    }


}

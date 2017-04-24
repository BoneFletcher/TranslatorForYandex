package frosteagle.com.yandextranslator.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import frosteagle.com.yandextranslator.R;
import frosteagle.com.yandextranslator.adapter.LanguageRecycleAdapter;
import frosteagle.com.yandextranslator.models.languageModel.LanguageCustomModel;

public class DialogChoseLanguage extends DialogFragment {
    private LanguageRecycleAdapter adapter;
    private RecyclerView rvLanguage;
    private ArrayList<LanguageCustomModel> languageList;
    private ProgressBar pbLanguage;
    private LinearLayoutManager mLayoutManager;
    private SearchView searchView;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog_language, null);
        Bundle bundle = this.getArguments();
        languageList = bundle.getParcelableArrayList("languageList");
        if (!languageList.isEmpty()) {
            Collections.sort(languageList, new Comparator<LanguageCustomModel>() {
                @Override
                public int compare(LanguageCustomModel lhs, LanguageCustomModel rhs) {
                    return lhs.getLanguageTitle().compareTo(rhs.getLanguageTitle());
                }
            });
        }
        rvLanguage = (RecyclerView) view.findViewById(R.id.rv_language);
        pbLanguage = (ProgressBar) view.findViewById(R.id.pb_language);
        searchView = (SearchView) view.findViewById(R.id.search_for_words);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        adapter = new LanguageRecycleAdapter(languageList, getContext());
        mLayoutManager = new LinearLayoutManager(getContext());
        rvLanguage.setAdapter(adapter);
        rvLanguage.setLayoutManager(mLayoutManager);
        adapter.setOnItemClickListener(new LanguageRecycleAdapter.mAdapterListener() {
            @Override
            public void onAnswerClick(View v, int position, LanguageCustomModel model) {
                Bundle bundle = new Bundle();
                bundle.putString("language", model.getLanguageTitle());
                bundle.putString("languageCode", model.getLanguageCode());
                Intent intent = new Intent().putExtras(bundle);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Выберите язык: ")
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }



}

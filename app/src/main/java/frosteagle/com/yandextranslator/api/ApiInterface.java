package frosteagle.com.yandextranslator.api;

import frosteagle.com.yandextranslator.models.languageModel.LanguageModels;
import frosteagle.com.yandextranslator.models.languageModel.languageTranslate.TranslateModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("getLangs")
    Call<LanguageModels> getLanguages(@Query("key") String key, @Query("ui") String ui);

    @GET("translate")
    Call<TranslateModel> getTranslate(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);



}

package frosteagle.com.yandextranslator.models.languageModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;

public class LanguageModels implements Parcelable{
    private List<String> dirs;
    private HashMap<String, String> langs;
   // private LanguageModelsLangs langs;

    protected LanguageModels(Parcel in) {
        dirs = in.createStringArrayList();

    }

    public static final Creator<LanguageModels> CREATOR = new Creator<LanguageModels>() {
        @Override
        public LanguageModels createFromParcel(Parcel in) {
            return new LanguageModels(in);
        }

        @Override
        public LanguageModels[] newArray(int size) {
            return new LanguageModels[size];
        }
    };

//    public LanguageModelsLangs getLangs() {
//        return this.langs;
//    }
//
//    public void setLangs(LanguageModelsLangs langs) {
//        this.langs = langs;
//    }

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(dirs);
      //  dest.writeParcelable(langs, flags);
    }

    public HashMap<String, String> getLangs() {
        return langs;
    }

    public void setLangs(HashMap<String, String> langs) {
        this.langs = langs;
    }
}

package frosteagle.com.yandextranslator.models.languageModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FrostEagle on 21.04.2017.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

public class LanguageCustomModel implements Parcelable{
    private String languageCode;
    private String languageTitle;

    public LanguageCustomModel(String languageCode, String languageTitle) {
        this.languageCode = languageCode;
        this.languageTitle = languageTitle;
    }

    public LanguageCustomModel(Parcel in) {
        languageCode = in.readString();
        languageTitle = in.readString();
    }

    public static final Creator<LanguageCustomModel> CREATOR = new Creator<LanguageCustomModel>() {
        @Override
        public LanguageCustomModel createFromParcel(Parcel in) {
            return new LanguageCustomModel(in);
        }

        @Override
        public LanguageCustomModel[] newArray(int size) {
            return new LanguageCustomModel[size];
        }
    };

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageTitle() {
        return languageTitle;
    }

    public void setLanguageTitle(String languageTitle) {
        this.languageTitle = languageTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(languageCode);
        dest.writeString(languageTitle);
    }
}

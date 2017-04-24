package frosteagle.com.yandextranslator.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteHistory extends RealmObject {

    @PrimaryKey
    private String firstWord;
    private String secondWord;
    private boolean favorite;
    private String locale;

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}

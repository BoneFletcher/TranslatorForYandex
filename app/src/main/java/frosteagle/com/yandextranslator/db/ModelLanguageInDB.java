package frosteagle.com.yandextranslator.db;

/**
 * Created by FrostEagle on 23.04.2017.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

public class ModelLanguageInDB {
    private long id;
    private String firstWord;
    private String secondWord;
    private int favorite;
    private String locale;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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



    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}

package frosteagle.com.yandextranslator.models.languageModel.languageTranslate;

import java.util.List;

public class TranslateModel {
    private int code;
    private List<String> text;
    private String lang;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}

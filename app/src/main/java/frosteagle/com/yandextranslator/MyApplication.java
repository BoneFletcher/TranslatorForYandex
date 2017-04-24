package frosteagle.com.yandextranslator;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by FrostEagle on 24.04.2017.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

public class MyApplication extends Application {
    public static int openFrag=0;
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(getBaseContext()).build();
        Realm.setDefaultConfiguration(config);
    }
}
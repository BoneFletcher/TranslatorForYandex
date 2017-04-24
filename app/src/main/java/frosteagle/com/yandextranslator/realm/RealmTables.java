package frosteagle.com.yandextranslator.realm;


import io.realm.Realm;

public  class RealmTables {

    public static void addToFavorite(Realm realm, String first, String second, String locale, boolean favorite){
        realm.beginTransaction();

        FavoriteHistory history = new FavoriteHistory();
        history.setFirstWord(first.trim());
        history.setSecondWord(second.trim());
        history.setLocale(locale);
        history.setFavorite(favorite);
        realm.copyToRealmOrUpdate(history);
        realm.commitTransaction();
    }
}

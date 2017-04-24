package frosteagle.com.yandextranslator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "historyTranslate.db";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_HISTORY_TRANSLATE = "historyTranslate";
    public static final String HISTORY_TRANSLATE_ID = "id";
    public static final String HISTORY_TRANSLATE_FIRST_WORD= "first_word";
    public static final String HISTORY_TRANSLATE_SECOND_WORD= "second_word";
    public static final String HISTORY_TRANSLATE_FAVORITE_IS= "favorite_is";

//    public static final String TABLE_FAVORITE_TRANSLATE = "favoriteTranslate";
//    public static final String HISTORY_FAVORITE_ID = "id";
//    public static final String HISTORY_FAVORITE_FIRST_WORD= "first_word";
//    public static final String HISTORY_FAVORITE_SECOND_WORD= "second_word";




    public DbHelper(Context context) {
        // конструктор суперкласса
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями

        db.execSQL("create table " + TABLE_HISTORY_TRANSLATE + " ("
                + HISTORY_TRANSLATE_ID + " integer primary key autoincrement,"
                + HISTORY_TRANSLATE_FIRST_WORD + " text,"
                + HISTORY_TRANSLATE_SECOND_WORD + " text"
                + HISTORY_TRANSLATE_FAVORITE_IS + " boolean" + ");");
//
//        db.execSQL("create table " + TABLE_FAVORITE_TRANSLATE + " ("
//                + HISTORY_FAVORITE_ID + " integer primary key autoincrement,"
//                + HISTORY_FAVORITE_FIRST_WORD + " text,"
//                + HISTORY_FAVORITE_SECOND_WORD + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (newVersion == 5 && oldVersion == 3) {
        db.execSQL("create table " + TABLE_HISTORY_TRANSLATE + " ("
                + HISTORY_TRANSLATE_ID + " integer primary key autoincrement,"
                + HISTORY_TRANSLATE_FIRST_WORD + " text,"
                + HISTORY_TRANSLATE_SECOND_WORD + " text"
                + HISTORY_TRANSLATE_FAVORITE_IS + " boolean" + ");");

//        db.execSQL("create table " + TABLE_FAVORITE_TRANSLATE + " ("
//                + HISTORY_FAVORITE_ID + " integer primary key autoincrement,"
//                + HISTORY_FAVORITE_FIRST_WORD + " text,"
//                + HISTORY_FAVORITE_SECOND_WORD + " text" + ");");
        }
    }
}

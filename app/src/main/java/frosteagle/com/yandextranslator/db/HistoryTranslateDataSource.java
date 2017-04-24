package frosteagle.com.yandextranslator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryTranslateDataSource {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
                    DbHelper.HISTORY_TRANSLATE_ID,
                    DbHelper.HISTORY_TRANSLATE_FIRST_WORD,
                    DbHelper.HISTORY_TRANSLATE_SECOND_WORD,
                    DbHelper.HISTORY_TRANSLATE_FAVORITE_IS
            };

    public HistoryTranslateDataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

//    public ModelLanguageInDB creteWord(HashMap<String, String> paramFavoriteForecaster) {
//        ContentValues values = new ContentValues();
//        for(Map.Entry<String, String> entry : paramFavoriteForecaster.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            values.put(key, value);
//        }
//        long insertId = database.insert(DbHelper.TABLE_HISTORY_TRANSLATE, null,
//                values);
//        Cursor cursor = database.query(DbHelper.TABLE_HISTORY_TRANSLATE,
//                allColumns, DbHelper.HISTORY_TRANSLATE_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        ModelLanguageInDB newFavoriteForecaster = cursorToHistory(cursor);
//        cursor.close();
//        return newFavoriteForecaster;
//    }


//    public void deleteWord(String userId) {
//        String selection = DbHelper.HISTORY_TRANSLATE_ID + " = ?";
//        String[] selectionArgs = new String[] {userId};
//        database.delete(DbHelper.TABLE_HISTORY_TRANSLATE, selection, selectionArgs);
//    }

    public ModelLanguageInDB createWord(ModelLanguageInDB word) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.HISTORY_TRANSLATE_FIRST_WORD, word.getFirstWord());
        values.put(DbHelper.HISTORY_TRANSLATE_SECOND_WORD, word.getSecondWord());
        values.put(DbHelper.HISTORY_TRANSLATE_FAVORITE_IS, word.getFavorite());
        long insertId = database.insert(dbHelper.TABLE_HISTORY_TRANSLATE, null, values);
        Cursor cursor = database.query(dbHelper.TABLE_HISTORY_TRANSLATE,
                allColumns, dbHelper.HISTORY_TRANSLATE_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ModelLanguageInDB newWord = cursorToHistory(cursor);
        cursor.close();

        return newWord;
    }

    public void deleteWord(ModelLanguageInDB word) {
        long id = word.getId();
        database.delete(DbHelper.TABLE_HISTORY_TRANSLATE, dbHelper.HISTORY_TRANSLATE_ID + " = " + id, null);
    }

    public List<ModelLanguageInDB> getAllWords() {
        List<ModelLanguageInDB> words = new ArrayList<>();

        Cursor cursor = database.query(dbHelper.TABLE_HISTORY_TRANSLATE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelLanguageInDB word = cursorToHistory(cursor);
            words.add(word);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return words;
    }



    private ModelLanguageInDB cursorToHistory(Cursor cursor) {
        ModelLanguageInDB history = new ModelLanguageInDB();
        history.setId(cursor.getLong(0));
        history.setFirstWord(cursor.getString(1));
        history.setSecondWord(cursor.getString(2));
        history.setFavorite(cursor.getInt(3));
        return history;
    }
}

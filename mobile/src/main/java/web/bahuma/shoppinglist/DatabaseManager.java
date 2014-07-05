package web.bahuma.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author bahuma
 */
public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ShoppingList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_SHOPS = "CREATE TABLE shops (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT " +
            "name TEXT NOT NULL" +
            ")";

    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE items (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT " +
            "shop INTEGER NOT NULL " +
            "name TEXT NOT NULL " +
            "bought BOOLEAN" +
            ")";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHOPS);
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

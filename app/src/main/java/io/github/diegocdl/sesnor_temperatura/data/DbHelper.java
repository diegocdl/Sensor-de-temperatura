package io.github.diegocdl.sesnor_temperatura.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import io.github.diegocdl.sesnor_temperatura.data.TemperatureDbContract.TempEntry;
/**
 * Created by diego on 6/14/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TempLog.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TempEntry.TABLE_NAME + " (" +
                    TempEntry._ID + " INTEGER PRIMARY KEY," +
                    TempEntry.COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                    TempEntry.COLUMN_TIME + TEXT_TYPE + COMMA_SEP +
                    TempEntry.COLUMN_VAL + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TempEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insert(TempRegister tr) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TempEntry.COLUMN_DATE, tr.getDate());
        values.put(TempEntry.COLUMN_TIME, tr.getTime());
        values.put(TempEntry.COLUMN_VAL, tr.getValue());
        return db.insert(TempEntry.TABLE_NAME, null, values);
    }

    public ArrayList<String> getDates() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT DISTINCT " + TempEntry.COLUMN_DATE + " FROM " + TempEntry.TABLE_NAME, new String[]{});
        ArrayList<String> res = new ArrayList<>();
        while (c.moveToNext())
            res.add(c.getString(c.getColumnIndex(TempEntry.COLUMN_DATE)));
        return res;
    }

    public ArrayList<TempRegister> getLogs(String date) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                TempEntry._ID,
                TempEntry.COLUMN_DATE,
                TempEntry.COLUMN_TIME,
                TempEntry.COLUMN_VAL
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = TempEntry.COLUMN_DATE + " = ?";
        String[] selectionArgs = { date };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                TempEntry.COLUMN_TIME + " DESC";

        Cursor c = db.query(
                TempEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        ArrayList<TempRegister> res = new ArrayList<>();
        while (c.moveToNext()) {
            String d = c.getString(c.getColumnIndex(TempEntry.COLUMN_DATE));
            String t = c.getString(c.getColumnIndex(TempEntry.COLUMN_TIME));
            String v = c.getString(c.getColumnIndex(TempEntry.COLUMN_VAL));
            res.add(new TempRegister(d, t, v));
        }
        return res;
    }
}

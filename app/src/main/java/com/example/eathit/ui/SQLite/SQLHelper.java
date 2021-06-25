package com.example.eathit.ui.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "MY_DATA";
    static final int DB_VERSION = 5;
    static final String TABLE_LIKED = "liked";
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;

    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_LIKED +
                "(id INTEGER NOT NULL PRIMARY KEY)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKED);
            onCreate(db);
        }
    }

    public void insertPostsLiked(int id) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
            contentValues.put("id", id);
            sqLiteDatabase.insert(TABLE_LIKED, null, contentValues);

    }


    public void deletePostLiked(int id) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_LIKED, "id=?", new String[]{id + ""});
    }

    public boolean checkExists(int id) {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_LIKED + " WHERE id=?", new String[]{id + ""});
        if (cursor.getCount() == 1) {
            return true;
        } else {
            return false;
        }
    }
}

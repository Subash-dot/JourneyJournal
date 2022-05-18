package com.example.journeyjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "JourneyJournal.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user_table (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE user_journal (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userID INTEGER," +
                "title TEXT," +
                "description LONGTEXT," +
                "latitude DOUBLE," +
                "longitude DOUBLE" +
                ")" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists question_table");

    }

    public Boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("user_table", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertJournal(int userID, String title, String description, Double latitude, Double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", userID);
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        long result = db.insert("user_journal", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor searchUserName(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from user_table where username = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username});
        return cursor;
    }

    public Cursor searchUserNameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from user_table where username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username, password});
        return cursor;
    }

    public Cursor viewAllJournal(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from user_journal where userID = ?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(userID)});
        return cursor;
    }

    public Cursor searchJournal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_journal where id = ?", new String[] {String.valueOf(id)});
        return cursor;
    }

    public Boolean updateJournal(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("description", description);
        Cursor cursor = db.rawQuery("Select * from user_journal where id = ?", new String[] {String.valueOf(id)});
        if(cursor.getCount()>0){
            long result = db.update("user_journal", contentValues, "id=?", new String[] {String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor searchUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_table where username = ? AND password = ?", new String[] {username, password});
        return cursor;
    }

    public Boolean deleteJournal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_journal where id = ?", new String[] {String.valueOf(id)});
        int a = cursor.getCount();
        if(cursor.getCount()>0) {
            long result = db.delete("user_journal", "id=?", new String[] {String.valueOf(id)});
            if(result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}

package br.ufrn.imd.laboratorios.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    public static final String DATABASE_NAME = "imd_labs.db";
    public static final int DATABASE_VERSION = 1;

    public static void registerUser(String email, Context context) {
        UserDBHelper dbHelper = new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserModel.UserEntry.COLUMN_EMAIL, email);

        long newUserId = sqLiteDatabase.insert(UserModel.UserEntry.TABLE_NAME, null, values);
    }

    public static boolean isUserRegistered(Context context) {
        UserDBHelper dbHelper = new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + UserModel.UserEntry.TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            return true;
        }

        return false;
    }

    public static boolean removeUserData(Context context) {
        UserDBHelper dbHelper = new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        sqLiteDatabase.execSQL("DELETE FROM " + UserModel.UserEntry.TABLE_NAME + ";");

        return true;
    }
}

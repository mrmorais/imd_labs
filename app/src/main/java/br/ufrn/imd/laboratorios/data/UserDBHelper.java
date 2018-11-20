package br.ufrn.imd.laboratorios.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + UserModel.UserEntry.TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY, " +
                    UserModel.UserEntry.COLUMN_EMAIL + " VARCHAR);";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + UserModel.UserEntry.TABLE_NAME + ";";

    public UserDBHelper(Context context) {
        super(context, Database.DATABASE_NAME, null, Database.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
    }
}

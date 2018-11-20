package br.ufrn.imd.laboratorios.data;

import android.provider.BaseColumns;

public final class UserModel {
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_EMAIL = "email";
    }
}

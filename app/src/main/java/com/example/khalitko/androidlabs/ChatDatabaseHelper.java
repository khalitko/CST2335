package com.example.khalitko.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AndroidLabs";
    public static final int VERSION_NUM = 3;
    public static final String TABLE_NAME = "AndroidTable";
    public static final String KEY_ID = "Id";
    public static final String KEY_MESSAGE = "Message";
    protected static final String SQLMSG = "ChatDatabaseHelper";

    public ChatDatabaseHelper(Context ctx) {

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_MESSAGE + " text not null);");
        Log.i(SQLMSG, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(SQLMSG, "Calling onUpgrade, oldVersion= " + oldVersion + "newVersion= "
                + newVersion);
    }

}

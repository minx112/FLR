package com.example.nicholasrocksvold.falloutliveradio;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mitchelldennen on 4/9/18.
 */

public class QuestDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "questdb.db";
    public QuestDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + QuestDB.QuestTable.NAME + "(" + "_id integer primary key autoincrement, " +
                QuestDB.QuestTable.Cols.timeClosed + ", " + QuestDB.QuestTable.Cols.currentQuestTime + ", " +
                QuestDB.QuestTable.Cols.questsDone + ", " + QuestDB.QuestTable.Cols.distances + ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

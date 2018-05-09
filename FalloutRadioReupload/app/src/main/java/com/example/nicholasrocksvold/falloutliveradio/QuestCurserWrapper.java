package com.example.nicholasrocksvold.falloutliveradio;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.nicholasrocksvold.falloutliveradio.Quest;
import com.example.nicholasrocksvold.falloutliveradio.QuestDB;

/**
 * Created by mitchelldennen on 4/12/18.
 */

public class QuestCurserWrapper extends CursorWrapper {
    public QuestCurserWrapper(Cursor cursor) {
        super(cursor);
    }
    public Quest getQuest() {
        //maybe set this as a a method in radio
        Integer currentQuest = getInt(getColumnIndex(QuestDB.QuestTable.Cols.currentQuest));
        Integer timeClosed = getInt(getColumnIndex(QuestDB.QuestTable.Cols.timeClosed));
        String questsDone = getString(getColumnIndex(QuestDB.QuestTable.Cols.questsDone));
        Integer currentQuestTime = getInt(getColumnIndex(QuestDB.QuestTable.Cols.currentQuestTime));
        String gender = getString(getColumnIndex(QuestDB.QuestTable.Cols.gender));
        Integer lastTheater = getInt(getColumnIndex(QuestDB.QuestTable.Cols.lastTheater));
        Integer currentQuestLength = getInt(getColumnIndex(QuestDB.QuestTable.Cols.currentQuestLength));
        //String distances = getString(getColumnIndex(QuestDB.QuestTable.Cols.distances));

        Quest quest = new Quest();
        quest.setCurrentQuest(currentQuest);
        quest.setTimeClosed(timeClosed);
        quest.setQuestsDone(questsDone);
        quest.setCurrentQuestTime(currentQuestTime);
        quest.set  //gender is in wanderer and the rest is not in quest
        //quest.setDistances(distances);
        return quest;
    }
}
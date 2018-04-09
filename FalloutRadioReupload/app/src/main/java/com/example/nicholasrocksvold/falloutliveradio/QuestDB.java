package com.example.nicholasrocksvold.falloutliveradio;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mitchelldennen on 4/9/18.
 */

public class QuestDB {
    public static final class QuestTable {
        public static final String NAME = "quests";
        public static final class Cols {
            //not sure iff all these have to be converted to strings
            public static final Date timeClosed = new Date();
            public static final ArrayList<Integer> questsDone = new ArrayList<>();
            public static final long currentQuestTime = 0;
            public static final ArrayList<Float> distances = new ArrayList<>();
        }
    }
}

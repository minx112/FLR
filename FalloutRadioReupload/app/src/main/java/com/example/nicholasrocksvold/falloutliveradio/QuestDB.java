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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
            public static final Integer currentQuest = 0;
            public static final Integer timeClosed = 0;// date convert to date
=======
            public static final String currentQuest = "currentquest";
            public static final String timeClosed = "timeclosed";// date convert to date
>>>>>>> parent of 97b722a... newest
=======
            public static final String currentQuest = "currentquest";
            public static final String timeClosed = "timeclosed";// date convert to date
>>>>>>> parent of 97b722a... newest
            public static final String questsDone = "questsdone";// make string, seperate "ints" by coma, parse string to get sub strings of quests done
            public static final Integer currentQuestTime = 0; // keep track in miliseconds
            public static final String gender = "gender";
            public static final Integer lastTheater = 0;
            public static final Integer currentQuestLength = 0;

            //public static final String distances = "distances";// same as up above do i need this??

=======
            //not sure iff all these have to be converted to strings
            public static final Date timeClosed = new Date();
            public static final ArrayList<Integer> questsDone = new ArrayList<>();
            public static final long currentQuestTime = 0;
            public static final ArrayList<Float> distances = new ArrayList<>();
>>>>>>> parent of bc22710... stuff
        }
    }
}

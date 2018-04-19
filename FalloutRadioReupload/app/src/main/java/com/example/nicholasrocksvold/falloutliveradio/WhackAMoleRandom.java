package com.example.nicholasrocksvold.falloutliveradio;
import java.util.ArrayList;
import java.util.Random;

public class WhackAMoleRandom {

    public int WAMR(Song[] priority)
    {
        int total = 0;

        for(int i = 0; i < priority.length; i++)
            total += priority[i].getPriority();

        Random rand = new Random();
        int decider = rand.nextInt(total);

        int i = -1;
        while(decider >= 0)
        {
            i++;
            decider -= priority[i].getPriority();
        }

        return i;
    }

    public int WAMR(ArrayList<Song[]> priority)
    {
        int total = 0;

        for(int i = 0; i < priority.size()-1; i++)
            total += priority.get(i)[0].getPriority();

        Random rand = new Random();
        int decider = rand.nextInt(total);

        int i = 0;
        while(decider > 0)
        {
            decider -= priority.get(i)[0].getPriority();
            i++;
        }

        return i;
    }

    public void alterPriority(Song[] priority, int chosen)
    {
        for(int i = 0; i < priority.length; i++)
        {
            if(i == chosen)
                priority[i].setPriority(0);
            else
                priority[i].setPriority(priority[i].getPriority()+1);
        }
    }

    public void alterPriority(ArrayList<Song[]> priority, int chosen)
    {
        for(int i = 0; i < priority.size()-1; i++)
        {
            if(i == chosen)
                priority.get(i)[0].setPriority(0);
            else
                priority.get(i)[0].setPriority(priority.get(i)[0].getPriority()+1);
        }
    }
}

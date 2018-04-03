package com.example.nicholasrocksvold.falloutliveradio;
import java.util.ArrayList;
import java.util.Random;

public class WhackAMoleRandom {

    public int WAMR(int[] priority)
    {
        int total = 0;

        for(int i = 0; i < priority.length; i++)
            total += priority[i];

        Random rand = new Random();
        int decider = rand.nextInt(total);

        int i = 0;
        while(decider > 0)
        {
            decider -= priority[i];
            i++;
        }

        if(priority[i] == 0)
            i++;

        return i;
    }

    public int WAMR(ArrayList<Integer> priority)
    {
        int total = 0;

        for(int i = 0; i < priority.size(); i++)
            total += priority.get(i);

        Random rand = new Random();
        int decider = rand.nextInt(total);

        int i = 0;
        while(decider > 0)
        {
            decider -= priority.get(i);
            i++;
        }

        return i;
    }

    public int[] alterPriority(int[] priority, int chosen)
    {
        for(int i = 0; i < priority.length; i++)
        {
            if(i == chosen)
                priority[i] = 0;
            else
                priority[i] += 1;
        }

        return priority;
    }

    public ArrayList<Integer> alterPriority(ArrayList<Integer> priority, int chosen)
    {
        for(int i = 0; i < priority.size(); i++)
        {
            if(i == chosen)
                priority.set(i, 0);
            else
                priority.set(i, priority.get(i) + 1);
        }

        return priority;
    }
}

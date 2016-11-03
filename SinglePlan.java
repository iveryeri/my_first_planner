package com.ilgarveryeri;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Ilgar on 03-Nov-16.
 */
public class SinglePlan implements Serializable, Comparable<SinglePlan> {
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    String planName;

    public SinglePlan(String name, int year, int month, int day, int h1, int m1, int h2, int m2){
        start.set(year, month, day, h1, m1);
        end.set(year, month, day, h2, m2);
        planName = name;
    }
    public int compareTo(SinglePlan comparedPlan){
        return (start.getTime().compareTo(comparedPlan.getStart().getTime()));
    }

    public String getName(){
        return planName;
    }
    public Calendar getStart(){
        return start;
    }
    public Calendar getEnd(){
        return end;
    }

}
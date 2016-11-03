package com.ilgarveryeri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Ilgar on 02-Nov-16.
 */
public class MyPlans implements Serializable {
    ArrayList<SinglePlan> pList = new ArrayList<SinglePlan>();

    public void addPlan(SinglePlan newPlan){
        pList.add(newPlan);
        Collections.sort(pList);
    }
    public void getSchedule(){
        System.out.println("You have " + getNumberOfPlans() + " plan(s) in total.");
        //System.out.println("   PLAN NAME:\t\t\tDATE:\t\tHOUR:");
        for(int i = 0; i< pList.size(); i++){
            writeSinglePlan(i);
        }
    }
    public void getDaily(){
        System.out.println("You have " + getNumberOfDailyPlans() + " plan(s) for today.");
        //System.out.println("   PLAN NAME:\t\t\tDATE:\t\tHOUR:");
        for(int i = 0; i< getNumberOfDailyPlans(); i++){
            writeSinglePlan(i);
        }
    }
    public int getNumberOfPlans(){
        return pList.size();
    }
    public int getNumberOfDailyPlans(){
        Calendar now = Calendar.getInstance();
        int pNum = 0;
        for(int i = 0; i< pList.size(); i++){
            if((pList.get(i).getStart().get(Calendar.YEAR) == now.get(Calendar.YEAR)) && (pList.get(i).getStart().get(Calendar.MONTH) == now.get(Calendar.MONTH)) && (pList.get(i).getStart().get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH))){
                pNum++;
            }
        }
        return pNum;
    }

    public void writeSinglePlan(int i){
        String h1 = String.format("%02d",pList.get(i).getStart().get(Calendar.HOUR_OF_DAY));
        String h2 = String.format("%02d",pList.get(i).getEnd().get(Calendar.HOUR_OF_DAY));
        String m1 = String.format("%02d",pList.get(i).getStart().get(Calendar.MINUTE));
        String m2 = String.format("%02d",pList.get(i).getEnd().get(Calendar.MINUTE));
        System.out.print((i+1) + ". " + pList.get(i).getName() + ": \t\t " + pList.get(i).getStart().get(Calendar.YEAR));
        System.out.print("/"+ pList.get(i).getStart().get(Calendar.MONTH) +"/"+ pList.get(i).getStart().get(Calendar.DAY_OF_MONTH));
        System.out.println("\t "+h1+":"+m1 + "-" + h2+":"+m2);
    }

    public int checkCoincide(SinglePlan checkPlan){
        int index = -1;
        for(int i = 0; i< pList.size(); i++){
            if(pList.get(i).getStart().compareTo(checkPlan.getStart()) > 0){
                if(pList.get(i-1).getEnd().compareTo(checkPlan.getStart()) > 0){
                    index = i-1;
                } else if(checkPlan.getEnd().compareTo(pList.get(i).getStart()) > 0){
                    index = i;
                }
                break;
            }
        }
        return (index);
    }
}
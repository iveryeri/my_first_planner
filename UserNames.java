package com.ilgarveryeri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ilgar on 02-Nov-16.
 */
public class UserNames implements Serializable {
    ArrayList<String> nameList = new ArrayList<String>();

    public boolean containsUserName(String checkUser){
        return (nameList.contains(checkUser));
    }
    public void addUserName(String newName){
        nameList.add(newName);
    }
    public void getUserNames(){
        for(int i=0; i<nameList.size(); i++){
            System.out.println(nameList.get(i));
        }
    }
}
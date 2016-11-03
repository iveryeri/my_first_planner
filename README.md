package com.ilgarveryeri;

import java.io.*;
import java.util.*;

public class MyDailyPlanner implements Serializable {
    String userName;
    UserNames users;
    MyPlans myPlans;
    SinglePlan newPlan;

    String pName;
    int pYear;
    int pMonth;
    int pDay;
    int pStartHour;
    int pEndHour;
    int pStartMin;
    int pEndMin;

    static MyDailyPlanner myPlanner = new MyDailyPlanner();

    public static void main(String[] args) {
        myPlanner.go();
    }

    public void go(){
        File checkFile = new File("UserNames.ser");
        if(checkFile.exists() && !checkFile.isDirectory()){
            loadUsers();
        }
        else {
            users = new UserNames();
            saveUsers();
        }

        System.out.print("Please enter your name: ");
        myPlanner.askName();
        //Get Absolute Path
        //File f = new File(".");
        //System.out.println(f.getAbsolutePath());

        //Ask the user name & Print the current user options accordingly:
    }

    //Print the EXISTING USER Main Menu Options & Go to the selected one:
    public void oldUserMenuOptions(){
        String selection = " ";
        selection = myPlanner.getUserInput("1.New Entry \n2.See my schedule \n3.See my daily plans \n4.Change User \n5.Exit ");
        if (selection.equals("1")){
            newPlanEntry();
        } else if(selection.equals("2")){
            myPlans.getSchedule();
            System.out.println("\nWhat else would you like to do now?");
            oldUserMenuOptions();
        } else if(selection.equals("3")){
            int numDaily = myPlans.getNumberOfDailyPlans();
            if(numDaily == 0){
                String input = " ";
                input = myPlanner.getUserInput("\nYou don't have any plans for today, want to add any? (Y/N):");
                if(input.equals("Y") || input.equals("y") || input.equals("yes") || input.equals("")){
                    newPlanEntry();
                } else {
                    System.out.println("What else would you like to do now?");
                    oldUserMenuOptions();
                }
            } else {
                myPlans.getDaily();
                System.out.println("\nWhat else would you like to do now?");
                oldUserMenuOptions();
            }


        } else if(selection.equals("4")){
            saveUsers();
            savePlans();
            System.out.println("\nGoodbye "+userName+"\n(Session saved)\n====================================\n\nNEW USER LOGIN:\n\n");
            myPlanner.go();
        } else if(selection.equals("5")){
            closeApplication();
        } else {
            System.out.println("Please select a number between 1 and 5:\n");
            myPlanner.oldUserMenuOptions();
        }
    }
    //Print the NEW USER Main Menu Options & Go to the selected one:
    public void newUserMenuOptions(){
        String selection = " ";
        selection = myPlanner.getUserInput("1.New Entry \n2.Change User \n3.Exit ");
        if (selection.equals("1")){
            newPlanEntry();
        } else if(selection.equals("2")){
            saveUsers();
            savePlans();
            System.out.println("\nGoodbye "+userName+"\n(Session saved)\n====================================\n\nNEW USER LOGIN:\n\n");
            myPlanner.go();
        } else if(selection.equals("3")){
            closeApplication();
        } else {
            System.out.println("Please select a number between 1 and 3:\n");
            myPlanner.newUserMenuOptions();
        }
    }
    //Asking and saving the details of a new plan:
    public void newPlanEntry(){
        System.out.print("\nPlease name your plan: \n(Write \"menu\" to go back to the Main Menu / \"exit\" to close the Application)");
        askPlan();
        System.out.println("-> Your plan: " + pName);

        System.out.print("\nPlease enter a year:");
        askYear();
        System.out.println("-> Selected year: " + pYear);

        System.out.print("\nPlease enter a month:");
        askMonth();
        System.out.println("-> Selected month: " + (pMonth+1));

        System.out.print("\nPlease enter the day of the plan:");
        askDay();
        System.out.println("-> Selected day: " + pDay);

        System.out.println("\nPlease enter the start time:");
        askStartTime();
        System.out.println("-> Start time: " + pStartHour + ":" + pStartMin);

        System.out.println("\nPlease enter the end time:");
        askEndTime();
        System.out.println("-> End time: " + pEndHour + ":" + pEndMin);

        ///////////////////////// CHECK OVERLAPPING PRIOR TO ADDING THE PLAN /////////////////////////

        newPlan = new SinglePlan(pName, pYear, pMonth, pDay, pStartHour, pStartMin, pEndHour, pEndMin);

        int ifCoincident = -1;
        if (myPlans.getNumberOfPlans() != 0){
            ifCoincident = myPlans.checkCoincide(newPlan);
        }
        if (ifCoincident == -1) {
            myPlans.addPlan(newPlan);
            System.out.println("\nRECENTLY ADDED PLAN -> " + pName + ": " + pYear+"/"+pMonth+"/"+pDay + " \t " + pStartHour+":"+pStartMin + "-" + pEndHour+":"+pEndMin);
        }
        else {
            System.out.println("Sorry, the plan you've entered coincides with this one:");
            myPlans.writeSinglePlan(ifCoincident);
            newPlan = null;
            //System.out.println("\nPlease choose another date/time for your plan");
            //sadece yeni tarih gir / eskisini silmek ister misin? / cancel the last entry
        }

        //////////////////////////////////////////////////////////////////////////////////////////////

        String input = " ";
        input = myPlanner.getUserInput("\nDo you want to add another plan? (Y/N):");
        if(input.equals("Y") || input.equals("y") || input.equals("yes") || input.equals("")){
            newPlanEntry();
        } else {
            System.out.println("What else would you like to do now?");
            oldUserMenuOptions();
        }
    }

    public void askName(){
        userName = myPlanner.getUserInput("");

        if(userName.equals("")){
            System.out.print("Please type your name below:");
            myPlanner.askName();
        } else {
            if(users.containsUserName(userName)){
                loadPlans();
                System.out.println("Welcome back, " + userName + "\n\nWhat would you like to do?");
                if(myPlans.getNumberOfPlans() == 0){
                    newUserMenuOptions();
                } else {
                    oldUserMenuOptions();
                }
            } else{
                users.addUserName(userName);
                myPlans = new MyPlans();
                System.out.println("Hello " + userName + ", nice to meet you\n\nWhat would you like to do?");
                newUserMenuOptions();
            }
        }
    }
    public void askPlan(){
        String input = " ";
        input = myPlanner.getUserInput("");
        if (input.equals("menu")){
            oldUserMenuOptions();
        } else if(input.equals("exit")){
            closeApplication();
        } else if(input.equals("")){
            System.out.println("Please enter a name for your plan:");
            askPlan();
        } else {
            pName = input;
        }
    }
    public void askYear(){
        Calendar now = Calendar.getInstance();
        int year = 0;
        String input = " ";
        input = myPlanner.getUserInput("");
        if (input.equals("menu")){
            oldUserMenuOptions();
        } else if(input.equals("exit")){
            closeApplication();
        } else if(input.equals("")){
            pYear = now.get(Calendar.YEAR);
        } else {
            try {
                year = Integer.parseInt(input);
                if(year < now.get(Calendar.YEAR)){
                    System.out.println("That date has already passed, please enter a valid year:\n(Press ENTER for this year / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                    askYear();
                } else if(year > (now.get(Calendar.YEAR)+100)){
                    System.out.println("You probably won't be around at that date, please enter a realistic year:\n(Press ENTER for this year / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                    askYear();
                } else {
                    pYear = year;
                }
            } catch (NumberFormatException e){
                System.out.println("Please enter the year in 4 digit format (eg. 1995): \n(Press ENTER for this year / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                askYear();
            }
        }
    }
    public void askMonth(){
        Calendar now = Calendar.getInstance();
        int month =0;
        String input = " ";
        input = myPlanner.getUserInput("");
        if (input.equals("menu")){
            oldUserMenuOptions();
        } else if(input.equals("exit")){
            closeApplication();
        } else if(input.equals("")){
            pMonth = now.get(Calendar.MONTH);
        } else {
            try {
                month = Integer.parseInt(input)-1;
                if (month < 1){
                    System.out.println("Please enter a positive number between 1 and 12:\n(Press ENTER for current month / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                    askMonth();
                } else if(month > 12){
                    System.out.println("There are only 12 months in a year, please enter a valid one:\n(Press ENTER for current month / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                    askMonth();
                } else if(pYear == now.get(Calendar.YEAR) && month < now.get(Calendar.MONTH)){
                    System.out.println("That date has already passed, please enter a valid month:\n(Press ENTER for current month / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                    askMonth();
                } else {
                    pMonth = month;
                }
            } catch (NumberFormatException e){
                System.out.println("Please enter the month in the specified format (eg. 1) (eg. 12): \n(Press ENTER for current month / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                askMonth();
            }
        }
    }
    public void askDay(){
        Calendar now = Calendar.getInstance();
        Calendar planDate = Calendar.getInstance();
        planDate.set(pYear, pMonth, 1);
        int day =0;
        String input = " ";
        input = myPlanner.getUserInput("");
        if (input.equals("menu")){
            oldUserMenuOptions();
        } else if(input.equals("exit")){
            closeApplication();
        } else if(input.equals("")){
            pDay = now.get(Calendar.DAY_OF_MONTH);
            if(pDay > planDate.getActualMaximum(Calendar.DAY_OF_MONTH)){
                pDay = planDate.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
        } else {
            try {
                day = Integer.parseInt(input);
                if (day < 1){
                    System.out.println("Please enter a positive number between 1 and " + planDate.getActualMaximum(Calendar.DAY_OF_MONTH) + ":\n(Press ENTER for current day / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                    askDay();
                } else if(day > planDate.getActualMaximum(Calendar.DAY_OF_MONTH)){
                    System.out.println("That month has " + planDate.getActualMaximum(Calendar.DAY_OF_MONTH) + " days, please enter a valid day:\n(Press ENTER for current day / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                    askDay();
                } else if(pYear == now.get(Calendar.YEAR) && pMonth == now.get(Calendar.MONTH) && day < now.get(Calendar.DAY_OF_MONTH)){
                    System.out.println("That day has already passed, please enter a valid day:\n(Press ENTER for current day / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                    askDay();
                } else {
                    pDay = day;
                }
            } catch (NumberFormatException e){
                System.out.println("Please enter the day in the specified format (eg. 1) (eg. 30):\n(Press ENTER for current day / write \"menu\" for the Main Menu / \"exit\" to close the Application)");
                askDay();
            }
        }
    }
    public void askStartTime(){
        int hour = 0;
        int min = 0;
        String input = " ";
        input = myPlanner.getUserInput("");
        if (input.equals("menu")){
            oldUserMenuOptions();
        } else if(input.equals("exit")){
            closeApplication();
        } else if(input.equals("")){
            System.out.println("Please enter a start hour in 24h format: (eg. 18:30 or 18.30)");
            askStartTime();
        } else {
            String inputSplit[] = input.split(":");
            if (inputSplit.length != 2){
                inputSplit = input.split("\\.");
            }
            if (inputSplit.length != 2){
                System.out.println("Invalid format. Please enter a start hour in 24h format: (eg. 18:30 or 18.30)");
                askStartTime();
            } else {
                try {
                    hour = Integer.parseInt(inputSplit[0]);
                    if (hour < 0 || hour > 23){
                        System.out.println("Invalid hour format. Please enter a time between 00:00 and 23:59");
                        askStartTime();
                    } else {
                        try {
                            min = Integer.parseInt(inputSplit[1]);
                            if (min < 0 || min > 59){
                                System.out.println("Invalid minute format. Please enter a time between 00:00 and 23:59");
                                askStartTime();
                            } else {
                                pStartHour = hour;
                                pStartMin = min;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid format. Please enter a start hour in 24h format: (eg. 18:30 or 18.30)");
                            askStartTime();
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format. Please enter a start hour in 24h format: (eg. 18:30 or 18.30)");
                    askStartTime();
                }
            }
        }
    }
    public void askEndTime(){
        int hour = 0;
        int min = 0;
        String input = " ";
        input = myPlanner.getUserInput("");
        if (input.equals("menu")){
            oldUserMenuOptions();
        } else if(input.equals("exit")){
            closeApplication();
        } else if(input.equals("")){
            System.out.println("Please enter a start hour in 24h format: (eg. 18:30 or 18.30)");
            askEndTime();
        } else {
            String inputSplit[] = input.split(":");
            if (inputSplit.length != 2){
                inputSplit = input.split("\\.");
            }
            if (inputSplit.length != 2){
                System.out.println("Invalid format. Please enter a start hour in 24h format: (eg. 18:30 or 18.30)");
                askEndTime();
            } else {
                try {
                    hour = Integer.parseInt(inputSplit[0]);
                    if (hour < 0 || hour > 23){
                        System.out.println("Invalid hour format. Please enter a time between 00:00 and 23:59");
                        askEndTime();
                    } else if (hour < pStartHour){
                        System.out.println("Please select an end time later than the start time ("+pStartHour+":"+pStartMin+")");
                        askEndTime();
                    } else {
                        try {
                            min = Integer.parseInt(inputSplit[1]);
                            if (min < 0 || min > 59){
                                System.out.println("Invalid minute format. Please enter a time between 00:00 and 23:59");
                                askEndTime();
                            } else if (hour == pStartHour && min <= pStartMin) {
                                System.out.println("Please select an end time later than the start time ("+pStartHour+":"+pStartMin+")");
                                askEndTime();
                            } else {
                                pEndHour = hour;
                                pEndMin = min;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid format. Please enter a start hour in 24h format: (eg. 18:30 or 18.30)");
                            askEndTime();
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format. Please enter a start hour in 24h format: (eg. 18:30 or 18.30)");
                    askEndTime();
                }
            }
        }
    }

    //Deserialize the UserNames Class:
    public void loadUsers(){
        try {
            FileInputStream userNamesFile = new FileInputStream("UserNames.ser");
            ObjectInputStream ois = new ObjectInputStream(userNamesFile);
            Object temp = ois.readObject();
            users = (UserNames) temp;
            ois.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //Serialize the UserNames Class:
    public void saveUsers(){
        try {
            FileOutputStream userNamesFile = new FileOutputStream("UserNames.ser");
            ObjectOutputStream oos = new ObjectOutputStream(userNamesFile);
            oos.writeObject(users);
            oos.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    //Deserialize the MyPlans Class:
    public void loadPlans(){
        try {
            FileInputStream userPlansFile = new FileInputStream(userName + ".ser");
            ObjectInputStream ois = new ObjectInputStream(userPlansFile);
            Object temp = ois.readObject();
            myPlans = (MyPlans) temp;
            ois.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //Serialize the MyPlans Class:
    public void savePlans(){
        try {
            FileOutputStream userPlansFile = new FileOutputStream(userName + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(userPlansFile);
            oos.writeObject(myPlans);
            oos.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    //Print the command and take the user input:
    public String getUserInput(String message){
        String inputLine = null;
        System.out.println(message);
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            inputLine = input.readLine();
            if (inputLine.length() == 0){
                return "";
            }
        }catch(IOException ex){
            return "";
        }
        return inputLine;
    }

    //Serialize my plans and exits the application:
    public void closeApplication(){
        saveUsers();
        savePlans();
        System.exit(0);
    }


}

package com.example.kiki.timemanagementtask;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Goal implements Parcelable {
    private String goalName ;
    private String ddl ;
    private String goalType ;
    private String startDate ;
    private ArrayList<SubGoal> subGoals = new ArrayList<>();

    public Goal() {
    }

    public Goal(String goalName, String ddl, String goalType, String startDate) {
        this.goalName = goalName;
        this.ddl = ddl;
        this.goalType = goalType;
        this.startDate = startDate;

    }


    protected Goal(Parcel in) {
        goalName = in.readString();
        ddl = in.readString();
        goalType = in.readString();
        startDate = in.readString();
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };

    public ArrayList<SubGoal> getSubGoals() {
        return subGoals;
    }

    public String getGoalName() {
        return goalName;
    }

    public String getDdl() {
        return ddl;
    }

    public String getGoalType() {
        return goalType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void addSubgoal(SubGoal subGoal){
        subGoals.add(subGoal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goalName);
        dest.writeString(ddl);
        dest.writeString(goalType);
        dest.writeString(startDate);
    }

    public static String computeHowManyDaysLeft(String startDate, String ddl){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date end = sdf.parse(ddl);
            long diff = end.getTime() - (new Date()).getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+" days left";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}

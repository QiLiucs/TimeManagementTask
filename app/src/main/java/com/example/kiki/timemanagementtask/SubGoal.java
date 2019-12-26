package com.example.kiki.timemanagementtask;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SubGoal implements Parcelable {
    private String subgoal;
    private String ddl;
    private String duration;
    private String difficulty;
    private String startDate;

    public SubGoal() {
    }

    public SubGoal(String subgoal, String ddl, String duration, String difficulty, String startDate) {
        this.subgoal = subgoal;
        this.ddl = ddl;
        this.duration = duration;
        this.difficulty = difficulty;
        this.startDate = startDate;
    }

    protected SubGoal(Parcel in) {
        subgoal = in.readString();
        ddl = in.readString();
        duration = in.readString();
        difficulty = in.readString();
        startDate = in.readString();
    }

    public static final Creator<SubGoal> CREATOR = new Creator<SubGoal>() {
        @Override
        public SubGoal createFromParcel(Parcel in) {
            return new SubGoal(in);
        }

        @Override
        public SubGoal[] newArray(int size) {
            return new SubGoal[size];
        }
    };

    public String getSubgoal() {
        return subgoal;
    }

    public void setSubgoal(String subgoal) {
        this.subgoal = subgoal;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subgoal);
        dest.writeString(ddl);
        dest.writeString(duration);
        dest.writeString(difficulty);
        dest.writeString(startDate);
    }

}

package com.example.mrboomba.codelax;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrboomba on 18/12/2559.
 */

public class Match {
    @SerializedName("username")
    String username;
    @SerializedName("id")
    String id;

    @SerializedName("rival")
    String rival;
    @SerializedName("num1")
    int num1;
    @SerializedName("num2")
    int num2;
    @SerializedName("num3")
    int num3;
    @SerializedName("num4")
    int num4;
    @SerializedName("num5")
    int num5;
    @SerializedName("ans1")
    String ans1;
    @SerializedName("ans2")
    String ans2;
    @SerializedName("ans3")
    String ans3;
    @SerializedName("ans4")
    String ans4;
    @SerializedName("ans5")
    String ans5;
    @SerializedName("point")
    int point;
    @SerializedName("userPoint")
    int userPoint;
    @SerializedName("rivalPoint")
    int rivalPoint;
    @SerializedName("userAnswer")
    String[] userAnswer;
    @SerializedName("rivalAnswer")
    String[] rivalAnswer;
    @SerializedName("challenge")
    int[] challenge;


    Match(String username,String rival,int num[],String[] ans,int point){
        this.username = username;
        this.rival = rival;
        num1 = num[0];
        num2 = num[1];
        num3 = num[2];
        num4 = num[3];
        num5 = num[4];
        ans1 = ans[0];
        ans2 = ans[1];
        ans3 = ans[2];
        ans4 = ans[3];
        ans5 = ans[4];
        this.point = point;
    }
    Match(String username,String rival,String[] ans,int point){
        this.username = username;
        this.rival = rival;
        ans1 = ans[0];
        ans2 = ans[1];
        ans3 = ans[2];
        ans4 = ans[3];
        ans5 = ans[4];
        this.point = point;
    }
    Match(String username,String rival){
        this.username = username;
        this.rival = rival;
    }

    public String getUsername() {
        return username;
    }

    public int[] getQuestion() {
        return challenge;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public int getRivalPoint() {
        return rivalPoint;
    }

    public int[] getChallenge() {
        return challenge;
    }

    public String[] getUserAnswer() {
        return userAnswer;
    }

    public String[] getRivalAnswer() {
        return rivalAnswer;
    }
}

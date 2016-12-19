package com.example.mrboomba.codelax;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrboomba on 12/12/2559.
 */

public class User {



    @SerializedName("lesson")
    int lesson;
    @SerializedName("challenge")
    int chalenge;
    @SerializedName("complete")
    String[] complete;
    @SerializedName("friend")
    String[] freind;
    @SerializedName("match")
    String[] match;
    @SerializedName("message")
    String message;
    @SerializedName("name")
    String name;
    @SerializedName("user")
    String user;

    public User() {

    }
    public User(String user) {
this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public int getChalenge() {
        return chalenge;
    }

    public int getLesson() {
        return lesson;
    }

    public String[] getFreind() {
        return freind;
    }

    public String[] getMatch() {
        return match;
    }

    public String getName() {
        return name;
    }

    public String[] getComplete() {
        return complete;
    }

    public String getUser() {
        return user;
    }
}

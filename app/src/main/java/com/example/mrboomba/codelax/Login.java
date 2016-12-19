package com.example.mrboomba.codelax;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrboomba on 12/12/2559.
 */

public class Login {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    @SerializedName("message")
    String message;
    public Login(String username, String password ) {
        this.username = username;
        this.password = password;
    }

    public String getMessage() {
        return message;
    }
    public String getUsername() {
        return username;
    }
}
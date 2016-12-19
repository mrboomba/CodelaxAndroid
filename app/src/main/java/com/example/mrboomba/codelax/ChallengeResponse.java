package com.example.mrboomba.codelax;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrboomba on 11/12/2559.
 */

public class ChallengeResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("image")
    private Image img;
    @SerializedName("question")
    private String question;
    @SerializedName("fake")
    private String[] fake;
    @SerializedName("answer")
    private String ans;

    public String getMessage() {
        return message;
    }

    public Image getImg() {
        return img;
    }

    public String[] getFake() {
        return fake;
    }

    public String getAns() {
        return ans;
    }

    public String getQuestion() {
        return question;
    }
}

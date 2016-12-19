package com.example.mrboomba.codelax;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrboomba on 10/12/2559.
 */

public class LessonResponse {
    @SerializedName("name")
    private String[] name;
    @SerializedName("message")
    private String message;
    @SerializedName("image")
    private Image img;
    @SerializedName("question")
    private String[] question;
    @SerializedName("fake")
    private String[] fake;
    @SerializedName("ans")
    private String[] ans;

    public Image getVideo() {
        return img;
    }

    public String getMessage() {
        return message;
    }

    public Image getImg() {
        return img;
    }

    public String[] getName() {
        return name;
    }

    public String[] getAns() {
        return ans;
    }

    public String[] getFake() {
        return fake;
    }

    public String[] getQuestion() {
        return question;
    }
}

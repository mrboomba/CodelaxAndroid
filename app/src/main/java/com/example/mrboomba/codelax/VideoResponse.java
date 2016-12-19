package com.example.mrboomba.codelax;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrboomba on 8/12/2559.
 */

public class VideoResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("video")
    private String video;

    public String getVideo() {
        return video;
    }

    public String getMessage() {
        return message;
    }


}

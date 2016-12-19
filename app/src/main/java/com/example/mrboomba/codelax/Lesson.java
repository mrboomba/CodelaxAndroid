package com.example.mrboomba.codelax;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mrboomba on 6/12/2559.
 */

public class Lesson implements Parcelable {
    String[] Question;
    String[][] fake;
    String[] correct;
    Image image;

    protected Lesson(Parcel in) {
        Question = in.createStringArray();
        correct = in.createStringArray();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(Question);
        parcel.writeStringArray(correct);
    }

}

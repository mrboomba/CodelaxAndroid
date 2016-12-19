package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lesson2Activity extends AppCompatActivity implements LessonAdapter.Close, Callback {
    ListView lv;
    static Button but;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson2);

        num = (int) getIntent().getSerializableExtra("lesson");


        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<LessonResponse> call = apiService.getLesson(num);

        call.enqueue(this);


    }


    @Override
    public void close(boolean input) {
        if (input)
            finishWithResult("true");
        else
            finishWithResult("false");
    }

    @Override
    public void onResponse(Call call, Response response) {
        Response<LessonResponse> response2 = (Response<LessonResponse>) response;
        but = (Button) findViewById(R.id.button1);
        but.setVisibility(View.INVISIBLE);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new LessonAdapter(getBaseContext(), response2.body().getQuestion(), response2.body().getAns(), response2.body().getFake(), num, this));
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Toast.makeText(getBaseContext(), "Please check your connection!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void finishWithResult(String input) {
        Bundle conData = new Bundle();
        conData.putString("results", input);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }
}

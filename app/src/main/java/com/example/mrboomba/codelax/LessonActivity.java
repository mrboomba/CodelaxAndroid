package com.example.mrboomba.codelax;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonActivity extends AppCompatActivity implements View.OnClickListener {

    String youtubeTag = "";
    final String TAG = "MrBoomba";
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        num = (int) getIntent().getSerializableExtra("lesson");

        num++;
        Log.d(TAG, "onCreate: " + num);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<VideoResponse> call = apiService.getMovie(num);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                youtubeTag = response.body().getVideo();
                Bundle bundle = new Bundle();
                bundle.putString("playid", youtubeTag);
                YoutubeFragment fragment = new YoutubeFragment();
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.youtube_view, fragment)
                        .commit();
                Log.d(TAG, "onResponse: " + youtubeTag);

            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getBaseContext(), "Please check your connection!", Toast.LENGTH_LONG).show();
                finish();

            }
        });


        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        Button test = (Button) findViewById(R.id.test);
        test.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.getBaseContext(), Lesson2Activity.class);
        intent.putExtra("lesson", num);
        startActivityForResult(intent, 90);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: enter");
        switch (requestCode) {

            case 90:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String result = res.getString("results");
                    if (result != null)
                        if (result.equals("true")) {
                            finish();
                        }
                }
                break;
        }
    }
}

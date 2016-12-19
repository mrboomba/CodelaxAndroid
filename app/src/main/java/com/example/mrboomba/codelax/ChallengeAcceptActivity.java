package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrboomba on 19/12/2559.
 */

public class ChallengeAcceptActivity extends AppCompatActivity {

    private Button[] choice;
    private boolean[] score;
    private ImageView pic;
    private TextView tv;
    private String[] fake;
    private String correct;
    private String question;
    private String[] answer;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int time = 0;
    String rival;
    RelativeLayout bg2;
    RelativeLayout bg;
    int num[];

    private static final String Tag = "mrboomba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        TextView number = (TextView) findViewById(R.id.number);
        number.setText("1/5");
        score = new boolean[5];
        bg2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
        num = (int[]) getIntent().getSerializableExtra("num");
        rival = (String) getIntent().getSerializableExtra("rival");
        answer = new String[5];
        choice = new Button[4];
        choice[0] = (Button) findViewById(R.id.button1);
        choice[1] = (Button) findViewById(R.id.button2);
        choice[2] = (Button) findViewById(R.id.button3);
        choice[3] = (Button) findViewById(R.id.button4);
        tv = (TextView) findViewById(R.id.question);
        bg = (RelativeLayout) findViewById(R.id.bg);
        pic = (ImageView) findViewById(R.id.imageView1);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ChallengeResponse> call = apiService.getChallenge(num[0]);
        call.enqueue(new Callback<ChallengeResponse>() {
            @Override
            public void onResponse(Call<ChallengeResponse> call, Response<ChallengeResponse> response) {
                Random r = new Random();

                tv.setText(response.body().getQuestion());
                fake = response.body().getFake();
                correct = response.body().getAns();
                Picasso.with(ChallengeAcceptActivity.this).load(Config.SERVER + "api/challenge/pic/" + num[0]).resize(900, 900).into(pic);

                final int check = r.nextInt(4);
                int[] tmp = new int[]{0, 1, 2};
                shuffleArray(tmp);
                for (int i = 0, j = 0; i < 4; i++) {

                    if (check != i) {
                        choice[i].setText(fake[tmp[j]]);
                        j++;
                        final int finalI = i;
                        choice[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bg2.setClickable(false);
                                bg2.setFocusable(false);
                                score[0] = false;
                                mCountDownTimer.cancel();
                                mCountDownTimer = null;
                                choice[0].setClickable(false);
                                choice[1].setClickable(false);
                                choice[2].setClickable(false);
                                choice[3].setClickable(false);

                                choice[finalI].setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
                                choice[check].setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
                                answer[0] = String.valueOf(choice[finalI].getText());

                                bg2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ChallengeAcceptActivity.this, ChallengeAcceptActivity2.class);
                                        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        intent.putExtra("num", num);
                                        intent.putExtra("point",0);
                                        intent.putExtra("answer", answer);
                                        intent.putExtra("rival", rival);
                                        startActivity(intent);

                                    }
                                });

                                bg.bringToFront();
                                setContentView(bg);
                                bg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ChallengeAcceptActivity.this, ChallengeAcceptActivity2.class);
                                        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        intent.putExtra("score", score);
                                        intent.putExtra("num", num);
                                        intent.putExtra("rival", rival);
                                        intent.putExtra("answer", answer);
                                        intent.putExtra("point",0);
                                        startActivity(intent);

                                    }
                                });
                            }
                        });
                    }
                }
                choice[check].setText(correct);
                choice[check].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bg2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChallengeAcceptActivity.this, ChallengeAcceptActivity2.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.putExtra("num", num);
                                intent.putExtra("answer", answer);
                                intent.putExtra("point",1);
                                intent.putExtra("rival", rival);
                                startActivity(intent);

                            }
                        });
                        choice[0].setClickable(false);
                        choice[1].setClickable(false);
                        choice[2].setClickable(false);
                        choice[3].setClickable(false);
                        mCountDownTimer.cancel();
                        mCountDownTimer = null;
                        choice[check].setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
                        answer[0] = String.valueOf(choice[check].getText());

                        bg.bringToFront();
                        setContentView(bg);
                        bg.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                Intent intent = new Intent(ChallengeAcceptActivity.this, ChallengeAcceptActivity2.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.putExtra("num", num);
                                intent.putExtra("answer", answer);
                                intent.putExtra("rival", rival);
                                intent.putExtra("point",1);
                                startActivity(intent);
                                return false;
                            }
                        });

                    }
                });
                mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
                mProgressBar.setProgress(time);
                mCountDownTimer = new CountDownTimer(30000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        time++;
                        mProgressBar.setProgress(time);


                    }

                    @Override
                    public void onFinish() {
                        //Do what you want
                        bg2.setClickable(false);
                        bg2.setFocusable(false);
                        answer[0] = String.valueOf("");
                        choice[0].setClickable(false);
                        choice[1].setClickable(false);
                        choice[2].setClickable(false);
                        choice[3].setClickable(false);
                        choice[check].setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
                        setContentView(bg);
                        bg2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChallengeAcceptActivity.this, ChallengeAcceptActivity2.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.putExtra("num", num);
                                intent.putExtra("answer", answer);
                                intent.putExtra("point",0);
                                intent.putExtra("rival", rival);
                                startActivity(intent);

                            }
                        });

                        bg.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                Intent intent = new Intent(ChallengeAcceptActivity.this, ChallengeAcceptActivity2.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.putExtra("num", num);
                                intent.putExtra("answer", answer);
                                intent.putExtra("rival", rival);
                                intent.putExtra("point",0);
                                startActivity(intent);
                                return false;
                            }
                        });
                    }
                };
                mCountDownTimer.start();
            }

            @Override
            public void onFailure(Call<ChallengeResponse> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getBaseContext(), "Please check your connection!", Toast.LENGTH_LONG).show();
                finish();

            }

        });


    }

    static void shuffleArray(int[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        String defaultValue = "no user";
        String username = sharedPref.getString("username", defaultValue);
        answer[0]="";
        answer[1]="";
        answer[2]="";
        answer[3]="";
        answer[4]="";
        Match match = new Match(rival,username,answer,0);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Match> call = apiService.finish(match);
        call.enqueue(new Callback<Match>() {
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {

                finish();
            }

            @Override
            public void onFailure(Call<Match> call, Throwable t) {
                Toast.makeText(ChallengeAcceptActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
            }


        });
        finish();
    }


}

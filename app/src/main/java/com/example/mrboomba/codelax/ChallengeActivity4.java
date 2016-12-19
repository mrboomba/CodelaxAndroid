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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrboomba on 11/12/2559.
 */

public class ChallengeActivity4 extends AppCompatActivity {

    private Button[] choice;
    private int check;
    private ImageView pic;
    private TextView tv;
    private String[] fake;
    private String correct;
    private String question;
    private String[] answer;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int time=0;
    RelativeLayout bg ;
    RelativeLayout bg2;
    String rival;
    int num[];
    int point;

    private static final String Tag = "mrboomba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        num  = (int[]) getIntent().getSerializableExtra("num");
        point = (int) getIntent().getSerializableExtra("point");
        answer = (String[])getIntent().getSerializableExtra("answer");
        rival = (String) getIntent().getSerializableExtra("rival");

        bg2 =(RelativeLayout) findViewById(R.id.RelativeLayout2);
        TextView number = (TextView)findViewById(R.id.number);
        number.setText("4/5");

        choice = new Button[4];
        choice[0] = (Button)findViewById(R.id.button1);
        choice[1] = (Button)findViewById(R.id.button2);
        choice[2] = (Button)findViewById(R.id.button3);
        choice[3] = (Button)findViewById(R.id.button4);
        tv = (TextView)findViewById(R.id.question);
        bg = (RelativeLayout)findViewById(R.id.bg) ;
        pic = (ImageView)findViewById(R.id.imageView1);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ChallengeResponse> call = apiService.getChallenge(num[3]);
        call.enqueue(new Callback<ChallengeResponse>() {
            @Override
            public void onResponse(Call<ChallengeResponse>call, Response<ChallengeResponse> response) {
                Random r = new Random();
                tv.setText(response.body().getQuestion());
                fake = response.body().getFake();
                correct = response.body().getAns();
                Picasso.with(ChallengeActivity4.this).load(Config.SERVER+"api/challenge/pic/"+num[3]).resize(450,500).into(pic);
                final int check = r.nextInt(4);
                int [] tmp = new int[]{0,1,2};
                shuffleArray(tmp);
                for(int i=0,j=0;i<4;i++){

                    if(check!=i){
                        choice[i].setText(fake[tmp[j]]);
                        j++;
                        final int finalI = i;
                        choice[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                choice[0].setClickable(false);
                                choice[1].setClickable(false);
                                choice[2].setClickable(false);
                                choice[3].setClickable(false);
                                mCountDownTimer.cancel();
                                mCountDownTimer=null;
                                choice[finalI].setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.red,null));
                                choice[check].setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.green,null));
                                answer[3] = String.valueOf(choice[finalI].getText());
                                bg2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ChallengeActivity4.this, ChallengeActivity5.class);
                                        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        intent.putExtra("num",num);
                                        intent.putExtra("answer",answer);
                                        intent.putExtra("rival",rival);
                                        intent.putExtra("point",point);
                                        startActivity(intent);

                                    }
                                });

                                setContentView(bg);
                                bg.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        Intent intent = new Intent(ChallengeActivity4.this, ChallengeActivity5.class);
                                        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        intent.putExtra("num",num);
                                        intent.putExtra("answer",answer);
                                        intent.putExtra("rival",rival);
                                        intent.putExtra("point",point);
                                        startActivity(intent);
                                        return false;
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
                        choice[0].setClickable(false);
                        choice[1].setClickable(false);
                        choice[2].setClickable(false);
                        choice[3].setClickable(false);
                        point++;
                        mCountDownTimer.cancel();
                        mCountDownTimer=null;
                        choice[check].setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.green,null));
                        answer[3] = String.valueOf(choice[check].getText());
                        setContentView(bg);
                        bg2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChallengeActivity4.this, ChallengeActivity5.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.putExtra("num",num);
                                intent.putExtra("answer",answer);
                                intent.putExtra("rival",rival);
                                intent.putExtra("point",point);
                                startActivity(intent);

                            }
                        });

                        bg.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                Intent intent = new Intent(ChallengeActivity4.this, ChallengeActivity5.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.putExtra("num",num);
                                intent.putExtra("answer",answer);
                                intent.putExtra("rival",rival);
                                intent.putExtra("point",point);
                                startActivity(intent);
                                return false;
                            }
                        });

                    }
                });
                mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
                mProgressBar.setProgress(time);
                mCountDownTimer=new CountDownTimer(30000,1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        time++;
                        mProgressBar.setProgress(time);

                    }

                    @Override
                    public void onFinish() {
                        //Do what you want
                        time++;
                        mProgressBar.setProgress(time);
                        choice[0].setClickable(false);
                        choice[1].setClickable(false);
                        choice[2].setClickable(false);
                        choice[3].setClickable(false);
                        choice[check].setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.green,null));

                        answer[3] = String.valueOf("");
                        setContentView(bg);
                        bg2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChallengeActivity4.this, ChallengeActivity5.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.putExtra("num",num);
                                intent.putExtra("answer",answer);
                                intent.putExtra("rival",rival);
                                intent.putExtra("point",point);
                                startActivity(intent);

                            }
                        });

                        bg.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                Intent intent = new Intent(ChallengeActivity4.this, ChallengeActivity5.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.putExtra("num", num);
                                intent.putExtra("answer", answer);
                                intent.putExtra("rival",rival);
                                intent.putExtra("point",point);
                                startActivity(intent);
                                return false;
                            }
                        });
                    }
                };
                mCountDownTimer.start();
            }
            @Override
            public void onFailure(Call<ChallengeResponse>call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getBaseContext(), "Please check your connection!", Toast.LENGTH_LONG).show();
                finish();

            }

        });








    }
    static void shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
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
        answer[3]="";
        answer[4]="";
        Match match = new Match(username,rival,num,answer,point);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Match> call = apiService.createMatch(match);
        call.enqueue(new Callback<Match>() {
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {

                finish();
            }

            @Override
            public void onFailure(Call<Match> call, Throwable t) {
                Toast.makeText(ChallengeActivity4.this, "Check your connection", Toast.LENGTH_SHORT).show();
            }


        });
        finish();
    }
}

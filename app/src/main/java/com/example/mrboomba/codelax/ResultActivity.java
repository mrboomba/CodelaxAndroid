package com.example.mrboomba.codelax;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity  {
    Button[] userBut,rivalBut;
    TextView result,score;
    TextView userTxt,rivalTxt;
    String[] userAnswer,rivalAnswer;
    int[] challenge;
    int userScore,rivalScore;
    ImageView userPic,rivalPic;
    String userName,rivalName;
    int userChallenge,rivalChallenge;
    String[] question,key;
    String TAG = "mrboomba";
    String check;
    String user,rival;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        userBut = new Button[5];
        rivalBut = new Button[5];
        question = new String[5];
        key = new String[5];
        userBut[0] = (Button) findViewById(R.id.user1);
        userBut[1] = (Button) findViewById(R.id.user2);
        userBut[2] = (Button) findViewById(R.id.user3);
        userBut[3] = (Button) findViewById(R.id.user4);
        userBut[4] = (Button) findViewById(R.id.user5);
        rivalBut[0] = (Button) findViewById(R.id.rival1);
        rivalBut[1] = (Button) findViewById(R.id.rival2);
        rivalBut[2] = (Button) findViewById(R.id.rival3);
        rivalBut[3] = (Button) findViewById(R.id.rival4);
        rivalBut[4] = (Button) findViewById(R.id.rival5);
        result = (TextView) findViewById(R.id.result);
        score = (TextView) findViewById(R.id.score);
        userPic = (ImageView) findViewById(R.id.userPic);
        rivalPic = (ImageView) findViewById(R.id.rivalPic);
        userTxt = (TextView) findViewById(R.id.userName);
        rivalTxt =(TextView) findViewById(R.id.rivalName);
        user =(String) getIntent().getSerializableExtra("user");
        rival =(String)getIntent().getSerializableExtra("rival");
        check = (String)getIntent().getSerializableExtra("check");
        Match match = new Match(user,rival);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
            Call<User> call2 = apiService.getUserChallenge(user);
        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userName = response.body().getName();
                userChallenge = response.body().getChalenge();
                userPic.setImageResource(Config.avatar[userChallenge]);
                userTxt.setText(userName);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        call2 = apiService.getUserChallenge(rival);
        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                rivalName = response.body().getName();
                rivalChallenge = response.body().getChalenge();
                rivalPic.setImageResource(Config.avatar[rivalChallenge]);
                rivalTxt.setText(rivalName);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });



        Call<Match> call = apiService.result(match);
        call.enqueue(new Callback<Match>() {
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {
                userAnswer = response.body().getUserAnswer();
                rivalAnswer = response.body().getRivalAnswer();

                challenge = response.body().getChallenge();
                userScore = response.body().getUserPoint();
                rivalScore = response.body().getRivalPoint();
                for(int i =0;i<5;i++) {
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<ChallengeResponse> call3 = apiService.getChallenge(challenge[i]);
                    final int finalI = i;
                    call3.enqueue(new Callback<ChallengeResponse>() {
                        @Override
                        public void onResponse(Call<ChallengeResponse> call, Response<ChallengeResponse> response) {
                            question[finalI] = response.body().getQuestion();
                            key[finalI] = response.body().getAns();
                            if(userScore>rivalScore){
                                result.setText(userName+" WIN!");
                            }
                            else{
                                result.setText(rivalName+" WIN!");
                            }
                            score.setText(userScore+" - "+rivalScore);
                            for(int i =0;i<5;i++){
                                if(userAnswer[i].equals(key[i])){
                                    userBut[i].setBackgroundColor(getResources().getColor(R.color.green));
                                }
                                else{
                                    userBut[i].setBackgroundColor(getResources().getColor(R.color.red));
                                }
                                if(rivalAnswer[i].equals(key[i])){
                                    rivalBut[i].setBackgroundColor(getResources().getColor(R.color.green));
                                }
                                else{
                                    rivalBut[i].setBackgroundColor(getResources().getColor(R.color.red));
                                }
                                final int finalI2 = i;
                                final int finalI3 = i;
                                userBut[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater layoutInflater
                                                = (LayoutInflater) ResultActivity.this
                                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                                        View popupView = layoutInflater.inflate(R.layout.popup_detail, null);
                                        final PopupWindow popupWindow = new PopupWindow(
                                                popupView ,
                                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT);
                                        popupWindow.showAtLocation(view,Gravity.CENTER, 0, 0);
                                        Button userBut = (Button) popupView.findViewById(R.id.userAnswer);
                                        Button rivalBut = (Button) popupView.findViewById(R.id.rivalAnswer);
                                        Button answer = (Button) popupView.findViewById(R.id.answer);
                                        TextView questiontxt = (TextView) popupView.findViewById(R.id.question);
                                        TextView usertxt = (TextView) popupView.findViewById(R.id.userName);
                                        TextView rivaltxt = (TextView) popupView.findViewById(R.id.rivalName);
                                        usertxt.setText(userName);
                                        rivaltxt.setText(rivalName);
                                        ImageView userPic,rivalPic,questionPic,close;
                                        close = (ImageView) popupView.findViewById(R.id.imageView4);
                                        close.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                popupWindow.dismiss();
                                            }
                                        });
                                        userPic = (ImageView)  popupView.findViewById(R.id.userPic);
                                        rivalPic = (ImageView)  popupView.findViewById(R.id.rivalPic);
                                        questionPic = (ImageView)  popupView.findViewById(R.id.questionPic);
                                        Picasso.with(ResultActivity.this).load(Config.SERVER + "api/challenge/pic/" + challenge[0]).resize(900, 900).into(questionPic);
                                        userPic.setImageResource(Config.avatar[userChallenge]);
                                        rivalPic.setImageResource(Config.avatar[rivalChallenge]);
                                        userBut.setText(userAnswer[finalI2]);
                                        rivalBut.setText(rivalAnswer[finalI2]);
                                        if(userAnswer[finalI3].equals(key[finalI3])){
                                            userBut.setBackgroundColor(getResources().getColor(R.color.green));
                                        }
                                        else{
                                            userBut.setBackgroundColor(getResources().getColor(R.color.red));
                                        }
                                        if(rivalAnswer.equals(key[finalI3])){
                                            rivalBut.setBackgroundColor(getResources().getColor(R.color.green));
                                        }
                                        else{
                                            rivalBut.setBackgroundColor(getResources().getColor(R.color.red));
                                        }
                                        answer.setText("คำตอบที่ถูกคือ "+key[finalI2]);
                                        questiontxt.setText(question[finalI2]);



                                    }
                                });
                                final int finalI1 = i;
                                rivalBut[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater layoutInflater
                                                = (LayoutInflater) ResultActivity.this
                                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                                        View popupView = layoutInflater.inflate(R.layout.popup_detail, null);
                                        final PopupWindow popupWindow = new PopupWindow(
                                                popupView ,
                                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT);
                                        popupWindow.showAtLocation(view,Gravity.CENTER, 0, 0);
                                        Button userBut = (Button) popupView.findViewById(R.id.userAnswer);
                                        Button rivalBut = (Button) popupView.findViewById(R.id.rivalAnswer);
                                        Button answer = (Button) popupView.findViewById(R.id.answer);
                                        TextView questiontxt = (TextView) popupView.findViewById(R.id.question);
                                        TextView usertxt = (TextView) popupView.findViewById(R.id.userName);
                                        TextView rivaltxt = (TextView) popupView.findViewById(R.id.rivalName);
                                        usertxt.setText(userName);
                                        rivaltxt.setText(rivalName);
                                        ImageView userPic,rivalPic,questionPic,close;
                                        close = (ImageView) popupView.findViewById(R.id.imageView4);
                                        close.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                popupWindow.dismiss();
                                            }
                                        });
                                        userPic = (ImageView)  popupView.findViewById(R.id.userPic);
                                        rivalPic = (ImageView)  popupView.findViewById(R.id.rivalPic);
                                        questionPic = (ImageView)  popupView.findViewById(R.id.questionPic);
                                        Picasso.with(ResultActivity.this).load(Config.SERVER + "api/challenge/pic/" + challenge[0]).resize(900, 900).into(questionPic);
                                        userPic.setImageResource(Config.avatar[userChallenge]);
                                        rivalPic.setImageResource(Config.avatar[rivalChallenge]);
                                        userBut.setText(userAnswer[finalI2]);
                                        rivalBut.setText(rivalAnswer[finalI2]);
                                        if(userAnswer[finalI3].equals(key[finalI3])){
                                            userBut.setBackgroundColor(getResources().getColor(R.color.green));
                                        }
                                        else{
                                            userBut.setBackgroundColor(getResources().getColor(R.color.red));
                                        }
                                        if(rivalAnswer.equals(key[finalI3])){
                                            rivalBut.setBackgroundColor(getResources().getColor(R.color.green));
                                        }
                                        else{
                                            rivalBut.setBackgroundColor(getResources().getColor(R.color.red));
                                        }
                                        answer.setText("คำตอบที่ถูกคือ "+key[finalI2]);
                                        questiontxt.setText(question[finalI2]);


                                    }
                                });

                            }

                        }

                        @Override
                        public void onFailure(Call<ChallengeResponse> call, Throwable t) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Match> call, Throwable t) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(check.equals("true")){
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Match match = new Match(user,rival);
            Call<Match> call = apiService.end(match);
            call.enqueue(new Callback<Match>() {
                @Override
                public void onResponse(Call<Match> call, Response<Match> response) {
                        finish();
                }

                @Override
                public void onFailure(Call<Match> call, Throwable t) {

                }
            });
        }
        else
        finish();
    }
}

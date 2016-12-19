package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendActivity extends AppCompatActivity {
    ImageView search,userPic;
    TextView name;
    EditText fill;
    Button b;
    String username;
    String keep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        search = (ImageView)findViewById(R.id.imageView2);
        userPic = (ImageView)findViewById(R.id.userPic);
        fill = (EditText)findViewById(R.id.editText3);
        name = (TextView) findViewById(R.id.name);
        b = (Button) findViewById(R.id.add);
        SharedPreferences sharedPref =getSharedPreferences("user", Context.MODE_PRIVATE);
        String defaultValue = "no user";
        username = sharedPref.getString("username", defaultValue);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<User> call = apiService.getUserChallenge(fill.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.body()==null){
                            name.setVisibility(View.VISIBLE);
                            name.setText("หาผู้ใช้ไม่พบ");
                        }
                        else{
                            name.setText(response.body().getName());
                            keep = fill.getText().toString();
                            name.setVisibility(View.VISIBLE);
                            userPic.setImageResource(Config.avatar[response.body().getChalenge()]);
                            b.setVisibility(View.VISIBLE);
                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ApiInterface apiService2 =
                                            ApiClient.getClient().create(ApiInterface.class);
                                    User user =new User(keep);
                                    Call<User> call2 = apiService2.addFriend(user,username);
                                    call2.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {

                                        }
                                    });
                                    b.setText("Finish");
                                    b.setBackgroundColor(getResources().getColor(R.color.green));
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
    }
}

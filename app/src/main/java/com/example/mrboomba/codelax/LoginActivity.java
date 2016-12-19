package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText user;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        Button login = (Button) findViewById(R.id.button5);
        SharedPreferences sharedPref = getSharedPreferences("user", MODE_PRIVATE);
        String defaultValue = "no user";
        String username = sharedPref.getString("username", defaultValue);
        if (!username.equals("no user")) {
            Intent intent = new Intent(this, SplashPage.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTmp = String.valueOf(user.getText());
                String passTmp = String.valueOf(pass.getText());
                Login login = new Login(userTmp, passTmp);
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<Login> call = apiService.login(login);
                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {

                        if (response.body() == null) {
                            Toast.makeText(LoginActivity.this, "Wrong user name or password", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences sharedPref = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", response.body().getUsername());
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, SplashPage.class);
                            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
                    }


                });
            }
        });
    }
}

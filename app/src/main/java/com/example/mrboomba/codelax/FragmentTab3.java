package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class FragmentTab3 extends Fragment {

    ListView lv;
    Context context;
    String username;
    String listFri[], listMat[],listCom[];
    Button random,addFriend;

    public static ArrayList<String> friendList;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendList = new ArrayList<>();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String defaultValue = "no user";
        username = sharedPref.getString("username", defaultValue);
        context = getContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment_tab3, container, false);
        random = (Button) v. findViewById(R.id.button);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<User> call = apiService.random();
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        int[] num = new int[5];
                        Random r = new Random();
                        for(int i=0;i<5;i++){
                            int tmp = r.nextInt(50)+1;
                            num[i] = tmp;
                            for(int j=0 ;j<i;j++){
                                if(num[j]==tmp){
                                    num[i]=0;
                                    i--;
                                    break;
                                }
                            }
                        }
                        Intent intent = new Intent(context, ChallengeActivity.class);
                        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("num",num);
                        intent.putExtra("rival",response.body().getUser());
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
        addFriend = (Button) v.findViewById(R.id.button2);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddFriendActivity.class);
                context.startActivity(intent);
            }
        });

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);



        Call<User> call = apiService.getList(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                listFri = response.body().getFreind();
                listMat = response.body().getMatch();
                listCom = response.body().getComplete();

                lv = (ListView) v.findViewById(R.id.listView);
                lv.setAdapter(new FriendAdapter(getActivity(), listFri, listMat,listCom));

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connection", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.toString());

            }
        });

        Call<Match> call2 = apiService.isReqest(username);
        call2.enqueue(new Callback<Match>() {
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {
                if(response.body()!=null) {
                    final int[] num = response.body().getQuestion();
                    final String user = response.body().getUsername();
                    Call<User> call4 = apiService.getUserChallenge(user);
                    call4.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            LayoutInflater layoutInflater
                                    = (LayoutInflater) context
                                    .getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.popup_challenge, null);
                            final PopupWindow popupWindow = new PopupWindow(
                                    popupView,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
                            Button btnConfirm = (Button) popupView.findViewById(R.id.confirm);
                            Button btnDecline = (Button) popupView.findViewById(R.id.decline);
                            TextView tv = (TextView) popupView.findViewById(R.id.message);
                            tv.setText(response.body().getName()+" กำลังท้าทายคุณมาเล่นเกมส์คุณต้องการเล่นไหม");
                            btnConfirm.setText("OK");
                            btnConfirm.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));
                            btnConfirm.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.green, null));
                            btnConfirm.setOnClickListener(new Button.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    Intent intent = new Intent(context, ChallengeAcceptActivity.class);
                                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.putExtra("num",num);
                                    intent.putExtra("rival",user);
                                    context.startActivity(intent);
                                    popupWindow.dismiss();
                                }
                            });
                            btnDecline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String[] ans = new String[]{"","","","",""};
                                    Match match = new Match(user,username,ans,0);
                                    Call<Match> call3 = apiService.finish(match);
                                    call3.enqueue(new Callback<Match>() {
                                        @Override
                                        public void onResponse(Call<Match> call, Response<Match> response) {
                                            popupWindow.dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<Match> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Check your connection", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Match> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connection", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.getList(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                listFri = response.body().getFreind();
                listMat = response.body().getMatch();


                lv = (ListView) v.findViewById(R.id.listView);
                lv.setAdapter(new FriendAdapter(getActivity(), listFri, listMat,listCom));

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connection", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.toString());

            }
        });

    }
}
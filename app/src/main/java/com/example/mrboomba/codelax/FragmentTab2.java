package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class FragmentTab2 extends Fragment {

    private String username;
    ListView lv;
    Context context;
    int num;
    LessonListAdapter ls;
    View v;

    public static String lessonList[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lessonList =new String[]{"FLOWCHART","Variables and Data type","Loop","Decision","Function and scope","Arrays","Pointer","Recursion","File",};

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment_tab2, container, false);


        SharedPreferences sharedPref = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        String defaultValue = "no user";
        username = sharedPref.getString("username", defaultValue);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.getUserLesson(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                num = response.body().getLesson();
                lv=(ListView) v.findViewById(R.id.listView);
                ls = new LessonListAdapter(getActivity(), lessonList,num);
                Log.d(TAG, "onResponse: "+num);
                lv.setAdapter(ls);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Check your connection", Toast.LENGTH_SHORT).show();

            }
        });

        

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
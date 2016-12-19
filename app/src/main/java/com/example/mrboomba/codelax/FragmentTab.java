package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class FragmentTab extends Fragment {
    ImageView profile;
    TextView name;
    GridView gridview;
    String username;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment_tab, container, false);
        gridview = (GridView) v.findViewById(R.id.gridview);
        profile = (ImageView) v.findViewById(R.id.imageView);
        name = (TextView) v.findViewById(R.id.name);
        SharedPreferences sharedPref = v.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        String defaultValue = "no user";
        username = sharedPref.getString("username", defaultValue);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.getUserChallenge(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                profile.setImageResource(Config.avatar[response.body().getChalenge()]);
                name.setText(response.body().getName());
                ImageAdapter im = new ImageAdapter(getActivity());
                gridview.setAdapter(im);
                im.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(v.getContext(), "Check your connection", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }


}
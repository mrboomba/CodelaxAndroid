package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrboomba on 30/11/2559.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String username;
    private int num;
    private String[] lessonList = new String[]{"FLOWCHART", "Variables and Data type", "Loop", "Decision", "Function and scope", "Arrays", "Pointer", "Recursion", "File"};
    ImageView imageView;
    TextView textView;

    public ImageAdapter(Context c) {
        mContext = c;
        SharedPreferences sharedPref = c.getSharedPreferences("user", Context.MODE_PRIVATE);
        String defaultValue = "no user";
        username = sharedPref.getString("username", defaultValue);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.getUserLesson(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                num = response.body().getLesson();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext, "Check your connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public int getCount() {
        return Config.ACH.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // if it's not recycled, initialize some attributes
        grid = new View(mContext);
        grid = inflater.inflate(R.layout.grid_single, null);
        textView = (TextView) grid.findViewById(R.id.grid_text);
        imageView = (ImageView) grid.findViewById(R.id.grid_image);

        if (position < num) {
            imageView.setImageResource(Config.ACH[position]);
            textView.setText(lessonList[position]);
        } else {
            imageView.setImageResource(Config.FADE[position]);
            textView.setText("");
        }
        return grid;
    }


}
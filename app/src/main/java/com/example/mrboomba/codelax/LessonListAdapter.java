package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class LessonListAdapter extends BaseAdapter {
    String[] result;
    Context context;
    Lesson lesson;
    String resource;
    private int num;
    protected static LayoutInflater inflater = null;

    public LessonListAdapter(Context mainActivity, String[] prgmNameList, int num) {
        // TODO Auto-generated constructor stub
        this.num = num;
        result = prgmNameList;
        context = mainActivity;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
        ImageView arc;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.content_list, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
        holder.arc = (ImageView) rowView.findViewById(R.id.arc_pic);
        holder.img.setImageResource(R.drawable.ic_action_action_lock);
        holder.tv.setText(result[position]);

        holder.arc.setImageResource(Config.ACH[position]);
        if (position < num + 1) {
            holder.img.setVisibility(View.INVISIBLE);
            //rowView.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.choose,null));

            rowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    rowView.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.choose, null));
                    Intent intent = new Intent(context, LessonActivity.class);
                    intent.putExtra("lesson", position);
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();

                }
            });
        } else {
            holder.img.setVisibility(View.VISIBLE);
            holder.arc.setImageResource(Config.FADE[position]);
        }


        return rowView;
    }


}
package com.example.mrboomba.codelax;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.View.VISIBLE;

public class LessonAdapter extends BaseAdapter {
    String[] question;
    String[][] choice;
    int[] correct;
    Context context;
    String[] check;
    private String username;
    int num;
    Close close;
    Bitmap[] img;
    protected static LayoutInflater inflater = null;

    public LessonAdapter(Context mainActivity, String[] question, String[] ans, String[] fake, int num, Close close) {
        // TODO Auto-generated constructor stub
        this.question = question;
        this.num = num;
        this.close = close;
        SharedPreferences sharedPref = mainActivity.getSharedPreferences("user", Context.MODE_PRIVATE);
        String defaultValue = "no user";
        username = sharedPref.getString("username", defaultValue);
        choice = new String[5][2];
        correct = new int[5];
        check = new String[]{"", "", "", "", ""};
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            correct[i] = r.nextInt(2);
            if (correct[i] == 0) {
                choice[i][0] = ans[i];
                choice[i][1] = fake[i];
            } else {
                choice[i][1] = ans[i];
                choice[i][0] = fake[i];
            }

        }
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return question.length;
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
        Button but1, but2;
        ImageView img;

    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.activity_lesson_adapter, null);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
        Picasso.with(rowView.getContext()).load(Config.SERVER + "api/lesson/pic/" + num + "/" + position).into(holder.img);


        holder.tv = (TextView) rowView.findViewById(R.id.question);
        holder.tv.setText(question[position]);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
        holder.but1 = (Button) rowView.findViewById(R.id.button1);
        holder.but1.setText(choice[position][0]);
        holder.but2 = (Button) rowView.findViewById(R.id.button2);
        holder.but2.setText(choice[position][1]);
        holder.tv.setText(question[position]);
        holder.but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (correct[position] == 0) {
                    check[position] = "true";
                    notifyDataSetChanged();

                } else {
                    check[position] = "false";
                    notifyDataSetChanged();
                }
            }
        });
        holder.but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (correct[position] == 1) {
                    check[position] = "true";
                    notifyDataSetChanged();

                } else {
                    check[position] = "false";
                    notifyDataSetChanged();
                }

            }
        });
        if (check[position] == "") {
            holder.but1.setBackgroundColor(rowView.getResources().getColor(R.color.choose));
            holder.but2.setBackgroundColor(rowView.getResources().getColor(R.color.choose));
        } else if (check[position] == "true") {
            if (correct[position] == 0) {
                holder.but1.setBackgroundColor(rowView.getResources().getColor(R.color.green));
                holder.but2.setClickable(false);
            } else {
                holder.but2.setBackgroundColor(rowView.getResources().getColor(R.color.green));
                holder.but1.setClickable(false);
            }
        } else {
            if (correct[position] == 0) {
                holder.but1.setBackgroundColor(rowView.getResources().getColor(R.color.green));
                holder.but2.setBackgroundColor(rowView.getResources().getColor(R.color.red));
                holder.but2.setClickable(false);
                holder.but1.setClickable(false);
            } else {
                holder.but2.setBackgroundColor(rowView.getResources().getColor(R.color.green));
                holder.but1.setBackgroundColor(rowView.getResources().getColor(R.color.red));
                holder.but1.setClickable(false);
                holder.but2.setClickable(false);
            }
        }
        boolean isAll = true;
        for (String s : check) {
            if (s.equals("")) {
                isAll = false;
            }
        }
        if (isAll) {

            for (String s : check) {
                if (s.equals("false")) {
                    isAll = false;
                }
            }
            if (isAll) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<User> call = apiService.updateUserLesson(username, num);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        LayoutInflater layoutInflater
                                = (LayoutInflater) context
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = layoutInflater.inflate(R.layout.popup, null);
                        final PopupWindow popupWindow = new PopupWindow(
                                popupView,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindow.showAtLocation(rowView, Gravity.CENTER, 0, 0);
                        Button btnConfirm = (Button) popupView.findViewById(R.id.but);
                        TextView tv = (TextView) popupView.findViewById(R.id.message);
                        tv.setText("Congratulations! new lesson unlock!");
                        btnConfirm.setText("OK");
                        btnConfirm.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));
                        btnConfirm.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.green, null));
                        btnConfirm.setOnClickListener(new Button.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                popupWindow.dismiss();
                                close.close(true);
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, "Check your connection", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                LayoutInflater layoutInflater
                        = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                Button btnConfirm = (Button) popupView.findViewById(R.id.but);
                TextView tv = (TextView) popupView.findViewById(R.id.message);
                tv.setText("Sorry you need to correct all");
                btnConfirm.setText("Try Again");
                btnConfirm.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));
                btnConfirm.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.red, null));
                btnConfirm.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                        close.close(false);
                    }
                });

                popupWindow.showAtLocation(rowView, Gravity.CENTER, 0, 0);


            }
        }


        return rowView;
    }


    public interface Close {
        void close(boolean input);
    }


}
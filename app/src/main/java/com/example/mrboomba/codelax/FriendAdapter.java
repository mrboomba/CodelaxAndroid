package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by mrboomba on 2/12/2559.
 */

public class FriendAdapter extends BaseAdapter{


    String[] friend;
    String[] match;
    String[] complete;
    ArrayList<String> results;
    ArrayList<String> list;
    ArrayList<Integer> listImg;
    Context context;
    String userName;
    String resource;
    protected static LayoutInflater inflater=null;
    public FriendAdapter(Context mainActivity, String[] friendList,String[] matchList,String[] completeList) {
        // TODO Auto-generated constructor stub

        friend =friendList;
        match = matchList;
        complete = completeList;
        list = new ArrayList<>();
        listImg = new ArrayList<>();
        results=new ArrayList<String>();

        if(friend!=null&&match!=null&&complete!=null) {
            for (int i = 0; i < complete.length; i++) {
                results.add(complete[i]);
            }
                for (int i = 0; i < match.length; i++) {
                    results.add(match[i]);
                }
            for (int i = 0; i < friend.length; i++) {
                if (!results.contains(friend[i]))
                    results.add(friend[i]);
            }
        }
        else if(friend!=null&&match!=null){
            for (int i = 0; i < match.length; i++) {
                results.add(match[i]);
            }
            for (int i = 0; i < friend.length; i++) {
                if (!results.contains(friend[i]))
                    results.add(friend[i]);
            }
        }
        else if(match!=null&&complete!=null){
            for (int i = 0; i < complete.length; i++) {
                results.add(complete[i]);
            }
            for (int i = 0; i < match.length; i++) {
                results.add(match[i]);
            }
        }
        else if(friend!=null&&complete!=null){
            for (int i = 0; i < complete.length; i++) {
                results.add(complete[i]);
            }
            for (int i = 0; i < friend.length; i++) {
                if (!results.contains(friend[i]))
                    results.add(friend[i]);
            }
        }
        else if(complete!=null){
            for (int i = 0; i < complete.length; i++) {
                results.add(complete[i]);
            }
        }
        else if(match!=null){
            for (int i = 0; i < match.length; i++) {
                results.add(match[i]);
            }
        }
        else{
            for (int i = 0; i < friend.length; i++) {
                    results.add(friend[i]);
            }
        }
        Log.d(TAG, "FriendAdapter: "+results.size());



        context=mainActivity;
        inflater = ( LayoutInflater ) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SharedPreferences sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String defaultValue = "no user";
        userName = sharedPref.getString("username", defaultValue);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return results.size();
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

    public class Holder
    {
        TextView tv;
        ImageView img;
        Button button;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.friend_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.button = (Button) rowView.findViewById(R.id.button1);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                intent.putExtra("rival",results.get(position));
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });
            if (position < match.length+complete.length&&position>complete.length-1) {
                holder.button.setText("pending..");
                holder.button.setClickable(false);
                holder.button.setBackgroundColor(rowView.getResources().getColor(R.color.grey));
            }
        if (position < complete.length) {
            holder.button.setText("results");
            holder.button.setClickable(true);
            holder.button.setBackgroundColor(rowView.getResources().getColor(R.color.green));
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ResultActivity.class);
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra("user",userName);
                    intent.putExtra("rival",results.get(position));
                    intent.putExtra("check","true");
                    context.startActivity(intent);
                    notifyDataSetChanged();

                }
            });
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

            Call<User> call = apiService.getUserChallenge(results.get(position));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    holder.img.setImageResource(Config.avatar[response.body().getChalenge()]);
                    holder.tv.setText(response.body().getName());
                    holder.img.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(context, "Check your connection", Toast.LENGTH_SHORT).show();

                }
            });



        return rowView;
    }
}

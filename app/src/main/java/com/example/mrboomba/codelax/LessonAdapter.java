package com.example.mrboomba.codelax;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LessonAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int[] images;
    String resource;
    protected static LayoutInflater inflater=null;
    public LessonAdapter(Context mainActivity, String[] prgmNameList) {
        // TODO Auto-generated constructor stub
        images = new int[]{R.mipmap.waterfall_1,
                R.mipmap.forest,
                R.mipmap.spruce,
                R.mipmap.mill,
                R.mipmap.mountains_1,
                R.mipmap.island,
                R.mipmap.forest};
        result=prgmNameList;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
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

    public class Holder
    {
        TextView tv;
        ImageView img;
        ImageView arc;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.content_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.arc = (ImageView) rowView.findViewById(R.id.arc_pic);
        holder.img.setImageResource(R.drawable.ic_action_action_lock);
        holder.tv.setText(result[position]);
        holder.arc.setImageResource(images[position]);
        if(position<1) {
            holder.img.setVisibility(View.INVISIBLE);
            rowView.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.choose,null));

            rowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(context, ExerciseActivity.class);
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            holder.img.setVisibility(View.VISIBLE);
        }


        return rowView;
    }

} 
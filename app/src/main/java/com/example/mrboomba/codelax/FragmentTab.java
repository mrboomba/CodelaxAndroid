package com.example.mrboomba.codelax;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class FragmentTab extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_tab, container, false);
        GridView gridview = (GridView) v.findViewById(R.id.gridview);
        ImageView profile = (ImageView) v.findViewById(R.id.imageView);
        profile.setImageResource(R.mipmap.hev1_cir);
        gridview.setAdapter(new ImageAdapter(getActivity()));
        return v;
    }
}
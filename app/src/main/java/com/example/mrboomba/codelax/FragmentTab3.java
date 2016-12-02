package com.example.mrboomba.codelax;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class FragmentTab3 extends Fragment {

    ListView lv;
    Context context;

    public static String friendList[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendList =new String[] {"May","Boomba","Pare"};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_tab3, container, false);



        lv=(ListView) v.findViewById(R.id.listView);
        lv.setAdapter(new FriendAdapter(getActivity(), friendList));


        return v;
    }
}
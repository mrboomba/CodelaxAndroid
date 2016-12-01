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

    ArrayList prgmName;
    public static String lessonNameList[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lessonNameList = new String[50];
        lessonNameList[0] = "Flow Chart";
        for(int i=1;i<50;i++){
            lessonNameList[i] = "Level "+i;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_tab2, container, false);



        lv=(ListView) v.findViewById(R.id.listView);
        lv.setAdapter(new LessonAdapter(getActivity(), lessonNameList));


        return v;
    }
}
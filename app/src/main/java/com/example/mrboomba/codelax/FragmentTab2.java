package com.example.mrboomba.codelax;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class FragmentTab2 extends Fragment {

    ListView lv;
    Context context;

    public static String lessonList[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lessonList =new String[]{"FLOWCHART","LOOP","IF-ELSE CONDITIONS","ARRAY","POINTER","FILE"};

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_tab2, container, false);



        lv=(ListView) v.findViewById(R.id.listView);
        lv.setAdapter(new LessonAdapter(getActivity(), lessonList));


        return v;
    }
}
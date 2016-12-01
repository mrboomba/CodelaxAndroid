package com.example.mrboomba.codelax;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity
{
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(
                tabHost.newTabSpec("tab1").setIndicator(null, ResourcesCompat.getDrawable(getResources(),R.drawable.tab_selector_home,null)),
                FragmentTab.class, null);
        tabHost.addTab(
                tabHost.newTabSpec("tab2").setIndicator(null, ResourcesCompat.getDrawable(getResources(),R.drawable.tab_selector_lesson,null)),
                FragmentTab2.class, null);
        tabHost.addTab(
                tabHost.newTabSpec("tab3").setIndicator(null, ResourcesCompat.getDrawable(getResources(),R.drawable.tab_selector_challenge,null)),
                FragmentTab3.class, null);



    }
}

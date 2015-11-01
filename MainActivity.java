package com.example.beeblebrox;

import android.os.Bundle;
import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

/*
 * Main Activity for BeebleChat.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class MainActivity extends FragmentActivity {
	
	private ActionBar actionBar;
	private TabPagerAdapter tabAdapter;
	private ViewPager tab;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        
        tab = (ViewPager)findViewById(R.id.pager);
        tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                    public void onPageSelected(int position){
                        actionBar = getActionBar();
                        actionBar.setSelectedNavigationItem(position);                    }
                });
        tab.setAdapter(tabAdapter);
 
        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new SwipeTabListener(tab);
        actionBar.addTab(actionBar.newTab().setText("Chats").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Contacts").setTabListener(tabListener));
    
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
package com.example.beeblebrox;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

/*
 * Listener to swipe tabs.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class SwipeTabListener implements ActionBar.TabListener {
	
	private ViewPager tab;
	
	public SwipeTabListener(ViewPager tab){
		this.tab = tab;
	}
	
    public void onTabReselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
     public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        this.tab.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }
}

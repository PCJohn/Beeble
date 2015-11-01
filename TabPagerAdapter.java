package com.example.beeblebrox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
 
/*
 * Adapter for swipe tabs in MainActivity.
 * Handle swipe between the chat room starter and contact list viewer
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }
 
    @Override
    public Fragment getItem(int i) {
        switch (i) {
        case 0:
            //Fragment for a list of chats
            return new ChatList();
        case 1:
           //Fragment for a list of all contacts
            return new ContactArray();
        }
        return null;
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 2; //No of Tabs
    }
}
package com.example.defaultdemotoken.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.defaultdemotoken.Fragment.CurrentOrdersFragment;
import com.example.defaultdemotoken.Fragment.PastOrdersFragment;

public class MyOrdersPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public MyOrdersPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CurrentOrdersFragment tab1 = new CurrentOrdersFragment();
                return tab1;
            case 1:
                PastOrdersFragment tab2 = new PastOrdersFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

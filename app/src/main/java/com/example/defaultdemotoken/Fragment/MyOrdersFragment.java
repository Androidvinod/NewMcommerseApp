package com.example.defaultdemotoken.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.MyOrdersPagerAdapter;
import com.example.defaultdemotoken.R;
import com.google.android.material.tabs.TabLayout;

public class MyOrdersFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    View v;
    Toolbar toolbar_orderlist;
    TabLayout myorders_tabLayout;
    ViewPager myorders_pager;
    NavigationActivity parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my_orders, container, false);
        parent=(NavigationActivity) getActivity();
        allocateMemory(v);
        setHasOptionsMenu(true);

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_orderlist);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        //((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Order List");
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        //toolbar_orderlist.setTitle(getActivity().getResources().getString(R.string.orderlist));

        myorders_tabLayout.setTabTextColors(parent.getResources().getColor(R.color.black), parent.getResources().getColor(R.color.black));
        myorders_tabLayout.setTabTextColors(ColorStateList.valueOf(parent.getResources().getColor(R.color.black)));
        myorders_tabLayout.setSelectedTabIndicatorColor(parent.getResources().getColor(R.color.greeen));
        myorders_tabLayout.addTab(myorders_tabLayout.newTab().setText("Current Order"));
        myorders_tabLayout.addTab(myorders_tabLayout.newTab().setText("Past Order"));
        myorders_tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        /*myorders_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout tabLayout = (LinearLayout)((ViewGroup) myorders_tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLayout.getChildAt(1);
                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout tabLayout = (LinearLayout)((ViewGroup) myorders_tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLayout.getChildAt(1);
                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });*/

        MyOrdersPagerAdapter adapter = new MyOrdersPagerAdapter(this.getChildFragmentManager(), myorders_tabLayout.getTabCount());
        myorders_pager.setAdapter(adapter);
        //myorders_pager.setOffscreenPageLimit(2);
        myorders_tabLayout.setOnTabSelectedListener(this);

        myorders_pager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        myorders_tabLayout.getTabAt(position).select();
                    }
                });

        return v;

    }

    private void allocateMemory(View v) {

        toolbar_orderlist = v.findViewById(R.id.toolbar_orderlist);
        myorders_tabLayout = v.findViewById(R.id.myorders_tabLayout);
        myorders_pager = v.findViewById(R.id.myorders_pager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        myorders_pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
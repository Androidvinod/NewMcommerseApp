package com.example.defaultdemotoken.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

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
import com.example.defaultdemotoken.R;


public class OrderSuccessfulFragment extends Fragment {

    public OrderSuccessfulFragment(){

    }

    View v;
    LinearLayout lv_back_to_home;
    TextView tv_order_successful,tv_order_thankyou,tv_desc,tv_continue_shopping;
    Toolbar toolbar_order_successful;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_order_successful, container, false);

        AllocateMemeory(v);
        setHasOptionsMenu(true);

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_order_successful);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        /*toolbar_order_successful.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });*/

        toolbar_order_successful.setTitle("Orders successful");
        toolbar_order_successful.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        tv_order_successful.setTypeface(SplashActivity.montserrat_semibold);
        tv_order_thankyou.setTypeface(SplashActivity.montserrat_semibold);
        tv_continue_shopping.setTypeface(SplashActivity.montserrat_semibold);

        /*lv_back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
            }
        });*/

        tv_continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),NavigationActivity.class);
                startActivity(i);
            }
        });

        return v;
    }

    private void AllocateMemeory(View v) {
        toolbar_order_successful=v.findViewById(R.id.toolbar_order_successful);
        tv_order_successful = v.findViewById(R.id.tv_order_successful);
        tv_order_thankyou = v.findViewById(R.id.tv_order_thankyou);
        tv_continue_shopping = v.findViewById(R.id.tv_continue_shopping);
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
                return super.onOptionsItemSelected(item);
        }
    }

}
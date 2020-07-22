package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.R;

public class TrackOrderFragment extends Fragment {

    public TrackOrderFragment() {
        // Required empty public constructor
    }

    View v;
    Toolbar toolbar_track_order;
    Bundle b;
    String order_id;
    TextView tv_toolbar_track_order, tv_order_signed_date, tv_order_signed_time, tv_order_signed_title, tv_order_signed_city, tv_order_processed_date, tv_order_processed_time, tv_order_processed_title, tv_order_processed_city,
            tv_shipped_date, tv_shipped_time, tv_shipped_title, tv_shipped_city, tv_out_for_delivery_date, tv_out_for_delivery_time, tv_out_for_delivery_title, tv_out_for_delivery_city, tv_delivered_date, tv_delivered_time,
            tv_delivered_title, tv_delivered_city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_track_order, container, false);

        AllocateMemory(v);
        setFontFamily();
        b = this.getArguments();

        if (b != null) {
            order_id = b.getString("order_id");
            Log.e("order_id_track", "" + order_id);
        }

        setHasOptionsMenu(true);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_track_order);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        tv_toolbar_track_order.setText("OD - " + order_id);

        return v;
    }

    private void setFontFamily() {
        tv_order_signed_date.setTypeface(SplashActivity.montserrat_regular);
        tv_order_signed_time.setTypeface(SplashActivity.montserrat_regular);
        tv_order_signed_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_order_signed_city.setTypeface(SplashActivity.montserrat_regular);
        tv_order_processed_date.setTypeface(SplashActivity.montserrat_regular);
        tv_order_processed_time.setTypeface(SplashActivity.montserrat_regular);
        tv_order_processed_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_order_processed_city.setTypeface(SplashActivity.montserrat_regular);
        tv_shipped_date.setTypeface(SplashActivity.montserrat_regular);
        tv_shipped_time.setTypeface(SplashActivity.montserrat_regular);
        tv_shipped_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_shipped_city.setTypeface(SplashActivity.montserrat_regular);
        tv_out_for_delivery_date.setTypeface(SplashActivity.montserrat_regular);
        tv_out_for_delivery_time.setTypeface(SplashActivity.montserrat_regular);
        tv_out_for_delivery_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_out_for_delivery_city.setTypeface(SplashActivity.montserrat_regular);
        tv_delivered_date.setTypeface(SplashActivity.montserrat_regular);
        tv_delivered_time.setTypeface(SplashActivity.montserrat_regular);
        tv_delivered_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_delivered_city.setTypeface(SplashActivity.montserrat_regular);
    }

    private void AllocateMemory(View v) {
        toolbar_track_order = v.findViewById(R.id.toolbar_track_order);
        tv_toolbar_track_order = v.findViewById(R.id.tv_toolbar_track_order);

        tv_order_signed_date = v.findViewById(R.id.tv_order_signed_date);
        tv_order_signed_time = v.findViewById(R.id.tv_order_signed_time);
        tv_order_signed_title = v.findViewById(R.id.tv_order_signed_title);
        tv_order_signed_city = v.findViewById(R.id.tv_order_signed_city);
        tv_order_processed_date = v.findViewById(R.id.tv_order_processed_date);
        tv_order_processed_time = v.findViewById(R.id.tv_order_processed_time);
        tv_order_processed_title = v.findViewById(R.id.tv_order_processed_title);
        tv_order_processed_city = v.findViewById(R.id.tv_order_processed_city);
        tv_shipped_date = v.findViewById(R.id.tv_shipped_date);
        tv_shipped_time = v.findViewById(R.id.tv_shipped_time);
        tv_shipped_title = v.findViewById(R.id.tv_shipped_title);
        tv_shipped_city = v.findViewById(R.id.tv_shipped_city);
        tv_out_for_delivery_date = v.findViewById(R.id.tv_out_for_delivery_date);
        tv_out_for_delivery_time = v.findViewById(R.id.tv_out_for_delivery_time);
        tv_out_for_delivery_title = v.findViewById(R.id.tv_out_for_delivery_title);
        tv_out_for_delivery_city = v.findViewById(R.id.tv_out_for_delivery_city);
        tv_delivered_date = v.findViewById(R.id.tv_delivered_date);
        tv_delivered_time = v.findViewById(R.id.tv_delivered_time);
        tv_delivered_title = v.findViewById(R.id.tv_delivered_title);
        tv_delivered_city = v.findViewById(R.id.tv_delivered_city);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //   inflater.inflate(R.menu.menu_search, menu);
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
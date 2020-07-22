package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Fragment.OrderDetailsFragment;
import com.example.defaultdemotoken.Fragment.OrderDetailsFragmentNew;
import com.example.defaultdemotoken.Fragment.TrackOrderFragment;
import com.example.defaultdemotoken.Model.CurrentOrdersModel;
import com.example.defaultdemotoken.R;

import java.util.List;

public class Current_Order_Adapter extends RecyclerView.Adapter<Current_Order_Adapter.MyViewHolder> {
    Context context;
    private List<CurrentOrdersModel> currentOrdersModels;

    public Current_Order_Adapter(Context context, List<CurrentOrdersModel> currentOrdersModels) {
        this.context = context;
        this.currentOrdersModels = currentOrdersModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_current_order, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final CurrentOrdersModel currentOrdersModel = currentOrdersModels.get(position);


        holder.lv__track.setVisibility(View.VISIBLE);
        holder.lv_reorder.setVisibility(View.GONE);

        holder.tv_orders_product_name.setTypeface(SplashActivity.montserrat_bold);
        holder.tv_order_itemid.setTypeface(SplashActivity.montserrat_semibold);
        holder.tv_orders_itemid_value.setTypeface(SplashActivity.montserrat_regular);
        holder.tv_orders_product_price.setTypeface(SplashActivity.montserrat_regular);
        holder.tv_order_status.setTypeface(SplashActivity.montserrat_semibold);
        holder.tv_orders_status_value.setTypeface(SplashActivity.montserrat_semibold);

        holder.tv_orders_product_name.setText(currentOrdersModels.get(position).getCurrent_order_product_name());
        holder.tv_orders_itemid_value.setText(currentOrdersModels.get(position).getCurrent_order_product_item_ID());
        holder.tv_orders_product_price.setText(currentOrdersModels.get(position).getCurrent_order_product_price());
        holder.tv_orders_status_value.setText(currentOrdersModels.get(position).getCurrent_order_product_status());

        holder.lv_order_list_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                OrderDetailsFragmentNew orderDetailsFragment = new OrderDetailsFragmentNew();
                Bundle b = new Bundle();
                b.putString("order_id", currentOrdersModels.get(position).getOrder_id());
                orderDetailsFragment.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, orderDetailsFragment).addToBackStack(null).commit();
            }
        });

        holder.iv_track_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                TrackOrderFragment trackOrderFragment = new TrackOrderFragment();
                Bundle b = new Bundle();
                b.putString("order_id", currentOrdersModels.get(position).getOrder_id());
                trackOrderFragment.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, trackOrderFragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return currentOrdersModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_orders_product_name, tv_order_itemid, tv_orders_itemid_value, tv_orders_product_price, tv_order_status, tv_orders_status_value;
        LinearLayout lv_main_order, lv_order_status_value, lv__track, lv_reorder,lv_order_list_main;
        ImageView iv_track_order;

        public MyViewHolder(@NonNull View view) {
            super(view);


            lv_reorder = view.findViewById(R.id.lv_reorder);
            lv__track = view.findViewById(R.id.lv__track);
            tv_orders_status_value = view.findViewById(R.id.tv_orders_status_value);
            tv_order_status = view.findViewById(R.id.tv_order_status);
            tv_orders_product_price = view.findViewById(R.id.tv_orders_product_price);
            tv_orders_itemid_value = view.findViewById(R.id.tv_orders_itemid_value);
            tv_order_itemid = view.findViewById(R.id.tv_order_itemid);
            tv_orders_product_name = view.findViewById(R.id.tv_orders_product_name);
            lv_main_order = view.findViewById(R.id.lv_main_order);
            lv_order_status_value = view.findViewById(R.id.lv_order_status_value);
            lv_order_list_main = view.findViewById(R.id.lv_order_list_main);
            iv_track_order = view.findViewById(R.id.iv_track_order);

        }
    }
}


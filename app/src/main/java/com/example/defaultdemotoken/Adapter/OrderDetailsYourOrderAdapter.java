package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Model.OrderDetailsYourOrderModel;
import com.example.defaultdemotoken.R;

import java.util.List;

public class OrderDetailsYourOrderAdapter extends RecyclerView.Adapter<OrderDetailsYourOrderAdapter.MyViewHolder> {
    private Context context;
    private List<OrderDetailsYourOrderModel> orderDetailsYourOrderModels;
    private int lastSelectedPosition;

    public OrderDetailsYourOrderAdapter(Context context, List<OrderDetailsYourOrderModel> orderDetailsYourOrderModels) {
        this.context = context;
        this.orderDetailsYourOrderModels = orderDetailsYourOrderModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_details_your_order, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final OrderDetailsYourOrderModel orderDetailsYourOrderModel = orderDetailsYourOrderModels.get(position);

        holder.tv_od_prod_ada_name.setTypeface(SplashActivity.montserrat_bold);
        holder.tv_od_prod_ada_itemid.setTypeface(SplashActivity.montserrat_medium);
        holder.tv_od_prod_ada_price_value.setTypeface(SplashActivity.montserrat_bold);

        holder.tv_od_prod_ada_name.setText(orderDetailsYourOrderModels.get(position).getProd_name());
        holder.tv_od_prod_ada_itemid.setText(orderDetailsYourOrderModels.get(position).getProd_item_id());
        holder.tv_od_prod_ada_price_value.setText(orderDetailsYourOrderModels.get(position).getProd_price());

        /*holder.tv_wishlist_name.setTypeface(Splash.poppinssemibold);
        holder.tv_wishlist_price.setTypeface(Splash.poppinslight);
        holder.tv_wishlist_delete.setTypeface(Splash.aktivgroteskwbold);
        holder.tv_wishlist_add_to_cart.setTypeface(Splash.aktivgroteskwbold);*/

        /*holder.tv_wishlist_name.setText(wishlistModels.get(position).getWishlist_product_name());
        holder.tv_wishlist_price.setText(wishlistModels.get(position).getWishlist_product_price());*/

    }

    @Override
    public int getItemCount() {
        return orderDetailsYourOrderModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_od_prod_ada_name,tv_od_prod_ada_itemid,tv_od_prod_ada_price_value;

        public MyViewHolder(View view) {
            super(view);

            tv_od_prod_ada_name = view.findViewById(R.id.tv_od_prod_ada_name);
            tv_od_prod_ada_itemid = view.findViewById(R.id.tv_od_prod_ada_itemid);
            tv_od_prod_ada_price_value = view.findViewById(R.id.tv_od_prod_ada_price_value);

        }
    }
}

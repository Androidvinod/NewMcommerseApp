package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Fragment.CheckoutFragment;
import com.example.defaultdemotoken.Fragment.DeliveryFragment;
import com.example.defaultdemotoken.Fragment.PaymentsFragment;
import com.example.defaultdemotoken.Model.DeliveryModel;
import com.example.defaultdemotoken.R;

import java.util.List;

import static com.example.defaultdemotoken.Fragment.DeliveryFragment.deliveryaddres;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.MyViewHolder> {
    private Context context;
    private List<DeliveryModel> deliveryModels;
    private int lastSelectedPosition;

    public DeliveryAdapter(Context context, List<DeliveryModel> deliveryModels) {
        this.context = context;
        this.deliveryModels = deliveryModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DeliveryModel deliveryModel = deliveryModels.get(position);

        holder.tv_delivery_method_name.setTypeface(SplashActivity.montserrat_bold);
        holder.rad_delivery_description.setTypeface(SplashActivity.montserrat_medium);

        holder.tv_delivery_method_name.setText(deliveryModels.get(position).getMethod_title());
        holder.rad_delivery_description.setText(deliveryModels.get(position).getCarrier_title());

        holder.rad_delivery_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                deliveryaddres=deliveryModels.get(position).getCarrier_code();

                DeliveryFragment.shiping_method_order=deliveryModels.get(position).getMethod_title()+"-"+deliveryModels.get(position).getCarrier_code();

                Log.e("deliveryaddres", "" + deliveryaddres);
               // CheckoutFragment.paymentmethod_selcted = datum.getValue();
                notifyDataSetChanged();
            }
        });

        if (lastSelectedPosition == position) {
            Log.e("selectedpo_76", "" + lastSelectedPosition);
            holder.rad_delivery_description.setChecked(true);
            DeliveryFragment.shiping_method_order=deliveryModels.get(position).getMethod_title()+"-"+deliveryModels.get(position).getCarrier_code();
        } else {
            holder.rad_delivery_description.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return deliveryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_delivery_method_name;
        public static RadioButton rad_delivery_description;

        public MyViewHolder(View view) {
            super(view);

            tv_delivery_method_name = view.findViewById(R.id.rad_delivery_description);
            rad_delivery_description = view.findViewById(R.id.rad_delivery_description);

        }
    }
}

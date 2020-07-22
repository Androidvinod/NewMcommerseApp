package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Fragment.PaymentsFragment;
import com.example.defaultdemotoken.Model.DeliveryModel;
import com.example.defaultdemotoken.Model.PaymentModel;
import com.example.defaultdemotoken.R;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    private Context context;
    private List<PaymentModel> paymentModels;
    private int lastSelectedPosition=0;

    public PaymentAdapter(Context context, List<PaymentModel> paymentModels) {
        this.context = context;
        this.paymentModels = paymentModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PaymentModel paymentModel = paymentModels.get(position);

        holder.tv_payment_method_name.setTypeface(SplashActivity.montserrat_bold);

        holder.tv_payment_method_name.setText(paymentModels.get(position).getTitle());
        holder.lv_payment_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                notifyDataSetChanged();
                PaymentsFragment.payment_method_order=paymentModels.get(position).getCode();

            }
        });
        if (lastSelectedPosition == position) {
            PaymentsFragment.payment_method_order=paymentModels.get(position).getCode();

            Log.e("selectedpo_76", "" + lastSelectedPosition);
            holder.lv_payment_row.setBackground(context.getResources().getDrawable(R.drawable.rounded));
        } else {
            holder.lv_payment_row.setBackground(context.getResources().getDrawable(R.drawable.square_borderr));
        }
    }

    @Override
    public int getItemCount() {
        return paymentModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_payment_method_name;
        LinearLayout lv_payment_row;

        public MyViewHolder(View view) {
            super(view);

            tv_payment_method_name = view.findViewById(R.id.tv_payment_method_name);
            lv_payment_row = view.findViewById(R.id.lv_payment_row);

        }
    }
}
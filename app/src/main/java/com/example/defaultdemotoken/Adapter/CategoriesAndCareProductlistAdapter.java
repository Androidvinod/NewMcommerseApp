package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Model.CategoriesAndCareProductsModel;
import com.example.defaultdemotoken.Model.CategoriesAndCareTitleModel;
import com.example.defaultdemotoken.R;

import java.util.List;

public class CategoriesAndCareProductlistAdapter extends RecyclerView.Adapter<CategoriesAndCareProductlistAdapter.MyViewHolder> {
    Context context;
    private List<CategoriesAndCareProductsModel> categoriesAndCareProductsModels;

    public CategoriesAndCareProductlistAdapter(FragmentActivity context, List<CategoriesAndCareProductsModel> categoriesAndCareProductsModels) {
        this.context = context;
        this.categoriesAndCareProductsModels = categoriesAndCareProductsModels;
    }

    @NonNull
    @Override
    public CategoriesAndCareProductlistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_cnc_product_list, viewGroup, false);
        return new CategoriesAndCareProductlistAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAndCareProductlistAdapter.MyViewHolder holder, final int position) {
        final CategoriesAndCareProductsModel categoriesAndCareProductsModel = categoriesAndCareProductsModels.get(position);
        holder.tv_cnc_product_name.setTypeface(SplashActivity.montserrat_medium);

        holder.tv_cnc_product_name.setText(categoriesAndCareProductsModels.get(position).getCnc_prod_name());
        holder.tv_cnc_product_name.setTypeface(SplashActivity.montserrat_medium);


        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image);
        requestOptions.error(R.drawable.image);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(categoriesAndCareProductsModels.get(position).getCnc_prod_image()).into(holder.iv_product_img_cnc);

    }

    @Override
    public int getItemCount() {
        return categoriesAndCareProductsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_cnc_product_name;
        ImageView iv_product_img_cnc;

        public MyViewHolder(@NonNull View view) {
            super(view);

            tv_cnc_product_name = view.findViewById(R.id.tv_cnc_product_name);
            iv_product_img_cnc = view.findViewById(R.id.iv_product_img_cnc);

        }
    }
}


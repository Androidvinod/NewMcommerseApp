package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.SplashActivity;

import com.example.defaultdemotoken.Model.HomeModel.BrowseCategory;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import java.util.List;

public class BrowseCategoryAdapter extends RecyclerView.Adapter<BrowseCategoryAdapter.MyViewHolder> {
    Context context;
    private List<BrowseCategory> browseCategories;

    public BrowseCategoryAdapter(Context context,List<BrowseCategory> browseCategories) {
        this.context = context;
        this.browseCategories = browseCategories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.browse_category_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BrowseCategory browseCategory = browseCategories.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        Log.e("browse_cat_name_ada",""+browseCategories.get(position).getName());
        myViewHolder.tv_product_name_browse_cats.setText(browseCategories.get(position).getName());
        myViewHolder.tv_product_name_browse_cats.setTypeface(SplashActivity.montserrat_medium);


        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image);
        requestOptions.error(R.drawable.image);

        Log.e("vp_ada",""+browseCategories.get(position).getImage());
        Log.e("vp_ada",""+browseCategory.getImage());

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(browseCategory.getImage()).into(myViewHolder.iv_product_img_browse_cat);

    }

    @Override
    public int getItemCount() {
        return browseCategories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       RoundRectCornerImageView iv_product_img_browse_cat;
       TextView tv_product_name_browse_cats;

        public MyViewHolder(@NonNull View view) {
            super(view);

            iv_product_img_browse_cat = view.findViewById(R.id.iv_product_img_browse_cat);
            tv_product_name_browse_cats = view.findViewById(R.id.tv_product_name_browse_cats);

        }
    }
}

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

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;

import java.util.List;



    public class ProductCategoryAdapter extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.ProductCategoryAdapter.MyViewHolder> {
        Context context;
        private List<HomebannerModel> HomebannerModelList;
        private int lastSelectedPosition = 0;
        public ProductCategoryAdapter(Context context,List<HomebannerModel> HomebannerModelList) {
            this.context = context;
            this.HomebannerModelList = HomebannerModelList;
        }

        @NonNull
        @Override
        public com.example.defaultdemotoken.Adapter.ProductCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.product_category_row, viewGroup, false);
            return new com.example.defaultdemotoken.Adapter.ProductCategoryAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.defaultdemotoken.Adapter.ProductCategoryAdapter.MyViewHolder holder, final int position) {
            final HomebannerModel HomebannerModel = HomebannerModelList.get(position);
            final com.example.defaultdemotoken.Adapter.ProductCategoryAdapter.MyViewHolder myViewHolder = (com.example.defaultdemotoken.Adapter.ProductCategoryAdapter.MyViewHolder) holder;

            holder.tv_cat_name_product.setTypeface(SplashActivity.montserrat_medium);
            holder.lv_product_category_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = position;
                    notifyDataSetChanged();
                }
            });

            if (lastSelectedPosition == position) {
                Log.e("selectedpo_76", "" + lastSelectedPosition);
                myViewHolder.tv_cat_name_product.setTextColor(context.getResources().getColor(R.color.white));
                myViewHolder.lv_product_category_click.setBackground(context.getResources().getDrawable(R.drawable.rounded_green));
            } else {
                myViewHolder.tv_cat_name_product.setTextColor(context.getResources().getColor(R.color.black));
                myViewHolder.lv_product_category_click.setBackground(context.getResources().getDrawable(R.drawable.rounded_black_border));
            }

            //            myViewHolder.lv_HomebannerModel_click.setEnabled(true);
         /*   myViewHolder.tvHomebannerModelName.setText(Html.fromHtml(HomebannerModel.getName()));
            holder.lv_HomebannerModel_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //  myViewHolder.lv_HomebannerModel_click.setEnabled(false);
                            Bundle b=new Bundle();
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            b.putString("categoryid", String.valueOf(HomebannerModel.getId()));
                            b.putString("categoryname",HomebannerModel.getName());
                            b.putString("screen","HomebannerModel");

                            ProductListFragment myFragment = new ProductListFragment();
                            myFragment.setArguments(b);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, myFragment)
                                    .addToBackStack(null).commit();

                        }
                    }, 100);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
            });*/



        }
        public void add(HomebannerModel r) {
            HomebannerModelList.add(r);
            notifyItemInserted(HomebannerModelList.size() - 1);
        }

        public void addAll(List<HomebannerModel> moveResults) {

            for (HomebannerModel result : moveResults) {
                Log.e("debug_127adapter",""+result);
                add(result);
            }
        }



        @Override
        public int getItemCount() {
            return HomebannerModelList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_cat_name_product;
            LinearLayout lv_product_category_click;


            public MyViewHolder(@NonNull View view) {
                super(view);
                tv_cat_name_product = view.findViewById(R.id.tv_cat_name_product);
                lv_product_category_click = view.findViewById(R.id.lv_product_category_click);

            }
        }
    }

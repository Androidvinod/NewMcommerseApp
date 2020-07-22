package com.example.defaultdemotoken.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Fragment.ProductDetailFragment;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;

import java.util.List;



    public class ColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ViewPagerAdapter adapter;
        Context context;
        public static String value;
        private List<HomebannerModel> HomebannerModelList;

        public ColorAdapter(Context context, List<HomebannerModel> bannerModels) {
            this.context = context;
            this.HomebannerModelList = bannerModels;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            RecyclerView.ViewHolder viewHolder = null;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            viewHolder = getViewHolder(parent, inflater);
            return viewHolder;
        }

        @NonNull
        private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
            RecyclerView.ViewHolder viewHolder;
            View v1 = inflater.inflate(R.layout.color_row, parent, false);
            viewHolder = new com.example.defaultdemotoken.Adapter.ColorAdapter.MyViewHolder(v1);
            return viewHolder;
        }

        private static AppCompatActivity unwrap(Context context) {
            while (!(context instanceof Activity) && context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            }

            return (AppCompatActivity) context;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final HomebannerModel HomebannerModel = HomebannerModelList.get(position);
            final com.example.defaultdemotoken.Adapter.ColorAdapter.MyViewHolder myViewHolder = (com.example.defaultdemotoken.Adapter.ColorAdapter.MyViewHolder) holder;

            //   myViewHolder.tv_viewsize_guide.setTypeface(BottomNavigationActivity.poppinslight);
            //  BottomNavigationActivity.Check_String_NULL_Value(myViewHolder.tv_viewsize_guide, HomebannerModel.getValue());
            myViewHolder.lv_color_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //set set selected item value
                            value = myViewHolder.tv_color.getText().toString();
                           // ProductDetailFragment.tv_pdetail_xl.setText("Small");

                            ProductDetailFragment.iv_color_green.setImageDrawable(context.getResources().getDrawable(R.drawable.round_greeeeeen));

                            //  tv_selectsize.setVisibility(View.GONE);
                            //   tv_product_year.setVisibility(View.VISIBLE);
                            //add to bag
                            ProductDetailFragment.colourDialoge.dismiss();
                        }
                    },0);
                }
            });
        }



        @Override
        public int getItemCount() {
            return HomebannerModelList == null ? 0 : HomebannerModelList.size();
        }

        public HomebannerModel getItem(int position) {
            return HomebannerModelList.get(position);
        }

        protected class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_color;
            LinearLayout lv_color_click;


            public MyViewHolder(@NonNull View view) {
                super(view);
                tv_color = itemView.findViewById(R.id.tv_color);

                lv_color_click = itemView.findViewById(R.id.lv_color_click);
            }
        }

    }



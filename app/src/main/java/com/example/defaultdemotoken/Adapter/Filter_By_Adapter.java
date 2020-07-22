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

import com.example.defaultdemotoken.Model.FilterModel.FilterModel;
import com.example.defaultdemotoken.R;

import java.util.List;


    public class Filter_By_Adapter extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.Filter_By_Adapter.MyViewHolder> {
        Context context;
        private List<FilterModel> FilterModelList;

        public Filter_By_Adapter(Context context,List<FilterModel> FilterModelList) {
            this.context = context;
            this.FilterModelList = FilterModelList;
        }

        @NonNull
        @Override
        public com.example.defaultdemotoken.Adapter.Filter_By_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_filter_by, viewGroup, false);
            return new com.example.defaultdemotoken.Adapter.Filter_By_Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.defaultdemotoken.Adapter.Filter_By_Adapter.MyViewHolder holder, int position) {
            final FilterModel filterModel = FilterModelList.get(position);
            final com.example.defaultdemotoken.Adapter.Filter_By_Adapter.MyViewHolder myViewHolder = (com.example.defaultdemotoken.Adapter.Filter_By_Adapter.MyViewHolder) holder;

            holder.tv_filter_by.setTypeface(SplashActivity.montserrat_medium);
            holder.tv_filter_by.setText(filterModel.getLable());

            //            myViewHolder.lv_FilterModel_click.setEnabled(true);
         /*   myViewHolder.tvFilterModelName.setText(Html.fromHtml(FilterModel.getName()));
            holder.lv_FilterModel_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //  myViewHolder.lv_FilterModel_click.setEnabled(false);
                            Bundle b=new Bundle();
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            b.putString("categoryid", String.valueOf(FilterModel.getId()));
                            b.putString("categoryname",FilterModel.getName());
                            b.putString("screen","FilterModel");

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
        public void add(FilterModel r) {
            FilterModelList.add(r);
            notifyItemInserted(FilterModelList.size() - 1);
        }

        public void addAll(List<FilterModel> moveResults) {

            for (FilterModel result : moveResults) {
              //  Log.e("debug_127adapter",""+result);
                add(result);
            }
        }



        @Override
        public int getItemCount() {
            return FilterModelList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_filter_by;
            LinearLayout lv_filter_click;
            View viewsubcat;

            public MyViewHolder(@NonNull View view) {
                super(view);



                lv_filter_click = view.findViewById(R.id.lv_filter_click);
                tv_filter_by = view.findViewById(R.id.tv_filter_by);
                ////    tvFilterModelName = view.findViewById(R.id.tvFilterModelName);
                //   viewsubcat = view.findViewById(R.id.viewsubcat);

            }
        }
    }


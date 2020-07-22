package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Model.FilterModel.SortModel;

import com.example.defaultdemotoken.R;

import java.util.List;


    public class SortByAdapter extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.SortByAdapter.MyViewHolder> {
        Context context;
        private List<SortModel> SortModelList;
        private int lastSelectedPosition ;
        public SortByAdapter(Context context,List<SortModel> SortModelList) {
            this.context = context;
            this.SortModelList = SortModelList;
        }

        @NonNull
        @Override
        public com.example.defaultdemotoken.Adapter.SortByAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_sort_by, viewGroup, false);
            return new com.example.defaultdemotoken.Adapter.SortByAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.defaultdemotoken.Adapter.SortByAdapter.MyViewHolder holder, final int position) {
            final SortModel sortModel = SortModelList.get(position);
            final com.example.defaultdemotoken.Adapter.SortByAdapter.MyViewHolder myViewHolder = (com.example.defaultdemotoken.Adapter.SortByAdapter.MyViewHolder) holder;

            holder.rad_sort.setTypeface(SplashActivity.montserrat_medium);
            holder.rad_sort.setText(sortModel.getLabel());

            //            myViewHolder.lv_SortModel_click.setEnabled(true);
         /*   myViewHolder.tvSortModelName.setText(Html.fromHtml(SortModel.getName()));
            holder.lv_SortModel_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //  myViewHolder.lv_SortModel_click.setEnabled(false);
                            Bundle b=new Bundle();
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            b.putString("categoryid", String.valueOf(SortModel.getId()));
                            b.putString("categoryname",SortModel.getName());
                            b.putString("screen","SortModel");

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


            holder.rad_sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                   // Log.e("debug_166", "" + sortmodel.getLabel());
                    lastSelectedPosition = position;
                    notifyDataSetChanged();
                  //  RefineFragment.selectedSort=sortlist.get(position).getName();

                }
            });

            if (lastSelectedPosition == position) {
                Log.e("selectedpo_76", "" + lastSelectedPosition);
                holder.rad_sort.setChecked(true);
              //  RefineFragment.selectedSort=sortlist.get(position).getName();
            } else {
                holder.rad_sort.setChecked(false);
            }

        }
        public void add(SortModel r) {
            SortModelList.add(r);
            notifyItemInserted(SortModelList.size() - 1);
        }

        public void addAll(List<SortModel> moveResults) {

            for (SortModel result : moveResults) {
                Log.e("debug_127adapter",""+result);
                add(result);
            }
        }



        @Override
        public int getItemCount() {
            return SortModelList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_product_new,tv_product_name,tv_product_price;
            LinearLayout lv_SortModel_click;
            View viewsubcat;
            RadioButton rad_sort;

            public MyViewHolder(@NonNull View view) {
                super(view);


                rad_sort = view.findViewById(R.id.rad_sort);

                ////    tvSortModelName = view.findViewById(R.id.tvSortModelName);
                //   viewsubcat = view.findViewById(R.id.viewsubcat);

            }
        }
    }




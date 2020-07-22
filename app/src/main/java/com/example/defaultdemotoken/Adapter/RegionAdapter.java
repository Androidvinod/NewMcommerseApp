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
import com.example.defaultdemotoken.Fragment.EditAddressFragment;
import com.example.defaultdemotoken.Model.CountryModel.AvailableRegion;
import com.example.defaultdemotoken.Model.CountryModel.CountryListModel;
import com.example.defaultdemotoken.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import static com.example.defaultdemotoken.Fragment.EditAddressFragment.tv_choose_region;


public class RegionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        private List<AvailableRegion> countryModelList;
        String screen;



        public RegionAdapter(Context context,String screen) {
            this.context = context;
            this.screen = screen;
            this.countryModelList =new ArrayList<>();
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
            View v1 = inflater.inflate(R.layout.row_city, parent, false);
            viewHolder = new com.example.defaultdemotoken.Adapter.RegionAdapter.MyViewHolder(v1);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            final AvailableRegion country = countryModelList.get(position);
            com.example.defaultdemotoken.Adapter.RegionAdapter.MyViewHolder myViewHolder = (com.example.defaultdemotoken.Adapter.RegionAdapter.MyViewHolder) holder;
            myViewHolder.tv_country_name.setTypeface(SplashActivity.montserrat_bold);
            myViewHolder.tv_country_name.setText(country.getName());

            myViewHolder.lv_city_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_choose_region.setText(country.getName());
                    tv_choose_region.setTextColor(context.getResources().getColor(R.color.black));
                    EditAddressFragment.region=countryModelList.get(position).getName();
                    EditAddressFragment.region_id=countryModelList.get(position).getId();

                    if(screen.equalsIgnoreCase("countryAdapter"))
                    {
                        CountryAdapter.dialog.dismiss();
                    }else if(screen.equalsIgnoreCase("EditAddressFragment"))
                    {
                        EditAddressFragment.regiondialog.dismiss();
                    }


                    Log.e("debug_72","="+countryModelList.get(position).getName());

                }
            });



        }


        @Override
        public int getItemCount() {
            return countryModelList == null ? 0 : countryModelList.size();
        }

        public void addAll(List<AvailableRegion> datumListt) {
            for (AvailableRegion result : datumListt) {
                Log.e("debug_127adapter", "" + result);
                add(result);
            }
        }
        public void add(AvailableRegion r) {
            countryModelList.add(r);
            notifyItemInserted(countryModelList.size() - 1);
        }
        public AvailableRegion getItem(int position) {
            Log.e("pos_galadaadapter", "" + position);
            return countryModelList.get(position);
        }

        protected class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_country_name;
            LinearLayout lv_city_click;

            public MyViewHolder(@NonNull View view) {
                super(view);
                tv_country_name = itemView.findViewById(R.id.tv_country_name);
                lv_city_click = itemView.findViewById(R.id.lv_city_click);

            }
        }

    }


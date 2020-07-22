package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Fragment.EditAddressFragment;
import com.example.defaultdemotoken.Model.Country;
import com.example.defaultdemotoken.Model.CountryModel.AvailableRegion;
import com.example.defaultdemotoken.Model.CountryModel.CountryListModel;
import com.example.defaultdemotoken.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import static com.example.defaultdemotoken.Fragment.EditAddressFragment.lv_main_region;
import static com.example.defaultdemotoken.Fragment.EditAddressFragment.lv_region;
import static com.example.defaultdemotoken.Fragment.EditAddressFragment.region_id;
import static com.example.defaultdemotoken.Fragment.EditAddressFragment.tv_choose_country;
import static com.example.defaultdemotoken.Fragment.EditAddressFragment.tv_choose_region;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LinearLayout lv_country_close,lv_progress_country;
    RegionAdapter regionAdapter;
    Context context;
    private List<CountryListModel> countryModelList;
    RecyclerView rv_country;
    List<AvailableRegion> availableRegionList;
    public static BottomSheetDialog dialog;
    public CountryAdapter(Context context) {
        this.context = context;
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
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final CountryListModel country = countryModelList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv_country_name.setTypeface(SplashActivity.montserrat_bold);
        myViewHolder.tv_country_name.setText(country.getFullNameEnglish());

        myViewHolder.lv_city_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_choose_country.setText(country.getFullNameEnglish());
                tv_choose_country.setTextColor(context.getResources().getColor(R.color.black));
                tv_choose_region.setTextColor(context.getResources().getColor(R.color.greey));
                tv_choose_region.setText(context.getResources().getString(R.string.chooseregion));
                EditAddressFragment.countryId=countryModelList.get(position).getId();
                EditAddressFragment.dialog.dismiss();
                region_id="";
                Log.e("debug_72","="+countryModelList.get(position).getAvailableRegions());

                if(countryModelList.get(position).getAvailableRegions()==null )
                {
                    lv_main_region.setVisibility(View.GONE);
                }else {
                    lv_main_region.setVisibility(View.VISIBLE);
                    availableRegionList=countryModelList.get(position).getAvailableRegions();
                }
            }
        });

        lv_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regionDialog(availableRegionList);

            }
        });


    }
    private void regionDialog(List<AvailableRegion> availableRegionList) {

        dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.choose_country);
        dialog.show();
        rv_country = dialog.findViewById(R.id.rv_country);
        lv_country_close = dialog.findViewById(R.id.lv_country_close);
        lv_progress_country = dialog.findViewById(R.id.lv_progress_country);
        lv_country_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        regionAdapter = new RegionAdapter(context,"countryAdapter");
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_country.setLayoutManager(layoutManager);
        rv_country.setAdapter(regionAdapter);

        regionAdapter.addAll(availableRegionList);
        if (CheckNetwork.isNetworkAvailable(context)) {
           // CallCountrylistApi("button click");
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }
      //  Log.e("debug_country","f"+countryId);
    }


    @Override
    public int getItemCount() {
        return countryModelList == null ? 0 : countryModelList.size();
    }

    public void addAll(List<CountryListModel> datumListt) {
        for (CountryListModel result : datumListt) {
          //  Log.e("debug_127adapter", "" + result);
            add(result);
        }
    }
    public void add(CountryListModel r) {
        countryModelList.add(r);
        notifyItemInserted(countryModelList.size() - 1);
    }
    public CountryListModel getItem(int position) {
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





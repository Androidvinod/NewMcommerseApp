package com.example.defaultdemotoken.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Fragment.ProductDetailFragment;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;

import java.util.List;


public class ProductSizeGuideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ViewPagerAdapter adapter;
    Context context;
    public static String value;
    private List<HomebannerModel> HomebannerModelList;

    public ProductSizeGuideAdapter(Context context, List<HomebannerModel> bannerModels) {
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
        View v1 = inflater.inflate(R.layout.product_size_text_row, parent, false);
        viewHolder = new MyViewHolder(v1);
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
        final MyViewHolder myViewHolder = (MyViewHolder) holder;

     //   myViewHolder.tv_viewsize_guide.setTypeface(BottomNavigationActivity.poppinslight);
      //  BottomNavigationActivity.Check_String_NULL_Value(myViewHolder.tv_viewsize_guide, HomebannerModel.getValue());
        myViewHolder.lv_size_guide_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //set set selected item value
                        value = myViewHolder.tv_viewsize_guide.getText().toString();
                        ProductDetailFragment.tv_pdetail_xl.setText("Small");
                      //  tv_selectsize.setVisibility(View.GONE);
                     //   tv_product_year.setVisibility(View.VISIBLE);
                        //add to bag
                        ProductDetailFragment.dialog.dismiss();
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
        TextView tv_viewsize_guide;
        LinearLayout lv_size_guide_click;


        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_viewsize_guide = itemView.findViewById(R.id.tv_viewsize_guide);

            lv_size_guide_click = itemView.findViewById(R.id.lv_size_guide_click);
        }
    }

}




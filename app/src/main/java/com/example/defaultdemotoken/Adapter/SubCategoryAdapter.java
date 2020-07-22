package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.defaultdemotoken.Fragment.ProductListFragment;
import com.example.defaultdemotoken.Model.SubCategory;
import com.example.defaultdemotoken.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    Context context;
    private List<SubCategory> SubCategoryList;

    public SubCategoryAdapter(FragmentActivity context) {
        this.context = context;
        SubCategoryList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.subcategory_row, viewGroup, false);
        return new SubCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyViewHolder holder, int position) {
        final SubCategory SubCategory = SubCategoryList.get(position);
        final SubCategoryAdapter.MyViewHolder myViewHolder = (SubCategoryAdapter.MyViewHolder) holder;
        myViewHolder.lv_SubCategory_click.setEnabled(true);
        myViewHolder.tvSubCategoryName.setText(Html.fromHtml(SubCategory.getName()));
        holder.lv_SubCategory_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                      //  myViewHolder.lv_SubCategory_click.setEnabled(false);
                        Bundle b=new Bundle();

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        b.putString("categoryid", String.valueOf(SubCategory.getId()));
                        b.putString("categoryname",SubCategory.getName());
                        b.putString("screen","SubCategory");

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
        });


        if(position==getItemCount()-1)
        {
            myViewHolder.viewsubcat.setVisibility(View.GONE);
        }else {
            myViewHolder.viewsubcat.setVisibility(View.VISIBLE);
        }

    }
    public void add(SubCategory r) {
        SubCategoryList.add(r);
        notifyItemInserted(SubCategoryList.size() - 1);
    }

    public void addAll(List<SubCategory> moveResults) {

        for (SubCategory result : moveResults) {
            Log.e("debug_127adapter",""+result);
            add(result);
        }
    }



    @Override
    public int getItemCount()

    {
        return SubCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubCategoryName;
        LinearLayout lv_SubCategory_click;
        View viewsubcat;

        public MyViewHolder(@NonNull View view) {
            super(view);


            lv_SubCategory_click = view.findViewById(R.id.lv_subcategory_click);
            tvSubCategoryName = view.findViewById(R.id.tvSubCategoryName);
            viewsubcat = view.findViewById(R.id.viewsubcat);

        }
    }
}




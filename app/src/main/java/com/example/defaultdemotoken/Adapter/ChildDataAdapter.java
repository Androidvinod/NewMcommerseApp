package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Fragment.SubCategoryFragment;
import com.example.defaultdemotoken.Model.ChildData;
import com.example.defaultdemotoken.Fragment.ProductListFragment;
import com.example.defaultdemotoken.Model.SubCategory;
import com.example.defaultdemotoken.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;


public class ChildDataAdapter extends RecyclerView.Adapter<ChildDataAdapter.MyViewHolder> {

    Context context;
    private List<ChildData> ChildDataList;

    public ChildDataAdapter(Context context) {
        this.context = context;
        ChildDataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ChildData childData = ChildDataList.get(position);
        holder.tv_category.setText(childData.getName());

        if (ChildDataList.get(position).getChildrenData().size() != 0) {
            holder.iv_cat_side_arrow.setVisibility(View.VISIBLE);
        } else {
            holder.iv_cat_side_arrow.setVisibility(View.GONE);
        }

        holder.cardview_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

            /*    if (ChildDataList.get(position).getChildrenData().size() != 0) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            Bundle b = new Bundle();
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            b.putString("categoryid", String.valueOf(ChildDataList.get(position).getId()));
                            b.putString("categoryname", ChildDataList.get(position).getName());
                            b.putSerializable("subCatarraylist", (Serializable) ChildDataList.get(position).getChildrenData());

                            SubCategoryFragment myFragment = new SubCategoryFragment();
                            myFragment.setArguments(b);
                            activity.getSupportFragmentManager().beginTransaction()
                                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                                    .commit();
                            if (drawer.isDrawerOpen(GravityCompat.START)) {
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }
                    }, 50);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                } else {*/

                    Bundle b = new Bundle();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    b.putString("categoryid", String.valueOf(ChildDataList.get(position).getId()));
                    b.putString("categoryname", ChildDataList.get(position).getName());
                    b.putSerializable("subCatarraylist", (Serializable) ChildDataList.get(position).getChildrenData());

                    ProductListFragment myFragment = new ProductListFragment();
                    myFragment.setArguments(b);
                    activity.getSupportFragmentManager().beginTransaction()

                            .addToBackStack(null).replace(R.id.framlayout, myFragment)
                            .commit();
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }


          /*  }*/
        });
    }

    public void addAll(List<ChildData> categories) {
        for (ChildData result : categories) {
            add(result);
        }
    }

    public void add(ChildData r) {
        ChildDataList.add(r);
        notifyItemInserted(ChildDataList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return ChildDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_category;
        LinearLayout cardview_click;
        ImageView iv_cat_side_arrow;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_category = view.findViewById(R.id.tv_category);
            cardview_click = view.findViewById(R.id.cardview_click);
            iv_cat_side_arrow = view.findViewById(R.id.iv_cat_side_arrow);
        }
    }
}





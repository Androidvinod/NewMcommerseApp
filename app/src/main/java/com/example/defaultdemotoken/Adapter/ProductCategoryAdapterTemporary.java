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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Fragment.ProductListFragment;
import com.example.defaultdemotoken.Fragment.SubCategoryFragment;
import com.example.defaultdemotoken.Model.SubCategory;
import com.example.defaultdemotoken.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

public class ProductCategoryAdapterTemporary extends RecyclerView.Adapter<ProductCategoryAdapterTemporary.MyViewHolder> {
    Context context;
    private List<SubCategory> SubCategoryList;
    public  List<SubCategory> catSubarraylist;

    /*public ProductCategoryAdapterTemporary(FragmentActivity context) {
        this.context = context;
        SubCategoryList = new ArrayList<>();
    }*/

    public ProductCategoryAdapterTemporary(FragmentActivity context, List<SubCategory> SubCategoryList,List<SubCategory> catSubarraylist) {
        this.context = context;
        this.SubCategoryList = SubCategoryList;
        this.catSubarraylist = catSubarraylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_category_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SubCategory subCategory = SubCategoryList.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        //myViewHolder.lv_SubCategory_click.setEnabled(true);
        holder.lv_see_all_categories_ada.setVisibility(View.GONE);

        if (position == SubCategoryList.size() - 1) {
            holder.lv_see_all_categories_ada.setVisibility(View.VISIBLE);
        }

        myViewHolder.tv_cat_name_product.setText(Html.fromHtml(subCategory.getName()));

        holder.lv_see_all_categories_ada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putSerializable("subCatarraylist", (Serializable)catSubarraylist);
                b.putString("categoryname",ProductListFragment.catname);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                SubCategoryFragment myFragment = new SubCategoryFragment();
                myFragment.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null).replace(R.id.framlayout, myFragment)
                        .commit();

            }
        });

        /*holder.lv_SubCategory_click.setOnClickListener(new View.OnClickListener() {
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
        });*/

        /*if(position==getItemCount()-1)
        {
            myViewHolder.viewsubcat.setVisibility(View.GONE);
        }else {
            myViewHolder.viewsubcat.setVisibility(View.VISIBLE);
        }*/

    }

    public void add(SubCategory r) {
        SubCategoryList.add(r);
        notifyItemInserted(SubCategoryList.size() - 1);
    }

    public void addAll(List<SubCategory> moveResults) {

        for (SubCategory result : moveResults) {
            Log.e("debug_127adapter", "" + result);
            add(result);
        }
    }


    @Override
    public int getItemCount() {
        return SubCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_cat_name_product;
        LinearLayout lv_see_all_categories_ada;

        //TextView tvSubCategoryName;
        //LinearLayout lv_SubCategory_click;
        //View viewsubcat;

        public MyViewHolder(@NonNull View view) {
            super(view);

            tv_cat_name_product = view.findViewById(R.id.tv_cat_name_product);
            lv_see_all_categories_ada = view.findViewById(R.id.lv_see_all_categories_ada);
            /*lv_SubCategory_click = view.findViewById(R.id.lv_subcategory_click);
            tvSubCategoryName = view.findViewById(R.id.tvSubCategoryName);
            viewsubcat = view.findViewById(R.id.viewsubcat);*/

        }
    }
}

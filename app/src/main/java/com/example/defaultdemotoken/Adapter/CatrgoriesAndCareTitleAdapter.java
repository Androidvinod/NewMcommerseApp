package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Model.CategoriesAndCareTitleModel;
import com.example.defaultdemotoken.R;

import java.util.List;

public class CatrgoriesAndCareTitleAdapter extends RecyclerView.Adapter<CatrgoriesAndCareTitleAdapter.MyViewHolder> {
    Context context;
    private List<CategoriesAndCareTitleModel> categoriesAndCareTitleModels;
    private int lastSelectedPosition = 0;

    public CatrgoriesAndCareTitleAdapter(FragmentActivity context, List<CategoriesAndCareTitleModel> categoriesAndCareTitleModels) {
        this.context = context;
        this.categoriesAndCareTitleModels = categoriesAndCareTitleModels;
    }

    @NonNull
    @Override
    public CatrgoriesAndCareTitleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_cnc_title, viewGroup, false);
        return new CatrgoriesAndCareTitleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatrgoriesAndCareTitleAdapter.MyViewHolder holder, final int position) {
        final CategoriesAndCareTitleModel gourmetTitleModel = categoriesAndCareTitleModels.get(position);

        holder.cnc_title.setTypeface(SplashActivity.montserrat_medium);

        holder.lv_cnc_title_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                notifyDataSetChanged();
            }
        });

        if (lastSelectedPosition == position) {
            Log.e("selectedpo_76", "" + lastSelectedPosition);
            holder.cnc_title.setTextColor(context.getResources().getColor(R.color.white));
            holder.lv_cnc_title_click.setBackground(context.getResources().getDrawable(R.drawable.rounded_green));
        } else {
            holder.cnc_title.setTextColor(context.getResources().getColor(R.color.black));
            holder.lv_cnc_title_click.setBackground(context.getResources().getDrawable(R.drawable.rounded_black_border));
        }
    }

    @Override
    public int getItemCount() {
        return categoriesAndCareTitleModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cnc_title;
        LinearLayout lv_cnc_title_click;

        public MyViewHolder(@NonNull View view) {
            super(view);

            cnc_title = view.findViewById(R.id.cnc_title);
            lv_cnc_title_click = view.findViewById(R.id.lv_cnc_title_click);

        }
    }
}




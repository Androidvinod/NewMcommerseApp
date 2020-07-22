package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Model.HomeModel.BestSelling;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReviewAdapter extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.ReviewAdapter.MyViewHolder> {
        Context context;
        private List<BestSelling> bestSellingList;

        public ReviewAdapter(Context context,List<BestSelling> bestSellingList) {
            this.context = context;
            this.bestSellingList = bestSellingList;
        }

        @NonNull
        @Override
        public com.example.defaultdemotoken.Adapter.ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.reviewlist_row, viewGroup, false);
            return new com.example.defaultdemotoken.Adapter.ReviewAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.defaultdemotoken.Adapter.ReviewAdapter.MyViewHolder holder, int position) {
            final BestSelling bestSelling = bestSellingList.get(position);
            final com.example.defaultdemotoken.Adapter.ReviewAdapter.MyViewHolder myViewHolder = (com.example.defaultdemotoken.Adapter.ReviewAdapter.MyViewHolder) holder;

           holder.tv_review_name.setTypeface(SplashActivity.montserrat_bold);
           holder.tv_review_desc.setTypeface(SplashActivity.montserrat_regular);

            //          myViewHolder.tv_product_name_bestselling.setText(bestSellingList.get(position).getName());
           // myViewHolder.tv_product_price_bestselling.setVisibility(View.GONE);

            //Log.e("best_selling_name",""+bestSellingList.get(position).getName());
            //Log.e("best_selling_rats",""+bestSellingList.get(position).getRating());

            final RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image);
            requestOptions.error(R.drawable.image);

         /*   Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(bestSellingList.get(position).getImage()).into(myViewHolder.iv_product_img_bestselling);
*/
//            myViewHolder.ratingbar_bestselling.setRating(Float.parseFloat(bestSellingList.get(position).getRating()));

        }

        /*public void add(HomebannerModel r) {
            HomebannerModelList.add(r);
            notifyItemInserted(HomebannerModelList.size() - 1);
        }

        public void addAll(List<HomebannerModel> moveResults) {

            for (HomebannerModel result : moveResults) {
                Log.e("debug_127adapter",""+result);
                add(result);
            }
        }*/

        @Override
        public int getItemCount() {
            return bestSellingList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            CircleImageView iv_image;
            TextView tv_review_name,tv_review_desc;
            RatingBar ratingbar_review;

            public MyViewHolder(@NonNull View view) {
                super(view);
                iv_image = view.findViewById(R.id.iv_image);
                tv_review_name = view.findViewById(R.id.tv_review_name);
                ratingbar_review = view.findViewById(R.id.ratingbar_review);
                tv_review_desc = view.findViewById(R.id.tv_review_desc);

            }
        }
    }



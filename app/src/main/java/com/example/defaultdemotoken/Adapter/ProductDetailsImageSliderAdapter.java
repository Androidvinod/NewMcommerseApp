package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Model.ProductDetailModel.MediaGalleryEntry;
import com.example.defaultdemotoken.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsImageSliderAdapter extends RecyclerView.Adapter<ProductDetailsImageSliderAdapter.MyViewHolder> {
    private Context context;
    private List<MediaGalleryEntry> models;

    public ProductDetailsImageSliderAdapter(Context context, List<MediaGalleryEntry> mediaGalleryEntries) {
        this.context = context;
        this.models = mediaGalleryEntries;
    }

    @Override
    public ProductDetailsImageSliderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_details_imageslider_m_row, parent, false);

        return new ProductDetailsImageSliderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductDetailsImageSliderAdapter.MyViewHolder holder, final int position) {

        final MediaGalleryEntry MediaGalleryEntry = models.get(position);

        Log.e("imageurl_44",""+MediaGalleryEntry.getFile());
        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image);
        requestOptions.error(R.drawable.image);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load("http://app.demoproject.info/pub/media/catalog/product" +MediaGalleryEntry.getFile()).into(holder.iv_productimage_m);

        //  Glide.with(context).load(MediaGalleryEntry.getUrl()).into(holder.iv_productimage);
    }
    @Override
    public int getItemCount() {
        return models.size();
    }
    public void  addAll(List<MediaGalleryEntry> media1) {
        for (MediaGalleryEntry MediaGalleryEntry : media1) {
            add(MediaGalleryEntry);
        }
    }

    private  void add(MediaGalleryEntry r) {
        models.add(r);
        notifyItemInserted(models.size() - 1);

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_productimage_m;

        public MyViewHolder(View view) {
            super(view);
            iv_productimage_m=view.findViewById(R.id.iv_productimage_m);
        }
    }
}
    


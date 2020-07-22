package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Rounded;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<HomebannerModel> homebannerModelList;
    public ViewPagerAdapter(Context context,    List<HomebannerModel> HomebannerModelList) {
        this.context = context;
        this.homebannerModelList = HomebannerModelList;
    }

    @Override
    public int getCount() {
        return homebannerModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.first_home_banner_row, null);
        Rounded imageView = (Rounded) view.findViewById(R.id.home_image);
        //imageView.setImageResource(images[position]);
       //imageView.setImageResource(Integer.parseInt(homebannerModelList.get(position).getIma()));

        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image);
        requestOptions.error(R.drawable.image);

      //  Log.e("vp_ada",""+homebannerModelList.get(position).getIma());

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(homebannerModelList.get(position).getIma()).into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
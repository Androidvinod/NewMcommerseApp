package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.BestSellingAdapter;
import com.example.defaultdemotoken.Adapter.BrowseCategoryAdapter;
import com.example.defaultdemotoken.Adapter.HomeCategoryAdapter;
import com.example.defaultdemotoken.Adapter.NewArrivalsAdapter;
import com.example.defaultdemotoken.Adapter.ViewPagerAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.EnhancedWrapContentViewPager;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.HomeModel.BestSelling;
import com.example.defaultdemotoken.Model.HomeModel.BrowseCategory;
import com.example.defaultdemotoken.Model.HomeModel.NewArrrivalsModel;
import com.example.defaultdemotoken.Model.HomebannerModel;

import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.example.defaultdemotoken.Rounded;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.bottom_navigation;
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public static Toolbar toolbar_home;
    SearchView searchView;
    TextView tv_sale, tv_home_titlee, tv_search_title, tv_browse_cat, tv_seeall_browse, tv_best_selling, tv_best_selling_seeall, tv_new_see_all, tv_new_arrivals, tv_home_off;
    public static ApiInterface apiInterface;
    public static ApiInterface apiInterface1;
    //HomeTopSliderAdapter homeTopSliderAdapter;
    View v;
    RecyclerView recv_categories, recv_bestselling, recv_newarrivals;

    BrowseCategoryAdapter browseCategoryAdapter;
    BestSellingAdapter bestSellingAdapter;
    ViewPagerAdapter viewPagerAdapter;
    NewArrivalsAdapter newArrivalsAdapter;

    private List<HomebannerModel> imagslidertop = new ArrayList<>();
    private List<BrowseCategory> browseCategories = new ArrayList<>();
    private List<BestSelling> bestSellingList = new ArrayList<>();
    private List<NewArrrivalsModel> newArrrivalsModelArrayList = new ArrayList<>();

    NestedScrollView home_nested;
    LinearLayout lv_home_progress,lv_search_home, lv_see_all_categories;
    EnhancedWrapContentViewPager viewPager;
    CircleIndicator indicatorr;
    Rounded iv_middle_block, iv_bottom_block;
    String browse_cat_array;
    public static JSONArray browse_category;
    private List<Integer> wishlitProductidList = new ArrayList<>();
    private List<Integer> wishlitItemId = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container, false);
        bottom_navigation.getMenu().getItem(2).setChecked(true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface1 = ApiClient.getClient().create(ApiInterface.class);
        setHasOptionsMenu(true);
        AllocateMemory(v);
        setFontFamily();

        hideKeyboard(getActivity());
        //SearchView_Focus();

        if (getActivity() != null) {
            Log.e("debu_home", "=");
            ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_home);
            ((NavigationActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
            ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

            toolbar_home.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("debu_home", "=kj");
                    ((NavigationActivity) getActivity()).getmDrawerLayout().openDrawer(GravityCompat.START);
                    //drawer.openDrawer(GravityCompat.START);
                }
            });

            toolbar_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("debu_home", "=kkk");
                    ((NavigationActivity) getActivity()).getmDrawerLayout().openDrawer(GravityCompat.START);
                }
            });
        }

        String mystring = new String("USE CODE : FASH20");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        tv_sale.setText(content);
        AttachRecyclerview();

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            if (Login_preference.getLogin_flag(getActivity()).equalsIgnoreCase("1")) {
                callWishistApi();
            } else {
                CallHommepageApi();
            }

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }

        lv_see_all_categories.setOnClickListener(this);
        lv_search_home.setOnClickListener(this);

        // setDynamicbackgroundButton();
        makeRoundCorner(getActivity().getResources().getColor(R.color.greylight), 15, lv_see_all_categories, 1, getActivity().getResources().getColor(R.color.greylight));

        return v;
    }

    private void CallHommepageApi() {
        bestSellingList.clear();
        browseCategories.clear();
        newArrrivalsModelArrayList.clear();
        lv_home_progress.setVisibility(View.VISIBLE);
        home_nested.setVisibility(View.GONE);
        callhomepagetopbannre().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                lv_home_progress.setVisibility(View.GONE);
                home_nested.setVisibility(View.VISIBLE);
                Log.e("homepage_response", "" + response.body());

                if (response.isSuccessful() || response.code() == 200) {
                    JSONArray itemArray = null;
                    try {
                        itemArray = new JSONArray(response.body().string());
                        Log.e("homepage_array_response", "" + itemArray);

                        JSONObject jsonObject = itemArray.getJSONObject(0);
                        Log.e("homepage_obj_response", "" + jsonObject);

                        JSONArray top_block = jsonObject.getJSONArray("topBlock");
                        Log.e("homepage_top_block", "" + top_block);

                        for (int i = 0; i < top_block.length(); i++) {

                            String value = top_block.getString(i);
                            Log.e("valss", "" + value);

                            imagslidertop.add((new HomebannerModel(value)));

                        }

                        viewPagerAdapter = new ViewPagerAdapter(getActivity(), imagslidertop);
                        viewPager.setAdapter(viewPagerAdapter);
                        //indicatorr.createIndicators(5,0);
                        indicatorr.setViewPager(viewPager);
                        viewPagerAdapter.registerDataSetObserver(indicatorr.getDataSetObserver());
                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 2000);

                        browse_category = jsonObject.getJSONArray("category");
                        browse_cat_array = String.valueOf(jsonObject.getJSONArray("category"));
                        Log.e("homepage_category", "" + browse_category);
                        Log.e("homepage_category_st", "" + browse_cat_array);

                        for (int i = 0; i < browse_category.length(); i++) {
                            try {
                                JSONObject vals = browse_category.getJSONObject(i);

                                String browse_cat_name = vals.getString("name");
                                Log.e("browse_cat_home_name", "" + browse_cat_name);

                                browseCategories.add(new BrowseCategory("id", vals.getString("name"), vals.getString("image")));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                browseCategoryAdapter.notifyItemChanged(i);

                            }
                        }

                        JSONArray bestSellers = jsonObject.getJSONArray("bestSellers");
                        Log.e("homepage_bestSellers", "" + bestSellers);

                        for (int i = 0; i < bestSellers.length(); i++) {
                            try {
                                JSONObject vals = bestSellers.getJSONObject(i);

                                String bestselling_name = vals.getString("name");
                                Log.e("_cat_home_name", "" + bestselling_name);

                                String isWishliste,wishlist_item_id = null;

                                int id= Integer.parseInt(bestSellers.getJSONObject(i).optString("id"));
                                Log.e("deebug_378", "=" +id);
                                if(wishlitProductidList.contains(id))
                                {
                                    Log.e("if", "=" +id);
                                    Log.e("if_item_id", "=" + wishlitProductidList.indexOf(id));
                                    int pos=wishlitProductidList.indexOf(id);
                                    if(wishlitItemId.size()>0)
                                    {
                                        wishlist_item_id= String.valueOf(wishlitItemId.get(pos));
                                        Log.e("wishlist_item_id_3317", "=" + wishlist_item_id);
                                    }
                                    isWishliste="true";

                                    bestSellingList.add(new BestSelling(
                                            vals.getString("name"),
                                            vals.getString("image"),
                                            vals.getString("sku"),
                                            vals.getString("id"),
                                            vals.getString("rating_count"),
                                            vals.getString("price"),
                                            isWishliste,
                                            wishlist_item_id));

                                }else{
                                    wishlist_item_id= "0";
                                    isWishliste="false";
                                    Log.e("else", "=" +bestSellers.getJSONObject(i).optString("id"));


                                    bestSellingList.add(new BestSelling(
                                            vals.getString("name"),
                                            vals.getString("image"),
                                            vals.getString("sku"),
                                            vals.getString("id"),
                                            vals.getString("rating_count"),
                                            vals.getString("price"),isWishliste,wishlist_item_id));
                                }



                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                bestSellingAdapter.notifyItemChanged(i);

                            }
                        }

                        JSONArray middleBlock = jsonObject.getJSONArray("middleBlock");
                        Log.e("homepage_middleBlock", "" + middleBlock);

                        for (int i = 0; i < middleBlock.length(); i++) {

                            String value = middleBlock.getString(i);

                            Log.e("valss", "" + value);

                            final RequestOptions requestOptions = new RequestOptions();
                            requestOptions.placeholder(R.drawable.image);
                            requestOptions.error(R.drawable.image);

                            if (getActivity() != null) {
                                Glide.with(getActivity())
                                        .setDefaultRequestOptions(requestOptions)
                                        .load(value).into(iv_middle_block);
                            }
                        }

                        JSONArray newProducts = jsonObject.getJSONArray("newProducts");
                        Log.e("homepage_newProducts", "" + newProducts);

                        for (int i = 0; i < newProducts.length(); i++) {
                            try {
                                JSONObject vals = newProducts.getJSONObject(i);

                                String bestselling_name = vals.getString("name");
                                Log.e("nes_cat_home_name", "" + bestselling_name);

                                String isWishliste,wishlist_item_id = null;

                                int id= Integer.parseInt(bestSellers.getJSONObject(i).optString("id"));
                                Log.e("deebug_378", "=" +id);
                                if(wishlitProductidList.contains(id))
                                {
                                    Log.e("if", "=" +id);
                                    Log.e("if_item_id", "=" + wishlitProductidList.indexOf(id));
                                    int pos=wishlitProductidList.indexOf(id);
                                    if(wishlitItemId.size()>0)
                                    {
                                        wishlist_item_id= String.valueOf(wishlitItemId.get(pos));
                                        Log.e("wishlist_item_id_3317", "=" + wishlist_item_id);
                                    }
                                    isWishliste="true";

                                    newArrrivalsModelArrayList.add(new NewArrrivalsModel(
                                            vals.getString("name"),
                                            vals.getString("image"),
                                            vals.getString("sku"),
                                            vals.getString("id"),
                                            vals.getString("rating_count"),
                                            vals.getString("price"),isWishliste,wishlist_item_id));

                                }else{
                                    wishlist_item_id= "0";
                                    isWishliste="false";
                                    Log.e("else", "=" +bestSellers.getJSONObject(i).optString("id"));


                                    newArrrivalsModelArrayList.add(new NewArrrivalsModel(
                                            vals.getString("name"),
                                            vals.getString("image"),
                                            vals.getString("sku"),
                                            vals.getString("id"),
                                            vals.getString("rating_count"),
                                            vals.getString("price"),isWishliste,wishlist_item_id));
                                }




                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                newArrivalsAdapter.notifyItemChanged(i);
                            }
                        }
                        JSONArray bottomBlock = jsonObject.getJSONArray("bottomBlock");
                        Log.e("homepage_bottomBlock", "" + middleBlock);
                        for (int i = 0; i < bottomBlock.length(); i++) {

                            String value = bottomBlock.getString(i);

                            Log.e("valss", "" + value);

                            final RequestOptions requestOptions = new RequestOptions();
                            requestOptions.placeholder(R.drawable.image);
                            requestOptions.error(R.drawable.image);

                            if(getActivity()!=null)
                            {
                                Glide.with(getActivity())
                                        .setDefaultRequestOptions(requestOptions)
                                        .load(value).into(iv_bottom_block);
                            }
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        lv_home_progress.setVisibility(View.GONE);
                        home_nested.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug_175125", "pages: " + t);
            }
        });
    }

    private void callWishistApi() {
        lv_home_progress.setVisibility(View.VISIBLE);
        home_nested.setVisibility(View.GONE);
        getwishlistdata().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_favourite", "" + response.body());
                Log.e("response_favourite", "" + response);
                ResponseBody getFavouriteslist = response.body();
                if (response.isSuccessful() || response.code() == 200) {
                    lv_home_progress.setVisibility(View.GONE);
                    home_nested.setVisibility(View.GONE);
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response.body().string());
                        Log.e("jsonarray", "=" + jsonArray);
                        Log.e("jsonarraylength", "=" + jsonArray.length());
                        //  Log.e("jsonarray66ss", "=" +jsonArray.getJSONObject(0).getJSONObject("product"));

                        if (jsonArray.length() == 0) {
                            CallHommepageApi();
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.e("product_id", "=" + jsonObject.getString("product_id"));
                                    wishlitProductidList.add(jsonObject.getInt("product_id"));
                                    wishlitItemId.add(jsonObject.getInt("wishlist_item_id"));
                                    Log.e("price", "=" + jsonObject.getJSONObject("product").optString("price"));
                                    Log.e("name", "=" + jsonObject.getJSONObject("product").optString("name"));
                                    Log.e("special_price", "=" + jsonObject.getJSONObject("product").optString("special_price"));
                                    Log.e("thumbnail", "=" + jsonObject.getJSONObject("product").optString("thumbnail"));
                                } catch (Exception e) {
                                    Log.e("exception22", "=" + e.getLocalizedMessage());
                                }
                            }

                            CallHommepageApi();
                            // wishListAdapter.notifyDataSetChanged();
                        }


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    lv_home_progress.setVisibility(View.GONE);
                    home_nested.setVisibility(View.GONE);
                    NavigationActivity.get_Customer_tokenapi();
                    //callWishistApi();

                    // Toast.makeText(parent, ""+getFavouriteslist.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                lv_home_progress.setVisibility(View.GONE);
                home_nested.setVisibility(View.GONE);
            }
        });
    }

    private Call<ResponseBody> getwishlistdata() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        String token = null;
        if(getActivity()!=null)
        {
            token=Login_preference.getCustomertoken(getActivity());
        }
///            Log.e("debug_11token22", "==" + Login_preference.getCustomertoken(getActivity()));
        return api.defaultgetWishlistData("Bearer " + token);

    }

    /*private List<CartListModel> fetchResults(Response<CartListModel> response) {
        return (List<CartListModel>) response.body();
    }*/

    public static Call<ResponseBody> callhomepagetopbannre() {
        return apiInterface1.homepagetopbanner("http://app.demoproject.info/rest/V1/homepage");
    }

    public void makeRoundCorner(int bgcolor, int radius, View v, int strokeWidth, int strokeColor) {
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(bgcolor);
        gdDefault.setCornerRadius(radius);
        gdDefault.setStroke(strokeWidth, strokeColor);
        v.setBackgroundDrawable(gdDefault);

        tv_seeall_browse.setTextColor(getActivity().getResources().getColor(R.color.black));
        tv_seeall_browse.setTypeface(SplashActivity.montserrat_medium);

    }

    private void setDynamicbackgroundButton() {

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(getActivity().getResources().getColor(R.color.greylight));
        shape.setStroke(3, getActivity().getResources().getColor(R.color.greylight));
        shape.setCornerRadius(15);
        shape.setCornerRadii(new float[]{12, 12, 12, 12, 12, 12, 12, 12});
        lv_see_all_categories.setBackgroundDrawable(shape);

    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < imagslidertop.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }

    private void setFontFamily() {
        tv_home_off.setTypeface(SplashActivity.montserrat_bold);
        tv_new_arrivals.setTypeface(SplashActivity.montserrat_bold);
        tv_new_see_all.setTypeface(SplashActivity.montserrat_semibold);
        tv_best_selling_seeall.setTypeface(SplashActivity.montserrat_semibold);
        tv_best_selling.setTypeface(SplashActivity.montserrat_bold);
        tv_seeall_browse.setTypeface(SplashActivity.montserrat_medium);
        tv_browse_cat.setTypeface(SplashActivity.montserrat_bold);
        tv_home_titlee.setTypeface(SplashActivity.montserrat_bold);
        tv_search_title.setTypeface(SplashActivity.montserrat_regular);
        tv_sale.setTypeface(SplashActivity.montserrat_bold);

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void AttachRecyclerview() {
        browseCategoryAdapter = new BrowseCategoryAdapter(getActivity(), browseCategories);
        recv_categories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recv_categories.setAdapter(browseCategoryAdapter);

        bestSellingAdapter = new BestSellingAdapter(getActivity(), bestSellingList);
        recv_bestselling.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recv_bestselling.setAdapter(bestSellingAdapter);

        newArrivalsAdapter = new NewArrivalsAdapter(getActivity(), newArrrivalsModelArrayList);
        recv_newarrivals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recv_newarrivals.setAdapter(newArrivalsAdapter);

        /*bestSellingAdapter = new BestSellingAdapter(getActivity(),bestSellingList);
        recv_newarrivals.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recv_newarrivals.setAdapter(bestSellingAdapter1);*/


        //set image slider data
        /*for (int i=0;i<5;i++)
        {
            imagslider.add((new HomebannerModel("")));
        }*/

        viewPagerAdapter = new ViewPagerAdapter(getActivity(), imagslidertop);
        viewPager.setAdapter(viewPagerAdapter);
        //indicatorr.createIndicators(5,0);
        indicatorr.setViewPager(viewPager);
        viewPagerAdapter.registerDataSetObserver(indicatorr.getDataSetObserver());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 2000);

    }


    /**
     * Autoscroll detected from here, where counter, time and runnable is declared.
     */

   /* public void autoScrollAnother() {


        scrollCount = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

               // recv_top_banner.smoothScrollToPosition((scrollCount++));
                if (scrollCount == homeTopSliderAdapter.getItemCount() ) {
                    //stockListModels.addAll(stockListModels);
                    homeTopSliderAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

   */

 /*   private void autoScrollSlider() {

        final int speedScroll = 2000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() { //0<5
                if(count < homeTopSliderAdapter.getItemCount()){
                    if(count==homeTopSliderAdapter.getItemCount()-1){ //0==4
                        flag = false;                   //flag=fa
                    }else if(count == 0){
                        flag = true;
                    }
                    if(flag)
                        count++;
                    else
                        count=0;
                    //autoScrollSlider();

                 //   recv_top_banner.smoothScrollToPosition(count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };

        handler.postDelayed(runnable,speedScroll);
    }
*/

    private void SearchView_Focus() {
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocus();
        searchView.requestFocusFromTouch();
        searchView.setIconified(true);

    }

    private void AllocateMemory(View v) {
        viewPager = v.findViewById(R.id.viewPager);
        lv_search_home = v.findViewById(R.id.lv_search_home);
        indicatorr = v.findViewById(R.id.indicatorr);
        lv_see_all_categories = v.findViewById(R.id.lv_see_all_categories);
        tv_home_off = v.findViewById(R.id.tv_home_off);
        tv_new_arrivals = v.findViewById(R.id.tv_new_see_all);
        tv_new_see_all = v.findViewById(R.id.tv_new_see_all);
        tv_best_selling_seeall = v.findViewById(R.id.tv_best_selling_seeall);
        tv_best_selling = v.findViewById(R.id.tv_best_selling);
        tv_seeall_browse = v.findViewById(R.id.tv_seeall_browse);
        tv_browse_cat = v.findViewById(R.id.tv_browse_cat);
        tv_search_title = v.findViewById(R.id.tv_search_title);
        tv_home_titlee = v.findViewById(R.id.tv_home_titlee);
        tv_sale = v.findViewById(R.id.tv_sale);
        recv_newarrivals = v.findViewById(R.id.recv_newarrivals);
        recv_bestselling = v.findViewById(R.id.recv_bestselling);
        recv_categories = v.findViewById(R.id.recv_categories);
        searchView = v.findViewById(R.id.search);
        toolbar_home = v.findViewById(R.id.toolbar_home);
        iv_middle_block = v.findViewById(R.id.iv_middle_block);
        iv_bottom_block = v.findViewById(R.id.iv_bottom_block);
        home_nested = v.findViewById(R.id.home_nested);
        lv_home_progress = v.findViewById(R.id.lv_home_progress);
    }

    @Override
    public void onClick(View v) {
        if (v == lv_see_all_categories) {
            /*Bundle b = new Bundle();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            b.putString("categoryid", String.valueOf(91));
            b.putString("categoryname", "1/2 Palle Træpiller ");
            ProductListFragment myFragment = new ProductListFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();*/

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            CategoriesAndSubcareFragment myFragment = new CategoriesAndSubcareFragment();
            /*Bundle b = new Bundle();
            b.putString("browse_cat_array", browse_cat_array);*/
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();

        }else if(v==lv_search_home)
        {
            pushFragment(new SearchFragment(),"searchview");
        }
    }


    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.framlayout, fragment);
                ft.addToBackStack(add_to_backstack);

                ft.commit();
            }
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cartt:
                if(Login_preference.getLogin_flag(getActivity()).equalsIgnoreCase("1"))
                { pushFragment(new CartListFragment(),"cartlist");

                }else {
                    pushFragment(new LoginFragment(),"login");
                }

                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                //return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}

package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;

import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Fragment.LoginFragment;
import com.example.defaultdemotoken.Fragment.MyBounceInterpolator;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.HomeModel.BestSelling;
import com.example.defaultdemotoken.Model.HomeModel.NewArrrivalsModel;

import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.tv_wishlist_count;

public class NewArrivalsAdapter extends RecyclerView.Adapter<NewArrivalsAdapter.MyViewHolder> {
    Context context;
    private List<NewArrrivalsModel> newArrrivalsModels;
    private List<Integer> wishlitProductidList = new ArrayList<>();
    private List<Integer> wishlitItemId = new ArrayList<>();

    public NewArrivalsAdapter(Context context,List<NewArrrivalsModel> newArrrivalsModels) {
        this.context = context;
        this.newArrrivalsModels = newArrrivalsModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.new_arrivals_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final NewArrrivalsModel newArrrivalsModel = newArrrivalsModels.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        holder.tv_product_name_newarrivals.setTypeface(SplashActivity.montserrat_medium);
        holder.tv_product_price_new_arrivals.setTypeface(SplashActivity.montserrat_medium);

        myViewHolder.tv_product_name_newarrivals.setText(newArrrivalsModels.get(position).getName());
        myViewHolder.tv_product_price_new_arrivals.setText(newArrrivalsModels.get(position).getPrice());

        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image);
        requestOptions.error(R.drawable.image);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load("http://app.demoproject.info/pub/media/catalog/product" +newArrrivalsModels.get(position).getImage()).into(myViewHolder.iv_product_img_newarrivals);

        myViewHolder.ratingbar_newarrivals.setRating(Float.parseFloat(newArrrivalsModels.get(position).getRating()));


        if (newArrrivalsModels.get(position).getIsWishlisted() == null || newArrrivalsModels.get(position).getIsWishlisted().equalsIgnoreCase(null) ||
                newArrrivalsModels.get(position).getIsWishlisted().equalsIgnoreCase("null")) {
            holder.iv_wishlist_remove_newarrival.setVisibility(View.GONE);
            holder.iv_wishlist_newarrival.setVisibility(View.VISIBLE);

        } else {
            if (newArrrivalsModels.get(position).getIsWishlisted().equalsIgnoreCase("true") == true) {
                holder.iv_wishlist_newarrival.setVisibility(View.GONE);
                holder.iv_wishlist_remove_newarrival.setVisibility(View.VISIBLE);
            } else if (newArrrivalsModels.get(position).getIsWishlisted().equalsIgnoreCase("false")) {
                holder.iv_wishlist_remove_newarrival.setVisibility(View.GONE);
                holder.iv_wishlist_newarrival.setVisibility(View.VISIBLE);
            }
        }

        holder.iv_wishlist_newarrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prod_id = String.valueOf(newArrrivalsModels.get(position).getId());
                if (Login_preference.getLogin_flag(context).equalsIgnoreCase("1")) {
                    if (CheckNetwork.isNetworkAvailable(context)) {
                        CallAddtoWishlistApi_list(holder, prod_id, position, newArrrivalsModels, v);
                    } else {
                        Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    LoginFragment myFragment = new LoginFragment();
                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
                }
            }
        });


        ///remove wishlist
        holder.iv_wishlist_remove_newarrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (Login_preference.getLogin_flag(context).equalsIgnoreCase("1")) {
                    if (CheckNetwork.isNetworkAvailable(context)) {
                        Log.e("prod_id_476", "=" + newArrrivalsModels.get(position).getId());
                        Log.e("prod_id_476size", "=" + wishlitProductidList.size());
                        Log.e("position_476", "=" + position);
                        Log.e("position_476", "=" + wishlitItemId.size());

                        if (wishlitProductidList.size() > 0) {

                            int id= Integer.parseInt(newArrrivalsModels.get(position).getId());
                            if (wishlitProductidList.contains(id)) {
                                int productid= Integer.parseInt(newArrrivalsModels.get(position).getId());
                                int pos = wishlitProductidList.indexOf(productid);
                                Log.e("wishlist_it222", "=" + pos);
                                Log.e("productid154", "=" + productid);
                                Log.e("wishlist_it222", "=" + wishlitProductidList.get(0));

                                if (wishlitItemId.size() > 0) {
                                    Log.e("wishlist_i155", "=" + wishlitItemId.size());
                                    String wishlist_item_id = String.valueOf(wishlitItemId.get(pos));
                                    Log.e("wishlist_item_id333333", "=" + wishlist_item_id);
                                    newArrrivalsModels.get(position).setWishlist_item_id(wishlist_item_id);
                                    callRemoveFromWishlistApi(wishlist_item_id, position, v, holder, newArrrivalsModels);
                                }
                            } else {
                                newArrrivalsModels.get(position).setWishlist_item_id("0");
                            }
                        } else {
                            final String itemid = String.valueOf(newArrrivalsModels.get(position).getWishlist_item_id());
                            Log.e("debg", "=" + itemid);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (itemid.equalsIgnoreCase("0")) {
                                        Log.e("debg_rem0ve_if", "=" + itemid);

                                    } else {

                                        Log.e("debg_remove_else", "=" + itemid);
                                        callRemoveFromWishlistApi(itemid, position, v, holder, newArrrivalsModels);

                                    }
                                }
                            }, 100);
                        }


                    } else {
                        Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    LoginFragment myFragment = new LoginFragment();
                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
                }
            }
        });


        //add to cart
        holder.lv_new_arrival_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Login_preference.getLogin_flag(context).equalsIgnoreCase("1")) {
                    if (CheckNetwork.isNetworkAvailable(context)) {
                        callAddtoCartApi(newArrrivalsModels.get(position).getSku(), holder.lv_main_new_arrival, holder.lv_pb_new_arrival);
                    } else {
                        Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    LoginFragment myFragment = new LoginFragment();
                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return newArrrivalsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundRectCornerImageView iv_product_img_newarrivals;
        TextView tv_product_name_newarrivals,tv_product_price_new_arrivals;
        RatingBar ratingbar_newarrivals;
        LinearLayout lv_new_arrival_addtocart,lv_main_new_arrival,lv_pb_new_arrival;
        ImageView iv_wishlist_newarrival,iv_wishlist_remove_newarrival;

        public MyViewHolder(@NonNull View view) {
            super(view);

            lv_pb_new_arrival = view.findViewById(R.id.lv_pb_new_arrival);
            lv_main_new_arrival = view.findViewById(R.id.lv_main_new_arrival);
            iv_wishlist_remove_newarrival = view.findViewById(R.id.iv_wishlist_remove_newarrival);
            iv_wishlist_newarrival = view.findViewById(R.id.iv_wishlist_newarrival);
            iv_product_img_newarrivals = view.findViewById(R.id.iv_product_img_newarrivals);
            tv_product_name_newarrivals = view.findViewById(R.id.tv_product_name_newarrivals);
            tv_product_price_new_arrivals = view.findViewById(R.id.tv_product_price_new_arrivals);
            ratingbar_newarrivals = view.findViewById(R.id.ratingbar_newarrivals);
            lv_new_arrival_addtocart = view.findViewById(R.id.lv_new_arrival_addtocart);

        }
    }
    private void callAddtoCartApi(final String sku, final LinearLayout lv_product_main, final LinearLayout lv_pb_prod) {
        lv_product_main.setVisibility(View.GONE);
        lv_pb_prod.setVisibility(View.VISIBLE);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("product_sku_pass", "" + sku);
        String url = ApiClient.MAIN_URLL+"carts/mine/items?cartItem[quoteId]=" +
                Login_preference.getquote_id(context) +
                "&cartItem[qty]=1" +
                "&cartItem[sku]=" + sku;

        final Call<ResponseBody> addtocart = api.addtocart("Bearer " + Login_preference.getCustomertoken(context), url);
        addtocart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                lv_product_main.setVisibility(View.VISIBLE);
                lv_pb_prod.setVisibility(View.GONE);

                /*AddToCartListModel model = response.body();*/
                /*Log.e("response_add_to_cart",""+model);*/
                Log.e("response_add_to_cartt", "" + response);
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        String name = jsonObject.getString("name");
                        Log.e("cart_prod_name", "" + name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, "Add to cart SuccessFully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == Integer.parseInt("200")) {
                    JSONObject jsonObject = null;
                    try {

                        jsonObject = new JSONObject(response.body().string());

                        String name = jsonObject.getString("name");
                        Log.e("cart_prod_name", "" + name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, "Add to cart SuccessFully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == Integer.parseInt("401")) {
                    lv_product_main.setVisibility(View.VISIBLE);
                    lv_pb_prod.setVisibility(View.GONE);
                    Toast.makeText(context, "Response NULL", Toast.LENGTH_SHORT).show();
                    NavigationActivity.get_Customer_tokenapi();

                    callAddtoCartApi(sku, lv_product_main, lv_pb_prod);

                } else if (response.code() == Integer.parseInt("400")) {
                    lv_product_main.setVisibility(View.VISIBLE);
                    lv_pb_prod.setVisibility(View.GONE);
                    Toast.makeText(context, "Bad Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // lv_product_progress.setVisibility(View.GONE);
                // coordinator_product_main.setVisibility(View.VISIBLE);
                Toast.makeText(context, context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callRemoveFromWishlistApi(final String itemid, final int position, final View v, final MyViewHolder holder, final List<NewArrrivalsModel> itemList) {
        holder.lv_main_new_arrival.setVisibility(View.GONE);
        holder.lv_pb_new_arrival.setVisibility(View.VISIBLE);
        callRemoveWishlistapi(itemid).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                holder.lv_pb_new_arrival.setVisibility(View.GONE);

                Boolean paymentMehtodModel = response.body();

                Log.e("resaaaremove wishlist", "=" + response.code());
                Log.e("resaaacccc", "=" + response);

                if (response.code() == 200) {

                    holder.iv_wishlist_remove_newarrival.setVisibility(View.GONE);
                    holder.iv_wishlist_newarrival.setVisibility(View.VISIBLE);
                    final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(interpolator);
                    holder.iv_wishlist_newarrival.startAnimation(myAnim);
                    itemList.get(position).setIsWishlisted("false");

                    //     Toast.makeText(context, "Product Remove from Wishlist successfully", Toast.LENGTH_SHORT).show();

                    callWishlistCountApi();
                    holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                    holder.lv_pb_new_arrival.setVisibility(View.GONE);

                } else {
                    holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                    holder.lv_pb_new_arrival.setVisibility(View.GONE);
                    NavigationActivity.get_Customer_tokenapi();

                    //  callRemoveFromWishlistApi(itemid,position,v,holder,itemList);
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                holder.lv_pb_new_arrival.setVisibility(View.GONE);


                // String error=  t.printStackTrace();
                Toast.makeText(context, "" + context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "pages: " + t);
            }
        });
    }

    private Call<Boolean> callRemoveWishlistapi(String itemid) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_11", "==" + itemid);
        ///http://dkbraende.demoproject.info/rest//V1/carts/mine/items/162920


        String url = ApiClient.MAIN_URLL + "wishlist/delete/" + itemid;
        Log.e("url11", "==" + url);
        return api.removeitemfromWishlistt("Bearer " + Login_preference.getCustomertoken(context), url);
    }

    private Call<Boolean> calladdtowishnew(String itemid) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_11111", "==" + itemid);
        Log.e("debug_11token", "==" + Login_preference.getCustomertoken(context));
        ///http://dkbraende.demoproject.info/rest//V1/carts/mine/items/162920


        String url = ApiClient.MAIN_URLL + "wishlist/add/" + itemid;
        Log.e("url1111", "==" + url);
        return api.defaultaddtowishlist("Bearer " +  Login_preference.getCustomertoken(context), url);
    }
    
    private void callWishistApi(final String prod_id, final List<NewArrrivalsModel> itemList, final int position) {
        wishlitProductidList.clear();
        wishlitItemId.clear();
        getwishlistdata().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_favourite", "" + response.body());
                Log.e("response_favourite", "" + response);
                ResponseBody getFavouriteslist = response.body();
                if (response.isSuccessful() || response.code() == 200) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response.body().string());
                        Log.e("jsonarray", "=" + jsonArray);
                        Log.e("jsonarraylength", "=" + jsonArray.length());
                        //  Log.e("jsonarray66ss", "=" +jsonArray.getJSONObject(0).getJSONObject("product"));
                        if (jsonArray.length() == 0) {
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.e("product_id", "=" + jsonObject.getString("product_id"));
                                    wishlitProductidList.add(jsonObject.getInt("product_id"));
                                    wishlitItemId.add(jsonObject.getInt("wishlist_item_id"));

                                } catch (Exception e) {
                                    Log.e("exception22", "=" + e.getLocalizedMessage());
                                }
                            }
                            Log.e("prod_id_476", "=" + prod_id);
                            Log.e("prod_id_476size", "=" + wishlitProductidList.size());
                            Log.e("position_476", "=" + position);
                            Log.e("position_476", "=" + wishlitItemId.size());

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (wishlitProductidList.contains(prod_id)) {
                                        int pos = wishlitProductidList.indexOf(prod_id);
                                        Log.e("wishlist_it222", "=" + pos);
                                        if (wishlitItemId.size() > 0) {
                                            String wishlist_item_id = String.valueOf(wishlitItemId.get(pos));
                                            Log.e("wishlist_item_id333333", "=" + wishlist_item_id);
                                            itemList.get(position).setWishlist_item_id(wishlist_item_id);
                                        }
                                    } else {
                                        itemList.get(position).setWishlist_item_id("0");
                                    }
                                }
                            }, 1000);
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    // Toast.makeText(parent, ""+getFavouriteslist.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "" + context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private Call<ResponseBody> getwishlistdata() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_11token22", "==" + Login_preference.getCustomertoken(context));
        return api.defaultgetWishlistData("Bearer " +  Login_preference.getCustomertoken(context));
    }
  
    private void CallAddtoWishlistApi_list(final MyViewHolder holder, final String prod_id, final int position, final List<NewArrrivalsModel> itemList, final View v) {

        holder.lv_main_new_arrival.setVisibility(View.GONE);
        holder.lv_pb_new_arrival.setVisibility(View.VISIBLE);

        calladdtowishnew(prod_id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                holder.lv_pb_new_arrival.setVisibility(View.GONE);
                Log.e("debug_166", "" + response);
                Log.e("debug_167", "" + response.body());
                Boolean addToWishlist = response.body();
                //  Log.e("response_168",""+addToWishlist);
                //   Log.e("status_wish",""+addToWishlist.getStatus());
                Log.e("status_wish", "ok");
                if (response.isSuccessful() || response.code() == 200) {

                    if (response.body() == true) {

                        itemList.get(position).setIsWishlisted("true");
                        callWishlistCountApi();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                callWishistApi(prod_id, itemList, position);
                            }
                        }, 0);

                        holder.iv_wishlist_newarrival.setVisibility(View.GONE);
                        holder.iv_wishlist_remove_newarrival.setVisibility(View.VISIBLE);
                        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                        myAnim.setInterpolator(interpolator);
                        holder.iv_wishlist_remove_newarrival.startAnimation(myAnim);
                        holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                        holder.lv_pb_new_arrival.setVisibility(View.GONE);

                    } else {

                        holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                        holder.lv_pb_new_arrival.setVisibility(View.GONE);

                    }


                } else {
                    holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                    holder.lv_pb_new_arrival.setVisibility(View.GONE);

                    NavigationActivity.get_Customer_tokenapi();
                    //CallAddtoWishlistApi_list(holder, prod_id, position, itemList, v);

                    Log.e("debug_error", "=" + response);
                    Log.e("error", "=" + response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                holder.lv_main_new_arrival.setVisibility(View.VISIBLE);
                holder.lv_pb_new_arrival.setVisibility(View.GONE);

                // Log.e("error_wish",""+t);
                Log.e("debug_remivr", "" + t.getMessage());
                Toast.makeText(context, context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();

            }
        });

    }
    
    private void callWishlistCountApi() {
        Log.e("response201tokenff", "" + Login_preference.getCustomertoken(context));
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> customertoken = api.defaultWishlistCount("Bearer " + Login_preference.getCustomertoken(context));
        customertoken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response200gffgdf", "" + response.toString());
                Log.e("response201fgd", "" + response.body());
                if (response.code() == 200 || response.isSuccessful()) {
                    try {
                        JSONArray jsonObject = new JSONArray(response.body().string());

                        String count = jsonObject.getJSONObject(0).getString("total_items");

                        if (count.equalsIgnoreCase("null") || count.equals("") || count.equals("0")) {
                            tv_wishlist_count.setVisibility(View.GONE);
                        } else {
                            tv_wishlist_count.setVisibility(View.VISIBLE);
                            tv_wishlist_count.setText(count);
                            Login_preference.set_wishlist_count(context, count);
                            Log.e("wishcount", "" + count);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
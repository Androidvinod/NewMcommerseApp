package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.defaultdemotoken.Fragment.ProductDetailFragment;
import com.example.defaultdemotoken.Fragment.WishlistFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.WishlistModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;
import static com.example.defaultdemotoken.Activity.NavigationActivity.tv_wishlist_count;
import static com.example.defaultdemotoken.Fragment.WishlistFragment.lv_progress_wishist;
import static com.example.defaultdemotoken.Fragment.WishlistFragment.lvnodata_wishlistlist;
import static com.example.defaultdemotoken.Fragment.WishlistFragment.recv_wishlist;


public class WishListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        private List<WishlistModel> productList;
        ApiInterface getwish;
        static String prod_id;
        LayoutInflater inflater;
        ApiInterface apiInterface;

        public WishListAdapter(Context context, List<WishlistModel> model) {
            this.context = context;
            this.productList = model;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.wishlist_row, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            getwish = ApiClient.getClient().create(ApiInterface.class);
            final WishlistModel product = productList.get(position);

            Log.e("debug822","="+product.getSpecial_price());
            Log.e("debug822","="+product.getPrice());
            Log.e("debug822","="+product.getName());
            prod_id = product.getProduct_id();
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.tv_add_cart.setTypeface(SplashActivity.montserrat_semibold);
        myViewHolder.tv_wishlist_msrp.setTypeface(SplashActivity.montserrat_semibold);
        myViewHolder.tv_wishlist_product_name.setTypeface(SplashActivity.montserrat_bold);
        myViewHolder.tv_wishlist_msrp_title.setTypeface(SplashActivity.montserrat_semibold);
        myViewHolder.tv_wishlist_special_price.setTypeface(SplashActivity.montserrat_semibold);
        myViewHolder.tv_wishlist_sp_title.setTypeface(SplashActivity.montserrat_semibold);

            NavigationActivity.Check_String_NULL_Value(myViewHolder.tv_wishlist_product_name, product.getName());
            //  myViewHolder.tv_wishlist_product_name.setText(product.getProductName());
            //   myViewHolder.tv_wishlist_msrp.setText(product.getProductPrice());

            if ( product.getSpecial_price()==null || product.getSpecial_price()=="null" ||
                    product.getSpecial_price().equalsIgnoreCase("null")
                    || product.getSpecial_price().equalsIgnoreCase("") == true ) {
                myViewHolder.lv_special.setVisibility(View.GONE);
                NavigationActivity.Check_String_NULL_Value(myViewHolder.tv_wishlist_msrp, product.getPrice());

            } else {
                myViewHolder.tv_wishlist_msrp.setPaintFlags(myViewHolder.tv_wishlist_msrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                if(product.getSpecial_price()==null)
                {
                    myViewHolder.lv_special.setVisibility(View.GONE);
                }else {
                    myViewHolder.lv_special.setVisibility(View.VISIBLE);
                    NavigationActivity.Check_String_NULL_Value(myViewHolder.tv_wishlist_msrp, product.getPrice());
                    NavigationActivity.Check_String_NULL_Value(myViewHolder.tv_wishlist_special_price, product.getSpecial_price());
                }

            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.dr);
            requestOptions.error(R.drawable.dr);
            Log.e("debug_fav_image",""+product.getThumbnail());
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load("http://app.demoproject.info/pub/media/catalog/product"+product.getThumbnail()).into(myViewHolder.iv_wishlist_product);

            myViewHolder.lv_productwish_click.setEnabled(true);
            myViewHolder.lv_productwish_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Handler handler = new Handler();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Bundle b = new Bundle();
                    b.putString("product_id", String.valueOf(productList.get(position).getProduct_id()));
                    b.putString("product_name", String.valueOf(productList.get(position).getName()));
                    ProductDetailFragment myFragment = new ProductDetailFragment();
                    myFragment.setArguments(b);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fade_in,
                                    0, 0, R.anim.fade_out)
                            .setCustomAnimations(R.anim.fade_in,
                                    0, 0, R.anim.fade_out)
                            .add(R.id.framlayout, myFragment)
                            .addToBackStack(null).commit();

                }
            });

            myViewHolder.lv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Login_preference.getLogin_flag(context).equalsIgnoreCase("1"))
                    {
                        if (CheckNetwork.isNetworkAvailable(context)) {
                            String  sku= String.valueOf(productList.get(position).getSku());
                            Log.e("debg","="+sku);
                            CallAddtoCartApi(sku);
                        } else {
                            Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        LoginFragment myFragment = new LoginFragment();
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
                    }



                }
            });
            ((MyViewHolder) holder).lv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Login_preference.getLogin_flag(context).equalsIgnoreCase("1"))
                    {
                        if (CheckNetwork.isNetworkAvailable(context)) {
                            String  itemid= String.valueOf(productList.get(position).getWishlist_item_id());
                            Log.e("debg","="+itemid);
                            callRemoveFromWishlistApi(itemid,position,v);

                        } else {
                            Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                        }
                    }else {
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
            return productList == null ? 0 : productList.size();
        }


    private void CallAddtoCartApi(String sku) {
        ApiInterface api  = ApiClient.getClient().create(ApiInterface.class);
        lvnodata_wishlistlist.setVisibility(View.GONE);
        lv_progress_wishist.setVisibility(View.VISIBLE);
        recv_wishlist.setVisibility(View.VISIBLE);

        String url=ApiClient.MAIN_URLL+"carts/mine/items?cartItem[quoteId]="+ Login_preference.getquote_id(context)+"&cartItem[qty]=1"+"&cartItem[sku]="+sku;
        Log.e("skuu","="+sku);
        Log.e("customertokensss","="+Login_preference.getCustomertoken(context));
        Log.e("customertokensss","="+url);
        Call<ResponseBody> addtocart = api.getaddtocartapi("Bearer "+Login_preference.getCustomertoken(context), url);
        addtocart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("res_addtocart",""+response.toString());
                Log.e("resquotaddtocart",""+response.body());


                if(response.isSuccessful() || response.code()==200)
                {
                    JSONObject jsonObject = null;
                    try {
                        try {
                            lvnodata_wishlistlist.setVisibility(View.GONE);
                            lv_progress_wishist.setVisibility(View.GONE);
                            recv_wishlist.setVisibility(View.VISIBLE);

                            jsonObject = new JSONObject(response.body().string());
                            String name=jsonObject.getString("name");
                            String price=jsonObject.getString("price");
                            String product_type=jsonObject.getString("product_type");
                            String quote_id=jsonObject.getString("quote_id");
                            String sku=jsonObject.getString("sku");
                            String item_id=jsonObject.getString("item_id");
                            String qty=jsonObject.getString("qty");

                            Toast.makeText(context, "Add to cart SuccessFully", Toast.LENGTH_SHORT).show();
                            Log.e("jsonObjectss","="+jsonObject);
                            Log.e("names","="+name);
                            Log.e("prices","="+price);
                            Log.e("product_types","="+product_type);
                            Log.e("quote_ids","="+quote_id);
                            Log.e("skus","="+sku);
                            Log.e("item_ids","="+item_id);
                            Log.e("qtys","="+qty);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    lvnodata_wishlistlist.setVisibility(View.GONE);
                    lv_progress_wishist.setVisibility(View.GONE);
                    recv_wishlist.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "The Product You Are Trying To Add is not Available", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }



    private void callRemoveFromWishlistApi(String itemid, int position, final View v) {
            lvnodata_wishlistlist.setVisibility(View.GONE);
            lv_progress_wishist.setVisibility(View.VISIBLE);
            recv_wishlist.setVisibility(View.VISIBLE);

        callRemovewishlistapi(itemid).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Boolean paymentMehtodModel = response.body();

                    Log.e("resaaaremove wishlist", "=" + response.code());
                    Log.e("resaaacccc", "=" + response);
                    lv_progress_wishist.setVisibility(View.GONE);
                    lvnodata_wishlistlist.setVisibility(View.GONE);
                    recv_wishlist.setVisibility(View.VISIBLE);

//                    Log.e("res_payment", "aaa" + response.body().toString());
                    //progressBar_review.setVisibility(View.GONE);

                    //  lv_cartlist_progress.setVisibility(View.GONE);
                    // lv_cart_Main.setVisibility(View.VISIBLE);


                    if(response.code()==200)
                    {
                        //    CallCartlistApi();
                        // cartlistdata.remove(position);
                        // cartlistAdapter.notifyDataSetChanged();
                        Toast.makeText(context, "Product Remove from Wishlist successfully", Toast.LENGTH_SHORT).show();


                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        WishlistFragment myFragment = new WishlistFragment();

                        activity.getSupportFragmentManager()
                                .beginTransaction().addToBackStack(null).
                                replace(R.id.framlayout, myFragment).commit();
                        callWishlistCountApi();

                    }else {

                    }

                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    // String error=  t.printStackTrace();
                    Toast.makeText(context, "" + context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                    Log.e("debug_175125", "pages: " + t);
                }
            });
        }



        private Call<Boolean> callRemovewishlistapi(String itemid) {

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Log.e("debug_11","=="+itemid);
            ///http://dkbraende.demoproject.info/rest//V1/carts/mine/items/162920


            String url=ApiClient.MAIN_URLL+"wishlist/delete/"+itemid;
            Log.e("url11","=="+url);
            return api.removeitemfromWishlistt("Bearer "+ Login_preference.getCustomertoken(context),url);
        }

        private void callWishlistCountApi() {
        Log.e("response201tokenff",""+Login_preference.getCustomertoken(context));
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> customertoken = api.defaultWishlistCount("Bearer "+Login_preference.getCustomertoken(context));
        customertoken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response200gffgdf",""+response.toString());
                Log.e("response201fgd",""+response.body());
                if(response.code()==200 || response.isSuccessful())
                {
                    try {
                        JSONArray jsonObject = new JSONArray(response.body().string());

                        String count= jsonObject.getJSONObject(0).getString("total_items");
                        tv_wishlist_count.setText(count);
                        Login_preference.set_wishlist_count(context,count);
                        Log.e("wishcount","="+count);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


        public boolean isEmpty() {
            return getItemCount() == 0;
        }


        public WishlistModel getItem(int position) {
            Log.e("pos_galada", "" + position);
            return productList.get(position);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_wishlist_product;
            TextView tv_wishlist_product_name,tv_add_cart, tv_wishlist_msrp_title, tv_wishlist_msrp, tv_wishlist_special_price,tv_wishlist_sp_title;
            LinearLayout lv_special, lv_productwish_click,lv_delete,lv_add_to_cart;

            public MyViewHolder(@NonNull View view) {
                super(view);

                tv_wishlist_sp_title = view.findViewById(R.id.tv_add_cart);
                tv_add_cart = view.findViewById(R.id.tv_add_cart);
                lv_add_to_cart = view.findViewById(R.id.lv_add_to_cart);
                lv_delete = view.findViewById(R.id.lv_delete);
                lv_productwish_click = view.findViewById(R.id.lv_productwish_click);
                lv_special = view.findViewById(R.id.lv_special);
                iv_wishlist_product = view.findViewById(R.id.iv_wishlist_product);
                tv_wishlist_product_name = view.findViewById(R.id.tv_wishlist_product_name);
                tv_wishlist_msrp_title = view.findViewById(R.id.tv_wishlist_msrp_title);
                tv_wishlist_msrp = view.findViewById(R.id.tv_wishlist_msrp);
                tv_wishlist_special_price = view.findViewById(R.id.tv_wishlist_special_price);

            }
        }


    }



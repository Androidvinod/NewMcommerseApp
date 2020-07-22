package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Adapter.WishListAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.GoogleMapModel;
import com.example.defaultdemotoken.Model.WishlistModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

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

import static com.example.defaultdemotoken.Activity.NavigationActivity.bottom_navigation;
import static com.example.defaultdemotoken.Activity.NavigationActivity.tv_wishlist_count;

public class WishlistFragment extends Fragment {
    public static List<WishlistModel> favouriteproductlist = new ArrayList<WishlistModel>();

    View v;
    WishListAdapter wishListAdapter;
    Toolbar toolbar_wishlist;
    public static LinearLayout lvnodata_wishlistlist, lv_progress_wishist;
    public static RecyclerView recv_wishlist;
    List<GoogleMapModel> googleMapModelList = new ArrayList<>();

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_wishlist, container, false);
        bottom_navigation.getMenu().getItem(1).setChecked(true);
        AllocateMemory();
        setHasOptionsMenu(true);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_wishlist);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

        toolbar_wishlist.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        toolbar_wishlist.setTitle("WishList");
        toolbar_wishlist.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        wishListAdapter = new WishListAdapter(getActivity(), favouriteproductlist);
        recv_wishlist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recv_wishlist.setAdapter(wishListAdapter);
        for (int i = 0; i < 5; i++) {
            favouriteproductlist.add(new WishlistModel("", "", "", "", "", "", "", ""));

        }
        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            //CallGetWishlistApi(page_no);
            callWishlistCountApi();
            callWishistApi();


        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void callWishistApi() {
        lvnodata_wishlistlist.setVisibility(View.GONE);
        favouriteproductlist.clear();
        lv_progress_wishist.setVisibility(View.VISIBLE);
        recv_wishlist.setVisibility(View.VISIBLE);

        getwishlistdata().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_favourite", "" + response.body());
                Log.e("response_favourite", "" + response);
                ResponseBody getFavouriteslist = response.body();
                lv_progress_wishist.setVisibility(View.GONE);
                lvnodata_wishlistlist.setVisibility(View.GONE);
                recv_wishlist.setVisibility(View.VISIBLE);

                if (response.isSuccessful() || response.code() == 200) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response.body().string());
                        Log.e("jsonarray", "=" + jsonArray);
                        Log.e("jsonarraylength", "=" + jsonArray.length());
                        //  Log.e("jsonarray66ss", "=" +jsonArray.getJSONObject(0).getJSONObject("product"));

                        if (jsonArray.length() == 0) {

                            lv_progress_wishist.setVisibility(View.GONE);
                            lvnodata_wishlistlist.setVisibility(View.VISIBLE);
                            recv_wishlist.setVisibility(View.GONE);

                        } else {

                            lv_progress_wishist.setVisibility(View.GONE);
                            lvnodata_wishlistlist.setVisibility(View.GONE);
                            recv_wishlist.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.e("price", "=" + jsonObject.getJSONObject("product").optString("price"));
                                    Log.e("name", "=" + jsonObject.getJSONObject("product").optString("name"));
                                    Log.e("special_price", "=" + jsonObject.getJSONObject("product").optString("special_price"));
                                    Log.e("thumbnail", "=" + jsonObject.getJSONObject("product").optString("thumbnail"));
                                    favouriteproductlist.add(new WishlistModel
                                            (jsonObject.getString("wishlist_item_id"),
                                                    jsonObject.getString("wishlist_id"),
                                                    jsonObject.getString("product_id"),
                                                    jsonObject.getJSONObject("product").optString("sku"),
                                                    jsonObject.getJSONObject("product").optString("price"),
                                                    jsonObject.getJSONObject("product").optString("special_price"),
                                                    jsonObject.getJSONObject("product").optString("name"),
                                                    jsonObject.getJSONObject("product").optString("thumbnail")));
                                } catch (Exception e) {
                                    Log.e("exception22", "=" + e.getLocalizedMessage());
                                }
                            }
                            wishListAdapter.notifyDataSetChanged();
                        }

                          /*  Log.e("size", "=" +favouriteproductlist.size());
                            wishListAdapter = new NewWishListAdapter(getAct ivity(), favouriteproductlist);
                            recv_favourites.setLayoutManager( new LinearLayoutManager(parent, LinearLayoutManager.VERTICAL, false));
                            recv_favourites.setAdapter(wishListAdapter)*/
                        ;
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Log.e("response_code", "=" + response.code());
                    try {
                        Log.e("errorbodyhh", "=" + response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    lv_progress_wishist.setVisibility(View.VISIBLE);
                    lvnodata_wishlistlist.setVisibility(View.GONE);
                    recv_wishlist.setVisibility(View.GONE);

                    // Toast.makeText(parent, ""+getFavouriteslist.getMessage(), Toast.LENGTH_SHORT).show();

                    NavigationActivity.get_Customer_tokenapi();
                    callWishistApi();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                lv_progress_wishist.setVisibility(View.VISIBLE);
                lvnodata_wishlistlist.setVisibility(View.GONE);
                recv_wishlist.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private Call<ResponseBody> getwishlistdata() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_11token22", "==" + Login_preference.getCustomertoken(getActivity()));

        return api.defaultgetWishlistData("Bearer " + Login_preference.getCustomertoken(getActivity()));
    }

    private void AllocateMemory() {
        toolbar_wishlist = v.findViewById(R.id.toolbar_wishlist);
        lvnodata_wishlistlist = v.findViewById(R.id.lvnodata_wishlistlist);
        lv_progress_wishist = v.findViewById(R.id.lv_progress_wishist);
        recv_wishlist = v.findViewById(R.id.recv_wishlist);
    }

    private void callWishlistCountApi() {
        Log.e("response201tokenff", "" + Login_preference.getCustomertoken(getActivity()));
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> customertoken = api.defaultWishlistCount("Bearer " + Login_preference.getCustomertoken(getActivity()));
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

                            if(getActivity()!=null)
                            {
                                Login_preference.set_wishlist_count(getActivity(), count);
                                Log.e("wishcount", "" + count);
                            }

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
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.notofication, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        /*  switch (item.getItemId()) {
         *//*case R.id.cart:
                pushFragment(new CartFragment());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:*//*
                //return super.onOptionsItemSelected(item);
        }
    */
        return super.onOptionsItemSelected(item);
    }
}

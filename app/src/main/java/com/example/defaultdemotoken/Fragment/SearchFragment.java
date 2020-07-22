package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Adapter.SearchProductListAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.ProductModel.Item;
import com.example.defaultdemotoken.Model.ProductModel.ProductModel;
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
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    String searhkey;
    View v;
    Toolbar toolbar_Search;
    RecyclerView recv_search;
    SearchView searchView;
    SearchProductListAdapter searchProductListAdapter;
    List<Item> searchModelslist = new ArrayList<>();
    EditText searchPlateEditText;
    LinearLayout lvnodata_searchist,lv_progress_search,lv_main_search;

    private List<Integer> wishlitProductidList = new ArrayList<>();
    private List<Integer> wishlitItemId = new ArrayList<>();
    String searchValue="";
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);
        bottom_navigation.getMenu().getItem(0).setChecked(true);

        allocateMemory();
        sarchViewFocuable();

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_Search);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

        toolbar_Search.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });



        //search view edittext clicklistner on keyboard button clicklistner
        searchPlateEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*  if(!TextUtils.isEmpty(textView.getText().toString())){*/
                    //Text entered
                    if (CheckNetwork.isNetworkAvailable(getActivity())) {
                        Log.e("debug_66", "" + searhkey);
                        searhkey = textView.getText().toString();
                        int length = searhkey.length();
                        Log.e("text_length_157_", "" + length);
                        if (length >= 3) {
                            hideKeyboard(getActivity());
                            if (CheckNetwork.isNetworkAvailable(getActivity())) {
                                CallSearchValueApi(searhkey);
                            } else {
                                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.vall), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            if (Login_preference.getLogin_flag(getActivity()).equalsIgnoreCase("1")) {
                callWishistApi();
            } else {

            }
        } else {
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }

        return v;
    }
    private void callWishistApi() {
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
                                    Log.e("price", "=" + jsonObject.getJSONObject("product").optString("price"));
                                    Log.e("name", "=" + jsonObject.getJSONObject("product").optString("name"));
                                    Log.e("special_price", "=" + jsonObject.getJSONObject("product").optString("special_price"));
                                    Log.e("thumbnail", "=" + jsonObject.getJSONObject("product").optString("thumbnail"));
                                } catch (Exception e) {
                                    Log.e("exception22", "=" + e.getLocalizedMessage());
                                }
                            }
                            // wishListAdapter.notifyDataSetChanged();
                        }


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    NavigationActivity.get_Customer_tokenapi();
                    callWishistApi();

                    // Toast.makeText(parent, ""+getFavouriteslist.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private Call<ResponseBody> getwishlistdata() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_11token22", "==" + Login_preference.getCustomertoken(getActivity()));
        return api.defaultgetWishlistData("Bearer " + Login_preference.getCustomertoken(getActivity()));
    }

    private void CallSearchValueApi(String searhkey) {
        searchModelslist.clear();
        searchValue="";
        lvnodata_searchist.setVisibility(View.GONE);
        lv_progress_search.setVisibility(View.VISIBLE);
        lv_main_search.setVisibility(View.GONE);
        getsearchValue(searhkey).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("res_searh", "" + response.body());
                Log.e("res_searh11", "" + response);

                if (response.isSuccessful() || response.code() == 200) {
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = new JSONObject(response.body().string());
                        Log.e("jsonObject1", "=" + jsonObject1);

                        if(jsonObject1.optString("items")==null || jsonObject1.optString("items").equalsIgnoreCase("null"))
                        {
                            Toast.makeText(getActivity(), "No Search Result Found", Toast.LENGTH_SHORT).show();
                            lvnodata_searchist.setVisibility(View.VISIBLE);
                            lv_progress_search.setVisibility(View.GONE);
                            lv_main_search.setVisibility(View.GONE);

                        }
                        JSONArray jsonArray=jsonObject1.getJSONArray("items");
                        Log.e("jsonarray", "=" + jsonArray);

                        if (jsonArray.length() == 0 || jsonArray==null || jsonArray.equals("null")) {
                            Log.e("jso_else", "=" + jsonArray);

                            Toast.makeText(getActivity(), "No Search Result Found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.e("product_id", "=" + jsonObject.optString("id"));

                                    if(searchValue.equalsIgnoreCase(""))
                                    {
                                        searchValue= jsonObject.optString("id");
                                    }else {
                                        searchValue+= "," + jsonObject.optString("id");
                                    }

                                } catch (Exception e) {
                                    Log.e("exception22", "=" + e.getLocalizedMessage());
                                }
                            }

                            Log.e("finalsearch", "" + searchValue);

                            if(searchValue.equalsIgnoreCase(""))
                            {

                            }else {
                                callSearchListApi(searchValue);
                            }


                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    lvnodata_searchist.setVisibility(View.GONE);
                    lv_progress_search.setVisibility(View.VISIBLE);
                    lv_main_search.setVisibility(View.GONE);

                    // Toast.makeText(parent, ""+getFavouriteslist.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Item> fetchResults(Response<ProductModel> response) {
        Log.e("newin_home_209", "" + response.body());
        ProductModel ProductModel = response.body();
        return ProductModel.getItems();
    }
    private void callSearchListApi(String searchValue) {
        searchModelslist.clear();
        lvnodata_searchist.setVisibility(View.GONE);
        lv_progress_search.setVisibility(View.VISIBLE);
        lv_main_search.setVisibility(View.GONE);

        getsearchapi(searchValue).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                Log.e("res_214", "" + response.body());
                Log.e("res_214_search", "" + response);

                if (response.isSuccessful() || response.code() == 200) {
                    JSONObject jsonObject1 = null;
                    lvnodata_searchist.setVisibility(View.GONE);
                    lv_progress_search.setVisibility(View.GONE);
                    lv_main_search.setVisibility(View.VISIBLE);
                    ProductModel model = response.body();
                    if (model.getItems() == null || model.getItems().size() == 0) {
                        Toast.makeText(getActivity(), "No Search Result Found", Toast.LENGTH_SHORT).show();
                        lvnodata_searchist.setVisibility(View.VISIBLE);
                        lv_progress_search.setVisibility(View.GONE);
                        lv_main_search.setVisibility(View.GONE);
                    } else {
                        List<Item> results = fetchResults(response);

                        for (int i = 0; i < results.size(); i++) {
                            Log.e("debug_productid", "=" + results.get(i).getId());
                            Log.e("wiish_itemid", "=" + wishlitItemId.size());
                            String isWishliste, imagge, wishlist_item_id = null;
                            if (wishlitProductidList.contains(results.get(i).getId())) {
                                Log.e("if", "=" + results.get(i).getId());
                                Log.e("if_item_id", "=" + wishlitProductidList.indexOf(results.get(i).getId()));
                                int pos = wishlitProductidList.indexOf(results.get(i).getId());

                                if (wishlitItemId.size() > 0) {
                                    wishlist_item_id = String.valueOf(wishlitItemId.get(pos));
                                    Log.e("wishlist_item_id_3317", "=" + wishlist_item_id);
                                }

                                isWishliste = "true";
                                results.get(i).setWishlisted(isWishliste);
                                results.get(i).setWishlist_item_id(wishlist_item_id);
                            } else {
                                wishlist_item_id = "0";
                                isWishliste = "false";

                                results.get(i).setWishlist_item_id(wishlist_item_id);
                                results.get(i).setWishlisted(isWishliste);
                                Log.e("else", "=" + results.get(i).getId());

                            }
                        }

                        Log.e("debug_145", "=" + results);
                        if (results.isEmpty()) {
                            Log.e("debug_147", "=" + results);
                            lvnodata_searchist.setVisibility(View.VISIBLE);
                            lv_progress_search.setVisibility(View.GONE);
                            lv_main_search.setVisibility(View.GONE);
                        } else {
                            Log.e("debug_148", "=" + results);
                            lvnodata_searchist.setVisibility(View.GONE);
                            lv_progress_search.setVisibility(View.GONE);
                            lv_main_search.setVisibility(View.VISIBLE);
                            searchProductListAdapter.addAll(results);
                        }
                    }
                   /* try {
                        jsonObject1 = new JSONObject(response.body().string());
                        Log.e("search_jsonobj", "=" + jsonObject1);

                        if(jsonObject1.optString("items")==null || jsonObject1.optString("items").equalsIgnoreCase("null"))
                        {
                            Toast.makeText(getActivity(), "No Search Result Found", Toast.LENGTH_SHORT).show();
                            lvnodata_searchist.setVisibility(View.VISIBLE);
                            lv_progress_search.setVisibility(View.GONE);
                            lv_main_search.setVisibility(View.GONE);

                        }
                        JSONArray jsonArray=jsonObject1.getJSONArray("items");
                        Log.e("jsonarray", "=" + jsonArray);

                        if (jsonArray.length() == 0 || jsonArray==null || jsonArray.equals("null")) {
                            Log.e("jso_else", "=" + jsonArray);

                            Toast.makeText(getActivity(), "No Search Result Found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.e("product_id_search", "=" + jsonObject.optString("id"));
                                    Log.e("sku", "=" + jsonObject.optString("sku"));

                                    JSONArray imgarray=jsonObject.getJSONArray("media_gallery_entries");
                                    String image=imgarray.getJSONObject(0).optString("file");
                                    Log.e("image", "=" + image);

                                 *//*   searchModelslist.add(new SearchModel(jsonObject.optString("id"),
                                            jsonObject.optString("sku"),
                                            jsonObject.optString("name"),
                                            jsonObject.optString("price"),
                                            image ));*//*

                                } catch (Exception e) {
                                    Log.e("exceptio333", "=" + e.getLocalizedMessage());
                                }
                            }
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
*/
                } else {
                    lvnodata_searchist.setVisibility(View.GONE);
                    lv_progress_search.setVisibility(View.GONE);
                    lv_main_search.setVisibility(View.VISIBLE);

                    // Toast.makeText(parent, ""+getFavouriteslist.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Call<ProductModel> getsearchapi(String searchValuee) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_213n22", "==" + Login_preference.gettoken(getActivity()));
        Log.e("searchValuee22", "==" + searchValuee);
        //http://dkbraende.demoproject.info/rest/all/V1/products?searchCriteria[filterGroups][0][filters][0][field]=entity_id&searchCriteria[filterGroups][0][filters][0][value]=364,227&searchCriteria[filterGroups][0][filters][0][conditionType]=fineset
        String url=ApiClient.MAIN_URLL+"products?searchCriteria[filterGroups][0][filters][0][field]=entity_id&searchCriteria[filterGroups][0][filters][0][value]="
                +searchValuee+"&searchCriteria[filterGroups][0][filters][0][conditionType]=fineset";

        Log.e("debug_searchvalue", "==" + url);
        return api.getSearchValueList("Bearer " + Login_preference.gettoken(getActivity()),url);
    }
    private Call<ResponseBody> getsearchValue(String searhkey) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_11token22", "==" + Login_preference.gettoken(getActivity()));
        ////http://dkbraende.demoproject.info/rest/V1/search?searchCriteria[requestName]=quick_search_container&searchCriteria[filter_groups][0][filters][0][field]=search_term&searchCriteria[filter_groups][0][filters][0][value]=brændetårn&fields=items[id]
        String url=ApiClient.MAIN_URLL+"search?searchCriteria[requestName]=quick_search_container&searchCriteria[filter_groups][0][filters][0][field]=search_term&searchCriteria[filter_groups][0][filters][0][value]="+searhkey+"&fields=items[id]";
        Log.e("debug_333", "==" + url);
        return api.getSearchValue("Bearer " + Login_preference.gettoken(getActivity()),url);
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

    private void allocateMemory() {
        toolbar_Search = v.findViewById(R.id.toolbar_Search);
        recv_search = v.findViewById(R.id.recv_search);
        searchView = v.findViewById(R.id.searchView);
        lvnodata_searchist = v.findViewById(R.id.lvnodata_searchist);
        lv_progress_search = v.findViewById(R.id.lv_progress_search);
        lv_main_search = v.findViewById(R.id.lv_main_search);


        //attachRecyclerView
        searchProductListAdapter = new SearchProductListAdapter(getActivity(), searchModelslist);
        recv_search.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recv_search.setAdapter(searchProductListAdapter);

    }
    private void sarchViewFocuable() {
        searchPlateEditText = ((EditText) searchView.findViewById(R.id.search_src_text));
        searchView.setBackground(null);
        searchPlateEditText.setBackgroundColor(Color.TRANSPARENT);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocus();
        searchView.isInEditMode();
        searchView.requestFocusFromTouch();
        searchView.setIconified(false);
        searchView.setQueryHint("Search Here");
    }
   /* private void sarchViewFocuable() {
        searchPlateEditText = ((EditText) searchView.findViewById(R.id.search_src_text));
        searchView.setBackground(null);
        searchPlateEditText.setBackgroundColor(Color.TRANSPARENT);
        searchView.setIconifiedByDefault(false);
        //  searchView.setFocusable(true);
        // searchView.requestFocus();
        searchView.isInEditMode();
        searchView.requestFocusFromTouch();
        //searchView.setIconified(false);
        searchView.setQueryHint("Search Here");
    }*/
}

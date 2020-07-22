package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.HomeCategoryAdapter;
import com.example.defaultdemotoken.Adapter.ProductCategoryAdapter;
import com.example.defaultdemotoken.Adapter.ProductCategoryAdapterTemporary;
import com.example.defaultdemotoken.Adapter.ProductListAdater;
import com.example.defaultdemotoken.Adapter.SubCategoryAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.CurrentOrdersModel;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.Model.ProductModel.Item;
import com.example.defaultdemotoken.Model.ProductModel.ProductModel;
import com.example.defaultdemotoken.Model.SubCategory;
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

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;
import static com.example.defaultdemotoken.Adapter.ProductListAdater.SPAN_COUNT_ONE;
import static com.example.defaultdemotoken.Adapter.ProductListAdater.SPAN_COUNT_THREE;

public class ProductListFragment extends Fragment implements View.OnClickListener {
    ProductListAdater productListAdater;
    RecyclerView recv_productlist, recv_categories_products;
    ApiInterface apiInterface;
    public static String catid, catname;
    View v;
    Bundle b;
    LinearLayout lv_progress_product, lvnodata_productlist, lv_main_productlist, lv_filter_clickk;
    TextView tv_filter, tv_product_title;
    Toolbar toolbar_product;
    //ProductCategoryAdapter productCategoryAdapter;
 //   private List<HomebannerModel> categorylist = new ArrayList<>();
    private List<Integer> wishlitProductidList = new ArrayList<>();
    private List<Integer> wishlitItemId = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    ImageView product_as_list_view, product_as_grid_view, product_as_grid_view_clicked, product_as_list_view_clicked;
    ProductCategoryAdapterTemporary productCategoryAdapterTemporary;
    private List<SubCategory> subCategoryArrayList = new ArrayList<>();
    public  ArrayList<SubCategory> catSubarraylist;
    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_product_list, container, false);

        allocateMemory();
        attachrecyclerview();
        setHasOptionsMenu(true);

        b = this.getArguments();
        if (b != null) {
            catid = b.getString("categoryid");
            catname = b.getString("categoryname");
            tv_product_title.setText(catname);
            catSubarraylist= (ArrayList<SubCategory>) b.getSerializable("subCatarraylist");

        }
        Log.e("debugeee", "e==" + catid);
        Log.e("catSubarraylist101", "e==" + catSubarraylist);
        Log.e("debugeee", "e==" + catname);
        Log.e("temp_cat_data", "e==" + subCategoryArrayList);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_product);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        attachrecyclerview();

        toolbar_product.setTitle(catname);
        toolbar_product.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        attachrecyclerview();

        if (CheckNetwork.isNetworkAvailable(getActivity())) {

            if (Login_preference.getLogin_flag(getActivity()).equalsIgnoreCase("1")) {
                callWishistApi();
            } else {
            }
            CALL_productlist_Api();
            CallProductCategoriesApi();

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }


        product_as_list_view.setOnClickListener(this);
        product_as_grid_view_clicked.setOnClickListener(this);
        lv_filter_clickk.setOnClickListener(this);

        return v;

    }


    private void CallProductCategoriesApi() {
        subCategoryArrayList.clear();
        if(catSubarraylist==null || catSubarraylist.size()==0)
        {
            recv_categories_products.setVisibility(View.GONE);
        }else {
            recv_categories_products.setVisibility(View.VISIBLE);

            if(catSubarraylist.size()>4)
            {
                for (int i = 0; i < 4; i++) {

                    // public SubCategory(Integer id, Integer parentId, String name, Boolean isActive, Integer position, Integer level, Integer productCount, List<Object> childrenData) {
                    subCategoryArrayList.add(new SubCategory(
                            catSubarraylist.get(i).getId(),
                            catSubarraylist.get(i).getParentId(),
                            catSubarraylist.get(i).getName(),
                            catSubarraylist.get(i).getIsActive(),
                            catSubarraylist.get(i).getPosition(),
                            catSubarraylist.get(i).getLevel(),
                            catSubarraylist.get(i).getProductCount(),
                            catSubarraylist.get(i).getChildrenData()
                    ));
                }

            }else {
                for (int i = 0; i < catSubarraylist.size(); i++) {

                    // public SubCategory(Integer id, Integer parentId, String name, Boolean isActive, Integer position, Integer level, Integer productCount, List<Object> childrenData) {
                    subCategoryArrayList.add(new SubCategory(
                            catSubarraylist.get(i).getId(),
                            catSubarraylist.get(i).getParentId(),
                            catSubarraylist.get(i).getName(),
                            catSubarraylist.get(i).getIsActive(),
                            catSubarraylist.get(i).getPosition(),
                            catSubarraylist.get(i).getLevel(),
                            catSubarraylist.get(i).getProductCount(),
                            catSubarraylist.get(i).getChildrenData()
                    ));
                }
            }


        }

        /*productCategoryAdapterTemporary.addAll(subCategoryArrayList);*/

        /*for (int i=0;i<8;i++)
        {
            categorylist.add(new HomebannerModel(" "));
        }*/


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

    private void attachrecyclerview() {

        String gridlist = Login_preference.getgridlist(getActivity());
        Log.e("gridlist_value",""+gridlist);

        if (gridlist=="1" || gridlist.equalsIgnoreCase("1")){
            gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT_ONE);
            productListAdater = new ProductListAdater(getActivity(), gridLayoutManager);
            recv_productlist.setLayoutManager(gridLayoutManager);
            /*recv_productlist.setLayoutManager(new GridLayoutManager(getContext(), 2));*/
            recv_productlist.setAdapter(productListAdater);
            product_as_list_view.setVisibility(View.GONE);
            product_as_list_view_clicked.setVisibility(View.VISIBLE);
            product_as_grid_view.setVisibility(View.GONE);
            product_as_grid_view_clicked.setVisibility(View.VISIBLE);
        }else if (gridlist=="2" || gridlist.equalsIgnoreCase("2")){
            gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT_THREE);
            productListAdater = new ProductListAdater(getActivity(), gridLayoutManager);
            recv_productlist.setLayoutManager(gridLayoutManager);
            /*recv_productlist.setLayoutManager(new GridLayoutManager(getContext(), 2));*/
            recv_productlist.setAdapter(productListAdater);
            product_as_grid_view.setVisibility(View.VISIBLE);
            product_as_grid_view_clicked.setVisibility(View.GONE);
            product_as_list_view.setVisibility(View.VISIBLE);
            product_as_list_view_clicked.setVisibility(View.GONE);
        }else {
            gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT_THREE);
            productListAdater = new ProductListAdater(getActivity(), gridLayoutManager);
            recv_productlist.setLayoutManager(gridLayoutManager);
            /*recv_productlist.setLayoutManager(new GridLayoutManager(getContext(), 2));*/
            recv_productlist.setAdapter(productListAdater);
        }

        /*productCategoryAdapter = new ProductCategoryAdapter(getActivity(),categorylist);
        recv_categories_products.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recv_categories_products.setAdapter(productCategoryAdapter);*/

        productCategoryAdapterTemporary = new ProductCategoryAdapterTemporary(getActivity(), subCategoryArrayList,catSubarraylist);
        recv_categories_products.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recv_categories_products.setAdapter(productCategoryAdapterTemporary);

    }

    private void allocateMemory() {
        tv_product_title = v.findViewById(R.id.tv_product_title);
        tv_filter = v.findViewById(R.id.tv_filter);
        recv_categories_products = v.findViewById(R.id.recv_categories_products);
        lvnodata_productlist = v.findViewById(R.id.lvnodata_productlist);
        lv_progress_product = v.findViewById(R.id.lv_progress_product);
        recv_productlist = v.findViewById(R.id.recv_productlist);
        toolbar_product = v.findViewById(R.id.toolbar_product);
        lv_main_productlist = v.findViewById(R.id.lv_main_productlist);
        lv_filter_clickk = v.findViewById(R.id.lv_filter_clickk);
        product_as_list_view = v.findViewById(R.id.product_as_list_view);
        product_as_grid_view = v.findViewById(R.id.product_as_grid_view);
        product_as_grid_view_clicked = v.findViewById(R.id.product_as_grid_view_clicked);
        product_as_list_view_clicked = v.findViewById(R.id.product_as_list_view_clicked);

        tv_filter.setTypeface(SplashActivity.montserrat_medium);
        tv_product_title.setTypeface(SplashActivity.montserrat_semibold);

    }

    private void CALL_productlist_Api() {
        lv_progress_product.setVisibility(View.VISIBLE);
        lv_main_productlist.setVisibility(View.GONE);
        lvnodata_productlist.setVisibility(View.GONE);

        getcategory().enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                Log.e("responseeee_cate", "=" + response.body());
                Log.e("responseeee_45343cate", "=" + response);

                if (response.code() == 200) {

                    Log.e("responseeee44", "=" + response.body().toString());
                    Log.e("item", "=" + response.body().getItems());
                    Log.e("wishlistidsize", "=" + wishlitProductidList.size());
                    //  Log.e("Success_35", "=" + response.body().getSearchCriteria());

                    lvnodata_productlist.setVisibility(View.GONE);
                    lv_progress_product.setVisibility(View.GONE);
                    lv_main_productlist.setVisibility(View.VISIBLE);

                    ProductModel model = response.body();
                    if (model.getItems() == null || model.getItems().size() == 0) {
                        lvnodata_productlist.setVisibility(View.VISIBLE);
                        lv_progress_product.setVisibility(View.GONE);
                        lv_main_productlist.setVisibility(View.GONE);
                        Log.e("debug_178", "=" + fetchResults(response));
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
                            lvnodata_productlist.setVisibility(View.VISIBLE);
                            lv_progress_product.setVisibility(View.GONE);
                            lv_main_productlist.setVisibility(View.GONE);
                        } else {
                            Log.e("debug_148", "=" + results);
                            lvnodata_productlist.setVisibility(View.GONE);
                            lv_progress_product.setVisibility(View.GONE);
                            lv_main_productlist.setVisibility(View.VISIBLE);
                            productListAdater.addAll(results);
                        }
                    }
                } else {
                    lvnodata_productlist.setVisibility(View.GONE);
                    lv_progress_product.setVisibility(View.VISIBLE);
                    lv_main_productlist.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                t.printStackTrace();
                Log.e("debug_failure", "=" + t.getMessage());
            }
        });
    }

    private Call<ProductModel> getcategory() {
        Log.e("catid_113", "=" + catid);
        return apiInterface.products("Bearer " + Login_preference.gettoken(getActivity()), "category_id", catid);
    }

    private List<Item> fetchResults(Response<ProductModel> response) {
        Log.e("newin_home_209", "" + response.body());
        ProductModel ProductModel = response.body();
        return ProductModel.getItems();
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
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == product_as_list_view) {
            Log.e("list_clicked","ok");
            Login_preference.setgridlist(getActivity(),"1");
            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
            product_as_list_view.setVisibility(View.GONE);
            product_as_list_view_clicked.setVisibility(View.VISIBLE);
            product_as_grid_view.setVisibility(View.GONE);
            product_as_grid_view_clicked.setVisibility(View.VISIBLE);
            /*if (gridLayoutManager.getSpanCount()==SPAN_COUNT_ONE){
                gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);
            }else {
                gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
            }*/
            productListAdater.notifyItemRangeChanged(0, productListAdater.getItemCount());
        } else if (v == product_as_grid_view_clicked) {
            Log.e("grid_clicked","ok");
            Login_preference.setgridlist(getActivity(),"2");
            gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);
            product_as_grid_view.setVisibility(View.VISIBLE);
            product_as_grid_view_clicked.setVisibility(View.GONE);
            product_as_list_view.setVisibility(View.VISIBLE);
            product_as_list_view_clicked.setVisibility(View.GONE);
            /*if (gridLayoutManager.getSpanCount()==SPAN_COUNT_THREE){
                gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
            }else {
                gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);
            }*/
            productListAdater.notifyItemRangeChanged(0, productListAdater.getItemCount());
        }else if(v==lv_filter_clickk)
        {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Bundle b = new Bundle();
            b.putString("categoryid", catid);
            b.putString("categoryname", catname);
            //   b.putString("product_name", String.valueOf(ItemList.get(position).getName()));
            FilterListFragment myFragment = new FilterListFragment();
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
    }
}

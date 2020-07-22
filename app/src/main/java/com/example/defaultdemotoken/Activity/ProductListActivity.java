package com.example.defaultdemotoken.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import com.example.defaultdemotoken.Adapter.ProductListAdater;
import com.example.defaultdemotoken.Model.ProductModel.Item;
import com.example.defaultdemotoken.Model.ProductModel.ProductModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {
    ProductListAdater productListAdater;
    RecyclerView recv_productlist;
    ApiInterface apiInterface;
    String catid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        catid=getIntent().getStringExtra("id");
        Log.e("debugeee","e=="+catid);
        recv_productlist=findViewById(R.id.recv_productlist);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

      //  productListAdater = new ProductListAdater(ProductListActivity.this);
        recv_productlist.setLayoutManager(new GridLayoutManager(ProductListActivity.this, 2));
        recv_productlist.setAdapter(productListAdater);

        CALL_productlist_Api();
    }

    private void CALL_productlist_Api() {
        getcategory().enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                Log.e("responseeee_cate", "=" + response.body());
                Log.e("responseeee_45343cate", "=" + response);
                Log.e("responseeee44", "=" + response.body().toString());
                Log.e("item", "=" + response.body().getItems());
              //  Log.e("Success_35", "=" + response.body().getSearchCriteria());

                ProductModel model = response.body();
                List<Item> results = fetchResults(response);
                productListAdater.addAll(results);

             /*   if (model.getName().equalsIgnoreCase("Success")) {
                    Log.e("debug_155", "=" + model.getCategory());

                    Check_String_NULL_Value(tv_home_fitness_title,model.getDealtext());

                    if(getActivity()!=null)
                    {
                        final RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.flex_logo);
                        requestOptions.error(R.drawable.flex_logo);
                        Glide.with(getActivity())
                                .setDefaultRequestOptions(requestOptions)
                                .load(model.getBanner()).into(iv_main_banner_home);

                    }



                    if (model.getCategory() == null || model.getCategory().size() == 0) {

                        Log.e("debug_178", "=" + fetchResults(response));
                    } else {

                        List<Category> results = fetchResults(response);
                        Log.e("debug_145", "=" + results);
                        if (results.isEmpty()) {
                            Log.e("debug_147", "=" + results);


                        } else {
                            Log.e("debug_148", "=" + results);


                            homeFirstAdapter.addAll(results);
                        }
                    }
                } else {
                    Log.e("debug_149", "=");


                    //   Toast.makeText(getActivity(), "" + model.ge(), Toast.LENGTH_SHORT).show();
                }*/
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
        return apiInterface.products("Bearer "+"pub5mq68fi5oq1rzdincr5rryzxnjl6n","category_id",catid);
    }

    private List<Item> fetchResults(Response<ProductModel> response) {
        Log.e("newin_home_209", "" + response.body());
        ProductModel ProductModel = response.body();
        return ProductModel.getItems();
    }

}

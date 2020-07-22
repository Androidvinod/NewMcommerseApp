package com.example.defaultdemotoken.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.defaultdemotoken.Adapter.ChildDataAdapter;
import com.example.defaultdemotoken.Model.CategoriesModel;
import com.example.defaultdemotoken.Model.ChildData;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    RecyclerView recv_list;
    ChildDataAdapter ChildDataAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recv_list=findViewById(R.id.recv_list);

        ChildDataAdapter = new ChildDataAdapter(MainActivity.this);
        recv_list.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recv_list.setAdapter(ChildDataAdapter);

        CALL_home_first();

    }

    private void CALL_home_first() {
        getcategory().enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                Log.e("responseeee_cate", "=" + response.body());
                Log.e("Success_35", "=" + response.body().getName());

                CategoriesModel model = response.body();
                List<ChildData> results = fetchResults(response);
                ChildDataAdapter.addAll(results);

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
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                t.printStackTrace();
                Log.e("debug_failure", "=" + t.getMessage());
            }
        });
    }

    private Call<CategoriesModel> getcategory() {
          return apiInterface.categories("Bearer "+"ot656q25ob0e5j38g050xtnfesuulvzj");
    }

    private List<ChildData> fetchResults(Response<CategoriesModel> response) {
        Log.e("newin_home_209", "" + response.body());
        CategoriesModel CategoriesModel = response.body();
        return CategoriesModel.getChildrenData();
    }


}

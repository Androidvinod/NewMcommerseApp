package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.Filter_By_Adapter;

import com.example.defaultdemotoken.Adapter.SortByAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.FilterModel.FilterModel;
import com.example.defaultdemotoken.Model.FilterModel.SortModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterListFragment extends Fragment implements View.OnClickListener {
    Toolbar toolbar_filter;
    TextView tv_refine_toolbar_title,tv_refine_sort_by,tv_refine_filter_by,tv_clearall_filter,tv_show_result;
    LinearLayout lvnodata_filter,lv_filter_progress,lv_sort,lv_filter,lv_showFilterResult,lv_clear_all_filter;
    CoordinatorLayout cordinator_main_filter;
    RecyclerView rv_sort_by,rv_filter_by;
    Filter_By_Adapter filter_by_adapter;

    SortByAdapter sortByAdapter;
    ApiInterface apiInterface;

    Bundle b;
    public  String catid,catname;
    private List<FilterModel> filterModelList=new ArrayList<>();
    private List<SortModel> sortModelList=new ArrayList<>();;

    public FilterListFragment() {
        // Required empty public constructor
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_filter_list, container, false);
        AllocateMemory(v);
        setHasOptionsMenu(true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        b=this.getArguments();
        if(b!=null)
        {
            catid=b.getString("categoryid");
            catname=b.getString("categoryname");
            Log.e("catid83", "=" + catid);
        }
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_filter);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.closse);


        filter_by_adapter = new Filter_By_Adapter(getActivity(),filterModelList);
        rv_filter_by.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv_filter_by.setAdapter(filter_by_adapter);

        sortByAdapter = new SortByAdapter(getActivity(),sortModelList);
        rv_sort_by.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv_sort_by.setAdapter(sortByAdapter);

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CALL_Filter_Api(catid);
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }

        lv_showFilterResult.setOnClickListener(this);
        lv_clear_all_filter.setOnClickListener(this);
        return v;

    }

    private void CALL_Filter_Api(String catid) {
        lv_filter_progress.setVisibility(View.VISIBLE);
        cordinator_main_filter.setVisibility(View.GONE);
        lvnodata_filter.setVisibility(View.GONE);
        sortModelList.clear();
        filterModelList.clear();
        getfilterList(catid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responseeee_fil", "=" + response.body());
                Log.e("responseeee_45343cate", "=" + response);

                if(response.code()==200)
                {
                    lv_filter_progress.setVisibility(View.GONE);
                    cordinator_main_filter.setVisibility(View.VISIBLE);
                    lvnodata_filter.setVisibility(View.GONE);

                    try {
                        JSONArray jsonArray=new JSONArray(response.body().string());
                        Log.e("jsonarray", "=" + jsonArray.length());
                        Log.e("jsonarray", "=" + jsonArray);

                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            JSONArray filterarray=jsonObject.getJSONArray("filter");
                            JSONArray sortarray=jsonObject.getJSONArray("sort");
                            Log.e("filterarray", "=" + filterarray);
                            Log.e("sortarrayuu", "=" + sortarray);

                            //filter array
                            for (int j=0;j<filterarray.length();j++)
                            {
                                JSONObject filterObject=filterarray.getJSONObject(j);
                                filterModelList.add(new FilterModel(filterObject.optString("label"),filterObject.optString("name")));
                            }

                            //sort array
                            for (int i=0;i<sortarray.length();i++)
                            {
                                JSONObject jsonObject1=sortarray.getJSONObject(i);
                                sortModelList.add(new SortModel(jsonObject1.optString("label"),jsonObject1.optString("name")));
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    lvnodata_filter.setVisibility(View.GONE);
                    lv_filter_progress.setVisibility(View.GONE);
                    cordinator_main_filter.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("debug_failure", "=" + t.getMessage());
            }
        });
    }


    private Call<ResponseBody> getfilterList(String catidd) {
        Log.e("catid_113", "=" + catidd);
        Log.e("debuyg_155_token", "=" + Login_preference.gettoken(getActivity()));

       // String url="http://dkbraende.demoproject.info/rest/V1/filterlist?categoryId="+ catidd;
        String url=ApiClient.MAIN_URLL+"filterlist?categoryId=2";
        return apiInterface.getFilterList("Bearer "+ Login_preference.gettoken(getActivity()),url);
    }

    private void AllocateMemory(View v) {
        lv_clear_all_filter=v.findViewById(R.id.lv_clear_all_filter);
        lv_showFilterResult=v.findViewById(R.id.lv_showFilterResult);
        toolbar_filter=v.findViewById(R.id.toolbar_filter);
        tv_refine_toolbar_title=v.findViewById(R.id.tv_refine_toolbar_title);
        lvnodata_filter=v.findViewById(R.id.lvnodata_filter);
        lv_filter_progress=v.findViewById(R.id.lv_filter_progress);
        cordinator_main_filter=v.findViewById(R.id.cordinator_main_filter);
        lv_sort=v.findViewById(R.id.lv_sort);
        tv_refine_sort_by=v.findViewById(R.id.tv_refine_sort_by);
        rv_sort_by=v.findViewById(R.id.rv_sort_by);
        lv_filter=v.findViewById(R.id.lv_filter);
        tv_refine_filter_by=v.findViewById(R.id.tv_refine_filter_by);
        rv_filter_by=v.findViewById(R.id.rv_filter_by);
        tv_clearall_filter=v.findViewById(R.id.tv_clearall_filter);
        tv_show_result=v.findViewById(R.id.tv_show_result);

        tv_refine_toolbar_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_refine_sort_by.setTypeface(SplashActivity.montserrat_semibold);
        tv_refine_filter_by.setTypeface(SplashActivity.montserrat_semibold);

        tv_clearall_filter.setTypeface(SplashActivity.montserrat_medium);
        tv_show_result.setTypeface(SplashActivity.montserrat_medium);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
       // inflater.inflate(R.menu.menu_clear_all, menu);

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
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==lv_showFilterResult)
        {

        }else if(v==lv_clear_all_filter)
        {

        }
    }
}

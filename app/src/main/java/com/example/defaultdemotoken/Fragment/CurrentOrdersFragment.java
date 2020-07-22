package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.Complete_Order_Adapter;
import com.example.defaultdemotoken.Adapter.Current_Order_Adapter;

import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.CompletedOrdersModel;
import com.example.defaultdemotoken.Model.CurrentOrdersModel;
import com.example.defaultdemotoken.Model.DeliveryModel;
import com.example.defaultdemotoken.Model.HomeModel.BrowseCategory;
import com.example.defaultdemotoken.Model.HomebannerModel;
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


public class CurrentOrdersFragment extends Fragment {
    LinearLayout lv_progress_order, lv_no_orders, lv_main_orders_and_returnsss;
    View v;
    TextView tv_order_title;
    RecyclerView recv_current_order;
    Current_Order_Adapter current_order_adapter;
    private List<CurrentOrdersModel> currentOrdersModels = new ArrayList<>();

    public CurrentOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_order_list, container, false);
        allocateMemory(v);
        setHasOptionsMenu(true);
        attachRecyclrview();

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CallCurrentOrdersListApi();
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void CallCurrentOrdersListApi() {
        currentOrdersModels.clear();
        lv_progress_order.setVisibility(View.VISIBLE);
        lv_main_orders_and_returnsss.setVisibility(View.GONE);
        callcurrentorderapi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_orderlist", "" + response.body());
                Log.e("response_orderlistt", "" + response);
                Log.e("status_orderlist", "" + response.body());

                if (response.code() == 200) {

                    lv_progress_order.setVisibility(View.GONE);
                    lv_main_orders_and_returnsss.setVisibility(View.VISIBLE);

                    /*lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);*/

                    Log.e("response_res_ol", "" + response);
                    Log.e("status_res_ol", "=" + response.body());

                    try {

                        JSONObject jsonObject = new JSONObject(response.body().string());

                        Log.e("debug_jsonobj", "=" + jsonObject);
                        JSONArray jsonArray = jsonObject.getJSONArray("items");
                        Log.e("orderlist_array", "" + jsonArray);

                        if (jsonArray.length() == 0) {

                            lv_no_orders.setVisibility(View.VISIBLE);

                        } else {

                            lv_no_orders.setVisibility(View.GONE);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                try {

                                    JSONObject vals = jsonArray.getJSONObject(i);
                                    Log.e("valss_arrayyy", "" + vals);
                                    //firstname,orderid
                                    currentOrdersModels.add(new CurrentOrdersModel(vals.getString("customer_firstname"), vals.getString("increment_id"),
                                            vals.getString("grand_total"), vals.getString("base_currency_code"), vals.getString("entity_id")));

                                } catch (Exception e) {

                                    Log.e("exception22", "=" + e.getLocalizedMessage());

                                }

                            }

                            current_order_adapter.notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    lv_progress_order.setVisibility(View.GONE);
                    lv_main_orders_and_returnsss.setVisibility(View.VISIBLE);


                    /*lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);*/

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lv_progress_order.setVisibility(View.GONE);
                lv_main_orders_and_returnsss.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<ResponseBody> callcurrentorderapi() {
        ApiInterface apiinterface = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_111", "" + Login_preference.getcustomer_id(getActivity()));
        String url = ApiClient.MAIN_URLL+"orders/?searchCriteria[filterGroups][0][filters][0][field]=customer_id&searchCriteria[filterGroups][0][filters][0][value]="
                + Login_preference.getcustomer_id(getActivity()) + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq";
        return apiinterface.getcompleteorderapi("Bearer " + Login_preference.gettoken(getActivity()), url);

    }

    /*for (int i=0;i<2;i++)
        {
            homebannerModelList.add(new HomebannerModel(""));
            list.add(new HomebannerModel(""));
        }*/

    private void attachRecyclrview() {

        current_order_adapter = new Current_Order_Adapter(getActivity(), currentOrdersModels);
        recv_current_order.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_current_order.setAdapter(current_order_adapter);
    }

    private void allocateMemory(View v) {
        lv_progress_order = v.findViewById(R.id.lv_progress_order);
        lv_main_orders_and_returnsss = v.findViewById(R.id.lv_main_orders_and_returnsss);
        // tv_order_title = v.findViewById(R.id.tv_order_title);
        recv_current_order = v.findViewById(R.id.recv_current_order);
        lv_no_orders = v.findViewById(R.id.lv_no_orders);
    }
}

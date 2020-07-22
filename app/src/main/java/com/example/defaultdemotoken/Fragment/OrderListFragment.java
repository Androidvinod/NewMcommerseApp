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


public class OrderListFragment extends Fragment {
    LinearLayout lv_progress_order, lvnodata_orderlist, lv_main_orders_and_returnsss;
    View v;
    TextView tv_order_title, tv_orders_history_title, tv_current_orders_title;
    Toolbar toolbar_orderlist;
    RecyclerView recv_current_order, recv_complete_order;
    Current_Order_Adapter current_order_adapter;
    Complete_Order_Adapter complete_order_adapter;
    private List<CurrentOrdersModel> currentOrdersModels = new ArrayList<>();
    private List<CompletedOrdersModel> completedOrdersModels = new ArrayList<>();
    //private List<HomebannerModel> list = new ArrayList<>();
    String orderid;
    String firstname;

    public OrderListFragment() {
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

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_orderlist);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CallCurrentOrdersListApi();
            CallCompletedtOrdersListApi();
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void CallCurrentOrdersListApi() {
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

                        } else {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                try {
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    Log.e("ol_jo", "" + object);

                                    Log.e("customer_firstname_ol", "" + object.getString("customer_firstname"));

                                    JSONArray items_arr = object.getJSONArray("items");
                                    Log.e("inner_itmes_data_ol", "" + items_arr);

                                    JSONObject object11 = jsonArray.getJSONObject(0);
                                    Log.e("ol_jo_11", "" + object11);

                                    JSONArray inner_items_arr = object.getJSONArray("items");
                                    Log.e("inner_itmes_data_ol", "" + inner_items_arr);

                                    JSONObject extension_attributes_od = object.getJSONObject("extension_attributes");
                                    Log.e("billing_address_od", "" + extension_attributes_od);

                                    JSONArray shipping_assignments_od = extension_attributes_od.getJSONArray("shipping_assignments");
                                    Log.e("shipping_assignments_od_arr", "" + shipping_assignments_od);

                                    for (int a = 0; a < shipping_assignments_od.length(); a++) {
                                        JSONObject vals = shipping_assignments_od.getJSONObject(i);
                                        Log.e("vals_shipping_inner", "" + vals);

                                        JSONObject shipping_innerrr_od = vals.getJSONObject("shipping");
                                        Log.e("shipping_iinneerr", "" + shipping_innerrr_od);

                                        JSONObject address_inner_od = shipping_innerrr_od.getJSONObject("address");
                                        Log.e("address_inneerr", "" + address_inner_od);

                                        firstname = address_inner_od.getString("firstname");
                                        Log.e("addtype_od", "" + firstname);

                                    }

                                    for (int j = 0; j < inner_items_arr.length(); j++) {
                                        JSONObject vals = inner_items_arr.getJSONObject(i);

                                        orderid = vals.getString("order_id");
                                        Log.e("order_id_ol", "" + orderid);

                                    }

                                  /*  currentOrdersModels.add(new CurrentOrdersModel(firstname, orderid,
                                            object.getString("grand_total"), object.getString("base_currency_code")));
*/
                                } catch (Exception e) {

                                    Log.e("Exception177", "=" + e);

                                } finally {
                                    current_order_adapter.notifyItemChanged(i);
                                }

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
                lv_progress_order.setVisibility(View.GONE);
                lv_main_orders_and_returnsss.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<ResponseBody> callcurrentorderapi() {
        ApiInterface apiinterface = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_111", "" + Login_preference.getcustomer_id(getActivity()));
        String url = "http://dkbraende.demoproject.info/rest/V1/orders/?searchCriteria[filterGroups][0][filters][0][field]=customer_id&searchCriteria[filterGroups][0][filters][0][value]=" + Login_preference.getcustomer_id(getActivity()) + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq";

        Log.e("debug_111", "" + url);
        Log.e("debug_111", "" + Login_preference.gettoken(getActivity()));

        return apiinterface.getcompleteorderapi("Bearer " + Login_preference.gettoken(getActivity()), url);

    }

    private void CallCompletedtOrdersListApi() {
        lv_progress_order.setVisibility(View.VISIBLE);
        lv_main_orders_and_returnsss.setVisibility(View.GONE);
        callcompleteorderapi().enqueue(new Callback<ResponseBody>() {
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

                        } else {

                            for (int i = 0; i < 4; i++) {

                                try {
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    Log.e("ol_jo", "" + object);

                                    Log.e("customer_firstname_ol", "" + object.getString("customer_firstname"));

                               /*     completedOrdersModels.add(new CompletedOrdersModel(object.getString("customer_firstname"), object.getString("increment_id"),
                                            object.getString("grand_total"), object.getString("base_currency_code")));
*/
                                } catch (Exception e) {

                                    Log.e("Exception22", "=" + e);

                                } finally {

                                    complete_order_adapter.notifyItemChanged(i);

                                }

                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {

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

    /*for (int i=0;i<2;i++)
        {
            homebannerModelList.add(new HomebannerModel(""));
            list.add(new HomebannerModel(""));
        }*/


    private Call<ResponseBody> callcompleteorderapi() {
        ApiInterface apiinterface = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_111", "" + Login_preference.getcustomer_id(getActivity()));
        String url = "http://dkbraende.demoproject.info/rest/V1/orders/?searchCriteria[filterGroups][0][filters][0][field]=customer_id&searchCriteria[filterGroups][0][filters][0][value]=" + Login_preference.getcustomer_id(getActivity()) + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq" + Login_preference.getcustomer_id(getActivity());
        return apiinterface.getcompleteorderapi("Bearer " + Login_preference.gettoken(getActivity()), url);

    }

    private void attachRecyclrview() {

        current_order_adapter = new Current_Order_Adapter(getActivity(), currentOrdersModels);
        recv_current_order.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_current_order.setAdapter(current_order_adapter);

        complete_order_adapter = new Complete_Order_Adapter(getActivity(), completedOrdersModels);
        recv_complete_order.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_complete_order.setAdapter(complete_order_adapter);
    }

    private void allocateMemory(View v) {
        lv_progress_order = v.findViewById(R.id.lv_progress_order);
        lv_main_orders_and_returnsss = v.findViewById(R.id.lv_main_orders_and_returnsss);
        //lvnodata_orderlist = v.findViewById(R.id.lvnodata_orderlist);
        //tv_order_title = v.findViewById(R.id.tv_order_title);
        toolbar_orderlist = v.findViewById(R.id.toolbar_orderlist);
      /*  tv_orders_history_title = v.findViewById(R.id.tv_orders_history_title);
        tv_current_orders_title = v.findViewById(R.id.tv_current_orders_title);
    */
        recv_complete_order = v.findViewById(R.id.recv_complete_order);
        recv_current_order = v.findViewById(R.id.recv_current_order);

        tv_current_orders_title.setTypeface(SplashActivity.montserrat_bold);
        tv_orders_history_title.setTypeface(SplashActivity.montserrat_bold);
        tv_order_title.setTypeface(SplashActivity.montserrat_bold);
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
}

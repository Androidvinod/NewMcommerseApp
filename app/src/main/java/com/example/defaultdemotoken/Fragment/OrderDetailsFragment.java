package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
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
import com.example.defaultdemotoken.Adapter.OrderDetailsYourOrderAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.CurrentOrdersModel;
import com.example.defaultdemotoken.Model.OrderDetailsYourOrderModel;
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

public class OrderDetailsFragment extends Fragment {

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    View v;
    Toolbar toolbar_order_details;
    Bundle b;
    String order_id;
    TextView tv_order_set_successful, tv_order_status_od, tv_orders_itemid_value_od, tv_order_order_no_od, tv_orders_number_value_od, tv_order_date_od, tv_orders_date_value_od, tv_order_payment_method_od,
            tv_orders_payment_mode_value_od, tv_shipping_address_od, tv_shipping_address_name_od, tv_shipping_address_address_od, tv_your_orders_od, tv_sub_total_od, tv_tax_od, tv_tax_value_od, tv_discount_od,
            tv_discount_value_od, tv_total_od, tv_total_value_od, tv_sub_total_value_od, tv_billing_address_od, tv_billing_address_name_od, tv_billing_address_address_od;
    RecyclerView rv_od_your_order_od;
    private List<OrderDetailsYourOrderModel> orderDetailsYourOrderModels = new ArrayList<OrderDetailsYourOrderModel>();
    private OrderDetailsYourOrderAdapter orderDetailsYourOrderAdapter;
    LinearLayout lv_progress_od, lv_od_main;
    String street_name, street_name_billing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_order_details, container, false);

        AllocateMemeory(v);
        AttachRecyclerview();
        setHasOptionsMenu(true);
        b = this.getArguments();

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_order_details);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        toolbar_order_details.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        if (b != null) {
            order_id = b.getString("order_id");
            Log.e("order_id_od", "" + order_id);
        }

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CallOrderDetailsApi();
            callyour_orders_api();
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }

        return v;

    }

    private void CallOrderDetailsApi() {

        lv_od_main.setVisibility(View.GONE);
        lv_progress_od.setVisibility(View.VISIBLE);

        callorderdetails().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_orderlist", "" + response.body());
                Log.e("response_orderlistt", "" + response);
                Log.e("status_orderlist", "" + response.body());
                lv_od_main.setVisibility(View.VISIBLE);
                lv_progress_od.setVisibility(View.GONE);

                if (response.code() == 200) {

                    /*lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);*/
                    JSONObject jsonObject = null;
                    try {

                        jsonObject = new JSONObject(response.body().string());

                        String store_name_od = jsonObject.getString("updated_at");
                        Log.e("updated_at_od", "" + store_name_od);

                        String od_increment_id = jsonObject.getString("increment_id");
                        Log.e("od_increment_id", "" + od_increment_id);

                        tv_orders_number_value_od.setText(od_increment_id);
                        tv_orders_date_value_od.setText(jsonObject.getString("created_at"));
                        //tv_shipping_address_name_od.setText(jsonObject.getString("customer_firstname"));
                        tv_sub_total_value_od.setText(jsonObject.getString("base_subtotal"));
                        tv_tax_value_od.setText(jsonObject.getString("base_tax_amount"));
                        tv_discount_value_od.setText(jsonObject.getString("discount_amount"));
                        tv_total_value_od.setText(jsonObject.getString("subtotal_incl_tax"));

                        Log.e("debug_jsonobj", "=" + jsonObject);
                        JSONArray jsonArray = jsonObject.getJSONArray("items");
                        Log.e("billing_135", "" + jsonObject.getString("billing_address"));


                        Log.e("orderlist_array_odod", "" + jsonArray);

                        if (jsonArray.length() == 0) {

                        } else {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject billingaddress = jsonObject.getJSONObject("billing_address");
                                Log.e("billing_address_order_details", "" + billingaddress);

                                if (billingaddress.length() == 0) {
                                    tv_billing_address_address_od.setText("Billing Address Not Found !!!");
                                } else {
                                    JSONArray street_arr_billing_address = billingaddress.getJSONArray("street");
                                    Log.e("billing_address_street_arrr", "" + street_arr_billing_address);

                                    Log.e("FIRSTNAME_BILLING", "=" + billingaddress.getString("firstname"));
                                    Log.e("FIRSTNAME_BILLING", "=" + billingaddress.getString("lastname"));

                                    for (int q = 0; q < street_arr_billing_address.length(); q++) {

                                        street_name_billing = street_arr_billing_address.getString(q);
                                        Log.e("billing_address_streett_namee", "=" + street_name_billing);

                                    }

                                    tv_billing_address_name_od.setText(billingaddress.getString("firstname") + " " + billingaddress.getString("lastname"));
                                    tv_billing_address_address_od.setText(street_name_billing + " " + billingaddress.getString("city") + " " + billingaddress.getString("postcode"));

                                }

                                JSONObject extension_attributes_od = jsonObject.getJSONObject("extension_attributes");
                                Log.e("extension_attributes_odd", "" + extension_attributes_od);

                                JSONArray shipping_assignments_od = extension_attributes_od.getJSONArray("shipping_assignments");
                                Log.e("shipping_assignments_od_arr", "" + shipping_assignments_od);

                                for (int a = 0; a < shipping_assignments_od.length(); a++) {
                                    JSONObject vals = shipping_assignments_od.getJSONObject(i);
                                    Log.e("vals_shipping_inner", "" + vals);

                                    JSONObject shipping_innerrr_od = vals.getJSONObject("shipping");
                                    Log.e("shipping_iinneerr", "" + shipping_innerrr_od);

                                    JSONObject address_inner_od = shipping_innerrr_od.getJSONObject("address");
                                    Log.e("shipping_address_order_details", "" + address_inner_od);

                                    if (address_inner_od.length() == 0) {
                                        tv_shipping_address_address_od.setText("Shipping Address Not Found !!!");
                                    } else {
                                        String street = address_inner_od.getString("street");
                                        Log.e("shipping_address_street", "" + street);

                                        JSONArray street_arr = address_inner_od.getJSONArray("street");
                                        Log.e("shipping_address_street_arrr", "" + street_arr);

                                        for (int q = 0; q < street_arr.length(); q++) {
                                            street_name = street_arr.getString(q);
                                            Log.e("shipping_address_street_name", "=" + street_name);
                                        }

                                        tv_shipping_address_name_od.setText(address_inner_od.getString("firstname") + " " + address_inner_od.getString("lastname"));
                                        tv_shipping_address_address_od.setText(street_name + " " + address_inner_od.getString("city") + " " + address_inner_od.getString("postcode"));

                                    }

                                }

                            }

                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("catch_e_add", "" + e.getMessage());
                    }

                } else {

                    /*lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);*/

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lv_od_main.setVisibility(View.VISIBLE);
                lv_progress_od.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<ResponseBody> callorderdetails() {
        ApiInterface apiinterface = ApiClient.getClient().create(ApiInterface.class);
        String url = ApiClient.MAIN_URLL+"orders/" + order_id;
        return apiinterface.getordeedetailsapi("Bearer " + Login_preference.gettoken(getActivity()), url);

    }

        /*for (int i = 0; i < 5; i++) {
            orderDetailsYourOrderModels.add(new OrderDetailsYourOrderModel("", "", "", ""));
        }
        orderDetailsYourOrderAdapter.notifyDataSetChanged();*/


    private void callyour_orders_api() {

        callorderdetails().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {

                    /*lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);*/

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        JSONArray jsonArray = jsonObject.getJSONArray("items");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject_inner_orders = jsonArray.getJSONObject(i);
                                orderDetailsYourOrderModels.add(new OrderDetailsYourOrderModel(jsonObject_inner_orders.getString("name"), jsonObject_inner_orders.getString("item_id"),
                                        jsonObject_inner_orders.getString("price_incl_tax")));
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            orderDetailsYourOrderAdapter.notifyDataSetChanged();

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
                Toast.makeText(getActivity(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AttachRecyclerview() {
        orderDetailsYourOrderAdapter = new OrderDetailsYourOrderAdapter(getActivity(), orderDetailsYourOrderModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_od_your_order_od.setLayoutManager(layoutManager);
        rv_od_your_order_od.setAdapter(orderDetailsYourOrderAdapter);
    }

    private void AllocateMemeory(View v) {

        toolbar_order_details = v.findViewById(R.id.toolbar_order_details);
        rv_od_your_order_od = v.findViewById(R.id.rv_od_your_order_od);
        lv_progress_od = v.findViewById(R.id.lv_progress_od);
        lv_od_main = v.findViewById(R.id.lv_od_main);

        tv_order_set_successful = v.findViewById(R.id.tv_order_set_successful);
        tv_sub_total_value_od = v.findViewById(R.id.tv_sub_total_value_od);
        tv_order_status_od = v.findViewById(R.id.tv_order_status_od);
        tv_orders_itemid_value_od = v.findViewById(R.id.tv_orders_itemid_value_od);
        tv_order_order_no_od = v.findViewById(R.id.tv_order_order_no_od);
        tv_orders_number_value_od = v.findViewById(R.id.tv_orders_number_value_od);
        tv_order_date_od = v.findViewById(R.id.tv_order_date_od);
        tv_order_payment_method_od = v.findViewById(R.id.tv_order_payment_method_od);
        tv_orders_payment_mode_value_od = v.findViewById(R.id.tv_orders_payment_mode_value_od);
        tv_shipping_address_name_od = v.findViewById(R.id.tv_shipping_address_name_od);
        tv_shipping_address_address_od = v.findViewById(R.id.tv_shipping_address_address_od);
        tv_your_orders_od = v.findViewById(R.id.tv_your_orders_od);
        tv_sub_total_od = v.findViewById(R.id.tv_sub_total_od);
        tv_tax_od = v.findViewById(R.id.tv_tax_od);
        tv_tax_value_od = v.findViewById(R.id.tv_tax_value_od);
        tv_discount_od = v.findViewById(R.id.tv_discount_od);
        tv_discount_value_od = v.findViewById(R.id.tv_discount_value_od);
        tv_total_od = v.findViewById(R.id.tv_total_od);
        tv_total_value_od = v.findViewById(R.id.tv_total_value_od);
        tv_orders_date_value_od = v.findViewById(R.id.tv_orders_date_value_od);
        tv_shipping_address_od = v.findViewById(R.id.tv_shipping_address_od);
        tv_billing_address_od = v.findViewById(R.id.tv_billing_address_od);
        tv_billing_address_name_od = v.findViewById(R.id.tv_billing_address_name_od);
        tv_billing_address_address_od = v.findViewById(R.id.tv_billing_address_address_od);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
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
}
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
import com.example.defaultdemotoken.Model.OrderDetailsModels.NewOrderDetailModel;
import com.example.defaultdemotoken.Model.OrderDetailsYourOrderModel;
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

public class OrderDetailsFragmentNew extends Fragment {

    public OrderDetailsFragmentNew() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_order_details_new, container, false);

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

        callorderdetailsnew().enqueue(new Callback<NewOrderDetailModel>() {
            @Override
            public void onResponse(Call<NewOrderDetailModel> call, Response<NewOrderDetailModel> response) {
                Log.e("response_orderlist", "" + response.body());
                Log.e("response_orderlistt", "" + response);
                Log.e("status_orderlist", "" + response.body());
                lv_od_main.setVisibility(View.VISIBLE);
                lv_progress_od.setVisibility(View.GONE);

                //NewOrderDetailModel model = response.body();

                if (response.code() == 200) {

                    tv_orders_number_value_od.setText(response.body().getIncrementId());
                    tv_orders_date_value_od.setText(response.body().getCreatedAt());
                    tv_sub_total_value_od.setText(String.valueOf(response.body().getBaseSubtotal()));
                    tv_tax_value_od.setText(String.valueOf(response.body().getBaseTaxAmount()));
                    tv_discount_value_od.setText(String.valueOf(response.body().getDiscountAmount()));
                    tv_total_value_od.setText(String.valueOf(response.body().getSubtotalInclTax()));

                    if (response.body().getBillingAddress() == null) {

                        tv_billing_address_name_od.setText("Billing Address Not Found !!!");

                    } else {

                        String street_billing = String.valueOf(response.body().getBillingAddress().getStreet().get(0));
                        String city_billing = String.valueOf(response.body().getBillingAddress().getCity());
                        String postcode_billing = String.valueOf(response.body().getBillingAddress().getPostcode());
                        String telephone_billing = String.valueOf(response.body().getBillingAddress().getTelephone());
                        String billing_address = street_billing + ", " + city_billing + ", " + postcode_billing + ",\n" + telephone_billing;

                        tv_billing_address_name_od.setText(response.body().getBillingAddress().getFirstname());
                        tv_billing_address_address_od.setText(billing_address);

                    }

                    if (response.body().getExtensionAttributes().getShippingAssignments().get(0).getShipping().getAddress() == null) {

                        tv_shipping_address_name_od.setText("Shipping Address Not Found !!!");

                    } else {

                        String street_shipping = String.valueOf(response.body().getExtensionAttributes().getShippingAssignments().get(0).getShipping().getAddress().getStreet().get(0));
                        String city_shipping = String.valueOf(response.body().getExtensionAttributes().getShippingAssignments().get(0).getShipping().getAddress().getCity());
                        String postcode_shipping = String.valueOf(response.body().getExtensionAttributes().getShippingAssignments().get(0).getShipping().getAddress().getPostcode());
                        String telephone_shipping = String.valueOf(response.body().getExtensionAttributes().getShippingAssignments().get(0).getShipping().getAddress().getTelephone());
                        String shipping_address = street_shipping + ", " + city_shipping + ", " + postcode_shipping + ",\n" + telephone_shipping;

                        tv_shipping_address_name_od.setText(response.body().getExtensionAttributes().getShippingAssignments().get(0).getShipping().getAddress().getFirstname());
                        tv_shipping_address_address_od.setText(shipping_address);

                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<NewOrderDetailModel> call, Throwable t) {
                lv_od_main.setVisibility(View.VISIBLE);
                lv_progress_od.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<NewOrderDetailModel> callorderdetailsnew() {
        ApiInterface apiinterface = ApiClient.getClient().create(ApiInterface.class);
        String url = ApiClient.MAIN_URLL+"orders/" + order_id;
        return apiinterface.getordeedetailsapinew("Bearer " + Login_preference.gettoken(getActivity()), url);

    }

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

    private Call<ResponseBody> callorderdetails() {
        ApiInterface apiinterface = ApiClient.getClient().create(ApiInterface.class);
        String url = ApiClient.MAIN_URLL+"orders/" + order_id;
        return apiinterface.getordeedetailsapi("Bearer " + Login_preference.gettoken(getActivity()), url);
    }

        /*for (int i = 0; i < 5; i++) {
            orderDetailsYourOrderModels.add(new OrderDetailsYourOrderModel("", "", "", ""));
        }
        orderDetailsYourOrderAdapter.notifyDataSetChanged();*/


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
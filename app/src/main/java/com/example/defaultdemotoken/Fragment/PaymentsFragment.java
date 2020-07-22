package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.PaymentAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.AddressModel.Address;
import com.example.defaultdemotoken.Model.AddressModel.AddressModell;
import com.example.defaultdemotoken.Model.DeliveryModel;
import com.example.defaultdemotoken.Model.PaymentModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.google.android.gms.common.api.Api;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_address;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_delivery;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_payments;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_last_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_last_grey;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_del_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_del_grey;

public class PaymentsFragment extends Fragment implements View.OnClickListener {

    View v;
    RecyclerView payment_recyclerview;
    private List<PaymentModel> paymentModels = new ArrayList<PaymentModel>();
    private PaymentAdapter paymentAdapter;
    LinearLayout lv_payment_progress,lv_placeorder,lv_main_checkout_pain;
    TextInputLayout layout_card_name,layout_card_number,layout_card_expirydate,layout_card_cvv;
    public static ApiInterface apiInterface;

    TextInputEditText et_card_name,et_card_no,et_card_expirydate,et_card_cvv;
    CheckBox checkbox_save_card;
    EditText edt_coupon_code;
    TextView tv_apply,tv_subtotal,tv_subtotal_value,tv_tax,tv_taxt_value,tv_discount,tv_discount_value,tv_total,tv_total_value,tv_place_order;
    CoordinatorLayout cordinator_checkout;
    String delivery_address,addressidd;
    Bundle b;


    String city,country_id,email,firstname,lastname,postcode,street,telephone,region,region_code,region_id;

    String base_currency_code,base_discount_amount,base_grand_total,base_shipping_amount,base_subtotal
    ,base_tax_amount,customer_email,customer_firstname,customer_id,customer_lastname,
            discount_amount,grand_total,shipping_amount,subtotal,subtotal_incl_tax,tax_amount,total_qty_ordered;
    String paymentMethod="cashondelivery",customer_group_id="",address_type="billing";

    String cart_base_discount_amount="",cart_base_original_price="",cart_base_price="",cart_base_price_incl_tax="",cart_base_row_invoiced=""
            ,cart_base_row_total="",cart_base_tax_amount="",cart_discount_amount="",cart_discount_percent="",cart_name="",cart_original_price="",cart_price="",
            cart_price_incl_tax="",cart_product_id="",cart_qty_ordered="",cart_row_total="",cart_row_total_incl_tax="";
    String halfUrl="",billingaddressurl="",shippingAddressUrl="",cartItemsUrl="",cart_sku,cart_product_type;
    Boolean isBillingAddress;
    public static String payment_method_order="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_payments, container, false);


        payment_method_order="";
       // passaddress="";
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        customer_email=Login_preference.getemail(getActivity());
        customer_firstname=Login_preference.getfirstname(getActivity());
        customer_id=Login_preference.getcustomer_id(getActivity());
        customer_lastname=Login_preference.getlastname(getActivity());
        customer_group_id=Login_preference.getgroupid(getActivity());

        AllocateMemory();
        AttachRecyclerview();
        setupUI(lv_main_checkout_pain);
        setHasOptionsMenu(true);

        b=this.getArguments();
        if(b != null)
        {
            delivery_address=b.getString("delivery_address");
            addressidd=b.getString("addressidd");
            isBillingAddress=b.getBoolean("isBillingAddress");
            cart_sku=b.getString("cart_sku");
            cart_product_type=b.getString("cart_product_type");

            Log.e("debug_100","s"+delivery_address);
            Log.e("addressidd","s"+addressidd);
            Log.e("isBillingAddress","s"+isBillingAddress);
        }

        rad_delivery.setChecked(false);
        rad_address.setChecked(false);
        rad_payments.setChecked(true);

        view_address_last_grey.setVisibility(View.GONE);
        view_address_last_green.setVisibility(View.VISIBLE);

        view_del_grey.setVisibility(View.VISIBLE);
        view_del_green.setVisibility(View.GONE);



        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CallPaymentApi();
            CallAddressApi();
            callPaymentInformationApi();
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }
      //  lv_payment_back.setOnClickListener(this);
        lv_placeorder.setOnClickListener(this);
        tv_apply.setOnClickListener(this);

        return v;
    }
    public  Call<ResponseBody> callcartdataapi() {
        Log.e("debugcustomertoen","="+Login_preference.getCustomertoken(getActivity()));

        return apiInterface.getpricedata("Bearer " + Login_preference.getCustomertoken(getActivity()));
    }

    private void callPaymentInformationApi() {
        callcartdataapi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("res_140", "" + response.body());
                if(response.isSuccessful() || response.code()==200)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject totalsobj = jsonObject.getJSONObject("totals");
                        Log.e("totalsobj", "" + totalsobj);
                        Log.e("res_147", "" + totalsobj.optString("grand_total"));
                        Log.e("pricereponse_158", "" + totalsobj.optString("base_currency_code"));


                        shipping_amount= totalsobj.optString("shipping_amount");
                        base_currency_code= totalsobj.optString("base_currency_code");
                        base_discount_amount= totalsobj.optString("base_discount_amount");
                        base_grand_total= totalsobj.optString("base_grand_total");
                        base_shipping_amount= totalsobj.optString("base_shipping_amount");
                        base_subtotal= totalsobj.optString("base_subtotal");
                        base_tax_amount= totalsobj.optString("base_tax_amount");
                        discount_amount= totalsobj.optString("discount_amount");
                        grand_total= totalsobj.optString("grand_total");
                        subtotal= totalsobj.optString("subtotal");
                        subtotal_incl_tax= totalsobj.optString("subtotal_incl_tax");
                        tax_amount= totalsobj.optString("tax_amount");
                        total_qty_ordered= totalsobj.optString("items_qty");
                        tv_subtotal_value.setText(subtotal);
                        tv_taxt_value.setText(subtotal_incl_tax);
                        tv_discount_value.setText(discount_amount);
                        tv_total_value.setText(grand_total);
                        JSONArray itemsArray = totalsobj.getJSONArray("items");
                        for (int i=0; i < itemsArray.length(); i++)
                        {
                            JSONObject item_obj=itemsArray.getJSONObject(i);
                           // cart_base_discount_amount=item_obj.optString("base_discount_amount");
                           // cart_base_original_price=item_obj.optString("price");
                           // cart_base_price=item_obj.optString("base_price");
                          //  cart_base_price_incl_tax=item_obj.optString("base_price_incl_tax");
                           // cart_base_row_invoiced=item_obj.optString("base_row_invoiced");
                          //  cart_base_row_total=item_obj.optString("base_row_total");
                            //cart_base_tax_amount=item_obj.optString("base_tax_amount");
                           // cart_discount_amount=item_obj.optString("discount_amount");
                          //  cart_discount_percent=item_obj.optString("discount_percent");
                          //  cart_name=item_obj.optString("name");
                          //  cart_original_price=item_obj.optString("price");
                            //cart_price=item_obj.optString("base_price");
                           // cart_price_incl_tax=item_obj.optString("price_incl_tax");
                          //  cart_product_id=item_obj.optString("item_id");
                          //  cart_qty_ordered=item_obj.optString("qty");
                         //   cart_row_total=item_obj.optString("row_total");
                          //  cart_row_total_incl_tax=item_obj.optString("row_total_incl_tax");

                            if(cart_row_total_incl_tax.equalsIgnoreCase(""))
                            {
                                cart_row_total_incl_tax="entity[items]"+"["+i+"]"+"[row_total_incl_tax]="+item_obj.optString("row_total_incl_tax");
                            }else {
                                cart_row_total_incl_tax+="&entity[items]"+"["+i+"]"+"[row_total_incl_tax]="+item_obj.optString("row_total_incl_tax");
                            }
                            if(cart_row_total.equalsIgnoreCase(""))
                            {
                                cart_row_total="entity[items]"+"["+i+"]"+"[row_total]="+item_obj.optString("row_total");
                            }else {
                                cart_row_total+="&entity[items]"+"["+i+"]"+"[row_total]="+item_obj.optString("row_total");
                            }
                            if(cart_qty_ordered.equalsIgnoreCase(""))
                            {
                                cart_qty_ordered="entity[items]"+"["+i+"]"+"[qty_ordered]="+item_obj.optString("qty");
                            }else {
                                cart_qty_ordered+="&entity[items]"+"["+i+"]"+"[qty_ordered]="+item_obj.optString("qty");
                            }
                            if(cart_product_id.equalsIgnoreCase(""))
                            {
                                cart_product_id="entity[items]"+"["+i+"]"+"[product_id]="+item_obj.optString("item_id");
                            }else {
                                cart_product_id+="&entity[items]"+"["+i+"]"+"[product_id]="+item_obj.optString("item_id");
                            }

                            if(cart_price_incl_tax.equalsIgnoreCase(""))
                            {
                                cart_price_incl_tax="entity[items]"+"["+i+"]"+"[price_incl_tax]="+item_obj.optString("price_incl_tax");
                            }else {
                                cart_price_incl_tax+="&entity[items]"+"["+i+"]"+"[price_incl_tax]="+item_obj.optString("price_incl_tax");
                            }

                            if(cart_price.equalsIgnoreCase(""))
                            {
                                cart_price="entity[items]"+"["+i+"]"+"[price]="+item_obj.optString("base_price");
                            }else {
                                cart_price+="&entity[items]"+"["+i+"]"+"[price]="+item_obj.optString("base_price");
                            }

                            if(cart_original_price.equalsIgnoreCase(""))
                            {
                                cart_original_price="entity[items]"+"["+i+"]"+"[original_price]="+item_obj.optString("price");
                            }else {
                                cart_original_price+="&entity[items]"+"["+i+"]"+"[original_price]="+item_obj.optString("price");
                            }
                            if(cart_name.equalsIgnoreCase(""))
                            {
                                cart_name="entity[items]"+"["+i+"]"+"[name]="+item_obj.optString("name");
                            }else {
                                cart_name+="&entity[items]"+"["+i+"]"+"[name]="+item_obj.optString("name");
                            }
                            if(cart_discount_percent.equalsIgnoreCase(""))
                            {
                                cart_discount_percent="entity[items]"+"["+i+"]"+"[discount_percent]="+item_obj.optString("discount_percent");
                            }else {
                                cart_discount_percent+="&entity[items]"+"["+i+"]"+"[discount_percent]="+item_obj.optString("discount_percent");
                            }

                            if(cart_discount_amount.equalsIgnoreCase(""))
                            {
                                cart_discount_amount="entity[items]"+"["+i+"]"+"[discount_amount]="+item_obj.optString("discount_amount");
                            }else {
                                cart_discount_amount+="&entity[items]"+"["+i+"]"+"[discount_amount]="+item_obj.optString("discount_amount");
                            }

                            if(cart_base_tax_amount.equalsIgnoreCase(""))
                            {
                                cart_base_tax_amount="entity[items]"+"["+i+"]"+"[base_tax_amount]="+item_obj.optString("base_tax_amount");
                            }else {
                                cart_base_tax_amount+="&entity[items]"+"["+i+"]"+"[base_tax_amount]="+item_obj.optString("base_tax_amount");
                            }
                            if(cart_base_row_total.equalsIgnoreCase(""))
                            {
                                cart_base_row_total="entity[items]"+"["+i+"]"+"[base_row_total]="+item_obj.optString("base_row_total");
                            }else {
                                cart_base_row_total+="&entity[items]"+"["+i+"]"+"[base_row_total]="+item_obj.optString("base_row_total");
                            }

                            if(cart_base_row_invoiced.equalsIgnoreCase(""))
                            {
                                cart_base_row_invoiced="entity[items]"+"["+i+"]"+"[base_row_invoiced]="+item_obj.optString("base_row_invoiced");
                            }else {
                                cart_base_row_invoiced+="&entity[items]"+"["+i+"]"+"[base_row_invoiced]="+item_obj.optString("base_row_invoiced");
                            }

                            if(cart_base_discount_amount.equalsIgnoreCase(""))
                            {
                                cart_base_discount_amount="entity[items]"+"["+i+"]"+"[base_discount_amount]="+item_obj.optString("base_discount_amount");
                            }else {
                                cart_base_discount_amount+="&entity[items]"+"["+i+"]"+"[base_discount_amount]="+item_obj.optString("base_discount_amount");
                            }

                            if(cart_base_original_price.equalsIgnoreCase(""))
                            {
                                cart_base_original_price="entity[items]"+"["+i+"]"+"[base_original_price]="+item_obj.optString("price");
                            }else {
                                cart_base_original_price+="&entity[items]"+"["+i+"]"+"[base_original_price]="+item_obj.optString("price");
                            }

                            if(cart_base_price.equalsIgnoreCase(""))
                            {
                                cart_base_price="entity[items]"+"["+i+"]"+"[base_price]="+item_obj.optString("base_price");
                            }else {
                                cart_base_price+="&entity[items]"+"["+i+"]"+"[base_price]="+item_obj.optString("base_price");
                            }

                            if(cart_base_price_incl_tax.equalsIgnoreCase(""))
                            {
                                cart_base_price_incl_tax="entity[items]"+"["+i+"]"+"[base_price_incl_tax]="+item_obj.optString("base_price_incl_tax");
                            }else {
                                cart_base_price_incl_tax+="&entity[items]"+"["+i+"]"+"[base_price_incl_tax]="+item_obj.optString("base_price_incl_tax");
                            }


                        }

                        halfUrl="entity[payment][method]="+paymentMethod+"&entity[base_currency_code]="+base_currency_code+"&entity[base_discount_amount]="+base_discount_amount+
                                    "&entity[base_grand_total]="+base_grand_total+"&entity[base_shipping_amount]="+base_shipping_amount+
                                "&entity[base_subtotal]="+base_subtotal+"&entity[base_tax_amount]="+base_tax_amount+
                                "&entity[customer_email]="+customer_email+"&entity[customer_firstname]="+customer_firstname+
                                "&entity[customer_group_id]="+customer_group_id+"&entity[customer_id]="+customer_id+
                                    "&entity[customer_lastname]="+customer_lastname+"&entity[discount_amount]="+discount_amount+
                            "&entity[grand_total]="+grand_total+"&entity[shipping_amount]="+shipping_amount+"&entity[subtotal]="+subtotal+
                            "&entity[subtotal_incl_tax]="+subtotal_incl_tax+"&entity[tax_amount]="+tax_amount+"&total_qty_ordered="+total_qty_ordered;


                        cartItemsUrl=cart_base_discount_amount+"&"+cart_base_original_price+"&"+cart_base_price+
                                "&"+cart_base_price_incl_tax+"&"+cart_base_price_incl_tax+"&"+cart_discount_amount+"&"+cart_base_row_total+"&"+
                                cart_row_total_incl_tax+"&"+cart_row_total+"&"+cart_qty_ordered+"&"+cart_product_id+"&"+cart_price_incl_tax+
                                "&"+cart_price+"&"+cart_original_price+"&"+cart_name+"&"+cart_discount_percent+"&"+cart_discount_amount;


                        Log.e("cartdiscountamount_212", "" +cart_base_discount_amount);
                        Log.e("cart_base_riginalprice", "" +cart_base_original_price);
                        Log.e("cart_base_price", "" +cart_base_price);
                        Log.e("cart_base_row_invoiced", "" +cart_base_row_invoiced);
                        Log.e("cart_basepricencl_tax", "" +cart_base_price_incl_tax);
                        Log.e("cart_discount_amount", "" +cart_discount_amount);
                        Log.e("cart_base_row_total", "" +cart_base_row_total);
                        Log.e("cart_row_total_incl_tax", "" +cart_row_total_incl_tax);
                        Log.e("cart_row_total", "" +cart_row_total);
                        Log.e("cart_qty_ordered", "" +cart_qty_ordered);
                        Log.e("cart_product_id", "" +cart_product_id);
                        Log.e("cart_price_incl_tax", "" +cart_price_incl_tax);
                        Log.e("cart_price", "" +cart_price);
                        Log.e("cart_original_price", "" +cart_original_price);
                        Log.e("cart_name", "" +cart_name);
                        Log.e("cart_discount_percent", "" +cart_discount_percent);
                        Log.e("cart_discount_amount", "" +cart_discount_amount);
                        Log.e("halfUrl", "========" +halfUrl);
                        Log.e("cartItemsUrl", "========" +cartItemsUrl);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.e("error_293",""+response.body());
                    NavigationActivity.get_Customer_tokenapi();
                    callPaymentInformationApi();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "pages: " + t);
            }
        });
    }


    private void CallAddressApi() {
        calladdressgapi().enqueue(new Callback<AddressModell>() {
            @Override
            public void onResponse(Call<AddressModell> call, Response<AddressModell> response) {
                Log.e("response", "" + response.body());
                Log.e("response_77", "" + response);
                Log.e("status", "" + response.body());

                if (response.code() == 200) {

                    Log.e("response_77", "" + response);
                    Log.e("status", "" + response.body());

                    AddressModell addressModell = response.body();
                    List<Address> additionalAddresses = response.body().getAddresses();
                    if (additionalAddresses.isEmpty()) {
                        // tv_no_addressfafound.setVisibility(View.VISIBLE);
                    } else {

                        Log.e("status127", "=" + response.body().getAddresses());
                        Log.e("status128", "=" + response.body().toString());

                        // tv_no_addressfafound.setVisibility(View.GONE);
                        List<Address> datalist = fetchResultsaa(response);

                        if (datalist.size() == 0) {
                        } else {
                        }

                        Log.e("feesInnerData", "=" + datalist);
                        List<String> stringArrayList=datalist.get(0).getStreet();
                        city=datalist.get(0).getCity();
                        telephone=datalist.get(0).getTelephone();
                        postcode=datalist.get(0).getPostcode();
                        firstname=datalist.get(0).getFirstname();
                        lastname=datalist.get(0).getLastname();
                        country_id=datalist.get(0).getCountryId();
                        email=response.body().getEmail();
                        street=stringArrayList.get(0);

                        region=datalist.get(0).getRegion().getRegion();
                        region_code=datalist.get(0).getRegion().getRegionCode();
                        region_id= String.valueOf(datalist.get(0).getRegion().getRegionId());

                        Log.e("region", "=" + region);
                        Log.e("region_code", "=" + region_code);
                        Log.e("region_id", "=" + region_id);
                        Log.e("street", "=" + street);
                        Log.e("email", "=" + email);
                        Log.e("country_id", "=" + country_id);
                        Log.e("lastname", "=" + lastname);
                        Log.e("firstname", "=" + firstname);
                        Log.e("postcode", "=" + postcode);
                        Log.e("telephone", "=" + telephone);
                        Log.e("city", "=" + city);

                        billingaddressurl="&entity[billing_address][address_type]="+address_type+
                                "&entity[billing_address][city]="+city+"&entity[billing_address][country_id]="+country_id+
                                "&entity[billing_address][email]="+email+"&entity[billing_address][firstname]="+firstname+
                                "&entity[billing_address][lastname]="+lastname+"&entity[billing_address][postcode]="+postcode+
                                "&entity[billing_address][street][0]="+street+"&entity[billing_address][telephone]="+telephone+
                        "&entity[billing_address][region]="+region+"&entity[billing_address][region_code]="+region_code+
                        "&entity[billing_address][region_id]="+region_id;


                        shippingAddressUrl="&entity[extension_attributes][shipping_assignments][0][shipping][address][address_type]=shipping"+
                                "&entity[extension_attributes][shipping_assignments][0][shipping][address][city]="+city+
                                "&entity[extension_attributes][shipping_assignments][0][shipping][address][country_id]="+country_id+
                                "&entity[extension_attributes][shipping_assignments][0][shipping][address][email]="+email+
                                "&entity[extension_attributes][shipping_assignments][0][shipping][address][firstname]="+firstname+
                                "&entity[extension_attributes][shipping_assignments][0][shipping][address][lastname]="+lastname+
                                "&entity[extension_attributes][shipping_assignments][0][shipping][address][postcode]="+postcode+
                        "&entity[extension_attributes][shipping_assignments][0][shipping][address][street][0]="+street+
                                "&entity[extension_attributes][shipping_assignments][0][shipping][address][telephone]="+telephone+
                        "&entity[extension_attributes][shipping_assignments][0][shipping][address][region]="+region+
                        "&entity[extension_attributes][shipping_assignments][0][shipping][address][region_code]="+region_code+
                        "&entity[extension_attributes][shipping_assignments][0][shipping][address][region_id]="+region_id;
//                        addressBookAdapter.addAll(feesInnerData);
                    }

                    Log.e("billingaddressurl", "==" + billingaddressurl);
                    Log.e("shippingAddressUrl", "==" + shippingAddressUrl);

                } else {
                    Log.e("response_77", "" + response);
                    Log.e("status", "" + response.body());
              }

            }

            @Override
            public void onFailure(Call<AddressModell> call, Throwable t) {
                Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Address> fetchResultsaa(Response<AddressModell> response) {
        AddressModell getAddressModel = response.body();
        return getAddressModel.getAddresses();
    }

    private Call<AddressModell> calladdressgapi() {

        Log.e("debug_111", "=" + Login_preference.gettoken(getActivity()));
        Log.e("custormid", "=" + Login_preference.getcustomer_id(getActivity()));
        String url = ApiClient.MAIN_URLL + "customers/" + Login_preference.getcustomer_id(getActivity());
        return apiInterface.address("Bearer " + Login_preference.gettoken(getActivity()), url);
    }



    private void CallPaymentApi() {
        paymentModels.clear();
        lv_payment_progress.setVisibility(View.VISIBLE);
        cordinator_checkout.setVisibility(View.GONE);

        callPaymentMethodsApi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                lv_payment_progress.setVisibility(View.GONE);
                cordinator_checkout.setVisibility(View.VISIBLE);
                Log.e("payment_method_response", "" + response);

                if (response.isSuccessful() || response.code() == 200) {
                    JSONArray itemArray = null;
                    try {
                        itemArray = new JSONArray(response.body().string());
                        Log.e("payment_method__array", "" + itemArray);

                        for (int i = 0; i < itemArray.length(); i++) {
                            try {
                                JSONObject vals = itemArray.getJSONObject(i);

                                String payment_code = vals.getString("code");
                                Log.e("payment_code", "" + payment_code);

                                paymentModels.add(new PaymentModel(vals.getString("code"), vals.getString("title")));

                            } catch (Exception e) {
                                Log.e("Exception", ""
                                        + e);
                            } finally {
                                paymentAdapter.notifyItemChanged(i);
                            }
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lv_payment_progress.setVisibility(View.GONE);
                cordinator_checkout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "" + t.getMessage());
            }
        });
    }

    public Call<ResponseBody> callPaymentMethodsApi() {
        return apiInterface.getPaymentMethod("Bearer " + Login_preference.gettoken(getActivity()), "http://dkbraende.demoproject.info/rest//V1/carts/"+Login_preference.getquote_id(getActivity())+"/payment-methods");
    }

    private void AllocateMemory() {

        tv_place_order = v.findViewById(R.id.tv_place_order);
        cordinator_checkout = v.findViewById(R.id.cordinator_checkout);
        lv_placeorder = v.findViewById(R.id.lv_placeorder);
        tv_total_value = v.findViewById(R.id.tv_total_value);
        tv_total = v.findViewById(R.id.tv_total);
        tv_discount_value = v.findViewById(R.id.tv_discount_value);
        tv_discount = v.findViewById(R.id.tv_discount);
        tv_taxt_value = v.findViewById(R.id.tv_taxt_value);
        payment_recyclerview = v.findViewById(R.id.payment_recyclerview);
        lv_payment_progress = v.findViewById(R.id.lv_payment_progress);

        layout_card_name = v.findViewById(R.id.layout_card_name);
        et_card_name = v.findViewById(R.id.et_card_name);
        layout_card_number = v.findViewById(R.id.layout_card_number);
        et_card_no = v.findViewById(R.id.et_card_no);
        layout_card_expirydate = v.findViewById(R.id.layout_card_expirydate);
        et_card_expirydate = v.findViewById(R.id.et_card_expirydate);
        layout_card_cvv = v.findViewById(R.id.layout_card_cvv);
        et_card_cvv = v.findViewById(R.id.et_card_cvv);

        checkbox_save_card = v.findViewById(R.id.checkbox_save_card);
        edt_coupon_code = v.findViewById(R.id.edt_coupon_code);
        tv_apply = v.findViewById(R.id.tv_apply);
        tv_subtotal = v.findViewById(R.id.tv_apply);
        tv_subtotal_value = v.findViewById(R.id.tv_subtotal_value);
        tv_tax = v.findViewById(R.id.tv_tax);
        lv_main_checkout_pain = v.findViewById(R.id.lv_main_checkout_pain);


        tv_subtotal_value.setTypeface(SplashActivity.montserrat_semibold);
        tv_discount_value.setTypeface(SplashActivity.montserrat_semibold);
        tv_taxt_value.setTypeface(SplashActivity.montserrat_semibold);
        tv_total_value.setTypeface(SplashActivity.montserrat_semibold);
        tv_place_order.setTypeface(SplashActivity.montserrat_semibold);

        tv_total.setTypeface(SplashActivity.montserrat_medium);
        tv_discount.setTypeface(SplashActivity.montserrat_medium);
        tv_tax.setTypeface(SplashActivity.montserrat_medium);
        tv_subtotal.setTypeface(SplashActivity.montserrat_medium);

        edt_coupon_code.setTypeface(SplashActivity.montserrat_medium);
        tv_apply.setTypeface(SplashActivity.montserrat_medium);

        checkbox_save_card.setTypeface(SplashActivity.montserrat_regular);
        layout_card_expirydate.setTypeface(SplashActivity.montserrat_regular);
        layout_card_number.setTypeface(SplashActivity.montserrat_regular);
        layout_card_name.setTypeface(SplashActivity.montserrat_regular);
        layout_card_cvv.setTypeface(SplashActivity.montserrat_regular);

        et_card_expirydate.setTypeface(SplashActivity.montserrat_regular);
        et_card_cvv.setTypeface(SplashActivity.montserrat_regular);
        et_card_name.setTypeface(SplashActivity.montserrat_regular);
        et_card_no.setTypeface(SplashActivity.montserrat_regular);
    }


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
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


    @Override
    public void onClick(View v) {
        if (v==lv_placeorder){

            CallCreateOrderApi();

       }else if(v==tv_apply)
        {

            if(tv_apply.getText().equals(getActivity().getResources().getString(R.string.apply)))
            {
                String code=edt_coupon_code.getText().toString();
                if(code.equalsIgnoreCase("") || code.length()==0)
                {

                }else {
                    if (CheckNetwork.isNetworkAvailable(getActivity())) {
                        callCopounCodeApi(code);
                    } else {
                        //    noInternetDialog(NavigationActivity.this);
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    }
                }
            }else if(tv_apply.getText().equals(getActivity().getResources().getString(R.string.remove)))
            {
                if (CheckNetwork.isNetworkAvailable(getActivity())) {
                    callremoveCodeApi();
                } else {
                    //    noInternetDialog(NavigationActivity.this);
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

    private void CallCreateOrderApi() {
        lv_payment_progress.setVisibility(View.VISIBLE);
        cordinator_checkout.setVisibility(View.GONE);

        createordrapi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                lv_payment_progress.setVisibility(View.GONE);
                cordinator_checkout.setVisibility(View.VISIBLE);
                Log.e("res634", "===" + response);
                Log.e("ressss634", "=" + response.body());
                Log.e("6sss34", "==" + response.body().toString());

                if (response.isSuccessful() || response.code() == 200) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        Log.e("jsonObject_640", "" + jsonObject);

                        JSONArray jsonArray=jsonObject.getJSONArray("items");
                        Log.e("jsonArray", "" + jsonArray);

                        JSONObject billing_address=jsonObject.getJSONObject("billing_address");
                        Log.e("billing_address", "" + billing_address);
                        Toast.makeText(getActivity(), "Order Placed successfully", Toast.LENGTH_SHORT).show();
                        pushFragment(new OrderSuccessfulFragment(),"ordersuces");

                      /*  for (int i = 0; i < itemArray.length(); i++) {
                            try {
                                JSONObject vals = itemArray.getJSONObject(i);

                                String carrier_code = vals.getString("carrier_code");
                                Log.e("delivery_carrier_code", "" + carrier_code);

                                deliveryModels.add(new DeliveryModel(vals.getString("carrier_code"), vals.getString("method_code"), vals.getString("carrier_title"),
                                        vals.getString("method_title"), vals.getString("amount"),vals.getString("base_amount"),vals.getString("available"),
                                        vals.getString("error_message"),vals.getString("price_excl_tax"), vals.getString("price_incl_tax")));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                deliveryAdapter.notifyItemChanged(i);
                            }
                        }*/


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lv_payment_progress.setVisibility(View.GONE);
                cordinator_checkout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "" + t.getMessage());
            }
        });
    }

    private Call<ResponseBody> createordrapi() {

        Log.e("debug_authtoken", "=" + Login_preference.gettoken(getActivity()));
        Log.e("cuome3333", "=" + Login_preference.getCustomertoken(getActivity()));
        String url;
         if(isBillingAddress==true)
         {
              url = ApiClient.MAIN_URLL + "orders/create?" + halfUrl+"&"+cartItemsUrl+shippingAddressUrl+billingaddressurl+
             "&"+cart_sku + "&entity[customer_is_guest]=0"+"&entity[email_sent]=1"+"&entity[is_virtual]=0"+"&entity[order_currency_code]="
                     +base_currency_code+"&entity[shipping_description]="+DeliveryFragment.shiping_method_order+"&entity[status]=pending"+"&entity[store_currency_code]="+base_currency_code+
                     "&entity[total_item_count]="+total_qty_ordered+"&entity[store_id]="+Login_preference.getstoreid(getActivity())+"&entity[store_name]="+
                     Login_preference.getstoreName(getActivity())+"&"+cart_product_type;;
         }else {
              url = ApiClient.MAIN_URLL + "orders/create?" + halfUrl+"&"+cartItemsUrl+billingaddressurl+ "&"+cart_sku
                      + "&entity[customer_is_guest]=0"+"&entity[email_sent]=1"+"&entity[is_virtual]=0"+"&entity[order_currency_code]="
                      +base_currency_code+"&entity[shipping_description]="+DeliveryFragment.shiping_method_order+"&entity[status]=pending"+"&entity[store_currency_code]="
                      +base_currency_code+ "&entity[total_item_count]="+total_qty_ordered+"&entity[store_id]="+Login_preference.getstoreid(getActivity())+"&entity[store_name]="+
                      Login_preference.getstoreName(getActivity())+"&"+cart_product_type;
         }
        Log.e("debug_155", "=" + url);
        return apiInterface.createorder("Bearer " + Login_preference.gettoken(getActivity()), url);
    }

    private void AttachRecyclerview() {
        paymentAdapter = new PaymentAdapter(getActivity(), paymentModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        payment_recyclerview.setLayoutManager(layoutManager);
        payment_recyclerview.setAdapter(paymentAdapter);
    }

    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.framlayout, fragment);
                ft.commit();
            }
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void callremoveCodeApi() {
        hideKeyboard(getActivity());
        lv_payment_progress.setVisibility(View.VISIBLE);
        cordinator_checkout.setVisibility(View.GONE);
        ApiInterface customeapi = ApiClient.getClient().create(ApiInterface.class);
        String url=ApiClient.MAIN_URLL+"carts/"+Login_preference.getquote_id(getActivity())+"/coupons";
        Log.e("debug_url","="+url);
        Log.e("token","="+ Login_preference.gettoken(getActivity()));

        Call<Boolean> homevideos = customeapi.deletecopouncode("Bearer "+Login_preference.gettoken(getActivity()),url);
        homevideos.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.e("response200",""+response.toString());
                Log.e("response201",""+response.body());

                if(response.code()==200)
                {  lv_payment_progress.setVisibility(View.GONE);
                    cordinator_checkout.setVisibility(View.VISIBLE);

                    if(response.body()==true)
                    {

                        Toast.makeText(getActivity(), "Copoun Code Removed  Successfully", Toast.LENGTH_SHORT).show();
                        edt_coupon_code.setText("");
                        edt_coupon_code.setEnabled(true);
                        edt_coupon_code.setClickable(true);
                        tv_apply.setText(getActivity().getResources().getString(R.string.apply));
                        tv_apply.setTextColor(getActivity().getResources().getColor(R.color.black));
                    }

                }else {
                    lv_payment_progress.setVisibility(View.GONE);
                    cordinator_checkout.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                lv_payment_progress.setVisibility(View.GONE);
                cordinator_checkout.setVisibility(View.VISIBLE);

            }
        });
    }

    private void callCopounCodeApi(String code) {
        hideKeyboard(getActivity());
        lv_payment_progress.setVisibility(View.VISIBLE);
        cordinator_checkout.setVisibility(View.GONE);
        addcoponcode(code).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                lv_payment_progress.setVisibility(View.GONE);
                cordinator_checkout.setVisibility(View.VISIBLE);

                Log.e("debug1111",""+ response.body());
                Log.e("debug1111dsgdf",""+ response);

                if(response.code()==200 || response.isSuccessful())
                { lv_payment_progress.setVisibility(View.GONE);
                    cordinator_checkout.setVisibility(View.VISIBLE);

                    if(response.body()==true)
                    {

                        Toast.makeText(getActivity(), "Copoun Code Added  Successfully", Toast.LENGTH_SHORT).show();
                        tv_apply.setText(getActivity().getResources().getString(R.string.remove));
                        tv_apply.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                        edt_coupon_code.setEnabled(false);
                        edt_coupon_code.setClickable(false);
                    }
                }else {
                    Toast.makeText(getActivity(), "The coupon code isn't valid. Verify the code and try again.", Toast.LENGTH_SHORT).show();
                    lv_payment_progress.setVisibility(View.GONE);
                    cordinator_checkout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                lv_payment_progress.setVisibility(View.GONE);
                cordinator_checkout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), ""+getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<Boolean> addcoponcode(String code) {
        ApiInterface customeapi = ApiClient.getClient().create(ApiInterface.class);

        //http://dkbraende.demoproject.info/rest/V1/carts/192029/coupons/test
        String url=ApiClient.MAIN_URLL+"carts/"+Login_preference.getquote_id(getActivity())+"/coupons/"+code;
        Log.e("debug_url","="+url);
        Log.e("token","="+ Login_preference.gettoken(getActivity()));

        return customeapi.addCoponCode("Bearer "+Login_preference.gettoken(getActivity()),url);
    }
}
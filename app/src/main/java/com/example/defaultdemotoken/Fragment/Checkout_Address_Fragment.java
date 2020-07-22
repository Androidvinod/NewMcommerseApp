package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.AddressBookAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Fragment.DeliveryFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.AddressModel.Address;
import com.example.defaultdemotoken.Model.AddressModel.AddressModell;
import com.example.defaultdemotoken.Fragment.PaymentsFragment;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import java.util.List;

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

public class Checkout_Address_Fragment extends Fragment implements View.OnClickListener {

    View v;
    LinearLayout lv_address_back, lv_address_next, lvnodata_addres, lv_progress_address;
    RecyclerView recyclerview_address;
    NavigationActivity parent;
    ApiInterface apiinterface;
    AddressBookAdapter addressBookAdapter;
    String delivery_address,cart_sku,cart_product_type,passaddress="";
    public static String addressidd;
    CheckBox checkbox_biling;
    Boolean isBillingAddress=false;
    Bundle b;
    LinearLayout lv_add_new_address;
    TextView tv_add_new_address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_checkout__address, container, false);
        parent = (NavigationActivity) getActivity();
        AllocateMemory();
        apiinterface = ApiClient.getClient().create(ApiInterface.class);

        b=this.getArguments();

        if(b!=null)
        {
            delivery_address=b.getString("delivery_address");
            cart_sku=b.getString("cart_sku");
            cart_product_type=b.getString("cart_product_type");

            Log.e("debug_706","="+delivery_address);
        }

        checkbox_biling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    isBillingAddress=checked;
                    Log.e("debug_89","="+checked);
                    Log.e("debug_89","="+isBillingAddress);

                    // Do your coding
                }
                else{
                    isBillingAddress=checked;

                    Log.e("debug_00_unchecked","="+checked);
                    Log.e("debug_00_unchecked","="+isBillingAddress);
                    // Do your coding
                }
            }
        });


        rad_delivery.setChecked(false);
        rad_address.setChecked(true);
        rad_payments.setChecked(false);

        view_del_grey.setVisibility(View.GONE);
        view_del_green.setVisibility(View.VISIBLE);

        view_address_last_grey.setVisibility(View.VISIBLE);
        view_address_last_green.setVisibility(View.GONE);

        lv_address_back.setOnClickListener(this);
        lv_address_next.setOnClickListener(this);
        lv_add_new_address.setOnClickListener(this);

        AttachRecyclerview();

        if (CheckNetwork.isNetworkAvailable(parent)) {
            CallAddressApi();
        } else {
            Toast.makeText(parent, parent.getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private void AttachRecyclerview() {
        addressBookAdapter = new AddressBookAdapter(getActivity());
        recyclerview_address.setLayoutManager(new LinearLayoutManager(parent, LinearLayoutManager.VERTICAL, false));
        recyclerview_address.setItemAnimator(new DefaultItemAnimator());
        recyclerview_address.setAdapter(addressBookAdapter);

    }

    private void AllocateMemory() {
        lv_address_back = v.findViewById(R.id.lv_address_back);
        lv_address_next = v.findViewById(R.id.lv_address_next);
        recyclerview_address = v.findViewById(R.id.recyclerview_address);
        lvnodata_addres = v.findViewById(R.id.lvnodata_addres);
        lv_progress_address = v.findViewById(R.id.lv_progress_address);
        checkbox_biling = v.findViewById(R.id.checkbox_biling);
        lv_add_new_address = v.findViewById(R.id.lv_add_new_address);
        tv_add_new_address = v.findViewById(R.id.tv_add_new_address);
        tv_add_new_address.setTypeface(SplashActivity.montserrat_semibold);
    }


    private void CallAddressApi() {
        lv_progress_address.setVisibility(View.VISIBLE);
        recyclerview_address.setVisibility(View.GONE);
        calladdressgapi().enqueue(new Callback<AddressModell>() {
            @Override
            public void onResponse(Call<AddressModell> call, Response<AddressModell> response) {
                Log.e("response", "" + response.body());
                Log.e("response_77", "" + response);
                Log.e("status", "" + response.body());

                if (response.code() == 200) {

                    lv_progress_address.setVisibility(View.GONE);
                    recyclerview_address.setVisibility(View.VISIBLE);
                    Log.e("response_77", "" + response);
                    Log.e("status", "" + response.body());

                    AddressModell addressModell = response.body();
                    List<Address> additionalAddresses = response.body().getAddresses();
                    if (additionalAddresses.isEmpty() || additionalAddresses==null) {
                        lv_add_new_address.setVisibility(View.VISIBLE);
                        recyclerview_address.setVisibility(View.GONE);
                        lv_progress_address.setVisibility(View.GONE);
                        checkbox_biling.setVisibility(View.GONE);
                        // tv_no_addressfafound.setVisibility(View.VISIBLE);
                        passaddress=null;
                    } else {

                        Log.e("status127", "=" + response.body().getAddresses());
                        Log.e("status128", "=" + response.body().toString());

                        // tv_no_addressfafound.setVisibility(View.GONE);
                        List<Address> feesInnerData = fetchResultsaa(response);

                        if (feesInnerData.size() == 0) {
                            lv_add_new_address.setVisibility(View.VISIBLE);
                            recyclerview_address.setVisibility(View.GONE);
                            checkbox_biling.setVisibility(View.GONE);
                            passaddress=null;
                        } else {
                            passaddress="text";
                            lv_add_new_address.setVisibility(View.GONE);
                            recyclerview_address.setVisibility(View.VISIBLE);
                            checkbox_biling.setVisibility(View.VISIBLE);
                        }
                        Log.e("feesInnerData", "=" + feesInnerData);
                        addressBookAdapter.addAll(feesInnerData);
                    }
                } else {
                    Log.e("response_77", "" + response);
                    Log.e("status", "" + response.body());

                    lv_progress_address.setVisibility(View.VISIBLE);
                    recyclerview_address.setVisibility(View.GONE);
                }

               }

            @Override
            public void onFailure(Call<AddressModell> call, Throwable t) {
                Toast.makeText(parent, "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Address> fetchResultsaa(Response<AddressModell> response) {
        AddressModell getAddressModel = response.body();
        return getAddressModel.getAddresses();
    }

    private Call<AddressModell> calladdressgapi() {

        Log.e("debug_111", "=" + Login_preference.gettoken(parent));
        Log.e("custormid", "=" + Login_preference.getcustomer_id(getActivity()));
        String url = ApiClient.MAIN_URLL + "customers/" + Login_preference.getcustomer_id(getActivity());
        return apiinterface.address("Bearer " + Login_preference.gettoken(getActivity()), url);
    }


    @Override
    public void onClick(View v) {
        if (v == lv_address_back) {

            Bundle b = new Bundle();
            b.putString("delivery_address", delivery_address);
            DeliveryFragment myFragment = new DeliveryFragment();
            myFragment.setArguments(b);
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out)
                    .setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out)
                    .replace(R.id.frameLayout_checkout, myFragment)
                    .addToBackStack("delivery_address").commit();

        }
       else if (v == lv_address_next) {

            if(passaddress==null || passaddress.equalsIgnoreCase("") || passaddress=="")
            {
                Toast.makeText(getActivity(), "Please Add Billing Address", Toast.LENGTH_SHORT).show();
            }else {
                Bundle b = new Bundle();
                b.putString("delivery_address", delivery_address);
                b.putString("addressidd", addressidd);
                b.putBoolean("isBillingAddress", isBillingAddress);
                b.putString("cart_sku", "" + cart_sku);
                b.putString("cart_product_type", "" + cart_product_type);


                PaymentsFragment myFragment = new PaymentsFragment();
                myFragment.setArguments(b);
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out)
                        .setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out)
                        .replace(R.id.frameLayout_checkout, myFragment)
                        .addToBackStack("checkout_address").commit();

            }
       }else if(v==lv_add_new_address)
        {

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Bundle b = new Bundle();
               b.putString("screen", "edit address");
            b.putString("address", "create");

            EditAddressFragment myFragment = new EditAddressFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();
        }

    }

    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.frameLayout_checkout, fragment);
                ft.addToBackStack(add_to_backstack);

                ft.commit();
            }
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
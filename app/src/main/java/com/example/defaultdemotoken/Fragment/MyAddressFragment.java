package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.NewAddressAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.AddressModel.Address;
import com.example.defaultdemotoken.Model.AddressModel.AddressModell;
import com.example.defaultdemotoken.Model.Country;
import com.example.defaultdemotoken.Model.CountryModel.CountryListModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.google.android.gms.common.api.Api;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.api;
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAddressFragment extends Fragment implements View.OnClickListener {

    View v;
    NewAddressAdapter newAddressAdapter;
    Toolbar toolbar_account_info;
    LinearLayout lv_edit_user_info,lv_add_addrss,  lv_progress_myadd,   lv_addresssss,  lv_main_fullname;

    TextView  tv_nodata, tv_acc_info, tv_email_main, tv_titleinfo, tv_full, tv_fullname, tv_emailll, tv_email, tv_phone,  tv_myadressess;
    ScrollView scroll_myadd;
    String add_id;
    RecyclerView recv_addresss;
    ApiInterface apiInterface;
    public static TextView tv_number;


    public static List<String> countryidlist=new ArrayList<>();
    public static List<String> countryLablelist=new ArrayList<>();
    public MyAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my_address, container, false);
        AllocateMemory();
        AttachRecyclrview();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        setHasOptionsMenu(true);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_account_info);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        lv_edit_user_info.setOnClickListener(this);
        lv_add_addrss.setOnClickListener(this);



        if (Login_preference.getfirstname(getActivity()) == null || Login_preference.getfirstname(getActivity()).equalsIgnoreCase("null")) {
//            tv_username.setText("");
            tv_fullname.setText("");
            tv_full.setVisibility(View.GONE);
            tv_fullname.setVisibility(View.GONE);
            lv_main_fullname.setVisibility(View.GONE);
        } else {

            tv_fullname.setText(Login_preference.getfirstname(getActivity()) + " " + Login_preference.getlastname(getActivity()));

        }
        tv_email_main.setText(Login_preference.getemail(getActivity()));
        tv_email.setText(Login_preference.getemail(getActivity()));
        tv_number.setText(Login_preference.getphone(getActivity()));

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            //CallGetWishlistApi(page_no);

            CallCountrylistApi("main");
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }

        return v;

    }
    public Call<List<CountryListModel>> callcountrylistapi() {
        return apiInterface.getCountryList("Bearer "+Login_preference.gettoken(getActivity()));
    }
    private void AttachRecyclrview() {

        newAddressAdapter = new NewAddressAdapter(getActivity());
        recv_addresss.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_addresss.setItemAnimator(new DefaultItemAnimator());
        recv_addresss.setAdapter(newAddressAdapter);
    }
    private void CallCountrylistApi(final String main) {

        countryidlist.clear();
        countryLablelist.clear();

        lv_progress_myadd.setVisibility(View.VISIBLE);
        scroll_myadd.setVisibility(View.GONE);
        callcountrylistapi().enqueue(new Callback<List<CountryListModel>>() {
            @Override
            public void onResponse(Call<List<CountryListModel>> call, Response<List<CountryListModel>> response) {
                List<CountryListModel> countryList = response.body();
                Log.e("debug_res", "" + response.body());
                Log.e("debug_11954", "=" + response);

                if (response.code()==200 || response.isSuccessful()) {
                    lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);


                        for(int i=0;i<countryList.size();i++)
                        {
                            countryidlist.add(countryList.get(i).getId());
                            countryLablelist.add(countryList.get(i).getFullNameEnglish());
                        }

                    CallAAddressApi();
                    Log.e("countryLablelist", "=: " + countryLablelist.size());



                } else {
                    lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onFailure(Call<List<CountryListModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "pages: " + t.getLocalizedMessage());
                lv_progress_myadd.setVisibility(View.GONE);
                scroll_myadd.setVisibility(View.VISIBLE);


            }
        });

    }

    private void CallAAddressApi() {
        lv_progress_myadd.setVisibility(View.VISIBLE);
        scroll_myadd.setVisibility(View.GONE);
        calladdressgapi().enqueue(new Callback<AddressModell>() {
            @Override
            public void onResponse(Call<AddressModell> call, Response<AddressModell> response) {
                Log.e("response",""+response.body());
                Log.e("response_77",""+response);
                Log.e("status",""+response.body());

                if (response.code()==200)
                {

                    lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);
                    Log.e("response_77",""+response);
                    Log.e("status",""+response.body());

                    AddressModell addressModell=response.body();

                    Login_preference.setstoreid(getActivity(), String.valueOf(response.body().getStoreId()));
                    List<Address> additionalAddresses = response.body().getAddresses();
                    if (additionalAddresses.isEmpty()) {
                        tv_nodata.setVisibility(View.VISIBLE);
                        recv_addresss.setVisibility(View.GONE);
                    }else {

                        Log.e("status127","="+response.body().getAddresses());
                        Log.e("status128","="+response.body().toString());

                        tv_nodata.setVisibility(View.GONE);
                        List<Address> feesInnerData =fetchResultsaa(response);

                        if(feesInnerData.size()==0)
                        {
                            tv_nodata.setVisibility(View.VISIBLE);
                            recv_addresss.setVisibility(View.GONE);
                        }else {

                            tv_nodata.setVisibility(View.GONE);
                            recv_addresss.setVisibility(View.VISIBLE);

                        }
                        Log.e("feesInnerData","="+feesInnerData);
                        newAddressAdapter.addAll(feesInnerData);


                    }
                }else {
                    Log.e("response_77",""+response);
                    Log.e("status",""+response.body());

                    lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);

                }

            }
            @Override
            public void onFailure(Call<AddressModell> call, Throwable t) {
                lv_progress_myadd.setVisibility(View.GONE);
                scroll_myadd.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private List<Address> fetchResultsaa(Response<AddressModell> response) {
        AddressModell getAddressModel = response.body();
        return getAddressModel.getAddresses();
    }




    private Call<AddressModell> calladdressgapi() {
        Log.e("debug_111",""+Login_preference.getcustomer_id(getActivity()));
        String url= ApiClient.MAIN_URLL+ "customers/"+Login_preference.getcustomer_id(getActivity());
        Log.e("debug_111url",""+url);
        Log.e("dtoken",""+Login_preference.gettoken(getActivity()));
        return apiInterface.address("Bearer "+Login_preference.gettoken(getActivity()),url);

    }





    private void AllocateMemory() {

        tv_acc_info = v.findViewById(R.id.tv_acc_info);
        recv_addresss = v.findViewById(R.id.recv_addresss);
        lv_main_fullname = v.findViewById(R.id.lv_main_fullname);
        lv_addresssss = v.findViewById(R.id.lv_addresssss);

        tv_nodata = v.findViewById(R.id.tv_nodata);
        scroll_myadd = v.findViewById(R.id.scroll_myadd);
        toolbar_account_info = v.findViewById(R.id.toolbar_account_info);
        lv_progress_myadd = v.findViewById(R.id.lv_progress_myadd);
        lv_edit_user_info = v.findViewById(R.id.lv_edit_user_info);

        //tv_username=v.findViewById(R.id.tv_username);
        tv_email_main = v.findViewById(R.id.tv_email_main);
        tv_titleinfo = v.findViewById(R.id.tv_titleinfo);
        tv_full = v.findViewById(R.id.tv_full);
        tv_fullname = v.findViewById(R.id.tv_fullname);
        tv_emailll = v.findViewById(R.id.tv_emailll);
        tv_email = v.findViewById(R.id.tv_email);
        tv_phone = v.findViewById(R.id.tv_phone);
        tv_number = v.findViewById(R.id.tv_number);
        tv_myadressess = v.findViewById(R.id.tv_myadressess);

        lv_add_addrss = v.findViewById(R.id.lv_add_addrss);


//        tv_username.setTypeface(SplashActivity.montserrat_regular);
        tv_email_main.setTypeface(SplashActivity.montserrat_regular);
        tv_titleinfo.setTypeface(SplashActivity.montserrat_semibold);
        tv_myadressess.setTypeface(SplashActivity.montserrat_semibold);
        tv_acc_info.setTypeface(SplashActivity.montserrat_semibold);
        tv_number.setTypeface(SplashActivity.montserrat_regular);
        tv_phone.setTypeface(SplashActivity.montserrat_regular);
        tv_email.setTypeface(SplashActivity.montserrat_regular);
        tv_emailll.setTypeface(SplashActivity.montserrat_regular);
        tv_fullname.setTypeface(SplashActivity.montserrat_regular);
        tv_full.setTypeface(SplashActivity.montserrat_regular);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        //    inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onClick(View v) {

        if (v == lv_edit_user_info) {

            Bundle b = new Bundle();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            b.putString("screen", "edit detail");

            EditAddressFragment myFragment = new EditAddressFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();

        }/* else if (v == lv_edit_address) {

            Bundle b = new Bundle();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            b.putString("screen", "edit address");
            b.putString("address", "edit");
            b.putString("address_id", add_id);

            EditAddressFragment myFragment = new EditAddressFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();

        } */else if (v == lv_add_addrss) {

            Bundle b = new Bundle();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            b.putString("screen", "edit address");
            b.putString("address", "create");

            EditAddressFragment myFragment = new EditAddressFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();

        }/* else if (v == lv_delete_add) {

            Log.e("debuaddid55", "=" + add_id);
            if (CheckNetwork.isNetworkAvailable(getActivity())) {
                DeleteAddressApi(add_id);
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }

        }*/

    }



}

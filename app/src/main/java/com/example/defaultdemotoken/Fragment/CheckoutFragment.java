package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.R;
import com.facebook.login.Login;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

public class CheckoutFragment extends Fragment {
    String cartid,cart_sku,cart_product_type;
    Bundle b;
    View v;
    Toolbar toolbar_checkout;
    String loginflag;
    public static RadioButton rad_delivery, rad_address,rad_payments;
    public static View view_del_grey,view_del_green,view_address_last_grey,view_address_last_green;

    TextView tv_checkout_delevery,tv_checkout_address,tv_checkout_payments,tv_checkout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_checkout, container, false);

        AllocateMemory();
        b=this.getArguments();
        if(b!=null)
        {
           // cartid=b.getString("cartid");
            cart_sku=b.getString("cart_sku");
            cart_product_type=b.getString("cart_product_type");
            Log.e("debug11cartid","="+cartid);
        }
        setHasOptionsMenu(true);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_checkout);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        loginflag = Login_preference.getLogin_flag(getActivity());

        /*rad_delivery.setChecked(true);
        rad_address.setChecked(false);

        view_del_grey.setVisibility(View.VISIBLE);
        view_del_green.setVisibility(View.GONE);*/


        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
          //  pushFragment(new DeliveryFragment(), "delivery");

            Bundle b = new Bundle();
            b.putString("cart_sku", "" + cart_sku);
            b.putString("cart_product_type", "" + cart_product_type);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            DeliveryFragment myFragment = new DeliveryFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager()
                    .beginTransaction().setCustomAnimations(R.anim.fade_in,
                    0, 0, R.anim.fade_out).
                    replace(R.id.frameLayout_checkout, myFragment).commit();

        } else {
            pushFragment(new LoginFragment(), "login");
        }

        return v;
    }

    private void AllocateMemory() {
        toolbar_checkout = v.findViewById(R.id.toolbar_checkout);
        rad_delivery = v.findViewById(R.id.rad_delivery);
        rad_address = v.findViewById(R.id.rad_address);
        rad_payments = v.findViewById(R.id.rad_payments);

        view_del_grey = v.findViewById(R.id.view_del_grey);
        view_del_green = v.findViewById(R.id.view_del_green);
        view_address_last_grey = v.findViewById(R.id.view_address_last_grey);
        view_address_last_green = v.findViewById(R.id.view_address_last_green);
        tv_checkout_delevery = v.findViewById(R.id.tv_checkout_delevery);
        tv_checkout_address = v.findViewById(R.id.tv_checkout_address);
        tv_checkout = v.findViewById(R.id.tv_checkout);
        tv_checkout_payments = v.findViewById(R.id.tv_checkout_address);

        tv_checkout.setTypeface(SplashActivity.montserrat_semibold);
        tv_checkout_payments.setTypeface(SplashActivity.montserrat_regular);
        tv_checkout_address.setTypeface(SplashActivity.montserrat_regular);
        tv_checkout_delevery.setTypeface(SplashActivity.montserrat_regular);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //    inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
}
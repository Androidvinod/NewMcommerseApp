package com.example.defaultdemotoken.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.R;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;

import static com.example.defaultdemotoken.Activity.NavigationActivity.bottom_navigation;
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment implements View.OnClickListener {

    public Toolbar toolbar_account;
    TextView tv_signinnn,tv_create_acc,tv_sihnin,tv_my_settings_title,tv_app_setting,tv_help,
            tv_hotline,tv_no,tv_terms_condition,tv_privacy,tv_changepssword,tv_my_account_title,tv_detail,tv_create_logout,tv_myorder;

    LinearLayout lv_create_account,lv_sign_in,lv_app_ssettings,lv_help,lv_hotline,lv_terms_condition,lv_privacy,lv_change_password,lv_my_detail,lv_with_login,
            lv_without_login,lv_logut,lv_my_order;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_my_account, container, false);
        bottom_navigation.getMenu().getItem(4).setChecked(true);
        AllocateMemory(v);
        setHasOptionsMenu(true);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_account);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

        toolbar_account.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        lv_change_password.setOnClickListener(this);
        lv_my_detail.setOnClickListener(this);
        lv_sign_in.setOnClickListener(this);
        lv_create_account.setOnClickListener(this);
        lv_logut.setOnClickListener(this);
        lv_my_order.setOnClickListener(this);
        lv_help.setOnClickListener(this);


        if(Login_preference.getLogin_flag(getActivity()).equalsIgnoreCase("1"))
        {
            lv_logut.setVisibility(View.VISIBLE);
            lv_with_login.setVisibility(View.VISIBLE);
            lv_without_login.setVisibility(View.GONE);
        }else {
            lv_logut.setVisibility(View.GONE);
            lv_with_login.setVisibility(View.GONE);
            lv_without_login.setVisibility(View.VISIBLE);
        }

        return v;
    }

    private void AllocateMemory(View v) {
        tv_myorder = v.findViewById(R.id.tv_myorder);
        tv_create_logout = v.findViewById(R.id.tv_create_logout);
        lv_logut = v.findViewById(R.id.lv_logut);
        lv_without_login = v.findViewById(R.id.lv_without_login);
        lv_with_login = v.findViewById(R.id.lv_with_login);
        tv_detail = v.findViewById(R.id.tv_detail);
        lv_my_detail = v.findViewById(R.id.lv_my_detail);
        tv_my_account_title = v.findViewById(R.id.tv_my_account_title);
        tv_changepssword = v.findViewById(R.id.tv_changepssword);
        tv_privacy = v.findViewById(R.id.tv_privacy);
        lv_privacy = v.findViewById(R.id.lv_privacy);
        tv_terms_condition = v.findViewById(R.id.tv_terms_condition);
        lv_terms_condition = v.findViewById(R.id.lv_terms_condition);
        tv_no = v.findViewById(R.id.tv_no);
        tv_hotline = v.findViewById(R.id.tv_hotline);
        lv_hotline = v.findViewById(R.id.lv_hotline);
        tv_help = v.findViewById(R.id.tv_help);
        lv_help = v.findViewById(R.id.lv_help);
        tv_app_setting = v.findViewById(R.id.tv_app_setting);
        lv_app_ssettings = v.findViewById(R.id.lv_app_ssettings);
        lv_sign_in = v.findViewById(R.id.lv_sign_in);
        toolbar_account = v.findViewById(R.id.toolbar_account);
        tv_signinnn = v.findViewById(R.id.tv_signinnn);
        lv_create_account = v.findViewById(R.id.lv_create_account);
        tv_create_acc = v.findViewById(R.id.tv_create_acc);
        tv_sihnin = v.findViewById(R.id.tv_sihnin);
        tv_my_settings_title = v.findViewById(R.id.tv_my_settings_title);
        lv_change_password = v.findViewById(R.id.lv_change_password);
        lv_my_order = v.findViewById(R.id.lv_my_order);



        tv_app_setting.setTypeface(SplashActivity.montserrat_medium);
        tv_my_account_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_my_settings_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_sihnin.setTypeface(SplashActivity.montserrat_medium);
        tv_create_acc.setTypeface(SplashActivity.montserrat_medium);
        tv_signinnn.setTypeface(SplashActivity.montserrat_medium);
        tv_create_logout.setTypeface(SplashActivity.montserrat_medium);
        tv_help.setTypeface(SplashActivity.montserrat_medium);
        tv_hotline.setTypeface(SplashActivity.montserrat_medium);
        tv_no.setTypeface(SplashActivity.montserrat_medium);
        tv_terms_condition.setTypeface(SplashActivity.montserrat_medium);
        tv_privacy.setTypeface(SplashActivity.montserrat_medium);
        tv_changepssword.setTypeface(SplashActivity.montserrat_medium);
        tv_detail.setTypeface(SplashActivity.montserrat_medium);
        tv_myorder.setTypeface(SplashActivity.montserrat_medium);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    //    inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        if(v==lv_change_password)
        {
            pushFragment(new ChangePasswordFragment(),"password");
        }else if(v== lv_my_detail)
        {
            pushFragment(new MyAddressFragment(),"detail");
        }else if(v==lv_sign_in)
        {
            pushFragment(new LoginFragment(),"new Login");
        }else if(v==lv_create_account)
        {
            pushFragment(new RegisterFragment(),"register");
        }else if(v==lv_logut)
        {
            if (Login_preference.getLogin_flag(getActivity()).equalsIgnoreCase("1")) {
                new iOSDialogBuilder(getActivity())
                        .setTitle(getString(R.string.app_name))
                        .setSubtitle(getString(R.string.logoutmessge))
                        .setBoldPositiveLabel(true)
                        .setCancelable(false)
                        .setPositiveListener(getString(R.string.yes), new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                                startActivity(intent);
                               /* if(getActivity()!=null)
                                {
                                    Login_preference.setLogin_flag(getActivity(), "0");
                                }*/
                                Login_preference.setLogin_flag(getActivity(), "0");
                                Login_preference.prefsEditor.remove("grouppid").apply();
                                Login_preference.prefsEditor.remove("storeid").apply();
                                Login_preference.prefsEditor.remove("storeName").apply();
                                Login_preference.prefsEditor.remove("customeremail").apply();
                                Login_preference.prefsEditor.remove("customerpassword").apply();

                                Login_preference.prefsEditor.remove("customertoken").apply();
                                Login_preference.prefsEditor.remove("quote_id").apply();
                                Login_preference.prefsEditor.remove("item_count").apply();
                                Login_preference.prefsEditor.remove("customer_id").apply();
                                Login_preference.prefsEditor.remove("email").apply();
                                Login_preference.prefsEditor.remove("fullname").apply();
                                ///     Login_preference.prefsEditor.remove("items_qty").apply();
                                //Login_preference.prefsEditor.remove("login_flag").apply();
                                Login_preference.prefsEditor.remove("wishlist_count").apply();

                                dialog.dismiss();
                                getActivity().finish();
                                Toast.makeText(getContext(), "" + getActivity().getResources().getString(R.string.logout_message), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeListener(getString(R.string.no), new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                dialog.dismiss();
                            }
                        })

                        .build().show();
            } else {
                LoginFragment myFragment = new LoginFragment();
                String screen = "Account";
                Bundle b = new Bundle();
                b.putString("screen", "" + screen);
                myFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out)
                        .addToBackStack("Account")
                        .add(R.id.framlayout, myFragment)
                        .commit();

            }
        }else if(v==lv_my_order)
        {
            pushFragment(new MyOrdersFragment(),"order");

        }else if (v == lv_help){
         //   pushFragment(new OrderSuccessfulFragment(),"categories_and_care");
        }
    }
}

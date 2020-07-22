package com.example.defaultdemotoken.Activity;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Fragment.CartListFragment;
import com.example.defaultdemotoken.Fragment.HomeFragment;

import com.example.defaultdemotoken.Adapter.ChildDataAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Fragment.LoginFragment;
import com.example.defaultdemotoken.Fragment.MyAccountFragment;
import com.example.defaultdemotoken.Fragment.SearchFragment;
import com.example.defaultdemotoken.Fragment.WishlistFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.CategoriesModel;
import com.example.defaultdemotoken.Model.ChildData;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private AppBarConfiguration mAppBarConfiguration;
    RecyclerView recv_category;
    //public static Toolbar toolbar;
    ImageView iv_navtoolbar_logo;
    public static DrawerLayout drawer;
    NavigationView navigationView;
    RelativeLayout relative_layout;
    public static BottomNavigationView bottom_navigation;
    boolean doubleBackToExitPressedOnce = false;
    public static ApiInterface api;
    ImageView nav_iv_close;
    ChildDataAdapter categoryAdapter;
    public static TextView tv_bottomcount, tv_wishlist_count;
    LinearLayout lv_nodata_category, lv_home;
    String cartitem_count, wishlist_count;
    public static NavigationActivity parent;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NavigationActivity.this.setTheme(R.style.green);
        setContentView(R.layout.activity_navigation);
        parent = (NavigationActivity) NavigationActivity.this;

        //setSupportActionBar(toolbar);
        //  changeToolbarFont(toolbar, Navigation_drawer_activity.this);

        AllocateMemory();

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.signs);

        View header = navigationView.getHeaderView(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        api = ApiClient.getClient().create(ApiInterface.class);
        AttachRecyclerView();
        bottom_navigation.setItemIconTintList(null);
        Bootom_Navigation_view();

        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.white));
       // text.setTextColor(Color.parseColor("#FFFFFF"));
        // bottom_navigation.setItemTextAppearanceActive(NavigationActivity.this,R .style.BottomNavigationVieww);
       //  bottom_navigation.setItemTextColor();
        // bottom_navigation.setItemTextColor(getResources().getDrawable(R.drawable.bottom_selection));
        // setSupportActionBar(toolbar);
        // toolbar.setTitle("");

        // calling api of categiry list for side menu
        if (CheckNetwork.isNetworkAvailable(NavigationActivity.this)) {
            getCategoryList();
            // get_Customer_tokenapi();

            if (Login_preference.getLogin_flag(NavigationActivity.this).equalsIgnoreCase("1")) {
                get_Customer_QuoteId();
                callWishlistCountApi();
            }
        } else {
            // noInternetDialog(NavigationActivity.this);
            Toast.makeText(NavigationActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }


    }

    public DrawerLayout getmDrawerLayout() {
        return drawer;
    }

    public static void get_Customer_QuoteId() {
        Log.e("customertoken", "" + Login_preference.getCustomertoken(parent));
        Call<Integer> customertoken = api.getQuoteid("Bearer " + Login_preference.getCustomertoken(parent),
                ApiClient.MAIN_URLL+"carts/mine/?customerId=" + Login_preference.getcustomer_id(parent));
        customertoken.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.e("res_quoteid", "" + response.toString());
                Log.e("resquoteiddd", "" + response.body());
                Login_preference.setquote_id(parent, String.valueOf(response.body()));

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(parent, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    private static void callWishlistCountApi() {
        Log.e("response201tokenff", "" + Login_preference.gettoken(parent));
        Call<ResponseBody> customertoken = api.defaultWishlistCount("Bearer " + Login_preference.getCustomertoken(parent));
        customertoken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response200gffgdf", "" + response.toString());
                Log.e("response201fgd", "" + response.body());
                if (response.code() == 200 || response.isSuccessful()) {
                    try {
                        JSONArray jsonObject = new JSONArray(response.body().string());

                        String count = jsonObject.getJSONObject(0).getString("total_items");
                        if (count.equalsIgnoreCase("null") || count.equals("") || count.equals("0")) {
                            tv_wishlist_count.setVisibility(View.GONE);
                        } else {
                            tv_wishlist_count.setVisibility(View.VISIBLE);
                            tv_wishlist_count.setText(count);
                            Login_preference.set_wishlist_count(parent, count);
                            Log.e("wishcount", "" + count);
                        }

                        Log.e("wishcount", "" + count);
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
                Toast.makeText(parent, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public static void get_Customer_tokenapi() {
        Log.e("response201tokenff", "" + Login_preference.gettoken(parent));
        String email = Login_preference.gettokenemail(parent);
        String password = Login_preference.gettokenpassword(parent);
        String url = ApiClient.MAIN_URLL + "integration/customer/token?username=" + email + "&password=" + password;
        Call<String> customertoken = api.getcustomerToken(url);
        customertoken.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e("response200", "" + response.toString());
                Log.e("response201", "" + response.body());
                Login_preference.setCustomertoken(parent, response.body());
                get_Customer_QuoteId();
                callWishlistCountApi();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e("failure_messge", "" + t);
                Toast.makeText(parent, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }

    private void AttachRecyclerView() {

        categoryAdapter = new ChildDataAdapter(NavigationActivity.this);
        recv_category.setLayoutManager(new LinearLayoutManager(NavigationActivity.this));
        recv_category.setItemAnimator(new DefaultItemAnimator());
        recv_category.setAdapter(categoryAdapter);

    }

    private void getCategoryList() {

        lv_nodata_category.setVisibility(View.GONE);
        recv_category.setVisibility(View.VISIBLE);

        callCategoryApi().enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                //CategoriesModel CategoriesModel = response.body();

                if (response.isSuccessful() || response.code() == 200) {

                    CategoriesModel model = response.body();
                    List<ChildData> results = fetchResults(response);
                    categoryAdapter.addAll(results);

                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                // String error=  t.printStackTrace();
                Toast.makeText(NavigationActivity.this, "" + getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "pages: " + t);

            }
        });

    }

    private Call<CategoriesModel> callCategoryApi() {
        Log.e("debug11w", "ccvff" + Login_preference.gettoken(NavigationActivity.this));
        return api.categories("Bearer " + Login_preference.gettoken(NavigationActivity.this));

    }

    private List<ChildData> fetchResults(Response<CategoriesModel> response) {

        Log.e("newin_home_209", "" + response.body());
        CategoriesModel CategoriesModel = response.body();
        return CategoriesModel.getChildrenData();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            selectFragment(item);
            return false;

        }
    };

    /*public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShifting(false);

                  ///  item .setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }*/

    private void Bootom_Navigation_view() {

        //code for big one icon size of bottom navigation item00
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottom_navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {

            final View iconView = menuView.getChildAt(2).findViewById(R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
            iconView.setLayoutParams(layoutParams);

        }

        //   BottomNavigationViewHelper.disableShiftMode(bottom_navigation);

        bottom_navigation.setItemIconTintList(null);
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = bottom_navigation.getMenu();
        selectFragment(menu.getItem(2));

        // cartitem_count = Login_preference.getCart_item_count(NavigationActivity.this);
        // BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottom_navigation.getChildAt(0);

        if (Login_preference.getLogin_flag(NavigationActivity.this).equalsIgnoreCase("1")) {
            //wishlist count show
            wishlist_count = Login_preference.get_wishlist_count(NavigationActivity.this);
            BottomNavigationMenuView menuView1 = (BottomNavigationMenuView) bottom_navigation.getChildAt(0);

            BottomNavigationItemView itemView_wishlist = (BottomNavigationItemView) menuView1.getChildAt(1);
            View wishlist_badge = LayoutInflater.from(this).inflate(R.layout.wishlist_count, menuView1, false);
            tv_wishlist_count = (TextView) wishlist_badge.findViewById(R.id.badge_wishlist);
            Log.e("debug_309", "fg" + Login_preference.get_wishlist_count(NavigationActivity.this));

            if (wishlist_count.equalsIgnoreCase("null") || wishlist_count.equals("") || wishlist_count.equals("0")) {
                tv_wishlist_count.setVisibility(View.GONE);
            } else {
                tv_wishlist_count.setVisibility(View.VISIBLE);
                tv_wishlist_count.setText(wishlist_count);
            }

            itemView_wishlist.addView(wishlist_badge);

        }

    }

    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
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

    private void selectFragment(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.bottom_search:
                pushFragment(new SearchFragment(), "search");
                break;

            case R.id.bottom_wishlist:
                if (Login_preference.getLogin_flag(this).equalsIgnoreCase("1")) {
                    pushFragment(new WishlistFragment(), "wishlist");
                } else {
                    //  pushFragment(new LoginFragment(), "login");
                    Bundle b = new Bundle();
                    b.putString("screen", "wishlist");
                    LoginFragment myFragment = new LoginFragment();
                    myFragment.setArguments(b);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack("login").commit();
                }

                break;

            case R.id.bottom_home:
                HomeFragment fragment = new HomeFragment();
                if (fragment == null)
                    return;
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    if (ft != null) {
                        bottom_navigation.setVisibility(View.VISIBLE);
                        ft.replace(R.id.framlayout, fragment);

                        ft.commit();
                    }
                }
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.bottom_cart:

                if (Login_preference.getLogin_flag(this).equalsIgnoreCase("1")) {
                    pushFragment(new CartListFragment(), "cart");
                } else {
                    //  pushFragment(new LoginFragment(), "login");
                    Bundle b = new Bundle();
                    b.putString("screen", "wishlist");
                    LoginFragment myFragment = new LoginFragment();
                    myFragment.setArguments(b);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack("login").commit();

                }
                break;

            case R.id.bottom_account:
                pushFragment(new MyAccountFragment(), "account");
                break;
        }
    }

    public static void Check_String_NULL_Value(TextView textview, String text) {
        if (text == null || text.equalsIgnoreCase("null") == true) {
            //textview.setHint("");
            textview.setText("");
        } else {
            textview.setText(Html.fromHtml(NavigationActivity.Convert_String_First_Letter(text)));
        }
    }

    public static String Convert_String_First_Letter(String convert_string) {
        String upperString;

        if (convert_string.length() > 0) {
            upperString = convert_string.substring(0, 1).toUpperCase() + convert_string.substring(1);
        } else {
            upperString = " ";
        }
        return upperString;
    }

    private void AllocateMemory() {
        bottom_navigation = findViewById(R.id.bottom_navigation);
        // toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        relative_layout = findViewById(R.id.relative_layout);
        recv_category = findViewById(R.id.recv_category);
        nav_iv_close = findViewById(R.id.nav_iv_close);
        lv_nodata_category = findViewById(R.id.lv_nodata_category);
        toolbar = findViewById(R.id.toolbar);
    }


    private void setNavigationIcon_headerview() {


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_open_drawer_description) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                relative_layout.setTranslationX(slideX);
            }
        };
        drawer.addDrawerListener(actionBarDrawerToggle);

        nav_iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        Log.e("count", "" + count);
        if (count == 0) {
            if (doubleBackToExitPressedOnce) {
                Log.e("flage", "" + doubleBackToExitPressedOnce);
                super.onBackPressed();
                super.finish();
                return;
            }
            Log.e("flage1", "" + doubleBackToExitPressedOnce);
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "" + getResources().getString(R.string.back), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            Log.e("count_494", "" + count);
            super.onBackPressed();
        }
    }
}

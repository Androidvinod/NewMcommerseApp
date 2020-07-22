package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Adapter.SubCategoryAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Model.SubCategory;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {
    Toolbar toolbar_subcategory;
    View v;
    RecyclerView recv_subcategory;
    SubCategoryAdapter subCategoryAdapter;
    LinearLayout lv_nodata_subcategory, lv_catalog_parent;
    String categoryid, categoryname;
    TextView tv_subcat_toolbar;

    ApiInterface api;
    Bundle b;
    NavigationActivity parent;
    public static ArrayList<SubCategory> subCategoryArrayList;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_sub_category, container, false);
        setHasOptionsMenu(true);
        AllocateMemory(v);
        setupUI(lv_catalog_parent);
        parent = (NavigationActivity) getActivity();
        api = ApiClient.getClient().create(ApiInterface.class);

        AttachRecyclerView();

        ((NavigationActivity) parent).setSupportActionBar(toolbar_subcategory);
        ((NavigationActivity) parent).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) parent).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);
        toolbar_subcategory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        hideKeyboard(parent);

        b = this.getArguments();
        if (b != null) {
            categoryid = b.getString("categoryid");
            categoryname = b.getString("categoryname");
            subCategoryArrayList = (ArrayList<SubCategory>) b.getSerializable("subCatarraylist");
            Log.e("debug_87", "" + categoryid);
            Log.e("debug_array", "" + subCategoryArrayList.size());
            tv_subcat_toolbar.setText(categoryname);
        } else {
            tv_subcat_toolbar.setText("Sub Category");
        }

        if (CheckNetwork.isNetworkAvailable(parent)) {
            subCategoryAdapter.addAll(subCategoryArrayList);
            //  callSubCategoryApi();
        } else {
            //noInternetDialog();
            Toast.makeText(parent, parent.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

        }
        return v;
    }


    private void AttachRecyclerView() {

        subCategoryAdapter = new SubCategoryAdapter(parent);
        recv_subcategory.setLayoutManager(new LinearLayoutManager(parent));
        recv_subcategory.setAdapter(subCategoryAdapter);

    }

   /* private void callSubCategoryApi() {

        lv_nodata_subcategory.setVisibility(View.GONE);
        recv_subcategory.setVisibility(View.VISIBLE);
        callsubcategoryapi().enqueue(new Callback<SubCategoryModel>() {
            @Override
            public void onResponse(Call<SubCategoryModel> call, Response<SubCategoryModel> response) {
                Log.e("debug_187", "" + fetchResults(response));
                if (fetchResults(response) == null || fetchResults(response).size()==0) {
                    Log.e("debug_175", "pages: " + fetchResults(response));
                    lv_nodata_subcategory.setVisibility(View.VISIBLE);
                    recv_subcategory.setVisibility(View.GONE);
                    tv_messgenoti.setText(parent.getResources().getString(R.string.subcategorynotfound));
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }else {
                    Log.e("debug_995", "pages: " + fetchResults(response));
                    recv_subcategory.setVisibility(View.VISIBLE);
                    List<Subcategory> results = fetchResults(response);
                    List<Subcategory> category_arr = response.body().getSubcategory();
                    if(category_arr.isEmpty())
                    {
                        recv_subcategory.setVisibility(View.GONE);
                        lv_nodata_subcategory.setVisibility(View.VISIBLE);
                        tv_messgenoti.setText(parent.getResources().getString(R.string.subcategorynotfound));
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }else {
                        recv_subcategory.setVisibility(View.VISIBLE);
                        lv_nodata_subcategory.setVisibility(View.GONE);
                    }

                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);

                }



            }

            @Override
            public void onFailure(Call<SubCategoryModel> call, Throwable t) {
                // String error=  t.printStackTrace();
                Toast.makeText(parent, ""+parent.getResources().getString(R.string.wentwrong),Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "pages: "+t);
            }
        });

    }

    private Call<SubCategoryModel> callsubcategoryapi() {
        return api.getSubCategoryData(categoryid);
    }

    private List<Subcategory> fetchResults(Response<SubCategoryModel> response) {
        SubCategoryModel gallery = response.body();
        Log.e("statussssss", "" + gallery.getStatus());
        return gallery.getSubcategory();
    }*/

    private void AllocateMemory(View v) {
        lv_catalog_parent = v.findViewById(R.id.lv_catalog_parent);
        recv_subcategory = v.findViewById(R.id.recv_subcategory);
        toolbar_subcategory = v.findViewById(R.id.toolbar_subcategory);
        lv_nodata_subcategory = v.findViewById(R.id.lv_nodata_subcategory);
        tv_subcat_toolbar = v.findViewById(R.id.tv_subcat_toolbar);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(parent);
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

    }


}

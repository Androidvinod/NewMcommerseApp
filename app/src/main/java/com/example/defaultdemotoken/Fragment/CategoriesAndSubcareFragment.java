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
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Adapter.CategoriesAndCareProductlistAdapter;
import com.example.defaultdemotoken.Adapter.CatrgoriesAndCareTitleAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Model.CategoriesAndCareProductsModel;
import com.example.defaultdemotoken.Model.CategoriesAndCareTitleModel;
import com.example.defaultdemotoken.Model.HomeModel.BrowseCategory;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Fragment.HomeFragment.browse_category;
import static com.example.defaultdemotoken.Fragment.HomeFragment.callhomepagetopbannre;

public class CategoriesAndSubcareFragment extends Fragment {

    public CategoriesAndSubcareFragment() {
        // Required empty public constructor
    }

    View v;
    Toolbar toolbar_categories_and_care;
    RecyclerView recv_categories_and_care_title, recv_categories_and_care_productlist;
    CatrgoriesAndCareTitleAdapter catrgoriesAndCareTitleAdapter;
    CategoriesAndCareProductlistAdapter categoriesAndCareProductlistAdapter;
    private List<CategoriesAndCareTitleModel> categoriesAndCareTitleModels = new ArrayList<>();
    private List<CategoriesAndCareProductsModel> categoriesAndCareProductsModels = new ArrayList<>();
    Bundle b;
    String browse_categories_array;
    LinearLayout lv_progress_cnc, lv_main_cnc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_categories_and_subcare, container, false);

        allocateMemory();
        attachRecyclerview();
        setHasOptionsMenu(true);
        b = this.getArguments();

        /*if (b!=null){
            browse_categories_array = b.getString("browse_cat_array");
            Log.e("array_browse_fh",""+browse_categories_array);
        }*/

        Log.e("array_browse_fh", "" + browse_category);

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_categories_and_care);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        toolbar_categories_and_care.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            //CALL_Cnc_title_Api();
            CALL_Cnc_productlist_Api();
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }

        return v;

    }

    /*private void CALL_Cnc_title_Api() {
        for (int i = 0; i < 5; i++) {
            categoriesAndCareTitleModels.add(new CategoriesAndCareTitleModel(""));
        }
        catrgoriesAndCareTitleAdapter.notifyDataSetChanged();
    }*/

    private void CALL_Cnc_productlist_Api() {
        /*for (int i = 0; i < 5; i++) {
            categoriesAndCareProductsModels.add(new CategoriesAndCareProductsModel("",""));
        }
        categoriesAndCareProductlistAdapter.notifyDataSetChanged();*/

        lv_progress_cnc.setVisibility(View.VISIBLE);
        lv_main_cnc.setVisibility(View.GONE);

        callhomepagetopbannre().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                lv_progress_cnc.setVisibility(View.GONE);
                lv_main_cnc.setVisibility(View.VISIBLE);

                for (int i = 0; i < browse_category.length(); i++) {
                    try {
                        JSONObject vals = browse_category.getJSONObject(i);

                        String browse_cat_name = vals.getString("name");
                        Log.e("browse_cat_cnc_name", "" + browse_cat_name);

                        categoriesAndCareProductsModels.add(new CategoriesAndCareProductsModel("id", vals.getString("image"), vals.getString("name")));

                    } catch (Exception e) {
                        Log.e("Exception", "" + e);
                    } finally {
                        categoriesAndCareProductlistAdapter.notifyItemChanged(i);

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lv_progress_cnc.setVisibility(View.GONE);
                lv_main_cnc.setVisibility(View.VISIBLE);
                Log.e("debug_175125", "pages: " + t);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void attachRecyclerview() {
        /*catrgoriesAndCareTitleAdapter = new CatrgoriesAndCareTitleAdapter(getActivity(), categoriesAndCareTitleModels);
        recv_categories_and_care_title.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recv_categories_and_care_title.setAdapter(catrgoriesAndCareTitleAdapter);*/

        categoriesAndCareProductlistAdapter = new CategoriesAndCareProductlistAdapter(getActivity(), categoriesAndCareProductsModels);
        recv_categories_and_care_productlist.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recv_categories_and_care_productlist.setAdapter(categoriesAndCareProductlistAdapter);
    }

    private void allocateMemory() {
        toolbar_categories_and_care = v.findViewById(R.id.toolbar_categories_and_care);
       // recv_categories_and_care_title = v.findViewById(R.id.recv_categories_and_care_title);
        recv_categories_and_care_productlist = v.findViewById(R.id.recv_categories_and_care_productlist);
        lv_progress_cnc = v.findViewById(R.id.lv_progress_cnc);
        lv_main_cnc = v.findViewById(R.id.lv_main_cnc);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_cart, menu);
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
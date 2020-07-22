package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    View v;
    Toolbar toolbar_changepsw;
    TextView tv_change;
    TextInputLayout layout_current_password,layout_new_password,layout_confirm_password;
    TextInputEditText et_current_password,et_new_password,et_confirm_password;
    NestedScrollView nested_change;
    LinearLayout lv_change_password,lv_progress_changepsw;
    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_change_password, container, false);

        allocateMemory();
        setHasOptionsMenu(true);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_changepsw);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        lv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                validateData();
            }
        });
        return v;
    }

    private void validateData() {
    String    current_pw = et_current_password.getText().toString();
        String new_pw = et_new_password.getText().toString();
        String confirm_new_pw = et_confirm_password.getText().toString();

        if (et_current_password.getText().length() == 0) {

            Toast.makeText(getActivity(), ""+getActivity().getResources().getString(R.string.entercurrentpw), Toast.LENGTH_SHORT).show();

            // et_current_password.setError(parent.getResources().getString(R.string.entercurrentpw));
            et_current_password.requestFocus();

        }else if (et_new_password.getText().length() == 0) {
            Toast.makeText(getActivity(), ""+getActivity().getResources().getString(R.string.enternewpw), Toast.LENGTH_SHORT).show();
            // et_new_password.setError(parent.getResources().getString(R.string.enternewpw));
            et_new_password.requestFocus();

        } else if (isValidPassword(et_new_password.getText().toString()) == false) {
            et_new_password.requestFocus();
            Toast.makeText(getContext(), "" + getResources().getString(R.string.validpassword_validate), Toast.LENGTH_SHORT).show();

        }else if (et_confirm_password.getText().length() == 0) {
            Toast.makeText(getActivity(), ""+getActivity().getResources().getString(R.string.enterconfirmpw), Toast.LENGTH_SHORT).show();
            //et_confirm_new_password.setError(parent.getResources().getString(R.string.enterconfirmpw));
            et_confirm_password.requestFocus();

        } else if (!new_pw.equals(confirm_new_pw)) {
            Toast.makeText(getActivity(), ""+getActivity().getResources().getString(R.string.pwmatch), Toast.LENGTH_SHORT).show();
            Log.e("comper_data_145"," "+et_new_password.getText().toString()+" "+et_confirm_password.getText().toString());
            //  et_confirm_new_password.setError(parent.getResources().getString(R.string.pwmatch));
            et_confirm_password.requestFocus();

        } else {
            if (CheckNetwork.isNetworkAvailable(getActivity())) {
                ChangePasswordApi(current_pw, new_pw);
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void ChangePasswordApi(String current_pw, String new_pw) {
        lv_progress_changepsw.setVisibility(View.VISIBLE);
        nested_change.setVisibility(View.GONE);
        callchangepasswordApi(current_pw,new_pw).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                lv_progress_changepsw.setVisibility(View.GONE);
                nested_change.setVisibility(View.VISIBLE);

                Log.e("debug1111",""+ response.body());
                Log.e("debug1111dsgdf",""+ response);

                    if(response.code()==200 || response.isSuccessful())
                    {
                        if(response.body()==true)
                        {
                            Toast.makeText(getActivity(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();

                            pushFragment(new MyAccountFragment() ,"account");
                        }
                    }else {
                        Toast.makeText(getActivity(), "The password doesn't match this account. Verify the password and try again.", Toast.LENGTH_SHORT).show();

                    }
                /*if (changepassword.getStatus().equalsIgnoreCase("Success")) {
                    Log.e("debug_mesahge",""+changepassword.getMessage());
                    Toast.makeText(parent, "Login succesfully", Toast.LENGTH_SHORT).show();
                    loadFragment(new AccountFragment());
                } else if(changepassword.getStatus().equalsIgnoreCase("error")){
                    lv_changepw_progress.setVisibility(View.GONE);
                    Toast.makeText(getContext(), changepassword.getMessage(), Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                lv_progress_changepsw.setVisibility(View.GONE);
                nested_change.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), ""+getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<Boolean> callchangepasswordApi(String current_pw, String new_pw) {
        ApiInterface  customeapi = ApiClient.getClient().create(ApiInterface.class);

        String url=ApiClient.MAIN_URLL+"customers/me/password?currentPassword="+current_pw+"&newPassword="+new_pw;
        Log.e("debug_url","="+url);
        Log.e("customertoken","="+Login_preference.getCustomertoken(getActivity()));

        return customeapi.changePassword("Bearer "+Login_preference.getCustomertoken(getActivity()),url);
    }

    private void allocateMemory() {
        nested_change=v.findViewById(R.id.nested_change);
        toolbar_changepsw=v.findViewById(R.id.toolbar_changepsw);
        layout_confirm_password=v.findViewById(R.id.layout_confirm_password);
        layout_current_password=v.findViewById(R.id.layout_current_password);
        et_current_password=v.findViewById(R.id.et_current_password);
        layout_new_password=v.findViewById(R.id.layout_new_password);
        et_new_password=v.findViewById(R.id.et_new_password);
        et_confirm_password=v.findViewById(R.id.et_confirm_password);
        tv_change=v.findViewById(R.id.tv_change);
        lv_change_password=v.findViewById(R.id.lv_change_password);
        lv_progress_changepsw=v.findViewById(R.id.lv_progress_changepsw);



        tv_change.setTypeface(SplashActivity.montserrat_semibold);
        et_confirm_password.setTypeface(SplashActivity.montserrat_medium);
        et_current_password.setTypeface(SplashActivity.montserrat_regular);
        et_new_password.setTypeface(SplashActivity.montserrat_regular);
        layout_confirm_password.setTypeface(SplashActivity.montserrat_regular);
        layout_current_password.setTypeface(SplashActivity.montserrat_regular);
        layout_new_password.setTypeface(SplashActivity.montserrat_regular);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //    inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

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

    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.framlayout, fragment);
                ft.addToBackStack(add_to_backstack);
                ft.setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out);
                ft.commit();
            }
        }

    }
}











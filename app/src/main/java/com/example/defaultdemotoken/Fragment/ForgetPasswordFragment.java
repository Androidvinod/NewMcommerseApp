package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

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
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.parent;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends Fragment {

    View v;
    Toolbar toolbar_forget;
    LinearLayout lv_progress_forgetpassword,lv_forgetpasswor_click,lv_forget_main;
    NestedScrollView nestedscrol_forget;
    TextInputLayout layout_forgetpsw_email;
    TextView tv_forget_psw_click,tv_forget_password;
    TextInputEditText et_forget_email;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_forget_password, container, false);
        allocateMemory(v);

        setHasOptionsMenu(true);
        setupUI(lv_forget_main);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_forget);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);



        lv_forgetpasswor_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validdate();
            }
        });
        return v;
    }

    private void validdate() {
        String   email = et_forget_email.getText().toString();
        if (et_forget_email.getText().length() == 0) {

            Toast.makeText(getActivity(), getResources().getString(R.string.enteremailaddress), Toast.LENGTH_SHORT).show();
            et_forget_email.requestFocus();

        }else if(isValidEmailAddress(et_forget_email.getText().toString())==false)
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.validemail), Toast.LENGTH_SHORT).show();
            et_forget_email.requestFocus();
        }else {
            if (CheckNetwork.isNetworkAvailable(getActivity())) {
                CallforgetApi(email);
                //Loginapi(email, password);
            } else {
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void CallforgetApi(String email) {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        nestedscrol_forget.setVisibility(View.GONE);
        lv_progress_forgetpassword.setVisibility(View.VISIBLE);
        Log.e("rdebug_104",""+ Login_preference.gettoken(getActivity()));
        Log.e("email",""+email);

        //http://dkbraende.demoproject.info/rest/V1/customers/password?email=info@gmail.com&template=email_reset
        String url=ApiClient.MAIN_URLL+"customers/password?template=email_reset&email="+email;
        Log.e("debug_122","="+url);

        Call<Boolean> customertoken = apiInterface.forgetpassword("Bearer "+Login_preference.getCustomertoken(getActivity()),url);
        customertoken.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.e("response20066","="+response.toString());
                Log.e("response20","="+response.body());
                Log.e("respo","="+response);

                if(response.isSuccessful() || response.code()==200)
                {
                    nestedscrol_forget.setVisibility(View.VISIBLE);
                    lv_progress_forgetpassword.setVisibility(View.GONE);
                    Log.e("response20166","=="+response.body());
                    Toast.makeText(getActivity(), "Mail sent successfully Please check your mail." , Toast.LENGTH_SHORT).show();

                    LoginFragment myFragment = new LoginFragment();
                    parent.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack("login").commit();

                }else {
                    Toast.makeText(getActivity(), "Email is not Exists", Toast.LENGTH_SHORT).show();

                    nestedscrol_forget.setVisibility(View.VISIBLE);
                    lv_progress_forgetpassword.setVisibility(View.GONE);

                }


            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                nestedscrol_forget.setVisibility(View.VISIBLE);
                lv_progress_forgetpassword.setVisibility(View.GONE);

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private boolean isValidEmailAddress(String toString) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(toString);
        return m.matches();
    }
    private void allocateMemory(View v) {
        tv_forget_password=v.findViewById(R.id.tv_forget_password);
        toolbar_forget=v.findViewById(R.id.toolbar_forget);
        lv_progress_forgetpassword=v.findViewById(R.id.lv_progress_forgetpassword);
        nestedscrol_forget=v.findViewById(R.id.nestedscrol_forget);
        layout_forgetpsw_email=v.findViewById(R.id.layout_forgetpsw_email);
        lv_forgetpasswor_click=v.findViewById(R.id.lv_forgetpasswor_click);
        tv_forget_psw_click=v.findViewById(R.id.tv_forget_psw_click);
        lv_forget_main=v.findViewById(R.id.lv_forget_main);
        et_forget_email=v.findViewById(R.id.et_forget_email);

        tv_forget_password.setTypeface(SplashActivity.montserrat_bold);
        tv_forget_psw_click.setTypeface(SplashActivity.montserrat_semibold);
        et_forget_email.setTypeface(SplashActivity.montserrat_regular);
        layout_forgetpsw_email.setTypeface(SplashActivity.montserrat_regular);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // inflater.inflate(R.menu.menu_cart, menu);
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

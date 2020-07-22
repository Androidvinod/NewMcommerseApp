package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */

public class RegisterFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    View v;
    TextInputEditText et_register_firstname,et_register_email,et_register_password,et_register_lastname;
    TextView tv_register,tv_sign_up,tv_create_acc_register;
    Toolbar toolbar_register;
    LinearLayout lv_register_main,lv_sign_up,lv_progress_register,lv_sign_up_with_facebook,lv_sign_up_with_google;
    TextInputLayout layout_register_firstname,layout_register_email,layout_register_password,layout_register_lastname;
    NestedScrollView nested_scroll_register;

    public static GoogleApiClient googleApiClient;
    public static final int SIGN_IN_CODE = 777;
    CallbackManager callbackManager;
    LoginButton login_button_register;
    private static final String EMAIL = "email";

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_register, container, false);

        allocateMemory(v);

        callbackManager = CallbackManager.Factory.create();

        setHasOptionsMenu(true);
        setupUI(lv_register_main);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_register);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        GOOGLE_LOGIN();

        lv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        login_button_register.setFragment(this);
        login_button_register.setReadPermissions(Arrays.asList(EMAIL));

        login_button_register.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getActivity(), "FB successfully login", Toast.LENGTH_SHORT).show();
                Log.e("fb_success",""+loginResult.toString());

                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {

                                    String Name = object.getString("name");
                                    String FEmail = object.getString("email");
                                    String FEid = object.getString("id");
                                    String fbpp = "http://graph.facebook.com/" + FEid + "/picture?type=square";

                                    Log.e("fbemail = ", " " + FEmail);
                                    Log.e("fbid = ", " " + FEid);
                                    Log.e("fbpp = ", " " + fbpp);
                                    Toast.makeText(getApplicationContext(), Name, Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), "FB cancel", Toast.LENGTH_SHORT).show();
                Log.e("fb_cancel","cancellll");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("fb_error",""+error.toString());
            }
        });


        return v;
    }

    private void GOOGLE_LOGIN() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        lv_sign_up_with_google = v.findViewById(R.id.lv_sign_up_with_google);
        lv_sign_up_with_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });

    }

    private void validateData() {
        if (et_register_firstname.getText().length() == 0) {

            Toast.makeText(getActivity(), getResources().getString(R.string.enterfirstname), Toast.LENGTH_SHORT).show();
            et_register_firstname.requestFocus();

        } else if (et_register_lastname.getText().length() == 0) {

            Toast.makeText(getActivity(), getResources().getString(R.string.enterlastname), Toast.LENGTH_SHORT).show();
            et_register_lastname.requestFocus();

        } else if (et_register_email.getText().length()==0) {

            Toast.makeText(getActivity(), getResources().getString(R.string.enteremailaddress), Toast.LENGTH_SHORT).show();
            et_register_email.requestFocus();
        }

        else if (isValidEmailAddress(et_register_email.getText().toString())==false) {

            Toast.makeText(getActivity(), getResources().getString(R.string.validemail), Toast.LENGTH_SHORT).show();
            et_register_email.requestFocus();


        }else if (et_register_password.getText().length()==0) {

            Toast.makeText(getActivity(), getResources().getString(R.string.enterpw), Toast.LENGTH_SHORT).show();
            et_register_password.requestFocus();
        }else if (isValidPassword(et_register_password.getText().toString()) == false) {
            et_register_password.requestFocus();

            Toast.makeText(getContext(), "" + getResources().getString(R.string.validpassword_validate), Toast.LENGTH_SHORT).show();

        }
        else {
            if (CheckNetwork.isNetworkAvailable(getActivity())) {
                String firstname,lastname,email,passowrd;
                firstname=et_register_firstname.getText().toString();
                lastname=et_register_lastname.getText().toString();
                email=et_register_email.getText().toString();
                passowrd=et_register_password.getText().toString();
                register(firstname, lastname,email,passowrd);

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    private void register(String firstname, String lastname, String email, String passowrd) {

        nested_scroll_register.setVisibility(View.GONE);
        lv_progress_register.setVisibility(View.VISIBLE);
        Log.e("login_email", "" + email);
        Log.e("login_password", "" + firstname);

        callregisterApi(firstname,lastname,email,passowrd).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("code","="+ response.code());
                Log.e("success","="+ response.isSuccessful());
                if(response.code()==200 || response.isSuccessful())
                {
                    //ResponseBody login_model = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.e("jsonObject","="+ jsonObject);
                        Log.e("jsonObject","="+ response);
                        String email=jsonObject.getString("email");
                        String firstname=jsonObject.getString("firstname");
                        String lastname=jsonObject.getString("lastname");
                        String group_id=jsonObject.getString("group_id");
                        Log.e("email111111111","="+email);
                        Log.e("firstname","="+firstname);
                        Log.e("lastname","="+lastname);
                        nested_scroll_register.setVisibility(View.GONE);
                        lv_progress_register.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Sign Up succesfully", Toast.LENGTH_SHORT).show();

                        LoginFragment myFragment = new LoginFragment();
                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fade_in,
                                        0, 0, R.anim.fade_out)
                                .replace(R.id.framlayout, myFragment)
                                .commit();
                        Login_preference.setemail(getActivity(), email);
                        Login_preference.setfirstname(getActivity(), firstname);
                        Login_preference.setlastname(getActivity(), lastname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else
                {
                    /*if (response.code()==400)*/
                    nested_scroll_register.setVisibility(View.VISIBLE);
                    lv_progress_register.setVisibility(View.GONE);
                    Log.e("codedddddddddddddd","="+ response.code());
                    Log.e("codedres","="+ response.body());
                    Log.e("response","="+ response);
                    try {
                        Log.e("responseeeeee","="+ response.errorBody().string());

                       /* JSONObject    jsonObject = new JSONObject(response.errorBody().string());
                        Log.e("jsonObject","="+jsonObject);

                        if(jsonObject.has("message"))
                        {

                            String userMessage = jsonObject.optString("message");
                            Log.e("userMessage","="+response.errorBody());
                            Toast.makeText(parent, "=="+userMessage, Toast.LENGTH_SHORT).show();
                        }*/

                        Toast.makeText(getActivity(), "A customer with the same email address already exists in an associated website.", Toast.LENGTH_LONG).show();

                        // Now use error.getMessage()

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                   /* try {

                        Log.e("codedddddddddddddd","="+ response.code());
                        Log.e("codedres","="+ response.body());
                        Log.e("response","="+ response);
                        Log.e("response","="+ response.errorBody().string());
                      //  Log.e("coded==","="+ response.body().string());
                       */
                   /* JSONObject    jsonObject = new JSONObject(response.body().string());
                        Log.e("jsonObject","="+ jsonObject);
                        Log.e("jsonObject","="+ response);
                        String msg=jsonObject.getString("message");
                        Log.e("codedmessage","="+ msg);
                   */
                   /*     Toast.makeText(parent, ""+response.errorBody().string(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }*/

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                nested_scroll_register.setVisibility(View.VISIBLE);
                lv_progress_register.setVisibility(View.GONE);
                Log.e("error","="+t.getMessage());
                Toast.makeText(getActivity(), ""+getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Call<ResponseBody> callregisterApi(String firstname, String lastname, String email, String passowrd) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_email","df"+email);
        Log.e("debug_email","df"+firstname);
        Log.e("debug_email","df"+lastname);
        Log.e("debug_email","df"+passowrd);
        String url=ApiClient.MAIN_URLL+"customers/?customer[email]="+
                email+"&customer[firstname]="+firstname+"&customer[lastname]="+lastname+"&password="+passowrd;

        return apiInterface.register("Bearer "+Login_preference.gettoken(getActivity()), url);
    }


    private boolean isValidEmailAddress(String toString) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(toString);
        return m.matches();
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

    private void allocateMemory(View v) {
        login_button_register = v.findViewById(R.id.login_button_register);
        lv_progress_register=v.findViewById(R.id.lv_progress_register);
        nested_scroll_register=v.findViewById(R.id.nested_scroll_register);
        et_register_lastname=v.findViewById(R.id.et_register_lastname);
        layout_register_lastname=v.findViewById(R.id.layout_register_lastname);
        tv_create_acc_register=v.findViewById(R.id.tv_create_acc_register);
        tv_sign_up=v.findViewById(R.id.tv_sign_up);
        toolbar_register=v.findViewById(R.id.toolbar_register);
        tv_register=v.findViewById(R.id.tv_register);
        //lv_sign_up_with_google=v.findViewById(R.id.lv_sign_up_with_google);
        lv_register_main=v.findViewById(R.id.lv_register_main);
        layout_register_firstname=v.findViewById(R.id.layout_register_firstname);
        et_register_firstname=v.findViewById(R.id.et_register_firstname);
        layout_register_email=v.findViewById(R.id.layout_register_email);
        et_register_email=v.findViewById(R.id.et_register_email);
        layout_register_password=v.findViewById(R.id.layout_register_password);
        et_register_password=v.findViewById(R.id.et_register_password);
        lv_sign_up=v.findViewById(R.id.lv_sign_up);
        //lv_sign_up_with_facebook=v.findViewById(R.id.lv_sign_up_with_facebook);
        //fb_login = v.findViewById(R.id.fb_login);
        layout_register_lastname.setTypeface(SplashActivity.montserrat_regular);
        et_register_lastname.setTypeface(SplashActivity.montserrat_regular);
        layout_register_email.setTypeface(SplashActivity.montserrat_regular);
        layout_register_firstname.setTypeface(SplashActivity.montserrat_regular);
        layout_register_password.setTypeface(SplashActivity.montserrat_regular);
        et_register_email.setTypeface(SplashActivity.montserrat_regular);
        et_register_firstname.setTypeface(SplashActivity.montserrat_regular);
        et_register_password.setTypeface(SplashActivity.montserrat_regular);

        tv_create_acc_register.setTypeface(SplashActivity.montserrat_bold);
        tv_register.setTypeface(SplashActivity.montserrat_bold);
        tv_sign_up.setTypeface(SplashActivity.montserrat_semibold);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "There are some problem to connect with google", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            Log.e("resultofgoogle", "" + result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            String gname = account.getDisplayName();
            Log.e("gimgl", "" + gname);
            Log.e("gnamel", "" + account.getDisplayName());
            Log.e("gemaill", "" + account.getEmail());
            Log.e("gidl", "" + account.getId());
            Log.e("gtokenidl", "" + account.getIdToken());

            Toast.makeText(getActivity(), account.getDisplayName(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Can not get data...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }

    }

    @Override
    public void onStop() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage((FragmentActivity) getContext());
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

}
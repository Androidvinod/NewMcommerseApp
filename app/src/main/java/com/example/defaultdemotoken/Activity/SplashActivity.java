package com.example.defaultdemotoken.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    ApiInterface api;
    public static Typeface montserrat_black,montserrat_bold,montserrat_extrabold,montserrat_extralight,montserrat_light,montserrat_medium
            ,montserrat_regular,montserrat_semibold,montserrat_thin;
    AssetManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setTheme(android.R.style.BlueTheme);
        setContentView(R.layout.activity_splash);
        api = ApiClient.getClient().create(ApiInterface.class);
        SET_FONT_STYLE();
        Login_preference.setgridlist(this,"2");
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (CheckNetwork.isNetworkAvailable(SplashActivity.this)) {
            getCategoryList();


        } else {
            //    noInternetDialog(NavigationActivity.this);
            Toast.makeText(SplashActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }


    private void SET_FONT_STYLE() {
        am = getApplicationContext().getAssets();

        montserrat_black= Typeface.createFromAsset(am, String.format(Locale.getDefault(), "montserrat_black.ttf"));
        montserrat_bold = Typeface.createFromAsset(am, String.format(Locale.getDefault(), "montserrat_bold.ttf"));
        montserrat_extrabold = Typeface.createFromAsset(am, String.format(Locale.getDefault(), "montserrat_extrabold.ttf"));
        montserrat_extralight = Typeface.createFromAsset(am, String.format(Locale.getDefault(), "montserrat_extralight.ttf"));
        montserrat_light = Typeface.createFromAsset(am,  String.format(Locale.getDefault(), "montserrat_light.ttf"));

        montserrat_medium = Typeface.createFromAsset(am,  String.format(Locale.getDefault(), "montserrat_medium.ttf"));

        montserrat_regular = Typeface.createFromAsset(am,  String.format(Locale.getDefault(), "montserrat_regular.ttf"));
        montserrat_semibold = Typeface.createFromAsset(am,  String.format(Locale.getDefault(), "montserrat_semibold.ttf"));
        montserrat_thin = Typeface.createFromAsset(am,  String.format(Locale.getDefault(), "montserrat_thin.ttf"));


    }

    private void getCategoryList() {
        callCategoryApi().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //CategoriesModel CategoriesModel = response.body();
                String model = response.body();

                Log.e("debug_165","168-"+response);
                Log.e("debug_167","168-"+response.body());

                if(response.code()==200 || response.isSuccessful())
                {
                    Login_preference.settoken(SplashActivity.this,response.body());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("debug_168","168");
                            Intent i =new Intent(SplashActivity.this,NavigationActivity.class);
                            startActivity(i);
                            finish();
                        }
                    },1500);
                }else {
                    Toast.makeText(SplashActivity.this, "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // String error=  t.printStackTrace();
                Toast.makeText(SplashActivity.this, "" + getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "pages: " + t);

            }
        });
    }
    //http://dkbraende.demoproject.info/rest/V1/integration/admin/token/?username=admin&password=9yWpe6v7(OZ7
    //http://app.demoproject.info/rest/V1/integration/admin/token/?username=admin1&password=admin@123
    private Call<String> callCategoryApi() {
        return api.token(ApiClient.MAIN_URLL+"integration/admin/token/?username=admin1&password=admin@123");

    }
}

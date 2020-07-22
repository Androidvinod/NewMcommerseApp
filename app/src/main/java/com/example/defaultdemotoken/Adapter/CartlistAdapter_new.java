package com.example.defaultdemotoken.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Fragment.CartListFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.CartListModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Fragment.CartListFragment.cordinator_cart;
import static com.example.defaultdemotoken.Fragment.CartListFragment.lv_cartlist_progress;


public class CartlistAdapter_new extends RecyclerView.Adapter<CartlistAdapter_new.MyViewHolder> {
    private Context context;
    private List<CartListModel> cartList;
    ApiInterface api;
    public static boolean flag = true;
    boolean isFlag=true;

    public CartlistAdapter_new(Context context, List<CartListModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartlist_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        api = ApiClient.getClient().create(ApiInterface.class);
        final CartListModel cart_model = cartList.get(position);
        Log.e("ada_cart_pdname",""+cart_model.getName());
        Log.e("ada_cart_pdprice",""+cart_model.getPrice());
        Log.e("ada_cart_pdquote",""+cart_model.getQuoteId());
        Log.e("ada_cart_pdimagee",""+cart_model.getImage());

        //Log.e("ada_cart_pdimage",""+cart_model.getExtensionAttributes());

        holder.tv_cartlist_product_name.setTypeface(SplashActivity.montserrat_semibold);
        holder.tv_cart_sku.setTypeface(SplashActivity.montserrat_medium);
        holder.et_cart_qty.setTypeface(SplashActivity.montserrat_medium);
        holder.tv_cartlist_price.setTypeface(SplashActivity.montserrat_semibold);

        holder.tv_cartlist_product_name.setText(cart_model.getName());
        holder.tv_cartlist_price.setText(String.valueOf(cart_model.getPrice()));
        holder.tv_cart_sku.setText(cart_model.getSku());
        holder.et_cart_qty.setText(String.valueOf(cart_model.getQty()));

        final RequestOptions requestOptions = new RequestOptions();

        requestOptions.placeholder(R.drawable.dress);
        requestOptions.error(R.drawable.dress);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(cart_model.getImage()).into(holder.iv_cartlist_product);

        holder.lv_remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemid=cartList.get(position).getItemId();
                Log.e("debug_109","="+itemid);
                removefromcart(itemid,holder);
            }
        });
      /*  holder.lv_cart_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, cartList.get(position).getItemId(), Toast.LENGTH_SHORT).show();

                holder.lv_cart_click.setEnabled(false);
                Bundle b=new Bundle();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                b.putString("product_id",cartList.get(position).getItemId());

                ProductDetailFragment myFragment = new ProductDetailFragment();
                myFragment.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out).addToBackStack(null).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();


            }
        });*/
        /*holder.et_cart_qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String model_qty= cart_model.getQty();

                    if (String.valueOf( holder.et_cart_qty.getText()).equalsIgnoreCase("")) {
                        Toast.makeText(context, context.getString(R.string.quantity), Toast.LENGTH_SHORT).show();
                    } else if (String.valueOf( holder.et_cart_qty.getText()).equalsIgnoreCase("0")||String.valueOf( holder.et_cart_qty.getText()).equalsIgnoreCase("00")
                            ||String.valueOf( holder.et_cart_qty.getText()).equalsIgnoreCase("000")||String.valueOf( holder.et_cart_qty.getText()).equalsIgnoreCase("0000")
                            ||String.valueOf( holder.et_cart_qty.getText()).equalsIgnoreCase("00000")) {
                        flag = false;
                        Log.e("zero_click_254",""+flag);
                        CartListFragment.lv_cart_checkout.setAlpha(0.4f);
                        //  CartFragment.lv_cart_checkout.setEnabled(false);

                        Toast.makeText(context, context.getString(R.string.quantity_messge), Toast.LENGTH_SHORT).show();
                    }else if (String.valueOf(holder.et_cart_qty.getText()).equals(model_qty)) {
                        flag = true;
                        CartListFragment.lv_cart_checkout.setAlpha(1f);
                        Log.e("zero_click_261",""+flag);
                        // CartFragment.lv_cart_checkout.setEnabled(true);
                        //  Toast.makeText(context, "Same", Toast.LENGTH_SHORT).show();
                    } else {
                        flag = true;
                        final String itemid = cartList.get(position).getItemId();
                        Log.e("debugitem", "sss" + itemid);
                        Log.e("debugcustomertoken", "email=" + Login_preference.getCustomertoken(context));
                        Log.e("quote", "email=" + Login_preference.getquote_id(context));
                        if (CheckNetwork.isNetworkAvailable(context)) {
                            callUpdateCartApi(itemid, String.valueOf( holder.et_cart_qty.getText()), holder);
                        } else {
                            Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });*/

        holder.et_cart_qty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String model_qty= cart_model.getQty();

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (String.valueOf(v.getText()).equalsIgnoreCase("")) {
                        Toast.makeText(context, context.getString(R.string.quantity), Toast.LENGTH_SHORT).show();
                    } else if (String.valueOf(v.getText()).equalsIgnoreCase("0")||String.valueOf(v.getText()).equalsIgnoreCase("00")
                            ||String.valueOf(v.getText()).equalsIgnoreCase("000")||String.valueOf(v.getText()).equalsIgnoreCase("0000")
                            ||String.valueOf(v.getText()).equalsIgnoreCase("00000")) {
                        flag = false;
                        Log.e("zero_click_254",""+flag);
                        CartListFragment.lv_cart_checkout.setAlpha(0.4f);
                        //  CartFragment.lv_cart_checkout.setEnabled(false);

                        Toast.makeText(context, context.getString(R.string.quantity_messge), Toast.LENGTH_SHORT).show();
                    }else if (String.valueOf(holder.et_cart_qty.getText()).equals(model_qty)) {
                        flag = true;
                        CartListFragment.lv_cart_checkout.setAlpha(1f);
                        Log.e("zero_click_261",""+flag);
                        // CartFragment.lv_cart_checkout.setEnabled(true);
                        //  Toast.makeText(context, "Same", Toast.LENGTH_SHORT).show();
                    } else {
                        flag = true;
                        final String itemid = cartList.get(position).getItemId();
                        Log.e("debugitem", "sss" + itemid);
                        Log.e("debugcustomertoken", "email=" + Login_preference.getCustomertoken(context));
                        Log.e("quote", "email=" + Login_preference.getquote_id(context));
                        if (CheckNetwork.isNetworkAvailable(context)) {
                            AppCompatActivity activity = (AppCompatActivity) holder.et_cart_qty.getContext();

                            hideKeyboard(activity);

                            callUpdateCartApi(itemid, String.valueOf(v.getText()), holder);
                        } else {
                            Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                return false;
            }
        });

    }

    private void callUpdateCartApi(final String itemid, final String valueOf, final MyViewHolder holder) {
        Log.e("debug_232_update", "a" + itemid);
        Log.e("debug_233_update", "b" + valueOf);
        AppCompatActivity activity = (AppCompatActivity) holder.et_cart_qty.getContext();

        hideKeyboard(activity);

        cordinator_cart.setVisibility(View.GONE);
        lv_cartlist_progress.setVisibility(View.VISIBLE);

        callupdateCartQty(itemid, valueOf).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("debug_162update", "" + response);
                Log.e("debug_167", "" + response.body());
                AppCompatActivity activity = (AppCompatActivity) holder.et_cart_qty.getContext();

                hideKeyboard(activity);

                if (response.code()==200 || response.isSuccessful()) {
                    Log.e("status_update", "ok");
                    cordinator_cart.setVisibility(View.GONE);
                    lv_cartlist_progress.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        Log.e("jsonObject=", "ok"+jsonObject);

                        // CartListFragment.CallCartlistApi();

                    /*    final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
*/
                                setupUI(holder.et_cart_qty);
                                Toast.makeText(context, "Quantity updated successfully.", Toast.LENGTH_SHORT).show();

                                AppCompatActivity activityy = (AppCompatActivity) holder.et_cart_qty.getContext();
                                hideKeyboard(activityy);

                                //CartListFragment.CallCartlistApi();
                                 CartListFragment myFragment = new CartListFragment();
                                activityy.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).commit();
                           /* }
                        }, 100);*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    cordinator_cart.setVisibility(View.GONE);
                    lv_cartlist_progress.setVisibility(View.GONE);

                    AppCompatActivity activityy = (AppCompatActivity) holder.et_cart_qty.getContext();

                    hideKeyboard(activityy);
                    NavigationActivity.get_Customer_tokenapi();
                    Log.e("status_wish402", "not_ok");
                    callUpdateCartApi(itemid, valueOf, holder);
                    //Toast.makeText(context, updateCartQtyModel.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error_wish", "" + t);
                Log.e("debug_remivr", "" + t);
                //CartFragment.cordinator_cart.setVisibility(View.VISIBLE);
                //CartFragment.lv_cartlist_progress.setVisibility(View.GONE);


                Toast.makeText(context, context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<ResponseBody> callupdateCartQty(String prod_idpass, String text) {
        Log.e("token", "=" + Login_preference.gettoken(context));
        Log.e("quoteid", "=" + Login_preference.getquote_id(context));
        Log.e("itemid", "=" + prod_idpass);
        Log.e("text_qty", "=" + text);
        ApiInterface  apiInterface = ApiClient.getClient().create(ApiInterface.class);

        String url=ApiClient.MAIN_URLL+"carts/:cartId/items?cartItem[quoteId]="+
                Login_preference.getquote_id(context)+"&cartItem[qty]="+text+"&cartItem[item_id]="+prod_idpass;
        return apiInterface.udatecarttt("Bearer " + Login_preference.gettoken(context),url);
    }

    private void removefromcart(final String itemId, final MyViewHolder holder) {
        cordinator_cart.setVisibility(View.GONE);
        lv_cartlist_progress.setVisibility(View.VISIBLE);
        callremoveFromCart(itemId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
              //  cordinator_cart.setVisibility(View.VISIBLE);
            //    lv_cartlist_progress.setVisibility(View.GONE);
                Boolean result = response.body();
                Log.e("rc_res","="+result);

                if(response.isSuccessful() || response.code()==200)
                {

                          cordinator_cart.setVisibility(View.VISIBLE);
                            lv_cartlist_progress.setVisibility(View.GONE);

                        //  CartListFragment.CallCartlistApi();
                        AppCompatActivity activityy = (AppCompatActivity) holder.lv_remove_from_cart.getContext();
                        hideKeyboard(activityy);

                        //CartListFragment.CallCartlistApi();
                        CartListFragment myFragment = new CartListFragment();
                        activityy.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).commit();
                        Toast.makeText(context, "Product has been successfully removed from your cart", Toast.LENGTH_SHORT).show();
                        //notifyItemRemoved(Integer.parseInt(itemId));

                }else {
                     cordinator_cart.setVisibility(View.VISIBLE);
                        lv_cartlist_progress.setVisibility(View.GONE);
                    NavigationActivity.get_Customer_tokenapi();
                    removefromcart(itemId,holder);
                }

            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("failllllll","");
            }
        });
    }

    private Call<Boolean> callremoveFromCart(String itemId) {
        return api.removeFromCart("Bearer " + Login_preference.getCustomertoken(context),ApiClient.MAIN_URLL+"carts/mine/items/"+itemId);
    }

    public void addAll(List<CartListModel> media1) {
        for (CartListModel cartListModel : media1) {
            add(cartListModel);
        }
    }

    private  void add(CartListModel r) {
        cartList.add(r);
        notifyItemInserted(cartList.size() - 1);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard((Activity) context);
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
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cartlist_product_name,tv_cartlist_price,tv_cart_sku;
        ImageView iv_cartlist_product;
        /*RoundRectCornerImageView iv_cartlist_product;*/
        EditText et_cart_qty;
        LinearLayout lv_remove_from_cart,lv_cart_click;
        public MyViewHolder(View view) {
            super(view);

            lv_cart_click =  view.findViewById(R.id.lv_cart_click);
            tv_cartlist_product_name =  view.findViewById(R.id.tv_cartlist_product_name);
            tv_cartlist_price =  view.findViewById(R.id.tv_cartlist_price);
            tv_cart_sku =  view.findViewById(R.id.tv_cart_sku);
            iv_cartlist_product =  view.findViewById(R.id.iv_cartlist_product);
            et_cart_qty =  view.findViewById(R.id.et_cart_qty);
            lv_remove_from_cart =  view.findViewById(R.id.lv_remove_from_cart);

        }
    }
}
package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Fragment.Checkout_Address_Fragment;
import com.example.defaultdemotoken.Fragment.EditAddressFragment;
import com.example.defaultdemotoken.Fragment.MyAddressFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.AddressModel.Address;
import com.example.defaultdemotoken.Model.Country;
import com.example.defaultdemotoken.Model.CountryModel.CountryListModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Fragment.MyAddressFragment.tv_number;


public class NewAddressAdapter extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.NewAddressAdapter.MyViewHolder> {


        private Context context;
        private List<Address> Addresss;
        private int lastSelectedPosition;

        public NewAddressAdapter(Context context) {
            this.context = context;
            this.Addresss = new ArrayList<>();
        }

        @Override
        public com.example.defaultdemotoken.Adapter.NewAddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.newaddress_row, parent, false);

            return new com.example.defaultdemotoken.Adapter.NewAddressAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final com.example.defaultdemotoken.Adapter.NewAddressAdapter.MyViewHolder holder, final int position) {
            final Address address = Addresss.get(position);

            holder.lv_edit_addrss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String add_id= String.valueOf(Addresss.get(position).getId());


                    Bundle b = new Bundle();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    b.putString("screen", "edit address");
                    b.putString("address", "edit");
                    b.putString("address_id", add_id);

                    EditAddressFragment myFragment = new EditAddressFragment();
                    myFragment.setArguments(b);
                    activity.getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null).replace(R.id.framlayout, myFragment)
                            .commit();
                }
            });

            holder.lv_delete_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String add_id= String.valueOf(Addresss.get(position).getId());

                    Log.e("debuaddid55","="+add_id);

                    Log.e("debuaddid554444444","="+add_id);

                    DelerteAddressApi(add_id,v,holder);

                }
            });

            int pos = 0; String countryname = null;
            if(MyAddressFragment.countryidlist.size()>0)
            {
                pos= MyAddressFragment.countryidlist.indexOf(address.getCountryId());
            }

            if(MyAddressFragment.countryLablelist.size()>0)
            {
                 countryname=MyAddressFragment.countryLablelist.get(pos);
                 Log.e("countryname=","="+countryname);

            }

            List<String> street=new ArrayList<>();
            street=address.getStreet();
            Log.e("addres9999","43   "+address.getFirstname()+"\n"+street.get(0)+"\n"+address.getRegion()+","+address.getCity()+","+address.getPostcode()+"\n"+address.getLastname()+"\n"+"T :"+address.getTelephone());
            Log.e("address7777","43   "+address.getFirstname()+"\n"+address.getStreet()+"\n"+address.getRegion()+","+address.getCity()+","+address.getPostcode()+"\n"+address.getLastname()+"\n"+"T :"+address.getTelephone());
            holder.tv_adderss.setText(address.getFirstname()+" "+address.getLastname()+", \n"
                    +street.get(0)+" ," +address.getCity()+" ,"+address.getRegion().getRegion()+" ,\n"+
                    countryname+ " ,"+address.getPostcode()+"\n"+"T :"+address.getTelephone());

            Login_preference.setphone(context,address.getTelephone());

            tv_number.setText(address.getTelephone());

        }

        @Override
        public int getItemCount() {
            return Addresss.size();
        }
        public void addAll(List<Address> feesInnerData) {
            for (Address Address : feesInnerData) {
                add(Address);
            }
        }
        public void add(Address r) {
            Addresss.add(r);
            notifyItemInserted(Addresss.size() - 1);
        }





    public static class MyViewHolder extends RecyclerView.ViewHolder {

            //     TextView tv_delivery_method_name;
            LinearLayout lv_add_progress,lv_add_mainnn,lv_edit_addrss,lv_delete_add;
            TextView tv_adderss;

            public MyViewHolder(View view) {
                super(view);

                //   tv_delivery_method_name = view.findViewById(R.id.rad_delivery_description);
                lv_add_progress = view.findViewById(R.id.lv_add_progress);
                lv_add_mainnn = view.findViewById(R.id.lv_add_mainnn);
                tv_adderss = view.findViewById(R.id.tv_adderss);
                lv_edit_addrss = view.findViewById(R.id.lv_edit_addrss);
                lv_delete_add = view.findViewById(R.id.lv_delete_add);

            }
        }


    private Call<Boolean> delerteAddressApi(String add_id) {
        ApiInterface customeapi = ApiClient.getClient().create(ApiInterface.class);

        String url=ApiClient.MAIN_URLL+"addresses/"+add_id;
        Log.e("debug_url","="+url);
        Log.e("token","="+ Login_preference.gettoken(context));

        return customeapi.deleteaddress("Bearer "+ Login_preference.gettoken(context),url);
    }


    private void DelerteAddressApi(String add_id, final View v, final MyViewHolder holder) {
        holder.lv_add_progress.setVisibility(View.VISIBLE);
        holder.lv_add_mainnn.setVisibility(View.GONE);
        delerteAddressApi(add_id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                holder.lv_add_progress.setVisibility(View.GONE);
                holder.lv_add_mainnn.setVisibility(View.VISIBLE);

                Log.e("debug1111",""+ response.body());
                Log.e("debug1111dsgdf",""+ response);

                if(response.code()==200 || response.isSuccessful())
                {
                    if(response.body()==true)
                    {

                        Toast.makeText(context, "Address Deleted Successfully", Toast.LENGTH_SHORT).show();
                        /*if(screenname.equalsIgnoreCase("null") || screenname ==null)
                        {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            AccountDashboard myFragment = new AccountDashboard();
                            activity.getSupportFragmentManager().beginTransaction()
                                    .addToBackStack("account").replace(R.id.framlayout, myFragment)
                                    .commit();
                        }
                        else if(screenname.equalsIgnoreCase("AddressBook"))
                        {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Address_Book myFragment = new Address_Book();
                            activity.getSupportFragmentManager().beginTransaction()
                                    .addToBackStack("Address_Book").replace(R.id.framlayout, myFragment)
                                    .commit();
                        }else if(screenname.equalsIgnoreCase("AccountDashboard")){
                         */
                        Login_preference.setphone(context,"");
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            MyAddressFragment myFragment = new MyAddressFragment();
                            activity.getSupportFragmentManager().beginTransaction()
                                    .addToBackStack("account").replace(R.id.framlayout, myFragment)
                                    .commit();
                       /* }*/


                    }
                }else {
                    //   Toast.makeText(context(), "The password doesn't match this account. Verify the password and try again.", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                holder.lv_add_progress.setVisibility(View.GONE);
                holder.lv_add_mainnn.setVisibility(View.VISIBLE);
                Toast.makeText(context, ""+context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }
    }




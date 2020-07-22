package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import androidx.recyclerview.widget.RecyclerView;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Fragment.Checkout_Address_Fragment;
import com.example.defaultdemotoken.Model.AddressModel.Address;
import com.example.defaultdemotoken.R;
import java.util.ArrayList;
import java.util.List;


 public class AddressBookAdapter extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.AddressBookAdapter.MyViewHolder> {
        private Context context;
        private List<Address> Addresss;
        private int lastSelectedPosition;

        public AddressBookAdapter(Context context) {
            this.context = context;
            this.Addresss = new ArrayList<>();
        }

        @Override
        public com.example.defaultdemotoken.Adapter.AddressBookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.address_row, parent, false);

            return new com.example.defaultdemotoken.Adapter.AddressBookAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final com.example.defaultdemotoken.Adapter.AddressBookAdapter.MyViewHolder holder, final int position) {
            final Address address = Addresss.get(position);

           
            holder.rad_delivery_address.setTypeface(SplashActivity.montserrat_medium);
            List<String> street=new ArrayList<>();
            street=address.getStreet();
            Log.e("addres9999","43   "+address.getFirstname()+"\n"+street.get(0)+"\n"+address.getRegion()+","+address.getCity()+","+address.getPostcode()+"\n"+address.getLastname()+"\n"+"T :"+address.getTelephone());
            Log.e("address7777","43   "+address.getFirstname()+"\n"+address.getStreet()+"\n"+address.getRegion()+","+address.getCity()+","+address.getPostcode()+"\n"+address.getLastname()+"\n"+"T :"+address.getTelephone());
            holder.rad_delivery_address.setText(address.getFirstname()+" "+address.getLastname()+" ,"+street.get(0)+" ,"+address.getCity()+","+address.getPostcode()+"\n"+"T :"+address.getTelephone());


            holder.rad_delivery_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Checkout_Address_Fragment.addressidd=String.valueOf(Addresss.get(position).getId());
                    lastSelectedPosition = position;
                    notifyDataSetChanged();
                }
            });

            if (lastSelectedPosition == position) {
                Log.e("selectedpo_76", "" + lastSelectedPosition);
                holder.rad_delivery_address.setChecked(true);
            } else {
                holder.rad_delivery_address.setChecked(false);
            }

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
            public static RadioButton rad_delivery_address;

            public MyViewHolder(View view) {
                super(view);

             //   tv_delivery_method_name = view.findViewById(R.id.rad_delivery_description);
                rad_delivery_address = view.findViewById(R.id.rad_delivery_address);

            }
        }
    }



package com.example.virtualwaiteruser;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousOrders extends Fragment {
    ListView ls;
    ProgressDialog progress;
    String name,qnt;
    Long cost1;

    public PreviousOrders() {
        // Required empty public constructor
    }
    public ArrayList<FinalCartDetails> itemDetailsArrayList = new ArrayList<>();
    MyAdapterOrder orders;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V=inflater.inflate(R.layout.fragment_previous_orders, container, false);
        ls=V.findViewById(R.id.ListViewadminorders);
        progress=new ProgressDialog(getActivity());
        progress.setMessage("Please Wait for Orders......."); // Setting Message
        progress.setTitle("Fetching Orders"); // Setting Title
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progress.show(); // Display Progress Dialog
        progress.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progress.dismiss();
            }
        }).start();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(dataSnapshot1.exists())
                    {
                        cost1=(dataSnapshot1.child("cost").getValue(Long.class));
                        name=dataSnapshot1.child("ItemName").getValue(String.class);
                        qnt=(dataSnapshot1.child("quant").getValue(String.class));
                          FinalCartDetails finalCartDetails=new FinalCartDetails(name,qnt,cost1);
                          itemDetailsArrayList.add(finalCartDetails);
//                        Log.e("Non-VegItems",itemDetails.name);
                    }
                }
                orders.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        orders=new MyAdapterOrder(getActivity(),R.layout.recyclerview_finalcart,itemDetailsArrayList);
        ls.setAdapter(orders);
        return V;
    }
    public class MyAdapterOrder extends ArrayAdapter<FinalCartDetails>
    {
        ArrayList<FinalCartDetails> itemDetailsArrayList;
        public MyAdapterOrder(Context context, int items, ArrayList<FinalCartDetails> objects) {
            super(context,items,objects);
            itemDetailsArrayList=objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            convertView=LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_finalcart,parent,false);
            final TextView ItemName = convertView.findViewById(R.id.ItemName);
            final TextView ItemCost = convertView.findViewById(R.id.ItemCost);
            final TextView qnt=convertView.findViewById(R.id.Quantity);
            ItemName.setText(itemDetailsArrayList.get(position).getName());
            ItemCost.setText(itemDetailsArrayList.get(position).getCost().toString());
            qnt.setText(itemDetailsArrayList.get(position).getQuant());
            return convertView;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }
    }

}

package com.example.virtualwaiteruser;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Desserts extends Fragment {
    public Context context;
    Long cost1;
    String name;
    Button Ordernow;
    ProgressDialog progress;
    public ArrayList<ItemDetails> itemDetailsArrayListDesserts = new ArrayList<>();
    Desserts.MyAdapterForDesserts myAdapterForDesserts;

    public Desserts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_desserts, container, false);
        progress=new ProgressDialog(getActivity());
        progress.setMessage("Quality Food Take's Time...Please Wait"); // Setting Message
        progress.setTitle("Fetching Items"); // Setting Title
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
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Admin-Starters");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemDetailsArrayListDesserts.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(dataSnapshot1.exists())
                    {
                        name=dataSnapshot1.child("ItemName").getValue(String.class);
                        cost1=(dataSnapshot1.child("cost").getValue(Long.class));
                        ItemDetails itemDetails=new ItemDetails(cost1,name);
                        itemDetailsArrayListDesserts.add(itemDetails);
                        Log.e("VegItems",itemDetails.name);
                    }
                }
                myAdapterForDesserts.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ListView listView = v.findViewById(R.id.ListViewDessertsItems);
        myAdapterForDesserts= new MyAdapterForDesserts(getContext(),R.layout.recyclerview_items,itemDetailsArrayListDesserts);
        listView.setAdapter(myAdapterForDesserts);
        return v;
    }
    class MyAdapterForDesserts  extends ArrayAdapter<ItemDetails> {

        ArrayList<ItemDetails> itemDetailsArrayList;

        public MyAdapterForDesserts(@NonNull Context context, int resource, @NonNull ArrayList<ItemDetails> objects) {
            super(context, resource, objects);
            itemDetailsArrayList = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_items, parent, false);
            //Toast.makeText(getActivity(), "Hey inside myadapter class", Toast.LENGTH_SHORT).show();
            Ordernow=convertView.findViewById(R.id.Ordernow);
            Ordernow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    Fragment newFragment = new DessertsItemSelect();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Bundle bundle=new Bundle();

                    bundle.putString("ItemName",(itemDetailsArrayList.get(position).getName()));
                    bundle.putLong("ItemCost",(itemDetailsArrayList.get(position).getCost()));
                    newFragment.setArguments(bundle);
                    ft.replace(R.id.mainframe_frame,newFragment,"Cartitemss");
                    ft.commit();
                }
            });
            TextView ItemName = convertView.findViewById(R.id.ItemName);
            TextView ItemCost = convertView.findViewById(R.id.ItemCost);

            ItemName.setText(itemDetailsArrayListDesserts.get(position).getName());
            ItemCost.setText(itemDetailsArrayListDesserts.get(position).getCost().toString());
            return convertView;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }
    }

}

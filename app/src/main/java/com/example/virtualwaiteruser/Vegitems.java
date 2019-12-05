package com.example.virtualwaiteruser;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Vegitems extends Fragment {


    public Context context;
    ListView listView;
    Button Ordernow;
    String name;
    Long cost1;
    ProgressDialog progress;
    public ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
    MyAdapterForVegItems myAdapterForVegItems;

    public Vegitems() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       /* final ProgressDialog progressDialog;
        final RecyclerView recyclerView;
        final RecyclerView.Adapter[] adapter = new RecyclerView.Adapter[1];*/
        View v = inflater.inflate(R.layout.fragment_vegitems, container, false);
        progress=new ProgressDialog(getActivity());
        progress.setMessage("Quality Food Take Time"); // Setting Message
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
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("AdminVegItems");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemDetailsArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(dataSnapshot1.exists())
                    {
                        name=dataSnapshot1.child("ItemName").getValue(String.class);
                        cost1=(dataSnapshot1.child("cost").getValue(Long.class));
                        ItemDetails itemDetails=new ItemDetails(cost1,name);
                        itemDetailsArrayList.add(itemDetails);
                        Log.e("VegItems",itemDetails.name);
                    }
                }
                myAdapterForVegItems.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         listView= v.findViewById(R.id.ListViewVegItems);
        myAdapterForVegItems=new MyAdapterForVegItems(getContext(),R.layout.recyclerview_items,itemDetailsArrayList);
        listView.setAdapter(myAdapterForVegItems);
        return v;
    }
    class MyAdapterForVegItems extends ArrayAdapter<ItemDetails> {

        ArrayList<ItemDetails> itemDetailsArrayList;

        public MyAdapterForVegItems(@NonNull Context context, int resource, @NonNull ArrayList<ItemDetails> objects) {
            super(context, resource, objects);
            itemDetailsArrayList = objects;
            
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

              convertView = LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_items, parent, false);
            //Toast.makeText(getActivity(), "Hey inside myadapter class", Toast.LENGTH_SHORT).show();
            final TextView ItemName = convertView.findViewById(R.id.ItemName);
            final TextView ItemCost = convertView.findViewById(R.id.ItemCost);
           Ordernow=convertView.findViewById(R.id.Ordernow);
            Ordernow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    Fragment newFragment = new Vegitemselect();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Bundle bundle=new Bundle();

                    bundle.putString("ItemName",(itemDetailsArrayList.get(position).getName()));
                    bundle.putLong("ItemCost",(itemDetailsArrayList.get(position).getCost()));
                    newFragment.setArguments(bundle);
                    ft.replace(R.id.mainframe_frame,newFragment,"Cartitemss");
                    ft.commit();
                }
            });
            ItemName.setText(itemDetailsArrayList.get(position).getName());
            ItemCost.setText(itemDetailsArrayList.get(position).getCost().toString());
            return convertView;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }
    }

}





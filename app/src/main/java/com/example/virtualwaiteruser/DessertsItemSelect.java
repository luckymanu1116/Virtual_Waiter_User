package com.example.virtualwaiteruser;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class DessertsItemSelect extends Fragment {

    TextView title,price,qnt;
    ImageView plus,minus,back;
    Long originalprice,org;
    int quant=1;
    Button Addtocart;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String uid;
    public DessertsItemSelect() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_desserts_item_select, container, false);
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        title=view.findViewById(R.id.title);
        minus=view.findViewById(R.id.btn_minus);
        Addtocart=view.findViewById(R.id.btn_order);
        Addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                store();
                Fragment newFragment = new FinalCart();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainframe_frame,newFragment,"Cart");
                ft.addToBackStack("Cart");
                ft.commit();

            }
        });
        price=view.findViewById(R.id.price);
        qnt=view.findViewById(R.id.qnt);
        String size=String.valueOf(quant);
        back=view.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment newFragment = new Desserts();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainframe_frame,newFragment);
                ft.addToBackStack("cartItems");
                ft.commit();
            }
        });

        qnt.setText(size);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                originalprice=Long.valueOf((price.getText()).toString());
                if(originalprice>org)
                {
                    Long n=Long.valueOf((price.getText()).toString());
                    Long finalamount=n-org;
                    price.setText(finalamount.toString());
                    --quant;
                    String fin=String.valueOf(quant);
                   // Toast.makeText(getActivity(), "Inside minus", Toast.LENGTH_SHORT).show();
                    qnt.setText(fin);

                }

            }
        });
        plus=view.findViewById(R.id.btn_add);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long n=Long.valueOf(price.getText().toString());
                Long finale=n+org;
                price.setText(finale.toString());
                ++quant;
                String fin=String.valueOf(quant);
                originalprice=finale;
                qnt.setText(fin);

            }
        });
        Bundle bundle=getArguments();
        title.setText(String.valueOf(bundle.getString("ItemName")));
        price.setText(String.valueOf(bundle.getLong("ItemCost")));
        org=((bundle.getLong("ItemCost")));
        return view;
    }
    private void store()
    {

        ItemDataModel singleItem=new ItemDataModel(title.getText().toString(),Long.valueOf(price.getText().toString()),qnt.getText().toString(),AftersignUp.tablenum.getText().toString());
        databaseReference= FirebaseDatabase.getInstance().getReference(uid);
        databaseReference.child(title.getText().toString()).setValue(singleItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getActivity(),"Item Added to Cart",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getActivity(), "Unable to insert", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

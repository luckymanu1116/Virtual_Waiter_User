package com.example.virtualwaiteruser;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
ImageButton veg,nonveg,starters,desserts;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_home, container, false);
        veg=v.findViewById(R.id.imageButton3);
        nonveg=v.findViewById(R.id.imageButton2);
        starters=v.findViewById(R.id.imageButton);
        desserts=v.findViewById(R.id.imageButton4);
        desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desserts();
            }
        });
        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegitems();
            }
        });
        starters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starters();
            }
        });
        nonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nonveg();
            }
        });
        return v;
    }

    private void desserts()
    {
        Fragment newFragment = new Desserts();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe_frame,newFragment);
        ft.addToBackStack("cartItems");
        ft.commit();

    }

    private void starters()
    {
        Fragment newFragment = new StarterItems();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe_frame,newFragment);
        ft.addToBackStack("cartItems");
        ft.commit();
    }

    private void nonveg()
    {
        Fragment newFragment = new NonVegItems();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe_frame,newFragment);
        ft.addToBackStack("cartItems");
        ft.commit();
    }

    public void vegitems()
    {
        Fragment newFragment = new Vegitems();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe_frame,newFragment);
        ft.addToBackStack("cartItems");
        ft.commit();
    }

}

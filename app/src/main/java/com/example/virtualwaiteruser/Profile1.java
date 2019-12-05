package com.example.virtualwaiteruser;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile1 extends Fragment {

    private ProfileViewModel profileViewModel;
    private FirebaseAuth mAuth;
    private ImageView avatar;
    private View signout,contactus;
    private TextView name, email;
    public Profile1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile1, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        avatar = getActivity().findViewById(R.id.avatar);
        name = getActivity().findViewById(R.id.fullname);
        email = getActivity().findViewById(R.id.email);
        signout = getActivity().findViewById(R.id.signout);
        contactus=getActivity().findViewById(R.id.contactus);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9915237774"));
                startActivity(intent);
            }
        });

        name.setText(currentUser.getDisplayName());
        email.setText(currentUser.getEmail());


        Glide.with(getActivity()).load(currentUser.getPhotoUrl()).into(avatar);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(getContext(), Signin.class);
                getActivity().finish();
                startActivity(login);
            }
        });
    }

    private void pin(String s) {
        Log.d("PRFX", s);
    }

}

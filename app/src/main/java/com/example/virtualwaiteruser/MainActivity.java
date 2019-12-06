package com.example.virtualwaiteruser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;





public class MainActivity extends AppCompatActivity implements PaymentStatusListener {
    Button button;
    Fragment newFragment;
    FragmentTransaction ft;

    public  static  Activity activity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         newFragment = new Home();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe_frame,newFragment);
        ft.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.Home:
                        newFragment = new Home();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.mainframe_frame,newFragment);
                        ft.commit();
                        break;
                    case R.id.Previousorders:
                        newFragment = new PreviousOrders();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.mainframe_frame,newFragment);
                        ft.commit();
                        //Toast.makeText(MainActivity.this, "printing", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Profiel:
                        newFragment = new Profile1();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.mainframe_frame,newFragment);
                        ft.commit();
                        break;
                    case R.id.Cart:
                        newFragment = new FinalCart();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.mainframe_frame,newFragment);
                        ft.commit();
                        break;
                    default:
                        newFragment= new Home();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.mainframe_frame,newFragment);
                        ft.commit();
                        break;

                }
                return true;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }




    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails)
    {

    }

    @Override
    public void onTransactionSuccess()
    {
        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTransactionSubmitted() {

    }

    @Override
    public void onTransactionFailed() {

    }

    @Override
    public void onTransactionCancelled() {

    }

}


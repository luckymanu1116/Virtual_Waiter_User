package com.example.virtualwaiteruser;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EventListener;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinalCart extends Fragment {


      Long cost1,total,nv;
    final int UPI_PAYMENT = 0;
    String name,qnt,v;
    String payeeAddress = "9915237774@ybl";
    String payeeName = "Virtual Waiter";
    String transactionNote = "Today's Order:";
    String amount;
    TextView totals;
    String currencyUnit = "INR";
    Button paynow,cash;
    public ArrayList<FinalCartDetails> ArraylstFnc = new ArrayList<>();
    FinalCart.MyAdapterFc myAdapterFc;
    FirebaseUser user;
    String uid;
    DatabaseReference ref;

    FinalCartDetails finalCartDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_final_cart, container, false);
        paynow=v.findViewById(R.id.payment);
        cash=v.findViewById(R.id.Cash);
        Checkout.preload(getActivity().getApplicationContext());
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        total=0l;
        ref = FirebaseDatabase.getInstance().getReference();
        totals=v.findViewById(R.id.costfinal);
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArraylstFnc.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.exists()) {
                            name = dataSnapshot1.child("ItemName").getValue(String.class);
                            cost1 = (dataSnapshot1.child("cost").getValue(Long.class));
                            qnt = (dataSnapshot1.child("quant").getValue(String.class));
                            finalCartDetails = new FinalCartDetails(name, qnt, cost1);
                            paynow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatabaseReference copyto = FirebaseDatabase.getInstance().getReference("Orders");
                                    DatabaseReference copyfrom = FirebaseDatabase.getInstance().getReference(uid);
                                    copy(copyfrom, copyto);
                                    BuyNowPage();
                                    Toast.makeText(getActivity(), "Your Order Receieved ....We will Serve you shortly", Toast.LENGTH_LONG).show();
                                    /*easyUpiPayment.startPayment();
                                    Fragment newFragment = new Home();
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.mainframe_frame, newFragment);
                                    ft.addToBackStack("cartItems");
                                    ft.commit();*/
                                    FirebaseDatabase.getInstance().getReference(uid).setValue(null);
                                }
                            });

                            ArraylstFnc.add(finalCartDetails);

                            total = total + cost1;
                        }
                    }
                    nv = total;
                    myAdapterFc.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

       //Toast.makeText(getActivity(), ""+total, Toast.LENGTH_LONG).show();
       // totals.setText(nv.toString());
        ListView listView = v.findViewById(R.id.ListViewFinalCart);
        myAdapterFc=new MyAdapterFc(getActivity(),R.layout.recyclerview_items,ArraylstFnc);
        listView.setAdapter(myAdapterFc);
        return v;
    }
    private void BuyNowPage()
    {
        amount=myAdapterFc.ff;
            Uri uri = Uri.parse("upi://pay?pa="+payeeAddress+"&pn="+payeeName+"&tn="+transactionNote+
                    "&am="+amount+"&cu="+currencyUnit);
            Log.d("CompleteViewOfProduct", "onClick: uri: "+uri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivityForResult(intent,2);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if (requestCode == 1) {
                if (resultCode == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Payment Failed", Toast.LENGTH_LONG).show();
                } else if (resultCode == 1) {
                    Toast.makeText(getActivity(), "Payment success", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == 2) {
                Log.d("CompleteViewOfProduct", "onActivityResult: requestCode: " + requestCode);
                Log.d("CompleteViewOfProduct", "onActivityResult: resultCode: " + resultCode);
                //txnId=UPI20b6226edaef4c139ed7cc38710095a3&responseCode=00&ApprovalRefNo=null&Status=SUCCESS&txnRef=undefined
                //txnId=UPI608f070ee644467aa78d1ccf5c9ce39b&responseCode=ZM&ApprovalRefNo=null&Status=FAILURE&txnRef=undefined

                if (data != null) {
                    Log.d("CompleteViewOfProduct", "onActivityResult: data: " + data.getStringExtra("response"));
                    String res = data.getStringExtra("response");
                    String search = "SUCCESS";
                    if (res.toLowerCase().contains(search.toLowerCase()))
                    {
                        Fragment newFragment;
                        FragmentTransaction ft;
                        newFragment=new PreviousOrders();
                        ft =getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.mainframe_frame,newFragment);
                        ft.commit();
                        //Toast.makeText(getActivity(), "Payment Successful", Toast.LENGTH_SHORT).show();

                    } else
                        {

                        Toast.makeText(getActivity(), "Payment Failed", Toast.LENGTH_SHORT).show();
                        Log.e("CompleteViewOfProduct", "Payment failed");

                    }
                }
            }
        }
    }




    private void copy(DatabaseReference copyfrom, final DatabaseReference copyto)
    {
        copyfrom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                copyto.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
                    {
                        if(databaseError!=null)
                        {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class MyAdapterFc extends ArrayAdapter<FinalCartDetails>
    {
        String ff;
        public MyAdapterFc(@NonNull Context context, int resource, @NonNull ArrayList<FinalCartDetails> objects)
        {
            super(context, resource, objects);
            ArraylstFnc=objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {

            convertView=LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_finalcart, parent, false);
            TextView ItemName = convertView.findViewById(R.id.ItemName);
            final TextView ItemCost = convertView.findViewById(R.id.ItemCost);
            TextView qnt=convertView.findViewById(R.id.Quantity);
            Button del=convertView.findViewById(R.id.deleteitem);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String delitemname=ArraylstFnc.get(position).getName();
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference(uid);
                    reference.child(delitemname).setValue(null);
                    Long delcost=ArraylstFnc.get(position).getCost();
                    total=total-delcost;
                    Fragment fragment=getFragmentManager().findFragmentById(R.id.mainframe_frame);
                    getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                }
            });
            ItemName.setText(ArraylstFnc.get(position).getName());
            ItemCost.setText(ArraylstFnc.get(position).getCost().toString());
            qnt.setText(ArraylstFnc.get(position).getQuant());
            totals.setText(nv.toString());
             v=ItemName.getText().toString();
            cash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    ArraylstFnc.clear();
                    DatabaseReference copyto = FirebaseDatabase.getInstance().getReference("Orders");
                    DatabaseReference copyfrom = FirebaseDatabase.getInstance().getReference(uid);
                    copy(copyfrom,copyto);
                    Fragment newFragment;
                    FragmentTransaction ft;
                    newFragment=new PreviousOrders();
                    ft =getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.mainframe_frame,newFragment);
                    ft.commit();

                    Toast.makeText(getActivity(), "Please Wait We will Serve you here..!", Toast.LENGTH_LONG).show();

                }
            });

            /*Activity activity=getActivity();
            easyUpiPayment.setPaymentStatusListener(activity);*/
           DecimalFormat decimalFormat=new DecimalFormat("#.00");
             ff=decimalFormat.format(nv);
            /*String tid=String.valueOf(transid);
            String rfd=String.valueOf(refid);
             easyUpiPayment = new EasyUpiPayment.Builder()
                    .with(getActivity())
                    .setPayeeVpa(upiid)
                    .setPayeeName("Virtual Waiter")
                    .setTransactionId(tid).setTransactionRefId(rfd)
                    .setDescription("For Today's Food")
                    .setAmount(ff)
                    .build();*/


            return convertView;
        }


        public int getCount() {
            return super.getCount();
        }
    }


}

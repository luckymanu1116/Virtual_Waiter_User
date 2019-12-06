package com.example.virtualwaiteruser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AftersignUp extends AppCompatActivity {

   static TextView tablenum;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersign_up);
        tablenum=findViewById(R.id.tablenum);
        submit=findViewById(R.id.tbnumbtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int n= Integer.parseInt(tablenum.getText().toString());
                if(n<=10) {
                    Intent m = new Intent(AftersignUp.this, MainActivity.class);
                    startActivity(m);
                }
                else
                {
                    Toast.makeText(AftersignUp.this, "Please Enter Table number between 1-10", Toast.LENGTH_LONG).show();
                    Toast.makeText(AftersignUp.this, "Invalid Table Number", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.example.virtualwaiteruser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                Intent n=new Intent(AftersignUp.this,MainActivity.class);
                startActivity(n);

            }
        });
    }

}

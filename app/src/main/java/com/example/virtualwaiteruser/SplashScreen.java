package com.example.virtualwaiteruser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity
{
    ImageView imageView;
    Handler handler;
    int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView=findViewById(R.id.splashimg);
        Animation myanim= AnimationUtils.loadAnimation(this, R.anim.animation);
        imageView.startAnimation(myanim);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent n=new Intent(SplashScreen.this,Signin.class);
                startActivity(n);
                finish();
            }
        },SPLASH_TIME);
    }
}

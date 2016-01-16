package com.bitscanvas.thehytt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.bitscanvas.thehytt.activity.Login;
import com.bitscanvas.thehytt.activity.SignUp;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.auth_layout);
        getSupportActionBar().hide();
    }


    public void onLogin(View view) {

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

    public void onJoin(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}

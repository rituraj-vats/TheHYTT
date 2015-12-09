package com.bitscanvas.thehytt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitscanvas.thehytt.activity.HomeScreen;
import com.bitscanvas.thehytt.activity.SignUp;
import com.bitscanvas.thehytt.model.response.AuthResponse;
import com.bitscanvas.thehytt.utils.SharedPrefUtil;


public class MainActivity extends ActionBarActivity {
    private TextView txtErrorMsg;
    private EditText edtMailId;
    private EditText edtPassWord;
    private LinearLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        validateAuth();
        getSupportActionBar().hide();
        initialize();
    }

    private void initialize(){
        try {
            txtErrorMsg = (TextView)findViewById(R.id.txtErrorMsg);
            edtMailId = (EditText)findViewById(R.id.edtMailId);
            edtPassWord = (EditText)findViewById(R.id.edtPassWord);

            errorLayout = (LinearLayout)findViewById(R.id.errorLayout);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onSignIn(View view) {

       // if(isValided()){
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        //}

    }

    public void onSingUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void validateAuth(){
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(MainActivity.this);
        AuthResponse authResponse = (AuthResponse)sharedPrefUtil.get("userProfile", AuthResponse.class);
        if(authResponse == null){
            setContentView(R.layout.login_layout);
        }else{
            Intent intent = new Intent(this, HomeScreen.class);
            intent.putExtra("userProfile",authResponse);
            startActivity(intent);
        }
    }

    private boolean isValided(){
        boolean isValid = true;
        String mesg = getString(R.string.PleaseEnter);
        if(edtMailId.getText().toString()!=null && edtMailId.getText().toString().equalsIgnoreCase("")){
            errorLayout.setVisibility(View.VISIBLE);
            mesg = mesg+" email id";
            isValid = false;

        } if(edtMailId.getText().toString()!=null && !edtMailId.getText().toString().equalsIgnoreCase("")
                && !isValidEmail(edtMailId.getText().toString())){
            errorLayout.setVisibility(View.VISIBLE);

            mesg = mesg+" valid email id";
            isValid = false;

        } if(edtPassWord.getText().toString()!=null && !edtPassWord.getText().toString().equalsIgnoreCase("")){
            errorLayout.setVisibility(View.VISIBLE);
            if(!isValid){
                mesg = mesg+", password";
            }else{
                mesg = mesg+" password";
            }
            isValid = false;
        }else{
            if(isValid){
                errorLayout.setVisibility(View.GONE);
            }

        }

        if(!isValid){
            txtErrorMsg.setText(mesg+".");
        }
        return isValid;
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

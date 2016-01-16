package com.bitscanvas.thehytt.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.emuns.AuthChannel;
import com.bitscanvas.thehytt.model.request.SignUpRequest;
import com.bitscanvas.thehytt.service.RegistrationIntentService;
import com.bitscanvas.thehytt.utils.CommonUtil;
import com.bitscanvas.thehytt.utils.SharedPrefUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;

/**
 * Created by vatsritu on 18/09/15.
 */
public class SignUp extends Activity {

    private ImageView profileImage;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText mobileNo;
    private EditText password;
    private TextView errorDispay;


    //keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    final int GALLERY = 3;
    //keep track of cropping intent
    final int PIC_CROP = 2;
    //captured picture uri
    private Uri picUri;

    private SignUpRequest signUpRequest;
    private String imageUri = null;
    String message = null;
    private static final String TAG = "SignUp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up_new);
       /* checkPlayServices();
        loadView();
        loadSignUpRequest();*/
    }

    public void onReturn(View view) {
        finish();
    }
    public void loadView(){
        profileImage = (ImageView)findViewById(R.id.imgProfilePic);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        email = (EditText)findViewById(R.id.emailId);
        mobileNo = (EditText)findViewById(R.id.mobileNo);
        password = (EditText)findViewById(R.id.password);
        errorDispay = (TextView)findViewById(R.id.errorDisplay);
    }


    public void loadSignUpRequest(){
        Intent intent = getIntent();
        signUpRequest = (SignUpRequest)intent.getSerializableExtra("signUpForm");
        if(signUpRequest == null){
            signUpRequest = new SignUpRequest();
            prepareRegistration();
        }else{
            prepareRegistration();
        }
    }

    public void prepareRegistration(){

        if (signUpRequest.getFirstName() != null)
            firstName.setText(signUpRequest.getFirstName());
        if (signUpRequest.getLastName() != null) lastName.setText(signUpRequest.getLastName());
        if (signUpRequest.getEmail() != null) email.setText(signUpRequest.getEmail());
        if (signUpRequest.getAuthChannel() == null)
            signUpRequest.setAuthChannel(AuthChannel.CUSTOM);
        signUpRequest.setMobNum(CommonUtil.getMobileNumber(this));
        mobileNo.setText(signUpRequest.getMobNum());
    }

    public void loadRegistration(){
        signUpRequest.setEmail(email.getText().toString());
        signUpRequest.setAuthChannel(AuthChannel.CUSTOM);
        signUpRequest.setFirstName(firstName.getText().toString());
        signUpRequest.setLastName(lastName.getText().toString());
        signUpRequest.setMobNum(mobileNo.getText().toString());
        signUpRequest.setPassword(password.getText().toString());
        signUpRequest.setImageUri("https://www.google.co.in/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0CAcQjRxqFQoTCL-Ww8fC38gCFUTmpgodgg4H5A&url=https%3A%2F%2Fplus.google.com%2Fcommunities%2F109298116487001221618&psig=AFQjCNH687kssXEOO0MLQ7s9N68tlBVMKA&ust=1445927670291420");
    }

    public boolean validateSignUpForm(){
        boolean isValid = true;
        if(CommonUtil.isEmpty(signUpRequest.getEmail()))return isValid=false;
        if(CommonUtil.isEmpty(signUpRequest.getFirstName()))return isValid=false;
        if(CommonUtil.isEmpty(signUpRequest.getLastName()))return isValid=false;
        if(CommonUtil.isEmpty(signUpRequest.getMobNum()))return isValid=false;
        if(CommonUtil.isEmpty(signUpRequest.getPassword()))return isValid=false;
        return isValid;
    }


    public void onSubmit(View v){
        loadRegistration();
        boolean isValid = validateSignUpForm();
        if(isValid) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            intent.putExtra("signUpForm",signUpRequest);
            startService(intent);
            //ProgressDialog.show(this,"","Loading...");
        }else{
            message ="Please enter required details.";
            displayMessage(message);
        }
    }


    public void onCameraClick(View v){

        Image_Picker_Dialog();

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //user is returning from capturing an image using the camera
            if(requestCode == CAMERA_CAPTURE){
                picUri = data.getData();
                Bundle extras = data.getExtras();
                Bitmap thePic = extras.getParcelable("data");
                thePic = CommonUtil.getCircleBitmap(thePic);
                profileImage.setImageBitmap(thePic);
                imageUri = CommonUtil.saveImage(thePic, firstName.getText().toString());
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(SignUp.this);
                sharedPrefUtil.saveSring("profilePic",imageUri);
            }else if(requestCode == GALLERY){
                Uri selectedImage = data.getData();
                Bitmap thePic = null;
                try {
                    thePic = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                thePic = CommonUtil.getCircleBitmap(thePic);
                profileImage.setImageBitmap(thePic);
                imageUri = CommonUtil.saveImage(thePic, firstName.getText().toString());
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(SignUp.this);
                sharedPrefUtil.saveSring("profilePic",imageUri);
            }
        }
    }
    public void Image_Picker_Dialog()
    {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Pictures Option");
        myAlertDialog.setMessage("Select Picture Mode");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , GALLERY);
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            }
        });
        myAlertDialog.show();

    }

    private void performCrop(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 9000)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    public void displayMessage(String message){
        errorDispay.setText(message);
        errorDispay.setVisibility(View.VISIBLE);
    }
}

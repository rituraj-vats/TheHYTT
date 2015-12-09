package com.bitscanvas.thehytt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitscanvas.thehytt.activity.SignUp;
import com.bitscanvas.thehytt.emuns.AuthChannel;
import com.bitscanvas.thehytt.model.request.SignUpRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;


/**
 * A placeholder fragment containing a facebook button view.
 * @author vatsritu
 *
 */
public class FacebookFragment extends Fragment {

    private CallbackManager callbackManager;
    private TextView textView;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    /*need to create a new wrapper dto for facebook response*/
    private String facebookEmail=null;

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            final Profile profile = Profile.getCurrentProfile();
            /*caliing the graph request for getting the email after login*/
            GraphRequest.newMeRequest(
                    loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject me, GraphResponse response) {
                            if (response.getError() != null) {
                                // handle error
                            } else {
                                facebookEmail = me.optString("email");
                                setTargetLayout(profile);
                            }
                        }
                    }).executeAsync();

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public FacebookFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                setTargetLayout(newProfile);
            }
        };

        //make the api call to get the user email and other info
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.facebook_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("user_status","email"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * this method redirect the user to desinated screen
     * @param profile
     */
    private void setTargetLayout(Profile profile){
        if(profile != null){
            SignUpRequest signUpRequest = prepareSignUpForm(profile);
            Intent intent = new Intent(getActivity(), SignUp.class);
            intent.putExtra("signUpForm",signUpRequest);
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();

        Profile profile = Profile.getCurrentProfile();
        setTargetLayout(profile);
    }

    public SignUpRequest prepareSignUpForm(Profile profile){
        SignUpRequest signUpRequest = new SignUpRequest();
        if(profile.getFirstName() != null)signUpRequest.setFirstName(profile.getFirstName());
        if(profile.getLastName() != null)signUpRequest.setLastName(profile.getLastName());
        if(profile.getProfilePictureUri(64,64) !=null)signUpRequest.setUri(profile.getProfilePictureUri(64, 64));
        if(facebookEmail != null)signUpRequest.setEmail(facebookEmail);
        signUpRequest.setAuthChannel(AuthChannel.FACEBOOK);
        return signUpRequest;
    }
}

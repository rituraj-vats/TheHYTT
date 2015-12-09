package com.bitscanvas.thehytt.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.bitscanvas.thehytt.MenuDrawerFragment;
import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.adapters.HopLeftRightAdapter;
import com.bitscanvas.thehytt.adapters.PostRightLeftAdapter;
import com.bitscanvas.thehytt.constants.AppConstant;
import com.bitscanvas.thehytt.dtos.HopDto;
import com.bitscanvas.thehytt.dtos.PostDto;
import com.bitscanvas.thehytt.model.request.HopRequest;
import com.bitscanvas.thehytt.model.request.PostRequest;
import com.bitscanvas.thehytt.model.request.RideSearchRequest;
import com.bitscanvas.thehytt.model.response.AuthResponse;
import com.bitscanvas.thehytt.model.response.RideSearchResponse;
import com.bitscanvas.thehytt.utils.CommonUtil;
import com.bitscanvas.thehytt.utils.GoogleMapUtil;
import com.bitscanvas.thehytt.utils.HttpConnectionUtil;
import com.bitscanvas.thehytt.utils.JSONConvertor;
import com.bitscanvas.thehytt.utils.SharedPrefUtil;
import com.bitscanvas.thehytt.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author  vatsritu
 */
public class HomeScreen extends FragmentActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,MenuDrawerFragment.FragmentDrawerListener{

    public static final String TAG = "HomeScreen";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final LatLngBounds BOUNDS_BANGALORE = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(12.972442, 12.972442));

    GoogleMap googleMap;
    LinearLayout destinationLayout;
    RelativeLayout mapViewLayout;

    ImageView drawer_menu;
    TextView srcLocation;
    private Button btnTimeValue;
    private EditText edtPrice;
    private String quantity;
    private TextView errorDispay;


    private PlaceAutocompleteAdapter mAutoCompleteAdapter;
    private AutoCompleteTextView mAutocompleteView;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Circle circle;
    Location mCurrentLocation;
    String mLastUpdateTime;

    //slider variable
    ImageView imgRightLeftSlider;
    SlidingDrawer slidingDrawer;
    int mRightSliderOpen = 0;
    private ListView home_Screen_list_view_right;

    ImageView imgRightLeftSliderLR;
    SlidingDrawer slidingDrawerLR;
    int mRightSliderOpenLR = 0;
    private ListView home_Screen_list_view_rightLR;
    RideSearchResponse rideSearchResponse;
    AuthResponse userProfile;


    private Toolbar mToolbar;
    private MenuDrawerFragment drawerFragment;
    private DrawerLayout drawerMenu;
    private View menuDrawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createCurrentLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_layout);
        initialize();
    }


    public void initialize() {
        initializeFields();
        initalizeForAutoCompleteDestination();
        createMapView();
        //loadProfileImage();

    }

    public void initializeForPool(){
        initializeFields();
        createMapView();
        mapViewLayout.setVisibility(View.VISIBLE);
        destinationLayout.setVisibility(View.GONE);
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
        String request = sharedPrefUtil.get("searchRequest");
        searchCoRiders(request);

    }

    public void initializeFields(){
        destinationLayout = (LinearLayout)findViewById(R.id.layoutDestination);
        mapViewLayout = (RelativeLayout)findViewById(R.id.layoutMapView);
        btnTimeValue = (Button)findViewById(R.id.btnTimeValue);
        edtPrice = (EditText)findViewById(R.id.edtPrice);

        drawer_menu = (ImageView) findViewById(R.id.drawer_menu);
        drawerMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuDrawerView = (View)findViewById(R.id.fragment_navigation_drawer);

        imgRightLeftSlider = (ImageView)findViewById(R.id.imgRightLeftSlider);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
        home_Screen_list_view_right = (ListView)findViewById(R.id.home_Screen_list_view_right);

        /*int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
        SlidingDrawer.LayoutParams params = (android.widget.SlidingDrawer.LayoutParams) slidingDrawer.getLayoutParams();
        params.width = width;
        home_Screen_list_view_right.setLayoutParams(params);*/

        imgRightLeftSliderLR = (ImageView)findViewById(R.id.imgRightLeftSliderLR);
        slidingDrawerLR = (SlidingDrawer) findViewById(R.id.slidingDrawerLR);
        home_Screen_list_view_rightLR = (ListView)findViewById(R.id.home_Screen_list_view_rightLR);

        /*int widthLR = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
        SlidingDrawer.LayoutParams paramsLR = (android.widget.SlidingDrawer.LayoutParams) slidingDrawerLR.getLayoutParams();
        paramsLR.width = widthLR;
        home_Screen_list_view_rightLR.setLayoutParams(paramsLR);*/

        errorDispay = (TextView)findViewById(R.id.errorDisplay);
        spinnerQuantityView();
        Intent intent = getIntent();
        userProfile = (AuthResponse)intent.getSerializableExtra("userProfile");
        Log.d("HomeScreen","HomeScreen Display");


    }



    public void createMapView(){
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        googleMap = supportMapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setIndoorEnabled(true);
        googleMap.setTrafficEnabled(false);
        googleMap.setBuildingsEnabled(true);
        CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(12.9466526, 77.6228252));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        // Instantiates a new CircleOptions object and defines the center and radius
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(12.9466526, 77.6228252))
                .radius(5000).fillColor(Color.TRANSPARENT).strokeColor(Color.BLUE)
                .strokeWidth(5);

        // Get back the mutable Circle
        circle = googleMap.addCircle(circleOptions);

    }

    public void updateMapOnLocationChange(Location location){
        if(null != googleMap){
            googleMap.clear();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Source Location").draggable(true));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            googleMap.animateCamera(cameraUpdate);
            circle.setCenter(latLng);
        }
    }

    public void initalizeForAutoCompleteDestination(){
        mAutocompleteView = (AutoCompleteTextView)findViewById(R.id.edtDestination);
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mAutoCompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_BANGALORE,
                null);
        mAutocompleteView.setAdapter(mAutoCompleteAdapter);
    }

    public void setSourceLocation(Location location){
        srcLocation = (TextView)findViewById(R.id.srcLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        GoogleMapUtil googleMapUtil = new GoogleMapUtil(HomeScreen.this);
        String address = googleMapUtil.getAddressLine(latitude,longitude);
        srcLocation.setText(address);
    }


    protected void createCurrentLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
        if (!Utils.isGPRSON && isGPRSOff()) {
            customDialog();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        setSourceLocation(location);
        updateMapOnLocationChange(location);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

        Log.d(TAG, "Location update started ..............: ");
    }


    /*code for spinner*/
    public void spinnerQuantityView() {
        final String strTimeValues[] = {"15 min","30 min", "45 min", "1 hour"};
        btnTimeValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final CharSequence[] items = new String[strTimeValues.length];
                int selected = 0;
                for (int i = 0; i < strTimeValues.length; i++) {
                    items[i] = strTimeValues[i];
                    if (strTimeValues[i].equals(btnTimeValue.getText().toString())) {
                        selected = i;
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);

                builder.setTitle("Travelling Time");
                builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        btnTimeValue.setText(items[item]);
                        quantity = strTimeValues[item];
                        dialog.cancel();
                    }

                });

                //builder.show();
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    /* code for autocomplete destination*/
   private AdapterView.OnItemClickListener mAutocompleteClickListener
           = new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
           final AutocompletePrediction item = mAutoCompleteAdapter.getItem(position);
           final String placeId = item.getPlaceId();
           final CharSequence primaryText = item.getPrimaryText(null);

           Log.i(TAG, "Autocomplete item selected: " + primaryText);



           Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                   Toast.LENGTH_SHORT).show();
           Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
           PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                   .getPlaceById(mGoogleApiClient, placeId);
           placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

           Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                   Toast.LENGTH_SHORT).show();
           Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
       }
   };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            /*mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }*/

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

/*prepare for map view*/

    public void onSearchRides(View v){

        if(!validateSearchRequestForm()) {
            hideSoftKeyboard();
        String destinationAddress = mAutocompleteView.getText().toString().trim();
        LatLng latLng = new GoogleMapUtil(this).getLocationFromAddress(destinationAddress);
        String time = btnTimeValue.getText().toString().trim();
        Long travelTime = new Long(15*60*1000);
        RideSearchRequest rideSearchRequest = new RideSearchRequest();
        rideSearchRequest.setSrcLat(mCurrentLocation.getLatitude());
        rideSearchRequest.setSrcLng(mCurrentLocation.getLongitude());
        rideSearchRequest.setDestLat(latLng.latitude);
        rideSearchRequest.setDestLng(latLng.longitude);
        rideSearchRequest.setTravelTime(travelTime);
        rideSearchRequest.setPps(Integer.parseInt(edtPrice.getText().toString()));

            String request = JSONConvertor.objectToJson(rideSearchRequest);
            searchCoRiders(request);

            mapViewLayout.setVisibility(View.VISIBLE);
            destinationLayout.setVisibility(View.GONE);

        }else{
            errorDispay.setVisibility(View.VISIBLE);
        }
    }


    /*validating the search form*/
    public boolean validateSearchRequestForm(){
        boolean isError = false;
        if(mAutocompleteView == null && CommonUtil.isEmpty(mAutocompleteView.getText().toString().trim())){
            errorDispay.setText("Please enter valid destination..");
            isError = true;
        }else if(mAutocompleteView != null){
            String destinationAddress = mAutocompleteView.getText().toString().trim();
            LatLng latLng = new GoogleMapUtil(this).getLocationFromAddress(destinationAddress);
            if(latLng == null){
                errorDispay.setText("Please enter valid destination..");
                isError = true;
            }
        } else if(CommonUtil.isEmpty(edtPrice.getText().toString())){
            errorDispay.setText("Please enter the price.");
            isError = true;
        }

        return isError;
    }
    /**
     * add the hop markers
     */

    private void addHopMarkers(){
        /** Make sure that the map has been initialised **/
        if(null != googleMap && rideSearchResponse.getHopList() != null) {
            for(HopDto hopDto: rideSearchResponse.getHopList()){
                LatLng latLng = new LatLng(hopDto.getSrcLat(), hopDto.getSrcLng());
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Hop").draggable(false));
            }
        }
    }

    /**
     * Add the post markers
     */
    private void addPostMarkers(){
        /** Make sure that the map has been initialised **/
        if(null != googleMap && rideSearchResponse.getPostList() != null){
            for(PostDto postDto: rideSearchResponse.getPostList()){
                LatLng latLng = new LatLng(postDto.getSrcLat(), postDto.getSrcLng());
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Post").draggable(false));
            }
        }
    }

    //slider code
    private void sliderRightToLeft(){

        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {

            @Override
            public void onDrawerOpened() {
                Log.d("Drawer","openDrawerRL");
                mRightSliderOpen = 1;
                imgRightLeftSlider.setImageResource(R.mipmap.hop_icon);
                slidingDrawerLR.setVisibility(View.GONE);
                imgRightLeftSliderLR.setVisibility(View.GONE);
            }
        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            public void onDrawerClosed() {
                Log.d("Drawer","closed RL");
                imgRightLeftSliderLR.setVisibility(View.VISIBLE);
                slidingDrawerLR.setVisibility(View.VISIBLE);
                mRightSliderOpen = 0;
                imgRightLeftSlider.setImageResource(R.mipmap.hop_icon);

            }
        });

        setPostAdapter();
    }
    private void sliderLeftToRight(){

        slidingDrawerLR.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {

            @Override
            public void onDrawerOpened() {
                Log.d("Drawer","openDrawerLR");
                imgRightLeftSlider.setVisibility(View.GONE);
                slidingDrawer.setVisibility(View.GONE);
                mRightSliderOpenLR = 1;
                imgRightLeftSliderLR.setImageResource(R.mipmap.post_icon);
            }
        });

        slidingDrawerLR.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            public void onDrawerClosed() {
                Log.d("Drawer","closeDrawerLR");
                imgRightLeftSlider.setVisibility(View.VISIBLE);
                slidingDrawer.setVisibility(View.VISIBLE);
                mRightSliderOpenLR = 0;
                imgRightLeftSliderLR.setImageResource(R.mipmap.post_icon);

            }
        });

        setHopAdapter();
    }

    private void setPostAdapter() {

        home_Screen_list_view_right.setAdapter(new PostRightLeftAdapter(this, R.layout.post_right_left_layout, rideSearchResponse.getPostList()));
        home_Screen_list_view_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostDto postDto = (PostDto) parent.getItemAtPosition(position);
                //todo remove the data from list
                Intent intent = new Intent(HomeScreen.this, JourneyDetails.class);
                intent.putExtra("postDto", postDto);
                startActivity(intent);
            }
        });
    }
    private void setHopAdapter() {
        home_Screen_list_view_rightLR.setAdapter(new HopLeftRightAdapter(this, R.layout.post_right_left_layout, rideSearchResponse.getHopList()));
        home_Screen_list_view_rightLR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HopDto hopDto = (HopDto) parent.getItemAtPosition(position);
                //todo remove the data from list
                Intent intent = new Intent(HomeScreen.this, JourneyDetails.class);
                intent.putExtra("hopDto", hopDto);
                startActivity(intent);
            }
        });
    }


    /*user action on the slider*/

    /*server calls*/
    public void searchCoRiders(String request){
        SearchRides task = new SearchRides();
        task.execute(new String[]{AppConstant.BASE_URI + AppConstant.SEARCH_RIDES, request});
    }

    /*drawer menu code*/
    public void openDrawerMenu(View v){
         //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        /*setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        drawerFragment = (MenuDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawerMenu);
        drawerFragment.setDrawerListener(this);

    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    /**
     * Implementation of AsyncTask, to fetch the data in the background away from
     * the UI thread.
     */
    private class SearchRides extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpConnectionUtil httpConnectionUtil = new HttpConnectionUtil();
                return httpConnectionUtil.loadFromNetwork(urls[0], urls[1]);
            } catch (IOException e) {
                Log.i(TAG, "Error while search rides: " + e.getMessage());
                return "";
            }
        }

        /**
         * Once the doInBackground method is complete this will be called
         * Based on the response take appropriate action
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                rideSearchResponse = objectMapper.readValue(result, RideSearchResponse.class);
                sliderRightToLeft();
                sliderLeftToRight();
                addHopMarkers();
                addPostMarkers();

            } catch (IOException e) {
                Log.e(TAG, "Error while searching rides: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /*user operations*/

    public void onHopClick(View v){
        String destinationAddress = mAutocompleteView.getText().toString().trim();;

        LatLng latLng = new GoogleMapUtil(this).getLocationFromAddress(destinationAddress);
        HopRequest hopRequest = new HopRequest();
        hopRequest.setUserId(userProfile.getUserId());
       // hopRequest.setUserId("200");
        hopRequest.setCscId("test");
        hopRequest.setDestAddress(destinationAddress);
        hopRequest.setDestLat(latLng.latitude);
        hopRequest.setDestLng(latLng.longitude);
        hopRequest.setDestPlaceId("test");
        hopRequest.setSrcAddress(srcLocation.getText().toString());
        hopRequest.setSrcLat(mCurrentLocation.getLatitude());
        hopRequest.setSrcLng(mCurrentLocation.getLongitude());
        hopRequest.setSrcPlaceId("test");
        hopRequest.setTravelTime(new Long(15 * 60 * 1000));
        hopRequest.setPps(Integer.parseInt(edtPrice.getText().toString()));
        Intent intent = new Intent(HomeScreen.this,HopRequestActivity.class);
        intent.putExtra("hopRequest",hopRequest);
        startActivity(intent);

    }

    public void onPostClick(View v){
        String destinationAddress = mAutocompleteView.getText().toString().trim();;

        LatLng latLng = new GoogleMapUtil(this).getLocationFromAddress(destinationAddress);
        PostRequest postRequest = new PostRequest();
        postRequest.setUserId(userProfile.getUserId());
        //postRequest.setUserId("100");
        postRequest.setCscId("test");
        postRequest.setDestAddress(destinationAddress);
        postRequest.setDestLat(latLng.latitude);
        postRequest.setDestLng(latLng.longitude);
        postRequest.setDestPlaceId("test");
        postRequest.setSrcAddress(srcLocation.getText().toString());
        postRequest.setSrcLat(mCurrentLocation.getLatitude());
        postRequest.setSrcLng(mCurrentLocation.getLongitude());
        postRequest.setSrcPlaceId("test");
        postRequest.setTravelTime(new Long(15 * 60 * 1000));
        postRequest.setPps(Integer.parseInt(edtPrice.getText().toString()));
        Intent intent = new Intent(HomeScreen.this,PostRequestActivity.class);
        intent.putExtra("postRequest", postRequest);
        startActivity(intent);
    }

    private boolean isGPRSOff() {
        boolean isGPSEnabled = false;
        boolean isNetworkEnabled = false;
        boolean isResult = false;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            isResult = true;
        } else {
            isResult = false;
        }
        return isResult;
    }


    /**
     * Create custom dialog
     */
    private void customDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // retrieve display dimensions
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        // inflate and adjust layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_alert_dialog, null);
        layout.setMinimumWidth((int) (displayRectangle.width() * 0.75f));
        Utils.isGPRSON = true;
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.show();
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                Utils.isGPRSON = true;
                dialog.dismiss();

            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    // dialog.dismiss();
                }
                return true;
            }
        });
    }


    public void loadProfileImage() {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
        String imageUri = sharedPrefUtil.get("profilePic");
        ImageView profileImage = (ImageView)findViewById(R.id.imgProfilePic);
        if(CommonUtil.isEmpty(imageUri)){
            Picasso.with(this)
                    .load(imageUri)
                    .into(profileImage);
        }else{
            Bitmap image = CommonUtil.getCircleBitmap(CommonUtil.loadImageFromStorage(imageUri));
            profileImage.setImageBitmap(image);
        }


    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if(drawerMenu!=null && drawerMenu.isDrawerOpen(menuDrawerView)){
                drawerMenu.closeDrawer(menuDrawerView);

            }else if(slidingDrawer.isOpened()){
                slidingDrawer.close();
            }else if(slidingDrawerLR.isOpened()){
                slidingDrawerLR.close();
            }else{
                finish();
            }

        }
        return false;
    }

}
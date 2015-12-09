package com.bitscanvas.thehytt.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by THEHYTT on 27/09/15.
 */
public class GoogleMapUtil {

    private Context context;

    public GoogleMapUtil(Context context){
        this.context=context;
    }

    public String getAddressLine(double lat,double lon) {
        String addressLine = null;
        List<Address> addresses = getGeocoderAddress(lat,lon);
        if (addresses != null) {
            try {
                try {
                    addressLine = addresses.get(0).getAddressLine(0) + "," + addresses.get(0).getAddressLine(1) + ","
                            + addresses.get(0).getAddressLine(2)+","+addresses.get(0).getAddressLine(3);
                } catch (Exception e) {
                    addressLine = addresses.get(0).getAddressLine(0) + "," + addresses.get(0).getAddressLine(1) + ","
                            + addresses.get(0).getAddressLine(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return addressLine;
        } else {
            return null;
        }
    }

    List<Address> getGeocoderAddress(double lat,double lon) {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(lat,lon, 1);
            return addresses;
        } catch (IOException e) {
            return null;
        }

    }

    public LatLng getLocationFromAddress(String strAddress) {

        List<Address> geocodeMatches = null;
        LatLng l1=null;

        try {
            geocodeMatches =
                    new Geocoder(context).getFromLocationName(
                            strAddress, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!geocodeMatches.isEmpty())
        {
            l1=new LatLng(geocodeMatches.get(0).getLatitude(),geocodeMatches.get(0).getLongitude());
        }

        return l1;
    }


}

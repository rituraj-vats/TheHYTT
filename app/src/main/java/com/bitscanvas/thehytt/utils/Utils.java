package com.bitscanvas.thehytt.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bitscanvas.thehytt.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;



public class Utils {
    public Context context;
    public static boolean isGPRSON = false;

    public Utils(Context context) {
	// TODO Auto-generated constructor stub
	this.context = context;
    }



    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	// Raw height and width of image
	final int height = options.outHeight;
	final int width = options.outWidth;
	int inSampleSize = 1;

	if (height > reqHeight || width > reqWidth) {

	    final int halfHeight = height / 2;
	    final int halfWidth = width / 2;

	    // Calculate the largest inSampleSize value that is a power of 2 and
	    // keeps both
	    // height and width larger than the requested height and width.
	    while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
		inSampleSize *= 2;
	    }
	}

	return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

	// First decode with inJustDecodeBounds=true to check dimensions
	final BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	BitmapFactory.decodeResource(res, resId, options);

	// Calculate inSampleSize
	options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	// Decode bitmap with inSampleSize set
	options.inJustDecodeBounds = false;
	return BitmapFactory.decodeResource(res, resId, options);
    }

    public static String getDeviceIMEI(Context context) {
	String deviceIMEI = "";
	try {
	    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	    deviceIMEI = telephonyManager.getDeviceId();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return deviceIMEI;
    }

    public static int getDeviceApiLevel() {
	return android.os.Build.VERSION.SDK_INT;
    }

    public static String getDeviceManufacture() {
	return android.os.Build.MANUFACTURER;
    }

    public static String getDeviceModel() {
	return android.os.Build.MODEL;
    }

    public static String getDeviceISO(Context context) {
	TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	return telephonyManager.getSimCountryIso();
    }


    public static boolean isNetworkAvailable(Context context) {
	boolean isConnectedWifi = false;
	boolean isConnectedMobile = false;

	ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	for (NetworkInfo ni : netInfo) {
	    if (ni.getTypeName().equals(ni.getTypeName()))
		if (ni.isConnected())
		    isConnectedWifi = true;
	    if (ni.getTypeName().equalsIgnoreCase(ni.getTypeName()))
		if (ni.isConnected())
		    isConnectedMobile = true;
	}
	return isConnectedWifi || isConnectedMobile;
    }


    public static final String createMD5AsToken(final String s) {
	final String MD5 = "MD5";
	try {
	    // Create MD5 Hash
	    MessageDigest digest = MessageDigest.getInstance(MD5);
	    digest.update(s.getBytes());
	    byte messageDigest[] = digest.digest();

	    // Create Hex String
	    StringBuilder hexString = new StringBuilder();
	    for (byte aMessageDigest : messageDigest) {
		String h = Integer.toHexString(0xFF & aMessageDigest);
		while (h.length() < 2)
		    h = "0" + h;
		hexString.append(h);
	    }
	    return hexString.toString();

	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	return "";
    }


    public static String readFileFromSdCard() {
	File sdcard = Environment.getExternalStorageDirectory();
	StringBuilder text = new StringBuilder();
	try {
	    File file = new File(sdcard, "med_perf_properties.txt");
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String line;
	    while ((line = br.readLine()) != null) {
		text.append(line);
	    }
	    br.close();
	} catch (IOException e) {
	    return "0";
	} catch (Exception e) {
	    return "0";
	}
	return text.toString();

    }

    public static void routeDirection(Context context, double currentLatitude, double currentLongitude, double destLatitude, double destLongitude) {
	try {
	    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?   saddr=" + currentLatitude + ","
		    + currentLongitude + "&daddr=" + destLatitude + "," + destLongitude));
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.addCategory(Intent.CATEGORY_LAUNCHER);
	    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
	    context.startActivity(intent);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /* display date in format */
    public static String getDate(String createdDate) {
	SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

	SimpleDateFormat desiredFormat = new SimpleDateFormat("dd'%s' MMM yyyy");
	try {
	    Date date = isoFormat.parse(createdDate);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    createdDate = String.format(desiredFormat.format(date), dateSuffix(calendar));
	} catch (java.text.ParseException e) {
	    e.printStackTrace();
	}
	return createdDate;
    }

    // add suffix for date
    public static String dateSuffix(final Calendar cal) {
	final int date = cal.get(Calendar.DATE);
	switch (date % 10) {
	case 1:
	    if (date != 11) {
		return "st";
	    }
	    break;

	case 2:
	    if (date != 12) {
		return "nd";
	    }
	    break;

	case 3:
	    if (date != 13) {
		return "rd";
	    }
	    break;
	}
	return "th";
    }

    /* convert bitmap to base64 string */
    public static String convertBase64(Bitmap bitmap) {
	// Bitmap bm = uploadPrescription.getDrawingCache();
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
	byte[] b = baos.toByteArray();
	return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * Create custom dialog
     */
    public static void customDialog(final Context context, String strTitle, String strMassag,final Integer type) {
	final Dialog dialog = new Dialog(context);

	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View layout = inflater.inflate(R.layout.custom_alert_dialog, null);
	dialog.setContentView(layout);
	dialog.setCancelable(false);
	WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
	//wmlp.gravity = Gravity.TOP | Gravity.LEFT;
	wmlp.x = 60;
	wmlp.y = 60;
	dialog.show();
	((TextView) dialog.findViewById(R.id.textDialog)).setText(strTitle);
	((TextView) dialog.findViewById(R.id.textDialogMsg)).setText(strMassag);
	Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
		btnOk.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
            if(type ==1){
                isGPRSON = true;
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
			dialog.dismiss();
		}
	});
		Button btnCancel = (Button) dialog.findViewById(R.id.btnOk);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
    }




    public static void writeFileToSdCard(String fname, String fcontent) {
	try {
	    String fpath = "/sdcard/" + fname + ".txt";
	    File file = new File(fpath);
	    // If file does not exists, then create it
	    if (!file.exists()) {
		file.createNewFile();
	    }
	    FileWriter fw = new FileWriter(file.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(fcontent);
	    bw.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}

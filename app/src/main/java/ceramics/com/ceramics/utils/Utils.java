package ceramics.com.ceramics.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.model.ApplicationDataModel;
import ceramics.com.ceramics.model.UserLocationData;

/**
 * Created by vikrantg on 22-06-2016.
 */
public class Utils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static String formateTime() {

        Calendar cal = Calendar.getInstance();
        String date = "";
        String time = "";
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

        try {

            date = fromUser.format(cal.getTime());
            time = timeFormat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String timeStamp = date+"T"+"00:00:00+"+time;
        return timeStamp;

    }

    public static boolean isNotBlank(String value) {

        if (value == null || TextUtils.isEmpty(value) || value.equalsIgnoreCase("null")) {
            return false;
        }
        return true;
    }

    public static String getUserLocation(BaseActivity activity){
        ApplicationPreferenceData preferenceData = ApplicationPreferenceData.getInstance(activity);
        return preferenceData.getApplicationData().getUserLocationData().getName();
    }

    public static void setUserLocation(BaseActivity activity,UserLocationData userLocation) {
        ApplicationPreferenceData preferenceData = ApplicationPreferenceData.getInstance(activity);
        ApplicationDataModel applicationDataModel = preferenceData.getApplicationData();
        applicationDataModel.setUserLocationData(userLocation);
        preferenceData.setApplicationData(applicationDataModel);
    }

    @Nullable
    public static String getLocationFromLatLon(BaseActivity activity, double latitude, double longitude) {
        List<Address> addresses = null;
        try {
            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5    if()

        } catch (Exception e) {
            activity.showToast("Sorry! Unable to fetch your location.");
        }
        if (addresses != null && addresses.size() > 0)
            return addresses.get(0).getLocality();
        else
            return null;
    }
}

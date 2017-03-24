package ceramics.com.ceramics.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.adapter.LocationListAdapter;
import ceramics.com.ceramics.custom.ActionBarListener;
import ceramics.com.ceramics.custom.CustomActionBar;
import ceramics.com.ceramics.fragments.DashboardFragment;
import ceramics.com.ceramics.fragments.FloorFragment;
import ceramics.com.ceramics.fragments.ProductTypeListFragment;
import ceramics.com.ceramics.fragments.ReferenceCodeDialogFragment;
import ceramics.com.ceramics.fragments.WallFragment;
import ceramics.com.ceramics.model.ApplicationDataModel;
import ceramics.com.ceramics.model.UserLocationData;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;
import ceramics.com.ceramics.utils.ApplicationPreferenceData;
import ceramics.com.ceramics.utils.GPSTracker;

public class HomeActivity extends BaseActivity implements ActionBarListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private CustomActionBar actionBar;
    private ListView lvLocation;
    private DrawerLayout drawer_parent;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Fragment wallFragment, floorFragment, productTypeFragment, dashboardFragment;
    private ReferenceCodeDialogFragment referCodefragment;
    private boolean isHome = true;
    private ArrayList<UserLocationData> locationDataList;
    private LocationListAdapter locationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        ApplicationPreferenceData preferenceData = ApplicationPreferenceData.getInstance(this);
        if (preferenceData.getApplicationData().getUserLocationData() == null){
            callGetCitiesAPI();
        }
        else {
            openDashboardFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        actionBar = (CustomActionBar) findViewById(R.id.action_bar);
        drawer_parent = (DrawerLayout) findViewById(R.id.drawer_parent);
        lvLocation = (ListView)findViewById(R.id.list_location);
        actionBar.setActionBarListner(this);

        lvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setUserLocation(locationListAdapter.getItem(position));
            }
        });
    }

    public void openDashboardFragment() {
        isHome = true;
        if (dashboardFragment == null) {
            dashboardFragment = new DashboardFragment();
        }
        loadFragment(dashboardFragment, R.id.base_layout, false);
        hideSlidingMenu();
    }

    public void openReferCodeFragment() {
        if (referCodefragment == null) {
            referCodefragment = new ReferenceCodeDialogFragment();
        }
        referCodefragment.show(getFragmentManager(), "");
        hideSlidingMenu();
    }

    public void openProductTypeListFragment() {
        isHome = true;
        if (productTypeFragment == null) {
            productTypeFragment = new ProductTypeListFragment();
        }
        loadFragment(productTypeFragment, R.id.base_layout, false);
        hideSlidingMenu();
    }

    public void openWallFragment() {
        if (wallFragment == null) {
            wallFragment = new WallFragment();
        }
        isHome = false;
        loadFragment(wallFragment, R.id.base_layout, false);
        hideSlidingMenu();
    }

    public void openFloorFragment() {
        if (floorFragment == null) {
            floorFragment = new FloorFragment();
        }
        isHome = false;
        loadFragment(floorFragment, R.id.base_layout, false);
        hideSlidingMenu();
    }

    private void callGetCitiesAPI(){
        try{
            Type responseModelType = new TypeToken<CommonJsonArrayModel<UserLocationData>>() {
            }.getType();
            GetCities getCities = new GetCities();
            APIRequestHelper.getCities(responseModelType,new JSONObject(),getCities,getCities,this);
        }
        catch (Exception e){
            showToast(getString(R.string.error));
        }
    }

    private boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0 && grantResults[1] == 0) {
            checkGPS();
        } else {
           /*TODO: call location list API*/
            showToast("fetch location list from API and show directly");
            updateRegion(true);
        }
    }

    public void hideSlidingMenu() {
        drawer_parent.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setActionBarTitle(title);
    }

    @Override
    public void showBackOption(boolean flag) {
        actionBar.showBackOption(flag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*try {
            FragmentManager fm = getSupportFragmentManager();
            if (fm != null && fm.getBackStackEntryCount() <= 0) {
               if (!isHome){
                   openProductTypeListFragment();
               }
               else
                   super.onBackPressed();
            }
            else
                super.onBackPressed();
        } catch ( Exception e){
            e.printStackTrace();
        }*/
    }

    @Override
    public void onBackIconPressed() {
        onBackPressed();
    }

    @Override
    public void onMenuIconPressed() {
        drawer_parent.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        showToast("Failed to get your location");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showToast("Failed to get your location");
    }

    public void checkGPS() {

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1001);
            }


            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this).build();
            }

            googleApiClient.connect();
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(5 * 1000);
            locationRequest.setFastestInterval(1 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            //**************************
            builder.setAlwaysShow(true); //this is the key ingredient
            //**************************

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            startLocationUpdates();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                            try {
                                status.startResolutionForResult(HomeActivity.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            // Ignore the error.
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == -1){
                updateRegion(false);
            }
            else {
                /*TODO: call location API and show location in list*/
                updateRegion(true);
            }
        }
    }

    private void updateRegion(boolean showList){
        if (showList){
            showLocationList(true);
        }
        else {
            List<Address> addresses = getLocationFromLatLon();
            if (addresses != null){
                for (UserLocationData data : locationDataList){
                    if (data.getName().equalsIgnoreCase(addresses.get(0).getLocality())){
                        setUserLocation(data);
                    }
                }
            }
            else {
                showLocationList(true);
            }
        }
    }

    private void setUserLocation(UserLocationData userLocation){
        ApplicationPreferenceData preferenceData = ApplicationPreferenceData.getInstance(this);
        ApplicationDataModel applicationDataModel = preferenceData.getApplicationData();
        applicationDataModel.setUserLocationData(userLocation);
        preferenceData.setApplicationData(applicationDataModel);
        showToast("Hello! You are in"+userLocation.getName());
        showLocationList(false);
        openDashboardFragment();
    }

    private void showLocationList(boolean flag){
        if (flag){
            lvLocation.setVisibility(View.VISIBLE);
            if (locationListAdapter == null){
                locationListAdapter = new LocationListAdapter(this,locationDataList);
            }
            lvLocation.setAdapter(locationListAdapter);
        }
        else {
            lvLocation.setVisibility(View.GONE);
        }
    }

    private List<Address> getLocationFromLatLon(){
        List<Address> addresses = null;
        double latitude = 0,longitude = 0;
        try {
            if (googleApiClient != null && googleApiClient.isConnected()) {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                GPSTracker gpsTracker = new GPSTracker(this);
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5    if()
            }


        } catch (Exception e) {
            showToast("Sorry! Unable to fetch your location. Please select your location from list");
        }

        return addresses;
    }

    protected void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //ActivityCompat.requestPermissions(activity,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1001);
                return;
            }
            if (googleApiClient != null && googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        googleApiClient, locationRequest, this);
            }
            updateRegion(false);
        } catch (Exception e) {
            e.getStackTrace();
        }


    }

    class GetCities implements Response.Listener<CommonJsonArrayModel<UserLocationData>>,Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError error) {
            showToast(getString(R.string.error));
        }

        @Override
        public void onResponse(CommonJsonArrayModel<UserLocationData> response) {
            try{
                if (response.isStatus()){
                    locationDataList = response.getData();
                    if (locationDataList != null && locationDataList.size() > 0){
                        checkLocationPermission();
                        checkGPS();
                    }
                    else {
                        showToast(getString(R.string.error));
                    }
                }
                else {
                    showToast(getString(R.string.error));
                }
            }
            catch (Exception e){
                showToast(getString(R.string.error));
            }
        }
    }
}

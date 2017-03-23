package ceramics.com.ceramics.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
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

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.custom.ActionBarListener;
import ceramics.com.ceramics.custom.CustomActionBar;
import ceramics.com.ceramics.fragments.DashboardFragment;
import ceramics.com.ceramics.fragments.FloorFragment;
import ceramics.com.ceramics.fragments.ProductTypeListFragment;
import ceramics.com.ceramics.fragments.ReferenceCodeDialogFragment;
import ceramics.com.ceramics.fragments.WallFragment;

public class HomeActivity extends BaseActivity implements ActionBarListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private CustomActionBar actionBar;
    private DrawerLayout drawer_parent;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Fragment wallFragment, floorFragment, productTypeFragment, dashboardFragment;
    private ReferenceCodeDialogFragment referCodefragment;
    private boolean isHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        openDashboardFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        actionBar = (CustomActionBar) findViewById(R.id.action_bar);
        drawer_parent = (DrawerLayout) findViewById(R.id.drawer_parent);
        actionBar.setActionBarListner(this);
        checkLocationPermission();
        checkGPS();
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
            showToast("feth location list from API and show directly");
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
        showToast("Connected");
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
        showToast("Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showToast("Failed");
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

        }
        else {

        }
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
}

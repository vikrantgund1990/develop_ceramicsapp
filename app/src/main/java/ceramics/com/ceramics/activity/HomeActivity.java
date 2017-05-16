package ceramics.com.ceramics.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.LinearLayout;
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
import ceramics.com.ceramics.fragments.ProductFragment;
import ceramics.com.ceramics.fragments.ReferenceCodeDialogFragment;
import ceramics.com.ceramics.fragments.WallFragment;
import ceramics.com.ceramics.fragments.WebViewFragment;
import ceramics.com.ceramics.model.ApplicationDataModel;
import ceramics.com.ceramics.model.UserLocationData;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;
import ceramics.com.ceramics.utils.ApplicationPreferenceData;
import ceramics.com.ceramics.utils.GPSTracker;

public class HomeActivity extends BaseActivity implements ActionBarListener {

    private CustomActionBar actionBar;
    private ListView lvLocation;
    private DrawerLayout drawer_parent;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Fragment wallFragment, floorFragment, productFragment, dashboardFragment,webFragment;
    private ReferenceCodeDialogFragment referCodefragment;
    private boolean isHome = true;
    double latitude = 0, longitude = 0;
    private ArrayList<UserLocationData> locationDataList;
    private LocationListAdapter locationListAdapter;
    private LinearLayout llProgress;
    private IGPSListner gpsListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        ApplicationPreferenceData preferenceData = ApplicationPreferenceData.getInstance(this);
        openDashboardFragment();
        /*Execute below code to get location from preferences*/
        /*if (preferenceData.getApplicationData().getUserLocationData() == null){
            callGetCitiesAPI();
        }
        else {
            openDashboardFragment();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        actionBar = (CustomActionBar) findViewById(R.id.action_bar);
        drawer_parent = (DrawerLayout) findViewById(R.id.drawer_parent);
        lvLocation = (ListView) findViewById(R.id.list_location);
        llProgress = (LinearLayout)findViewById(R.id.progress_layout);
        actionBar.setActionBarListner(this);
    }

    public void openDashboardFragment() {
        isHome = true;
        if (dashboardFragment == null) {
            dashboardFragment = new DashboardFragment();
        }
        loadFragment(dashboardFragment, R.id.base_layout, false);
        hideSlidingMenu();
    }

    public void openProductFragment(){
        if (productFragment == null) {
            productFragment = new ProductFragment();
        }
        loadFragment(productFragment, R.id.base_layout, true);
        hideSlidingMenu();
    }

    public void openWallFragment(){
        if (wallFragment == null) {
            wallFragment = new WallFragment();
        }
        loadFragment(wallFragment, R.id.base_layout, true);
        hideSlidingMenu();
    }

    public void openFloorFragment(){
        if (floorFragment == null) {
            floorFragment = new FloorFragment();
        }
        loadFragment(floorFragment, R.id.base_layout, true);
        hideSlidingMenu();
    }

    public void openReferCodeFragment() {
        if (referCodefragment == null) {
            referCodefragment = new ReferenceCodeDialogFragment();
        }
        referCodefragment.show(getFragmentManager(), "");
        hideSlidingMenu();
    }

    public void setGpsListner(IGPSListner gpsListner){
        this.gpsListner = gpsListner;
    }

    private void callGetCitiesAPI() {
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<UserLocationData>>() {
            }.getType();
            GetCities getCities = new GetCities();
            APIRequestHelper.getCities(responseModelType, new JSONObject(), getCities, getCities, this);
        } catch (Exception e) {
            showToast(getString(R.string.error));
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
    public void openWebFragment(String url) {
        if (webFragment == null) {
            webFragment = new WebViewFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString("URL",url);
        webFragment.setArguments(bundle);
        loadFragment(webFragment, R.id.base_layout, true);
        hideSlidingMenu();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (gpsListner != null)
            gpsListner.onGPSEnable(resultCode,requestCode);
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
                        /*checkLocationPermission();
                        checkGPS();*/
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

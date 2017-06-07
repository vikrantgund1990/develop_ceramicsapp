package ceramics.com.ceramics.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
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

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.activity.HomeActivity;
import ceramics.com.ceramics.activity.IGPSListner;
import ceramics.com.ceramics.activity.TileVisualizerActivity;
import ceramics.com.ceramics.adapter.ImageListAdapter;
import ceramics.com.ceramics.helper.GetProductListDataHelper;
import ceramics.com.ceramics.helper.ProductListDataListner;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.model.UserLocationData;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;
import ceramics.com.ceramics.utils.ApplicationPreferenceData;
import ceramics.com.ceramics.utils.Utils;

/**
 * Created by vikrantg on 16-03-2017.
 */

public class DashboardFragment extends BaseFragment implements View.OnClickListener,LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,IGPSListner,ProductListDataListner {

    private ImageListAdapter imageListAdapter;
    private LayoutInflater inflater;
    private SliderLayout sliderLayout;
    private ListView lvImages;
    private BaseActivity activity;
    private ArrayList<String> imageList;
    private ArrayList<String> productImageList;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private ProgressDialog progressDialog;
    private TextView tvTileVisualiser,tvTileCalculator,tvContactUs,tvAboutUs,tvEvents;
    private Fragment wallFragment,floorFragment,productByApplication;
    private TextView tvWall,tvFloor;
    private String imgageBaseURL = "http://images.ceramicskart.com/img/home/";
    private String prodctImgageBaseURL = "http://images.ceramicskart.com/application/";
    private final int BEDROOM = 1,LIVING_ROOM = 2,KITCHEN = 3,BATHROOM = 4,OFFICE = 5,OUTDOOR = 6;
    private final int LOCATION_REQUEST = 1100,GPS_REQUEST = 1000;
    private int selectedApplication = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (BaseActivity)getActivity();
        initView(getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setActionBarTitle(getString(R.string.app_name));
        activity.showBackOption(false);
    }

    private void initView(View view){
        inflater = LayoutInflater.from(getActivity());
        lvImages = (ListView)view.findViewById(R.id.image_list);
        tvWall = (TextView)view.findViewById(R.id.text_wall);
        tvFloor = (TextView)view.findViewById(R.id.floor_wall);
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.image_header_view,null);
        ViewGroup footer = (ViewGroup)inflater.inflate(R.layout.dashboard_footer,null);
        lvImages.addHeaderView(header);
        lvImages.addFooterView(footer);
        sliderLayout = (SliderLayout)header.findViewById(R.id.slider);
        tvTileVisualiser = (TextView)header.findViewById(R.id.text_visualizer);
        tvTileCalculator = (TextView)footer.findViewById(R.id.text_calculator);
        tvContactUs = (TextView)footer.findViewById(R.id.text_contact_us);
        tvAboutUs = (TextView)footer.findViewById(R.id.text_about_us);
        tvEvents = (TextView)footer.findViewById(R.id.text_events);

        tvTileVisualiser.setOnClickListener(this);
        tvTileCalculator.setOnClickListener(this);
        tvContactUs.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
        tvEvents.setOnClickListener(this);

        addItems();
        imageListAdapter = new ImageListAdapter(activity,productImageList);
        lvImages.setAdapter(imageListAdapter);
        initSlider();
        showPromotionCode();

        lvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkGPS();
                selectedApplication = position;
            }
        });

        if (activity instanceof HomeActivity){
            ((HomeActivity)activity).setGpsListner(this);
        }
    }

    private void initSlider(){
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    //    sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
    }

    private void showPromotionCode(){
        ApplicationPreferenceData preferenceData = ApplicationPreferenceData.getInstance(activity);
        if (preferenceData.getApplicationData().isReferCodeShow()){
            openReferCodeFragment();
        }
    }

    public void openReferCodeFragment() {
        ReferenceCodeDialogFragment referCodefragment = new ReferenceCodeDialogFragment();
        referCodefragment.show(activity.getFragmentManager(), "");
    }



    /*private void openProductByApplicationFragment(){
        if (productByApplication == null) {
            productByApplication = new ProductByApplicationListFragment();
        }
        activity.loadFragment(productByApplication, R.id.base_layout, true);
    }*/

    private void addItems(){
        imageList = new ArrayList<>();
        imageList.add("kitchen.jpg");
        imageList.add("Drawing.jpg");
        imageList.add("Bathroom.jpeg");
        addProductImages();

        for (int i = 0; i < imageList.size(); i++){
            TextSliderView textSliderView = new TextSliderView(activity);
            textSliderView
                    .image(imgageBaseURL+imageList.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());

            sliderLayout.addSlider(textSliderView);
        }
    }

    private void addProductImages(){
        productImageList = new ArrayList<>();

        productImageList.add(prodctImgageBaseURL+"bedroom.jpg");
        productImageList.add(prodctImgageBaseURL+"living_room.jpg");
        productImageList.add(prodctImgageBaseURL+"kitchen.jpg");
        productImageList.add(prodctImgageBaseURL+"bathroom.jpg");
        productImageList.add(prodctImgageBaseURL+"office.jpg");
        productImageList.add(prodctImgageBaseURL+"outdoor.jpg");

    }

    /*private void openProductByApplication(int application){
        switch (application){
            case BEDROOM:
                activity.showToast("Bed");
                break;
            case LIVING_ROOM:
                activity.showToast("Living");
                break;
            case KITCHEN:
                activity.showToast("Kitchen");
                break;
            case BATHROOM:
                break;
            case OFFICE:
                break;
            case OUTDOOR:
                break;
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_visualizer:
                activity.openTileVisualizer();
                break;
            case R.id.text_calculator:
                openTileCalculator();
                break;
            case R.id.text_contact_us:
                (activity).openWebFragment(getString(R.string.contact_us));
                break;
            case R.id.text_about_us:
                (activity).openWebFragment(getString(R.string.about_us));
                break;
            case R.id.text_events:

                break;
        }
    }

    private void openTileCalculator(){
        TileCalculatorFragment tileCalculatorFragment = new TileCalculatorFragment();
        tileCalculatorFragment.show(getFragmentManager(),"");
    }

    private void getApplicationProductDetails(int product) {
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetProductListDataHelper dataHelper = new GetProductListDataHelper(this);
            switch (product){
                case BEDROOM:
                    APIRequestHelper.bedroom(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
                    break;
                case LIVING_ROOM:
                    APIRequestHelper.livingroom(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
                    break;
                case KITCHEN:
                    APIRequestHelper.kitchen(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
                    break;
                case BATHROOM:
                    APIRequestHelper.bathroom(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
                    break;
                case OFFICE:
                    APIRequestHelper.office(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
                    break;
                case OUTDOOR:
                    APIRequestHelper.outdoor(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
                    break;
            }

        } catch (Exception e) {
            activity.showToast(e.getMessage());
        }

    }

    @Override
    public void onSuccess(ArrayList<ProductDetails> productDetailsArrayList) {
        if (productDetailsArrayList != null && productDetailsArrayList.size() > 0){
            openProductByApplicationListFragment(productDetailsArrayList);
        }
        else {
            activity.showToast(getString(R.string.product_not_available));
        }
    }

    private void openProductByApplicationListFragment(ArrayList<ProductDetails> list){
        ProductByApplicationListFragment fragment = new ProductByApplicationListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProductList",list);
        fragment.setArguments(bundle);
        activity.loadFragment(fragment,R.id.base_layout,true);
    }

    @Override
    public void onFailed(String errorMessage) {
        activity.showToast(getString(R.string.error));
    }

    public void checkGPS() {
        try {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                requestPermissions(new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST);
                return;
            }

            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(activity)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .build();
            }
            googleApiClient.connect();

            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
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
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                if (this != null) {
                                    status.startResolutionForResult(activity, GPS_REQUEST);
                                }
                            } catch (Exception e) {
                                activity.showToast(getString(R.string.error));
                            }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    checkGPS();
                }
                else {
                    activity.showToast("Sorry! We need your location to get proceed to products");
                }
        }
    }

    @SuppressLint("NewApi")
    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST);
            return;
        }
        else {

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            progressDialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading...");
            progressDialog.setContentView(R.layout.custome_progress_bar);
            if(progressDialog.getWindow()!=null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
            //        callGetCampaignStoreListAPI();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            progressDialog.dismiss();
            UserLocationData data = new UserLocationData();
            data.setName(Utils.getLocationFromLatLon(activity,location.getLatitude(),location.getLongitude()));
            Utils.setUserLocation(activity,data);
            getApplicationProductDetails(selectedApplication);
        }
    }

    @Override
    public void onGPSEnable(int resultCode, int requestCode) {
        switch (requestCode){
            case GPS_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        activity.showToast("Sorry! We need your location to get proceed to products");
                        break;
                    default:
                        break;
                }
                break;
        }
    }

}

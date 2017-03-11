package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;
import ceramics.com.ceramics.utils.Utils;

/**
 * Created by vikrantg on 25-02-2017.
 */

public class ProductTypeListFragment extends BaseFragment implements View.OnClickListener{

    private FrameLayout flKitchen,flBathroom,flLivingRoom,flBedroom,flOffice,flOutdoor;
    private BaseActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_type_list,null);
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
        activity.setActionBarTitle("Ceramics");
    }

    private void initView(View view){
        flKitchen = (FrameLayout)view.findViewById(R.id.kitchen);
        flBathroom = (FrameLayout)view.findViewById(R.id.bathroom);
        flLivingRoom = (FrameLayout)view.findViewById(R.id.living_room);
        flBedroom = (FrameLayout)view.findViewById(R.id.bedroom);
        flOffice = (FrameLayout)view.findViewById(R.id.office);
        flOutdoor = (FrameLayout)view.findViewById(R.id.outdoor);

        flKitchen.setOnClickListener(this);
        flLivingRoom.setOnClickListener(this);
        flBathroom.setOnClickListener(this);
        flBedroom.setOnClickListener(this);
        flOffice.setOnClickListener(this);
        flOutdoor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isNetworkConnected(activity)){
            switch (v.getId()){
                case R.id.kitchen:
                    getKitchen();
                    break;
                case R.id.living_room:
                    getLivingRoom();
                    break;
                case R.id.bathroom:
                    getBathroom();
                    break;
                case R.id.bedroom:
                    getBedroom();
                    break;
                case R.id.office:
                    getOffice();
                    break;
                case R.id.outdoor:
                    getOutdoor();
                    break;
            }
        }
        else {
            activity.showToast(getString(R.string.no_internet));
        }
    }

    private void getKitchen(){
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetKitchen kitchen = new GetKitchen();
            APIRequestHelper.kitchen(responseModelType, new JSONObject(), kitchen, kitchen, activity);
        }
        catch (Exception e){
            activity.showToast(e.getMessage());
        }
    }

    private void getBathroom(){
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetKitchen kitchen = new GetKitchen();
            APIRequestHelper.kitchen(responseModelType, new JSONObject(), kitchen, kitchen, activity);
        }
        catch (Exception e){
            activity.showToast(e.getMessage());
        }
    }

    private void getLivingRoom(){
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetKitchen kitchen = new GetKitchen();
            APIRequestHelper.livingroom(responseModelType, new JSONObject(), kitchen, kitchen, activity);
        }
        catch (Exception e){
            activity.showToast(e.getMessage());
        }
    }

    private void getBedroom(){
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetKitchen kitchen = new GetKitchen();
            APIRequestHelper.bedroom(responseModelType, new JSONObject(), kitchen, kitchen, activity);
        }
        catch (Exception e){
            activity.showToast(e.getMessage());
        }
    }

    private void getOffice(){
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetKitchen kitchen = new GetKitchen();
            APIRequestHelper.office(responseModelType, new JSONObject(), kitchen, kitchen, activity);
        }
        catch (Exception e){
            activity.showToast(e.getMessage());
        }
    }

    private void getOutdoor(){
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetKitchen kitchen = new GetKitchen();
            APIRequestHelper.outdoor(responseModelType, new JSONObject(), kitchen, kitchen, activity);
        }
        catch (Exception e){
            activity.showToast(e.getMessage());
        }
    }

    class GetKitchen implements Response.Listener<CommonJsonArrayModel<ProductDetails>>,Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError error) {
            activity.showToast(error.getMessage());
        }

        @Override
        public void onResponse(CommonJsonArrayModel<ProductDetails> response) {
            try{
                if (response.isStatus() == true){
                    if (response.getData() != null && response.getData().size() > 0){

                    }
                    else {
                        activity.showToast(getString(R.string.product_not_available));
                    }
                }
                else {
                    activity.showToast(response.getMessage());
                }
            }
            catch (Exception e){
                activity.showToast(e.getMessage());
            }
        }
    }
}

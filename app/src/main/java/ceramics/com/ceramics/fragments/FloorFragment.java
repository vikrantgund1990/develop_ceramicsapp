package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.adapter.ProductListGridAdapater;
import ceramics.com.ceramics.helper.GetProductListDataHelper;
import ceramics.com.ceramics.helper.ProductListDataListner;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;

/**
 * Created by vikrantg on 11-03-2017.
 */

public class FloorFragment extends BaseFragment implements ProductListDataListner {

    private BaseActivity activity;
    private GridView gvProductList;
    private ProductListGridAdapater productListGridAdapater;
    private ArrayList<ProductDetails> floorList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_floor,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (BaseActivity)getActivity();
        initView(getView());
        getWallTilesDetails();
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setActionBarTitle("Floor Tiles");
    }

    private void initView(View view){
        gvProductList = (GridView)view.findViewById(R.id.grid_wall);
    }

    private void setData(){
        productListGridAdapater = new ProductListGridAdapater(activity,floorList);
        gvProductList.setAdapter(productListGridAdapater);
    }

    private void getWallTilesDetails(){
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetProductListDataHelper dataHelper = new GetProductListDataHelper(this);
            APIRequestHelper.getFloor(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
        }
        catch (Exception e){
            activity.showToast(e.getMessage());
        }

    }

    @Override
    public void onSuccess(ArrayList<ProductDetails> productDetailsArrayList) {
        try{
            if (productDetailsArrayList != null && productDetailsArrayList.size() > 0){
                this.floorList = productDetailsArrayList;
                setData();
            }
            else {
                activity.showToast(getString(R.string.product_not_available));
            }
        }
        catch (Exception e){
            activity.showToast(getString(R.string.error));
        }
    }

    @Override
    public void onFailed(String errorMessage) {
        activity.showToast(errorMessage);
    }
}

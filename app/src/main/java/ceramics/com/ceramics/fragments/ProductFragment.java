package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.helper.GetProductListDataHelper;
import ceramics.com.ceramics.helper.ProductListDataListner;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;

/**
 * Created by Prakash on 5/14/2017.
 */

public class ProductFragment extends BaseFragment implements View.OnClickListener,ProductListDataListner {

    private BaseActivity activity;
    private TextView tvWall,tvFloor;
    private String title;
    private final int FLOOR = 1,WALL = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product,null);
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
        activity.setActionBarTitle("Products");
        activity.showBackOption(true);
    }

    private void initView(View v){
        tvFloor = (TextView)v.findViewById(R.id.text_floor);
        tvWall = (TextView)v.findViewById(R.id.text_wall);

        tvFloor.setOnClickListener(this);
        tvWall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_floor:
                title = "Floor";
                getTilesDetails(FLOOR);
                break;
            case R.id.text_wall:
                title = "Wall";
                getTilesDetails(WALL);
                break;
        }
    }

    private void getTilesDetails(int product) {
        try {
            Type responseModelType = new TypeToken<CommonJsonArrayModel<ProductDetails>>() {
            }.getType();
            GetProductListDataHelper dataHelper = new GetProductListDataHelper(this);
            switch (product){
                case FLOOR:
                    APIRequestHelper.getFloor(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
                    break;
                case WALL:
                    APIRequestHelper.getWall(responseModelType, new JSONObject(), dataHelper, dataHelper, activity);
                    break;
            }

        } catch (Exception e) {
            activity.showToast(e.getMessage());
        }

    }

    private void openProductListFragment(ArrayList<ProductDetails> list){
        ProductByApplicationListFragment fragment = new ProductByApplicationListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProductList",list);
        bundle.putString("Title",title);
        fragment.setArguments(bundle);
        activity.loadFragment(fragment,R.id.base_layout,true);
    }

    @Override
    public void onSuccess(ArrayList<ProductDetails> productDetailsArrayList) {
        if (productDetailsArrayList != null && productDetailsArrayList.size() > 0){
            openProductListFragment(productDetailsArrayList);
        }
        else {
            activity.showToast(getString(R.string.product_not_available));
        }
    }

    @Override
    public void onFailed(String errorMessage) {
        activity.showToast(getString(R.string.error));
    }
}

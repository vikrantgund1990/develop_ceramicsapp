package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.adapter.RetailerListAdapter;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.model.RetailersDetails;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;
import ceramics.com.ceramics.utils.AppConstants;
import ceramics.com.ceramics.utils.CeramicsApplication;

/**
 * Created by vikrantg on 11-03-2017.
 */

public class ProductDetailsFragment extends BaseFragment implements View.OnClickListener{

    private BaseActivity activity;
    private TextView tvProduct,tvSize,tvCost,tvFinishing,tvMaterial,tvCoverage,tvCalculator,
                     tvQuantity,tvClosed;
    private Button btnWhareToBuy;
    private NetworkImageView ivProductImage;
    private ListView lvRetailer;
    private LinearLayout llRetailer;
    private String imgageBaseURL = "http://images.ceramicskart.com/img/";
    private ImageLoader imageLoader;
    private ProductDetails productDetails;
    private ArrayList<RetailersDetails> retailerList;
    private RetailerListAdapter retailerListAdapter;
    private double coverage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (BaseActivity)getActivity();
        initView(getView());
    }

    private void initView(View view){
        tvProduct = (TextView)view.findViewById(R.id.product);
        tvSize = (TextView)view.findViewById(R.id.size);
        tvCost = (TextView)view.findViewById(R.id.cost);
        tvFinishing = (TextView)view.findViewById(R.id.finishing);
        tvMaterial = (TextView)view.findViewById(R.id.material);
        tvCoverage = (TextView)view.findViewById(R.id.coverage);
        ivProductImage = (NetworkImageView)view.findViewById(R.id.product_image);
        ivProductImage.setDefaultImageResId(R.mipmap.merchandise_stub_image);
        tvCalculator = (TextView)view.findViewById(R.id.text_calculator);
        tvQuantity = (TextView)view.findViewById(R.id.quantity);
        //tvClosed = (TextView)view.findViewById(R.id.list_closed);
        //llRetailer = (LinearLayout)view.findViewById(R.id.retailer_list_layout);
        lvRetailer = (ListView)view.findViewById(R.id.retailer_list);
        btnWhareToBuy = (Button) view.findViewById(R.id.purchase_button);

        ivProductImage.setOnClickListener(this);
        tvCalculator.setOnClickListener(this);
        //tvClosed.setOnClickListener(this);
        btnWhareToBuy.setOnClickListener(this);

        imageLoader = CeramicsApplication.getInstance().getImageLoader();
        productDetails = (ProductDetails) getArguments().getSerializable(AppConstants.PRODUCT_DETAILS);
        if (productDetails != null){
            ivProductImage.setImageUrl(imgageBaseURL+productDetails.getManufacturerProductId()+".jpg",imageLoader);
            tvProduct.setText(productDetails.getManufacturerProductId());
            tvSize.setText(productDetails.getWidthInMM()+" X "+productDetails.getLengthInMM());
            tvCost.setText(productDetails.getCost()+" INR");
            tvFinishing.setText(productDetails.getFinishType());
            tvMaterial.setText(productDetails.getLineOfBusiness());
            tvQuantity.setText(productDetails.getQtyPerBox()+"");
            double area = productDetails.getWidthInFT()*productDetails.getLengthInFT();
            coverage = area * productDetails.getQtyPerBox();
            tvCoverage.setText(area+"");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.product_image:
                openImageDialog();
                break;
            case R.id.text_calculator:
                openTileCalculator();
                break;
            case R.id.purchase_button:
                if ("Where to Buy?".equalsIgnoreCase(btnWhareToBuy.getText().toString())) {
                    getRetailerList();
                }
                else {
                    lvRetailer.setVisibility(View.INVISIBLE);
                    btnWhareToBuy.setText("Where to Buy?");
                }
                break;
        }
    }

    private void openTileCalculator(){
        TileCalculatorFragment tileCalculatorFragment = new TileCalculatorFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("Coverage",coverage);
        tileCalculatorFragment.setArguments(bundle);
        tileCalculatorFragment.show(getFragmentManager(),"");
    }

    private void openImageDialog(){
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("URL",imgageBaseURL+productDetails.getManufacturerProductId()+".jpg");
        webViewFragment.setArguments(bundle);
        activity.loadFragment(webViewFragment,R.id.base_layout, true);
    }

    private void getRetailerList(){
        if (retailerList == null) {
            Type type = new TypeToken<CommonJsonArrayModel<RetailersDetails>>() {
            }.getType();
            RetailersList retailersList = new RetailersList();
            APIRequestHelper.retailers(productDetails.getManufacturerProductId(), type, new JSONObject(), retailersList, retailersList, activity);
        }
        else {
            showRetailerList();
        }
    }

    private void showRetailerList(){
        if (retailerListAdapter == null){
            retailerListAdapter = new RetailerListAdapter(activity,retailerList);
            lvRetailer.setAdapter(retailerListAdapter);
        }
        lvRetailer.setVisibility(View.VISIBLE);
        btnWhareToBuy.setText("Okay");
    }

    class RetailersList implements Response.Listener<CommonJsonArrayModel<RetailersDetails>>,Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            activity.showToast(volleyError.getMessage());
        }

        @Override
        public void onResponse(CommonJsonArrayModel<RetailersDetails> response) {
            try{
                if (response.isStatus()){
                    retailerList = response.getData();
                    showRetailerList();
                }
                else {
                    activity.showToast(getString(R.string.error));
                }
            }
            catch (Exception e){
                activity.showToast(getString(R.string.error));
            }
        }
    }
}

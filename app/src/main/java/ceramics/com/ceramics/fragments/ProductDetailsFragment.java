package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.utils.AppConstants;
import ceramics.com.ceramics.utils.CeramicsApplication;

/**
 * Created by vikrantg on 11-03-2017.
 */

public class ProductDetailsFragment extends BaseFragment {

    private TextView tvProduct,tvSize,tvCost;
    private NetworkImageView ivProductImage;
    private String imgageBaseURL = "http://images.ceramicskart.com/img/";
    private ImageLoader imageLoader;
    private ProductDetails productDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
    }

    private void initView(View view){
        tvProduct = (TextView)view.findViewById(R.id.product);
        tvSize = (TextView)view.findViewById(R.id.size);
        tvCost = (TextView)view.findViewById(R.id.cost);
        ivProductImage = (NetworkImageView)view.findViewById(R.id.product_image);
        ivProductImage.setDefaultImageResId(R.mipmap.merchandise_stub_image);

        imageLoader = CeramicsApplication.getInstance().getImageLoader();
        productDetails = (ProductDetails) getArguments().getSerializable(AppConstants.PRODUCT_DETAILS);
        if (productDetails != null){
            ivProductImage.setImageUrl(imgageBaseURL+productDetails.getManufacturerProductId()+".jpg",imageLoader);
            tvProduct.setText(productDetails.getManufacturerProductId());
            tvSize.setText(productDetails.getWidthInCM()+" Cm");
            tvCost.setText(productDetails.getCost()+" INR");
        }
    }
}

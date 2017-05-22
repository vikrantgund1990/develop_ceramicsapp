package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
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
import ceramics.com.ceramics.adapter.ProductListGridAdapater;
import ceramics.com.ceramics.custom.ListPopupWindow;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;
import ceramics.com.ceramics.utils.CeramicsApplication;
import ceramics.com.ceramics.utils.Utils;

/**
 * Created by vikrantg on 25-02-2017.
 */

public class ProductByApplicationListFragment extends BaseFragment implements View.OnClickListener{

    private BaseActivity activity;
    private GridView gvProductList;
    private TextView tvSize;
    private ProductListGridAdapater productListGridAdapater;
    private ArrayList<ProductDetails> productList;
    private ArrayAdapter<String> sizeAdapter;
    private ListPopupWindow lpwSize;
    private ArrayList<String> sizeList;

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
        activity.setActionBarTitle("Product by Application");
        activity.showBackOption(true);
    }

    private void initView(View view){
        gvProductList = (GridView)view.findViewById(R.id.grid_products);
        tvSize = (TextView)view.findViewById(R.id.filter_size);
        Bundle bundle = getArguments();
        productList = (ArrayList<ProductDetails>) bundle.getSerializable("ProductList");
        productListGridAdapater = new ProductListGridAdapater(activity,productList);
        gvProductList.setAdapter(productListGridAdapater);

        sizeList = new ArrayList<>();
        sizeList.add("30 X 20");
        sizeList.add("10 X 30");
        sizeList.add("20 X 10");
        sizeList.add("40 X 50");

        sizeAdapter = new ArrayAdapter<String>(activity,R.layout.row_dropdown_list,sizeList);
        lpwSize = new ListPopupWindow(activity);
        lpwSize.setAdapter(sizeAdapter);
        lpwSize.setAnchorView(tvSize);
        lpwSize.setModal(true);
        lpwSize.setPromptPosition(ListPopupWindow.POSITION_PROMPT_BELOW);
        tvSize.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isNetworkConnected(activity)){
            switch (v.getId()){
                case R.id.filter_size:
                    lpwSize.show();
            }
        }
        else {
            activity.showToast(getString(R.string.no_internet));
        }
    }

}

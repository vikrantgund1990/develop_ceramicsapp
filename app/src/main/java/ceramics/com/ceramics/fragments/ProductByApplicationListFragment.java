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
import ceramics.com.ceramics.adapter.FilterAdapter;
import ceramics.com.ceramics.adapter.ProductListGridAdapater;
import ceramics.com.ceramics.custom.ListPopupWindow;
import ceramics.com.ceramics.helper.GetFilter;
import ceramics.com.ceramics.helper.IFilter;
import ceramics.com.ceramics.model.Filter;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonArrayModel;
import ceramics.com.ceramics.utils.AppConstants;
import ceramics.com.ceramics.utils.CeramicsApplication;
import ceramics.com.ceramics.utils.Utils;

/**
 * Created by vikrantg on 25-02-2017.
 */

public class ProductByApplicationListFragment extends BaseFragment implements View.OnClickListener,IFilter{

    private BaseActivity activity;
    private GridView gvProductList;
    private TextView tvSize,tvColor,tvType;
    private ProductListGridAdapater productListGridAdapater;
    private ArrayList<ProductDetails> productList;
    private ArrayAdapter<String> sizeAdapter;
    private ListPopupWindow lpwSize,lpwColor,lpwType;
    private ArrayList<Filter> sizeList,colorList,typeList;

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
        getFilter();
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
        tvColor = (TextView)view.findViewById(R.id.filter_color);
        tvType = (TextView)view.findViewById(R.id.filter_type);
        Bundle bundle = getArguments();
        productList = (ArrayList<ProductDetails>) bundle.getSerializable("ProductList");
        productListGridAdapater = new ProductListGridAdapater(activity,productList);
        gvProductList.setAdapter(productListGridAdapater);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isNetworkConnected(activity)){
            switch (v.getId()){
                case R.id.filter_size:
                    lpwSize.show();
                    break;
                case R.id.filter_color:
                    lpwColor.show();
                    break;
                case R.id.filter_type:
                    lpwType.show();
                    break;
            }
        }
        else {
            activity.showToast(getString(R.string.no_internet));
        }
    }

    private void getFilter(){
        Type responseModelType = new TypeToken<CommonJsonArrayModel<ArrayList<Filter>>>() {
        }.getType();
        GetFilter filter = new GetFilter(this);
        APIRequestHelper.getFilter(responseModelType,new JSONObject(),filter,filter,activity);
    }

    private void setUpFilter(ArrayList<ArrayList<Filter>> filterList){
        for (int i = 0; i < filterList.size(); i++){
            switch (filterList.get(i).get(0).getFilterCategoryId()){
                case AppConstants.COLOR:
                    colorFilter(filterList.get(i));
                    break;
                case AppConstants.SIZE:
                    sizeFilter(filterList.get(i));
                    break;
                case AppConstants.FINISHING_TYPE:
                    typeFilter(filterList.get(i));
                    break;
            }
        }
    }

    private void sizeFilter(ArrayList<Filter> sizeFilter){
        sizeList = sizeFilter;
        sizeAdapter = new ArrayAdapter<String>(activity,R.layout.row_dropdown_list,getList(sizeFilter));
        lpwSize = new ListPopupWindow(activity);
        lpwSize.setAdapter(sizeAdapter);
        lpwSize.setAnchorView(tvSize);
        lpwSize.setModal(true);
        lpwSize.setPromptPosition(ListPopupWindow.POSITION_PROMPT_BELOW);
        tvSize.setOnClickListener(this);
    }

    private void colorFilter(ArrayList<Filter> colorFilter){
        colorList = colorFilter;
        sizeAdapter = new ArrayAdapter<String>(activity,R.layout.row_dropdown_list,getList(colorFilter));
        lpwColor = new ListPopupWindow(activity);
        lpwColor.setAdapter(sizeAdapter);
        lpwColor.setAnchorView(tvColor);
        lpwColor.setModal(true);
        lpwColor.setPromptPosition(ListPopupWindow.POSITION_PROMPT_BELOW);
        tvColor.setOnClickListener(this);
    }

    private void typeFilter(ArrayList<Filter> typeFilter){
        typeList = typeFilter;
        sizeAdapter = new ArrayAdapter<String>(activity,R.layout.row_dropdown_list,getList(typeFilter));
        lpwType = new ListPopupWindow(activity);
        lpwType.setAdapter(sizeAdapter);
        lpwType.setAnchorView(tvType);
        lpwType.setModal(true);
        lpwType.setPromptPosition(ListPopupWindow.POSITION_PROMPT_BELOW);
        tvType.setOnClickListener(this);
    }

    private ArrayList<String> getList(ArrayList<Filter> filters){
        ArrayList<String> list = new ArrayList<>();
        for (Filter filter : filters){
            list.add(filter.getFilterName());
        }

        return list;
    }

    @Override
    public void onSuccess(ArrayList<ArrayList<Filter>> filterList) {
        setUpFilter(filterList);
    }

    @Override
    public void onFailed(String msg) {
        activity.showToast(msg);
    }
}

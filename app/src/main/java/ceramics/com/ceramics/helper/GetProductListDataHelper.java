package ceramics.com.ceramics.helper;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.network.CommonJsonArrayModel;

/**
 * Created by vikrantg on 11-03-2017.
 */

public class GetProductListDataHelper implements Response.Listener<CommonJsonArrayModel<ProductDetails>>,Response.ErrorListener {

    private ProductListDataListner listner;

    public GetProductListDataHelper(ProductListDataListner listner){
        this.listner = listner;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        listner.onFailed(error.getMessage());
    }

    @Override
    public void onResponse(CommonJsonArrayModel<ProductDetails> response) {
           if (response.isStatus()){
               listner.onSuccess(response.getData());
           }
           else {
                listner.onFailed(response.getMessage());
           }

    }
}

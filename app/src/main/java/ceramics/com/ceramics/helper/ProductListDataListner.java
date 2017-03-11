package ceramics.com.ceramics.helper;

import java.util.ArrayList;

import ceramics.com.ceramics.model.ProductDetails;

/**
 * Created by vikrantg on 11-03-2017.
 */

public interface ProductListDataListner {
    public void onSuccess(ArrayList<ProductDetails> productDetailsArrayList);
    public void onFailed(String errorMessage);
}

package ceramics.com.ceramics.filter;

import java.util.ArrayList;

import ceramics.com.ceramics.model.ProductDetails;

/**
 * Created by vikrantg on 18-06-2017.
 */

public interface Criteria {

    public ArrayList<ProductDetails> meetCriteria(ArrayList<ProductDetails> productList);
}

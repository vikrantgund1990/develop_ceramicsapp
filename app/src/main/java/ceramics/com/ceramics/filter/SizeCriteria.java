package ceramics.com.ceramics.filter;

import java.util.ArrayList;

import ceramics.com.ceramics.model.ProductDetails;

/**
 * Created by vikrantg on 18-06-2017.
 */

public class SizeCriteria implements Criteria {

    private String size;

    public SizeCriteria(String size){
        this.size = size;
    }

    @Override
    public ArrayList<ProductDetails> meetCriteria(ArrayList<ProductDetails> productList) {
        ArrayList<ProductDetails> list = new ArrayList<>();
        if (!"Size".equalsIgnoreCase(size)) {
            for (ProductDetails details : productList) {
            }
        }
        else {
            list.addAll(productList);
        }
        return list;
    }
}

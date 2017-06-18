package ceramics.com.ceramics.filter;

import java.util.ArrayList;

import ceramics.com.ceramics.model.ProductDetails;

/**
 * Created by vikrantg on 18-06-2017.
 */

public class FinishingTypeCriteria implements Criteria {

    private String finishingType;

    public FinishingTypeCriteria(String finishingType){
        this.finishingType = finishingType;
    }

    @Override
    public ArrayList<ProductDetails> meetCriteria(ArrayList<ProductDetails> productList) {
        ArrayList<ProductDetails> list = new ArrayList<>();
        if (!"Type".equalsIgnoreCase(finishingType)) {
            for (ProductDetails details : productList) {
                if (details.getFinishType().equalsIgnoreCase(finishingType)) {
                    list.add(details);
                }
            }
        }
        else {
            list.addAll(productList);
        }
        return list;
    }
}

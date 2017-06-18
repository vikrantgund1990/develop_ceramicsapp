package ceramics.com.ceramics.filter;

import java.util.ArrayList;

import ceramics.com.ceramics.model.ProductDetails;

/**
 * Created by vikrantg on 18-06-2017.
 */

public class ColorCriteria implements Criteria {

    private String color;

    public ColorCriteria(String color){
        this.color = color;
    }

    @Override
    public ArrayList<ProductDetails> meetCriteria(ArrayList<ProductDetails> productList) {
        ArrayList<ProductDetails> list = new ArrayList<>();
        if (!"Color".equalsIgnoreCase(color)) {
            for (ProductDetails details : productList) {
                if (details.getProductColour().getColourName().equalsIgnoreCase(color)) {
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

package ceramics.com.ceramics.filter;

import java.util.ArrayList;

import ceramics.com.ceramics.model.ProductDetails;

/**
 * Created by vikrantg on 18-06-2017.
 */

public class AndCriteria implements Criteria{

    private Criteria[] criterias;

    public AndCriteria(Criteria... criterias){
        this.criterias = criterias;
    }

    @Override
    public ArrayList<ProductDetails> meetCriteria(ArrayList<ProductDetails> productList) {
        ArrayList<ProductDetails> list = new ArrayList<>();
        list.addAll(productList);
        for (Criteria criteria : criterias){
            list = criteria.meetCriteria(list);
        }
        return list;
    }
}

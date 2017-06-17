package ceramics.com.ceramics.helper;

import java.util.ArrayList;

import ceramics.com.ceramics.model.Filter;

/**
 * Created by vikrantg on 17-06-2017.
 */

public interface IFilter {
    public void onSuccess(ArrayList<ArrayList<Filter>> filterList);
    public void onFailed(String msg);
}

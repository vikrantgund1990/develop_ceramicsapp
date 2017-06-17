package ceramics.com.ceramics.helper;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.model.Filter;
import ceramics.com.ceramics.network.CommonJsonArrayModel;

/**
 * Created by vikrantg on 17-06-2017.
 */

public class GetFilter implements Response.Listener<CommonJsonArrayModel<ArrayList<Filter>>>,Response.ErrorListener{

    private IFilter filter;

    public GetFilter(IFilter filter){
        this.filter = filter;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        filter.onFailed(volleyError.getMessage());
    }

    @Override
    public void onResponse(CommonJsonArrayModel<ArrayList<Filter>> response) {
        try{
            if (response.isStatus()){
                if (response.getData() != null && response.getData().size() > 0){
                    filter.onSuccess(response.getData());
                }
                else {
                    filter.onFailed("Unable to fetch filter data");
                }
            }
            else {
                filter.onFailed(response.getMessage());
            }
        }
        catch (Exception e){
            filter.onFailed(e.getMessage());
        }
    }
}

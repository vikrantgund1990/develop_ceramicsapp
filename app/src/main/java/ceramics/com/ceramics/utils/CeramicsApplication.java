package ceramics.com.ceramics.utils;


import android.support.multidex.MultiDexApplication;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.HashSet;
import java.util.Set;

import ceramics.com.ceramics.network.BitmapLruCache;

/**
 * Created by vikrantg on 22-06-2016.
 */
public class CeramicsApplication extends MultiDexApplication {

    public static final String TAG = CeramicsApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private final Set<Request<?>> saveLastRequest = new HashSet<Request<?>>();
    private static CeramicsApplication mInstance;
    private ImageLoader mImageLoader;

    public static synchronized CeramicsApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @SuppressWarnings("unchecked")
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);

        //saveLastRequest.clear();
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,
                1f));
        getRequestQueue().add(req);
        saveLastRequest.add(req);

    }

    @SuppressWarnings("unchecked")
    public <T> void callLastRequest() {
        if(Utils.isNetworkConnected(getApplicationContext()))
        {
            Object[] obj = saveLastRequest.toArray();
            for (int i=0;i<obj.length;i++){
                getRequestQueue().add((Request<T>) obj[i]);
            }
            //getRequestQueue().add((Request<T>) obj[0]);
        }

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new BitmapLruCache());
        }
        return this.mImageLoader;
    }
}

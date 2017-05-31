package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;

/**
 * Created by vikrantg on 16-05-2017.
 */

public class WebViewFragment extends BaseFragment {

    private BaseActivity activity;
    private WebView webView;
    private Button btnOK;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_view,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (BaseActivity)getActivity();
        initView(getView());
    }

    private void initView(View v){
        webView = (WebView)v.findViewById(R.id.web_view);
        btnOK = (Button)v.findViewById(R.id.button_ok);

        Bundle bundle = getArguments();
        String url = bundle.getString("URL");

        webView.loadUrl(url);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new MyBrowser());
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setActionBarTitle("CeramicsKart");
        activity.showBackOption(true);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

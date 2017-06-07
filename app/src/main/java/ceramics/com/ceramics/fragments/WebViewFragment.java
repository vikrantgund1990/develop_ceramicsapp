package ceramics.com.ceramics.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private ProgressDialog progressDialog;

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
    //    webView.getSettings().setBuiltInZoomControls(true);
    //    webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new MyBrowser());
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setActionBarTitle("CeramicsKart");
        activity.showBackOption(true);
    }

    /*private int getScale(){
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(PIC_WIDTH);
        val = val * 100d;
        return val.intValue();
    }*/

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(activity, " ", "");
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.getWindow().setGravity(Gravity.CENTER);
                progressDialog.setContentView(R.layout.custome_progress_bar);
                progressDialog.show();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }
}

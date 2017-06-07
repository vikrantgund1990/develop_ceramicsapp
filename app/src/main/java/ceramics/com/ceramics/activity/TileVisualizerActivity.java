package ceramics.com.ceramics.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.custom.ActionBarListener;
import ceramics.com.ceramics.custom.CustomActionBar;
import ceramics.com.ceramics.fragments.WebViewFragment;

/**
 * Created by vikrantg on 07-06-2017.
 */

public class TileVisualizerActivity extends BaseActivity implements ActionBarListener{

    private CustomActionBar actionBar;
    private Fragment webFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_visualizer);
        initView();
    }

    private void initView(){
        actionBar = (CustomActionBar)findViewById(R.id.action_bar);
        actionBar.setActionBarListner(this);
        if (getIntent().hasExtra("URL")){
            openWebFragment(getIntent().getStringExtra("URL"));
        }
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setActionBarTitle(title);
    }

    @Override
    public void showBackOption(boolean flag) {
        actionBar.showBackOption(flag);
    }

    @Override
    public void openWebFragment(String url) {
        if (webFragment == null) {
            webFragment = new WebViewFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString("URL",url);
        webFragment.setArguments(bundle);
        loadFragment(webFragment, R.id.tile_visualiser_layout, false);
    }

    @Override
    public void onBackIconPressed() {
        onBackPressed();
    }

    @Override
    public void onMenuIconPressed() {

    }
}

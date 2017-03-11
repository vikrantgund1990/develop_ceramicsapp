package ceramics.com.ceramics.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.custom.ActionBarListener;
import ceramics.com.ceramics.custom.CustomActionBar;
import ceramics.com.ceramics.fragments.FloorFragment;
import ceramics.com.ceramics.fragments.ProductTypeListFragment;
import ceramics.com.ceramics.fragments.WallFragment;

public class HomeActivity extends BaseActivity implements ActionBarListener {

    private CustomActionBar actionBar;
    private DrawerLayout drawer_parent;
    private Fragment wallFragment,floorFragment,productTypeFragment;
    private boolean isHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        openProductTypeListFragment();
    }

    private void initView(){
        actionBar = (CustomActionBar)findViewById(R.id.action_bar);
        drawer_parent = (DrawerLayout) findViewById(R.id.drawer_parent);
        actionBar.setActionBarListner(this);
    }

    public void openProductTypeListFragment(){
        isHome = true;
        if (productTypeFragment == null) {
            productTypeFragment = new ProductTypeListFragment();
        }
        loadFragment(productTypeFragment,R.id.base_layout,false);
        hideSlidingMenu();
    }

    public void openWallFragment(){
        if (wallFragment == null) {
            wallFragment = new WallFragment();
        }
        isHome = false;
        loadFragment(wallFragment, R.id.base_layout, false);
        hideSlidingMenu();
    }

    public void openFloorFragment(){
        if (floorFragment == null) {
            floorFragment = new FloorFragment();
        }
        isHome = false;
        loadFragment(floorFragment, R.id.base_layout, false);
        hideSlidingMenu();
    }

    public void hideSlidingMenu() {
        drawer_parent.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setActionBarTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (isHome){
            super.onBackPressed();
        }
        openProductTypeListFragment();
    }

    @Override
    public void onBackIconPressed() {

    }

    @Override
    public void onMenuIconPressed() {
        drawer_parent.openDrawer(Gravity.LEFT);
    }
}

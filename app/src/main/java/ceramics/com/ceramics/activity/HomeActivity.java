package ceramics.com.ceramics.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.fragments.ProductTypeListFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        openProductTypeListFragment();
    }

    private void openProductTypeListFragment(){
        ProductTypeListFragment fragment = new ProductTypeListFragment();
        loadFragment(fragment,R.id.base_layout);
    }
}

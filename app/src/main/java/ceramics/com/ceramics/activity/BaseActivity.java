package ceramics.com.ceramics.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import ceramics.com.ceramics.R;

/**
 * Created by vikrantg on 25-02-2017.
 */

public abstract class BaseActivity extends FragmentActivity {

    private Context activity;

    public void showToast(String message){
        activity = getApplicationContext();
        if (activity != null){
            Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
        }
    }

    public void loadFragment(Fragment fragment, int fragmentLayoutID,boolean isBackStack){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(fragmentLayoutID, fragment);
        fragmentTransaction.commit();
    }

    public abstract void setActionBarTitle(String title);
    public abstract void showBackOption(boolean flag);
    public abstract void openWebFragment(String url);

    public void openTileVisualizer(){
        Intent intent = new Intent(this,TileVisualizerActivity.class);
        intent.putExtra("URL",getString(R.string.tile_visualiser));
        startActivity(intent);
    }
}


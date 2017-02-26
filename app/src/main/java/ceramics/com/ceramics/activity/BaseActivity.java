package ceramics.com.ceramics.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

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

    public void loadFragment(Fragment fragment, int fragmentLayoutID){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(fragmentLayoutID, fragment);
        fragmentTransaction.commit();
    }
}


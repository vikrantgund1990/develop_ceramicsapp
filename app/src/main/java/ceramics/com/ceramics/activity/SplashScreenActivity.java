package ceramics.com.ceramics.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ceramics.com.ceramics.R;

public class SplashScreenActivity extends AppCompatActivity {

    private Thread splashTread;
    private int _splashTime = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashTread = new Thread() {
            @Override
            public void run() {
                try
                {
                    int waited = 0;
                    while (waited < _splashTime) {
                        sleep(100);
                        waited += 100;
                    }
                    goToNextScreen();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    //showToast(getStringFromResource(R.string.error));
                }

            }
        };

        splashTread.start();
    }

    private void goToNextScreen() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}

package pl.dawidgdanski.compass.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import pl.dawidgdanski.compass.util.ApplicationUtils;
import pl.dawidgdanski.compass.util.Intents;

public class WelcomeSplashActivity extends AppCompatActivity {

    private static final long DELAY_MILLIS = TimeUnit.SECONDS.toMillis(1);

    private Handler welcomeSplashDelayHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationUtils.setOrientationChangeEnabled(false, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(welcomeSplashDelayHandler == null) {
            welcomeSplashDelayHandler = new Handler();
            welcomeSplashDelayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(Intents.newActivityIntent(WelcomeSplashActivity.this, MainActivity.class));
                    finish();
                }
            }, DELAY_MILLIS);
        }
    }

    @Override
    public void onBackPressed() {
        if(welcomeSplashDelayHandler != null) {
            welcomeSplashDelayHandler.removeCallbacksAndMessages(null);
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationUtils.setOrientationChangeEnabled(true, this);
    }
}

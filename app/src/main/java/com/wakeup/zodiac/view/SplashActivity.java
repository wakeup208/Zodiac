package com.wakeup.zodiac.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

import com.wakeup.zodiac.R;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = PathInterpolatorCompat.MAX_NUM_POINTS;
    public static String birthDay = "";
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        aniRotate.setRepeatCount(2);
        ((ImageView) findViewById(R.id.img)).startAnimation(aniRotate);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                SplashActivity splashActivity = SplashActivity.this;
                splashActivity.sharedPreferences = splashActivity.getSharedPreferences("MyPref", 0);
                if (!SplashActivity.this.sharedPreferences.contains("yourbirthdate") || !SplashActivity.this.sharedPreferences.contains("yourname")) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, InfoScreenActivity.class));
                    SplashActivity.this.finish();
                    return;
                }
                SplashActivity.birthDay = SplashActivity.this.sharedPreferences.getString("yourbirthdate", "");
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, (long) SPLASH_TIME_OUT);
    }
}

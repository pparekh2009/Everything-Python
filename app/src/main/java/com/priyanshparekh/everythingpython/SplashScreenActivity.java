package com.priyanshparekh.everythingpython;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMEOUT = 3000;

    Animation topAnim, bottomAnim, fadeAnim;
    TextView splashText1, splashText2;
    ImageView pythonLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        fadeAnim = AnimationUtils.loadAnimation(this, R.anim.fade_anim);

        splashText1 = findViewById(R.id.splash_text_1);
        splashText2 = findViewById(R.id.splash_text_2);
        pythonLogo = findViewById(R.id.python_logo);

        splashText1.setAnimation(topAnim);
        splashText2.setAnimation(bottomAnim);
        pythonLogo.setAnimation(fadeAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}
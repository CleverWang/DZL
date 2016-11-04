package com.wangcong.dzl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Frank on 2016/10/7.
 */

public class AppStart extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View view = View.inflate(this, R.layout.start, null);
        setContentView(view);

        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(1500);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

    }

    private void redirectTo() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }

}
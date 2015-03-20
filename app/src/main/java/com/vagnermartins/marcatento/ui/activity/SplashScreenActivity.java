package com.vagnermartins.marcatento.ui.activity;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.vagnermartins.marcatento.R;
import com.vagnermartins.marcatento.app.App;

public class SplashScreenActivity extends ActionBarActivity {

    private static final long DELAY = 1000;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, DELAY);
    }

}

package com.learn.flavio_mauricio.beatemupgame;

import android.app.Activity;
import android.os.Bundle;

/**
 * This is the main activity - the first activity to be executed
 * (as configured in AndroidManifest.xml).
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}

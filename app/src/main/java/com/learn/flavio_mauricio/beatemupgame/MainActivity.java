package com.learn.flavio_mauricio.beatemupgame;

import android.app.ActionBar;
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
        if(android.os.Build.VERSION.SDK_INT >= 11) {
            /* TODO
                Forcing to hide that d*mn action bar. Go away!!
                We need to test it though...
             */
            ActionBar killMe = this.getActionBar();
            if(killMe != null) {
                killMe.hide();
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}

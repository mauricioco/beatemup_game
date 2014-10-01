package com.learn.flavio_mauricio.beatemupgame.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.learn.flavio_mauricio.beatemupgame.R;

/**
 * This is the main activity - the first activity to be executed
 * (as configured in AndroidManifest.xml).
 */
public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if(android.os.Build.VERSION.SDK_INT >= 11) {
            ActionBar killMe = this.getActionBar();
            if(killMe != null) {
                killMe.hide();
            }

            /* Code to hide nav bar
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            */
        }


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
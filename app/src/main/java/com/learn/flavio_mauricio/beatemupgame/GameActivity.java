package com.learn.flavio_mauricio.beatemupgame;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        System.out.println("wow");
        return super.onOptionsItemSelected(item);
    }

}
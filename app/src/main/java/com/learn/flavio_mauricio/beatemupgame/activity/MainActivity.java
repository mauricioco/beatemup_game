package com.learn.flavio_mauricio.beatemupgame.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.learn.flavio_mauricio.beatemupgame.R;
import com.learn.flavio_mauricio.beatemupgame.game.GameOptions;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonStart = (Button) findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
/*
        final Button buttonSettings = (Button) findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                //MainActivity.this.startActivity(intent);
            }
        });
*/
        final Button buttonQuit = (Button) findViewById(R.id.button_quit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_control_method_touch:
                if (checked)
                    GameOptions.controlMethod = false;
                break;
            case R.id.radio_control_method_dpad:
                if (checked)
                    GameOptions.controlMethod = true;
                break;
            default:
                break;
        }
    }

}

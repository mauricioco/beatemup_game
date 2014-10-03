package com.learn.flavio_mauricio.beatemupgame.game;

import android.content.Context;
import android.graphics.Point;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

import com.learn.flavio_mauricio.beatemupgame.logic.ActorState;

/**
 * Created by mauriciodeoliveira on 10/3/14.
 */
public class GameControl implements View.OnTouchListener {

    // Common attributes
    private Vibrator vib;
    private Camera camera;

    // DPad attributes (merge'em or not. That's the question.)

    // Touch attributes
    private int xPress = 0;
    private int yPress = 0;
    protected boolean gonnaAttack = false;

    public GameControl(Context context, Camera camera) {
        this.vib =  (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        this.camera = camera;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(GameOptions.controlMethod) {
            return dPadMove(motionEvent);
        }else{
            return touchMove(motionEvent);
        }

    }

    private boolean dPadMove(MotionEvent motionEvent) {
        int hat[] = camera.getButtonPressed(motionEvent.getX(), motionEvent.getY());
        if(hat[0]==0 && hat[1]==0) {
            camera.getActiveMap().getPlayer().setState(ActorState.Attacking);
            //System.out.println("Attacked");
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                camera.getActiveMap().getPlayer().setDerivative(hat[0], hat[1]);
                break;
            case MotionEvent.ACTION_MOVE:
                camera.getActiveMap().getPlayer().setDerivative(hat[0], hat[1]);
                break;
            case MotionEvent.ACTION_UP:
                camera.getActiveMap().getPlayer().setDerivative(0, 0);
                break;
            default:
                camera.getActiveMap().getPlayer().setDerivative(0, 0);
                break;
        }
        return true;
    }

    private boolean touchMove(MotionEvent motionEvent) {
        Point topLeft = camera.getTopLeftCoord();
        int x = (int) (topLeft.x + motionEvent.getX());
        int y = (int) (topLeft.y + motionEvent.getY());
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //camera.startMovingActorToFollowTo(x, y - 32);
                xPress = (int) motionEvent.getX();
                yPress = (int) motionEvent.getY();
                gonnaAttack = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gonnaAttack) {
                    camera.startMovingActorToFollowTo(x, y - 32);
                } else if (Math.abs(motionEvent.getX() - xPress) > 8) {
                    gonnaAttack = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(gonnaAttack) {
                    vib.vibrate(25);
                    camera.getActiveMap().getPlayer().setDerivative(0, 0);
                    if(motionEvent.getX() < camera.getWidth()/2)
                        camera.getActiveMap().getPlayer().setDirection(-1);
                    else
                        camera.getActiveMap().getPlayer().setDirection(1);
                    camera.getActiveMap().getPlayer().setState(ActorState.Attacking);
                }else{
                    camera.getActiveMap().getPlayer().setDerivative(0, 0);
                }

                break;
            default:
                camera.getActiveMap().getPlayer().setDerivative(0, 0);
                break;
        }
        return true;
    }
}

package com.learn.flavio_mauricio.beatemupgame.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.learn.flavio_mauricio.beatemupgame.logic.Actor;
import com.learn.flavio_mauricio.beatemupgame.logic.ActorState;
import com.learn.flavio_mauricio.beatemupgame.logic.EnemyActor;
import com.learn.flavio_mauricio.beatemupgame.logic.GameMap;
import com.learn.flavio_mauricio.beatemupgame.logic.LogicManager;

/**
 * This is the SurfaceView where the game is rendered in.
 */
public class GameView extends SurfaceView {

    private GameThread gameLoopThread;
    private Camera camera;

    /**
     * Creates a GameView. This extends from SurfaceView and renders
     * the game screen.
     * @param context The context of the call.
     * @param attributeSet  Not used by us.
     */
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.setKeepScreenOn(true); // turn off screen sleep.

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            // screen measures to scale the camera for different devices.

        // Setting current map (an example), camera and loop - the basics!
        GameMap activeMap = LogicManager.defaultInstance(getResources(), metrics.widthPixels, metrics.heightPixels);
        this.camera = new Camera(metrics.widthPixels, metrics.heightPixels,
                activeMap, activeMap.getPlayer(), getResources());
        gameLoopThread = new GameThread(this, activeMap);

        this.createHolder();
        this.setListeners(context);
    }

    private void createHolder() {
        // This holder thing manages the game thread loop. It's required!
        SurfaceHolder holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    /**
     * Sets the listeners for the view.
     */
    private void setListeners(final Context context) {
        this.setOnTouchListener(new OnTouchListener() {

            protected boolean gonnaAttack = false;
            private Vibrator vib = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
            private int xPress = 0;
            private int yPress = 0;

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
        });

    }

    /**
     * This is the method called by the game thread to update the screen.
     * @param canvas the canvas to draw on.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);  // black background.
        /*
            Element drawing. Currently I know no way to set depth for this,
            so we have to draw them in order.
         */
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(20);
        p.setAntiAlias(true);
        p.setFilterBitmap(true);
/*
        p.setColor(Color.RED);
        ColorFilter filter = new LightingColorFilter(Color.RED, 1);
        p.setColorFilter(filter);
*/
        camera.drawBackground(canvas, null);
        camera.drawFloor(canvas, null);
        camera.drawActors(canvas, null);
        if(GameOptions.controlMethod)
            camera.drawControls(canvas, null);
        camera.drawHud(canvas, p);

    }


}
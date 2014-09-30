package com.learn.flavio_mauricio.beatemupgame.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.learn.flavio_mauricio.beatemupgame.logic.Actor;
import com.learn.flavio_mauricio.beatemupgame.logic.GameMap;
import com.learn.flavio_mauricio.beatemupgame.logic.LogicManager;

/**
 * This is the SurfaceView where the game is rendered in.
 */
public class GameView extends SurfaceView {

    private boolean CONTROL_METHOD;

    private GameThread gameLoopThread;
    private Camera camera;

    /**
     * Creates a GameView. This extends from SurfaceView and renders
     * the game screen.
     * @param context I still don't know what this is...
     * @param attributeSet  same goes for this.
     */
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.setKeepScreenOn(true);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        GameMap activeMap = LogicManager.defaultInstance(getResources(), metrics.widthPixels, metrics.heightPixels);

        System.out.println(metrics.widthPixels);
        this.camera = new Camera(metrics.widthPixels, metrics.heightPixels,
                activeMap, activeMap.getPlayer(), getResources());

        gameLoopThread = new GameThread(this, activeMap);

        //This holder thing manages the game thread loop.
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

        this.setListeners();
    }

    /**
     * Sets the listeners for the view. So far, only the touch commands
     * for the d-pad are (not fully) implemented.
     */
    private void setListeners() {
        this.setOnTouchListener(new OnTouchListener() {

            protected boolean gonnaAttack = false;
            Thread checkAttack = new Thread() {
                public void run() {
                    while(true) {
                        try {
                            sleep(25);
                            if(gonnaAttack) {
                                sleep(25);
                                gonnaAttack = false;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };



            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(CONTROL_METHOD) {
                    if(checkAttack.isAlive())
                        checkAttack.interrupt();
                    int hat[];
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            hat = camera.getButtonPressed(motionEvent.getX(), motionEvent.getY());
                            camera.getActiveMap().getPlayer().setDerivative(hat[0], hat[1]);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            hat = camera.getButtonPressed(motionEvent.getX(), motionEvent.getY());
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
                }else{
                    if(!checkAttack.isAlive())
                        checkAttack.start();
                    //PointF centerCoord = camera.getActorToFollowPos();
                    Point topLeft = camera.getTopLeftCoord();
                    int x = (int) (topLeft.x + motionEvent.getX());
                    int y = (int) (topLeft.y + motionEvent.getY());
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //camera.startMovingActorToFollowTo(x, y - 32);
                            gonnaAttack = true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if(!gonnaAttack)
                                camera.startMovingActorToFollowTo(x, y - 32);
                            break;
                        case MotionEvent.ACTION_UP:
                            if(gonnaAttack)
                                System.out.println("ATTACK!!");
                            camera.getActiveMap().getPlayer().setDerivative(0, 0);
                            break;
                        default:
                            camera.getActiveMap().getPlayer().setDerivative(0, 0);
                            break;
                    }
                    return true;
                }

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
         Paint paint = new Paint();
         paint.setColor(Color.TRANSPARENT);
        paint.setAlpha(255);
        */

        /*
            Element drawing. Currently I know no way to set depth for this,
            so we have to draw them in order.
         */
        camera.drawBackground(canvas, null);
        camera.drawFloor(canvas, null);
        camera.drawActors(canvas, null);   // Still haven't figured how to draw actors with paint.
        camera.drawControls(canvas, null);


    }


}
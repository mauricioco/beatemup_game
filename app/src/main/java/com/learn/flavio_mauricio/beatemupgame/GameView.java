package com.learn.flavio_mauricio.beatemupgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;
import com.learn.flavio_mauricio.beatemupgame.graphic.Sprite;
import com.learn.flavio_mauricio.beatemupgame.logic.Actor;
import com.learn.flavio_mauricio.beatemupgame.logic.GameMap;
import com.learn.flavio_mauricio.beatemupgame.logic.LogicManager;

import java.util.ArrayList;

/**
 * This is the SurfaceView where the game is rendered in.
 */
public class GameView extends SurfaceView {

    private GameThread gameLoopThread;
    protected GameMap activeMap;

    /**
     * Creates a GameView. This extends from SurfaceView and renders
     * the game screen.
     * @param context I still don't know what this is...
     * @param attributeSet  same goes for this.
     */
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.activeMap = LogicManager.defaultInstance(getResources());
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
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                /*  TODO
                    !!!Warning!!!
                    These touch positions are relative to the 94x94 d-pad on 2-inch devices.
                    Hadn't tested with different devices.
                    We need to create a class to manage this stuff...
                 */
                float x = motionEvent.getX();
                float y = motionEvent.getY() - (320-112);
                System.out.println("Pressed at " + x + ", " + y);
                if(y >= 94*0.334f && y <= 94*0.656f) {
                    if (x <= 0.32f * 94) {
                        //go left
                        activeMap.getPlayer().setDerivative(-.75f, 0);
                    } else if (x >= 0.688f * 94) {
                        //go right
                        activeMap.getPlayer().setDerivative(.75f, 0);
                    }
                }else if(x >= 0.32f * 94 && x <= 0.688f * 94){
                    if (y <= 94*0.334f) {
                        //go up
                        activeMap.getPlayer().setDerivative(0, -.75f);
                    } else if (y >= 94*0.656f) {
                        //go down
                        activeMap.getPlayer().setDerivative(0, .75f);
                    }
                }
                // }else{
                //activeMap.getPlayer().setDerivative(0, 0);
                //}
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
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAlpha(255);
        */

        /*
            Element drawing. Currently I know no way to set depth for this,
            so we have to draw them in order.
         */
        drawBackground(canvas, null);
        drawFloor(canvas, null);
        drawActors(canvas, null);
        drawControls(canvas, null);

    }

    private void drawBackground(Canvas canvas, Paint paint) {
        Sprite sprite = GraphicManager.getSprite(activeMap.getBackground());
        canvas.drawBitmap(sprite.update(), this.getLeft(), this.getTop(), paint);
        //canvas.drawRect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom(), paint);
    }

    private void drawFloor(Canvas canvas, Paint paint) {
        Sprite sprite = GraphicManager.getSprite(activeMap.getFloor());
        canvas.drawBitmap(sprite.update(), this.getLeft(), this.getTop() + 120, paint);
    }

    private void drawActors(Canvas canvas, Paint paint) {
        ArrayList<Actor> renderList = activeMap.getRenderList();
        for(Actor actor : renderList) {
            Sprite sprite = GraphicManager.getSprite(actor);
            PointF pos = activeMap.getActorPos(actor);
            canvas.drawBitmap(sprite.update(), pos.x, pos.y, paint);
        }
    }

    private void drawControls(Canvas canvas, Paint paint) {
        //paint.setAntiAlias(true);
        //paint.setFilterBitmap(true);
        /* TODO
            This little guy creates an object for the Bitmap every loop iteration. This
            cannot happen on future releases!!
         */
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.button_dpad);
        canvas.drawBitmap(bmp, this.getLeft(), this.getBottom()-bmp.getHeight(), paint);
    }

}
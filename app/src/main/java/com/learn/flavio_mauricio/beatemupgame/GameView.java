package com.learn.flavio_mauricio.beatemupgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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
    private DPad dPad;

    /**
     * Creates a GameView. This extends from SurfaceView and renders
     * the game screen.
     * @param context I still don't know what this is...
     * @param attributeSet  same goes for this.
     */
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        this.activeMap = LogicManager.defaultInstance(getResources(), metrics.widthPixels, metrics.heightPixels);
        Bitmap sprite = BitmapFactory.decodeResource(getResources(), R.drawable.button_dpad);

        this.dPad = new DPad(sprite, metrics.widthPixels, metrics.heightPixels);

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
                int hat[];
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        hat = dPad.getButtonPressed(motionEvent.getX(), motionEvent.getY());
                        activeMap.getPlayer().setDerivative(hat[0], hat[1]);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        hat = dPad.getButtonPressed(motionEvent.getX(), motionEvent.getY());
                        activeMap.getPlayer().setDerivative(hat[0], hat[1]);
                        break;
                    case MotionEvent.ACTION_UP:
                        activeMap.getPlayer().setDerivative(0, 0);
                        break;
                    default:
                        activeMap.getPlayer().setDerivative(0, 0);
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
        drawActors(canvas, null);   // Still haven't figured how to draw actors with paint.
        drawControls(canvas, null);


    }

    private void drawBackground(Canvas canvas, Paint paint) {
        /* TODO
            remember, both background and floor are being drawn for one screen
            only!
         */
        Rect bmpRect = new Rect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
        Sprite sprite = GraphicManager.getSprite(activeMap.getBackground());
        canvas.drawBitmap(sprite.update(), null, bmpRect, paint);


    }

    private void drawFloor(Canvas canvas, Paint paint) {
        activeMap.drawFloor(canvas, paint);
    }

    private void drawActors(Canvas canvas, Paint paint) {
        activeMap.drawActors(canvas, paint);
        //paint.setColor(Color.TRANSPARENT);
        /*ArrayList<Actor> renderList = activeMap.getRenderList();
        for(Actor actor : renderList) {
            Sprite sprite = GraphicManager.getSprite(actor);
            PointF pos = activeMap.getActorPos(actor);
            int posX = ((pos.x/100) * )
            canvas.drawBitmap(sprite.update(), pos.x, pos.y, paint);
            /*Rect bmpRect = new Rect((int)pos.x, (int)pos.y,
                    (int)actor.getWidth()+(int)pos.x, (int)actor.getHeight()+(int)pos.y);
            if(actor.getDx() == 0 && actor.getDy() == 0) {  // is not animated
                canvas.drawBitmap(sprite.update(), null, bmpRect, paint);
            }else{  // is animated
                canvas.drawBitmap(sprite.updateAnim(), null, bmpRect, paint);
            }

        }*/
    }

    private void drawControls(Canvas canvas, Paint paint) {
        //paint.setAntiAlias(true);
        //paint.setFilterBitmap(true);
        dPad.Draw(canvas, paint);
        //Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.button_dpad);
        //canvas.drawBitmap(bmp, this.getLeft(), this.getBottom()-bmp.getHeight(), paint);
    }
}
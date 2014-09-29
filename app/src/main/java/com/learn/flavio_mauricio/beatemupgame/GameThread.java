package com.learn.flavio_mauricio.beatemupgame;

import android.graphics.Canvas;

import com.learn.flavio_mauricio.beatemupgame.logic.GameMap;

/**
 * This is the game thread loop. This thread keeps updating
 * the map and screen.
 */
public class GameThread extends Thread {
    static final long FPS = 30;

    private GameView view;
    private boolean running = false;
    private GameMap activeMap;

    public GameThread(GameView view, GameMap activeMap) {
        this.view = view;
        this.activeMap = activeMap;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                activeMap.update();
                //synchronized (view.getHolder()) {
                view.onDraw(c);
                //}
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

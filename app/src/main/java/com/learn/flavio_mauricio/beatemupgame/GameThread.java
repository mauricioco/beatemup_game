package com.learn.flavio_mauricio.beatemupgame;

import android.graphics.Canvas;

import com.learn.flavio_mauricio.beatemupgame.logic.GameMap;

/**
 * This is the game thread loop. This thread keeps updating
 * the map and screen.
 */
public class GameThread extends Thread {
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
        /*
            TODO
            This loop implementation displays somewhat choppy refreshes.
            We have to ask uncle google for help later.
         */
        while (running) {
            Canvas c = null;
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
        }
    }
}

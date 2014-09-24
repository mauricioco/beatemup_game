package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;
import com.learn.flavio_mauricio.beatemupgame.graphic.Sprite;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The world where all other game objects resides.
 */
public class GameMap extends GameObject {

    /* TODO
        - treat collisions. As of now, the player can fly...
        - create a camera to support bigger maps. As of now, the maps are a single screen.
     */

    //private int blocks = 1; // each block occupies a device screen. As of now, it can only show 1 block.
    private int width;
    private int height;
    private Background background;
    private Floor floor;
    private IActor player = null;
    private ArrayList<Actor> contents;  // list of existing actors
    private Hashtable<Actor, PointF> actorsLocation;    // hashtable where each actor is mapped to its current position.

    public GameMap(String id, Background background, Floor floor, int width, int height) {
        super(id);
        this.width = width;
        this.height = height;
        //this.blocks = blocks;
        this.background = background;
        this.floor = floor;
        contents = new ArrayList<Actor>();
        actorsLocation = new Hashtable<Actor, PointF>();
    }
    /*
    public GameMap(String id, Background background, Floor floor, float floorSize) {
        super(id);
        this.blocks = blocks;
        this.background = background;
        this.floor = floor;
        contents = new ArrayList<Actor>();
        actorsLocation = new Hashtable<Actor, PointF>();
    }*/

    public void putActorAt(Actor actor, int x, int y) {
        contents.add(actor);
        actorsLocation.put(actor, new PointF(x, y));
    }

    public void putPlayerAt(IActor player, int x, int y) {
        this.player = player;
        this.putActorAt(player, x, y);
    }

    public PointF getActorPos(Actor actor) {
        return actorsLocation.get(actor);
    }

    public Background getBackground() {
        return background;
    }

    public Floor getFloor() {
        return floor;
    }

    public float getFloorSize() {
        return height/2; // This will be dynamic in the future. For now, we are testing with fixed values.
    }

    public IActor getPlayer() {
        return player;
    }

    public ArrayList<Actor> getRenderList() {
        /* TODO
            REMEMBER - currently only the player is being rendered.
         */
        ArrayList<Actor> renderList = new ArrayList<Actor>();
        renderList.add(player);
        return renderList;
    }

    /**
     * This is the method to verify pertinence within the map. The beginning
     * of collision detection! By now it's using the android screen as reference.
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(float x, float y, float actorWidth, float actorHeight) {
        int posX = (int) ((x / 100) * width);
        int posY = (int) ((y / 100) * height);

        //API 11 or newer has a navigation bar, so map limits have to be adjusted
        if (android.os.Build.VERSION.SDK_INT >= 11){
            if((posX < 0 || (posX + actorWidth*2) > width) || (posY < getFloorSize() || (posY + actorHeight*2) > height) ) {
                return false;
            }
        }else{
            if((posX < 0 || (posX + actorWidth) > width) || (posY < getFloorSize() || (posY + actorHeight) > height) ) {
                return false;
            }
        }

        return true;
    }

    public void drawFloor(Canvas canvas, Paint paint){
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();

        Rect bmpRect = new Rect(0, height/2,
                width, height);
        Sprite sprite = GraphicManager.getSprite(this.getFloor());
        canvas.drawBitmap(sprite.update(), null, bmpRect, paint);
    }

    public void drawActors(Canvas canvas, Paint paint){
        ArrayList<Actor> renderList = this.getRenderList();
        for(Actor actor : renderList) {
            Sprite sprite = GraphicManager.getSprite(actor);
            PointF pos = this.getActorPos(actor);
            int posX = (int) ((pos.x / 100) * width);
            int posY = (int) ((pos.y / 100) * height);
            canvas.drawBitmap(sprite.update(), posX, posY, paint);
        }
    }

    /*
    // This will return the list of actors to render (in order).
    public ArrayList<Actor> getRenderList() {
        ArrayList<Actor> renderList = new ArrayList<Actor>();
        for(int i=0; i<contents.size(); i++) {
            Actor actor = contents.get(i);
            PointF pos = actorsLocation.get(actor);
            for(int j=0; i<renderList.size(); j++) {
                Actor actorToCompare = renderList.get(j);
                PointF posToCompare = actorsLocation.get(actorToCompare);
                if(pos.x < posToCompare.x) {

                }
            }
        }
        return renderList;
    }
    */

    public void update() {
        for(Actor actor : contents) {
            updateActorPos(actor);
        }
    }

    private void updateActorPos(Actor actor) {
        PointF oldPos = actorsLocation.get(actor);
        PointF newPos = new PointF(oldPos.x+actor.getDx(), oldPos.y+actor.getDy());
        if(isInside(newPos.x, newPos.y, actor.getWidth(), actor.getHeight())) {
            actorsLocation.put(actor, newPos);
        }
    }

}

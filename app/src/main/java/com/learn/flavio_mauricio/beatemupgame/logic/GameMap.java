package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.PointF;

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
    private float width;
    private float height;
    private Background background;
    private IActor player = null;
    private ArrayList<Actor> actorList;  // list of existing actors
    private ArrayList<Floor> floorList;
    private Hashtable<Actor, PointF> actorsLocation;    // hashtable where each actor is mapped to its current position.

    public GameMap(String id, Background background, float width, float height) {
        super(id);
        this.width = width;
        this.height = height;
        //this.blocks = blocks;
        this.background = background;
        this.actorList = new ArrayList<Actor>();
        this.floorList = new ArrayList<Floor>();
        this.actorsLocation = new Hashtable<Actor, PointF>();
    }
    /*
    public GameMap(String id, Background background, Floor floor, float floorSize) {
        super(id);
        this.blocks = blocks;
        this.background = background;
        this.floor = floor;
        actorList = new ArrayList<Actor>();
        actorsLocation = new Hashtable<Actor, PointF>();
    }*/

    public void putActorAt(Actor actor, float x, float y) {
        actorList.add(actor);
        actorsLocation.put(actor, new PointF(x, y));
    }

    public void putPlayerAt(IActor player, float x, float y) {
        this.player = player;
        this.putActorAt(player, x, y);
    }

    public void putFloor(Floor floor) {
        floorList.add(floor);
    }

    public PointF getActorPos(Actor actor) {
        return actorsLocation.get(actor);
    }

    public Background getBackground() {
        return background;
    }

    public float getHeight() {
        return height;
    }

    public IActor getPlayer() {
        return player;
    }

    public float getWidth() {
        return width;
    }

    public ArrayList<Actor> getActorRenderList() {
        ArrayList<Actor> renderList = new ArrayList<Actor>();
        renderList.add(actorList.get(0));
        /*
        for(int i=1; i<actorList.size(); i++) {
            Actor actor = actorList.get(i);
            PointF pos = actorsLocation.get(actor);
            renderList.add(0, actor);
            int indexToSet = 0;
            for(int j=1; i<renderList.size(); j++) {
                Actor actorToCompare = renderList.get(j);
                PointF posToCompare = actorsLocation.get(actorToCompare);
                if(posToCompare.y < pos.y) {
                    renderList.set(0, actorToCompare);
                }
            }
        }
        */
        return renderList;
    }

    public int getActorFloorPosX(float x, Actor actor) {
        int i, atWidth=0;
        for(i=0; i<floorList.size(); i++) {
            if (x < atWidth) {
                break;
            }
            atWidth += floorList.get(i).getSizeX();
        }
        int actorX = (int) getActorPos(actor).x;
        atWidth -= floorList.get(i-1).getSizeX();
        actorX = atWidth - actorX;

        return atWidth;
    }

    public Floor[] getFloorRenderList(float x) {
        Floor[] renderList = new Floor[3];
        int i, atWidth=0;
        for(i=0; i<floorList.size(); i++) {
            if (x < atWidth) {
                break;
            }
            atWidth += floorList.get(i).getSizeX();
        }
        if(i-2<floorList.size() && i-2>=0)
            renderList[0] = floorList.get(i-2);
        if(i-1<floorList.size() && i-1>=0)
            renderList[1] = floorList.get(i-1);
        if(i<floorList.size() && i>=0)
            renderList[2] = floorList.get(i);

        return renderList;
    }

    /**
     * This is the method to verify pertinence within the map. The beginning
     * of collision detection! By now it's using the android screen as reference.
     * @param x
     * @return
     */
    public boolean isInsideHorizontal(float x) {
        if ((x < 0) || (x > width)){
            return false;
        }

        return true;
    }

    public boolean isInsideVertical(float x, float y) {
        int floorIndex = (int) (x / width);
        // TODO CHECK FOR EXCEPTION!!
        if ((y < floorList.get(floorIndex).getFloorLimit()) || (y > height)){
            return false;
        }
        
        return true;
    }

    /*
    // This will return the list of actors to render (in order).
    public ArrayList<Actor> getActorRenderList() {
        ArrayList<Actor> renderList = new ArrayList<Actor>();
        for(int i=0; i<actorList.size(); i++) {
            Actor actor = actorList.get(i);
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
        for(Actor actor : actorList) {
            updateActorPos(actor);
        }
    }

    private void updateActorPos(Actor actor) {
        PointF actorPos = actorsLocation.get(actor);

        PointF newPos = new PointF(actorPos.x + actor.getDx(), actorPos.y);
        if(isInsideHorizontal(newPos.x)) {
            actorPos.x = newPos.x;
        }

        newPos.x = actorPos.x;
        newPos.y = actorPos.y + actor.getDy();
        if(isInsideVertical(newPos.x, newPos.y)){
            actorPos.y = newPos.y;
        }

        actorsLocation.put(actor, actorPos);
    }

}

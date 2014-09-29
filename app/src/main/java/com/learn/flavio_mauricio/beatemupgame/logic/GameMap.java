package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The world where all other game objects reside.
 */
public class GameMap extends GameObject {
    private int width;
    private int height;
    private Background background;
    private IActor player = null;
    private ArrayList<Actor> actorList;  // list of existing actors
    private ArrayList<Floor> floorList;
    private Hashtable<Actor, PointF> actorsLocation;    // hashtable where each actor is mapped to its current position.

    public GameMap(String id, Background background, Floor floor, int width, int height) {
        super(id);
        this.width = width;
        this.height = height;
        this.background = background;
        this.actorList = new ArrayList<Actor>();
        this.floorList = new ArrayList<Floor>();
        this.floorList.add(floor);
        this.actorsLocation = new Hashtable<Actor, PointF>();
    }

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

    /** TODO
     * Gets a list of all actors in crescent order of its `y` position.
     * Warning: ALL ACTORS are in this list. We should get only the ones
     * currently on-screen.
     * @return
     */
    public ArrayList<Actor> getActorRenderList() {
        ArrayList<Actor> renderList = new ArrayList<Actor>();
        renderList.add(actorList.get(0));
        for(int i=1; i<actorList.size(); i++) {
            Actor actorToAdd = actorList.get(i);
            PointF posToAdd = actorsLocation.get(actorToAdd);
            int j;
            for(j=0; j<renderList.size(); j++) {
                Actor actorToCompare = renderList.get(j);
                PointF posToCompare = actorsLocation.get(actorToCompare);
                if(posToCompare.y > posToAdd.y) {
                    break;
                }
            }
            renderList.add(j, actorToAdd);
        }
        return renderList;
    }

    public Floor getFloorAt(float x) {
        int i, atWidth=0;
        for(i=0; i<floorList.size(); i++) {
            if (x < atWidth) {
                return floorList.get(i-1);
            }
            atWidth += floorList.get(i).getSizeX();
        }
        return floorList.get(i-1);
    }

    public Floor getPreviousFloor(Floor currFloor) {
        int i = floorList.indexOf(currFloor);
        if(i > 0)
            return floorList.get(i-1);
        return floorList.get(0);
    }

    public Floor getNextFloor(Floor currFloor) {
        int i = floorList.indexOf(currFloor);
        if(i < floorList.size()-1)
            return floorList.get(i+1);
        return floorList.get(floorList.size()-1);
    }



    /**
     * This is the method to verify pertinence within the map. The beginning
     * of collision detection! By now it's using the android screen as reference.
     *
     */
    public boolean isInsideHorizontal(float x) {
        return (x >= 0) && (x < width);
    }

    public boolean isInsideVertical(float x, float y) {
        int floorIndex = (int) (x / width);
        // TODO CHECK FOR EXCEPTION!!
        return (y >= floorList.get(floorIndex).getFloorLimit()) && (y < height);
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

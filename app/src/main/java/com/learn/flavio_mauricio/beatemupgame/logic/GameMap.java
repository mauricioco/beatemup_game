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
    private Background background;
    private Floor floor;
    private IActor player = null;
    private ArrayList<Actor> contents;  // list of existing actors
    private Hashtable<Actor, PointF> actorsLocation;    // hashtable where each actor is mapped to its current position.

    public GameMap(String id, Background background, Floor floor) {
        super(id);
        //this.blocks = blocks;
        this.background = background;
        this.floor = floor;
        contents = new ArrayList<Actor>();
        actorsLocation = new Hashtable<Actor, PointF>();
    }

    public void putActorAt(Actor actor, float x, float y) {
        contents.add(actor);
        actorsLocation.put(actor, new PointF(x, y));
    }

    public void putPlayerAt(IActor player, float x, float y) {
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
        actorsLocation.put(actor, newPos);
    }

}

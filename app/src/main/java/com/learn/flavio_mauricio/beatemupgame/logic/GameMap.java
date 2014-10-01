package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

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
        if(actor instanceof EnemyActor){
            ((EnemyActor) actor).setMap(this);
        }
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

    public Iterator<Actor> getActorListIterator() {
        return actorList.iterator();
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

    public int getFloorOffset(Floor currFloor) {
        int offset = 0;
        for(Floor floor : floorList) {
            if(floor.equals(currFloor))
                break;
            offset += floor.getSizeX();
        }
        return offset;
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
        Floor floor = getFloorAt(x);
        return (y >= floor.getFloorLimit()) && (y < height);
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
            if(actor instanceof EnemyActor){
                ((EnemyActor) actor).RandomAI(actorsLocation.get(actor), actorsLocation.get(player));
            }
        }
    }

    private void updateActorPos(Actor actor) {
        PointF actorPos = actorsLocation.get(actor);

        PointF newPos = new PointF(actorPos.x + actor.getDx(), actorPos.y);
        if(actor instanceof EnemyActor){
            if(!isInsideHorizontal(((EnemyActor) actor).getTarget().x) && !isInsideVertical(((EnemyActor) actor).getTarget().x, ((EnemyActor) actor).getTarget().y)){
                ((EnemyActor) actor).setState(States.Idle);
            }
        }
        if(isInsideHorizontal(newPos.x) && isInsideVertical(newPos.x, newPos.y)) {
            actorPos.x = newPos.x;
        }

        newPos.x = actorPos.x;
        newPos.y = actorPos.y + actor.getDy();
        if(isInsideVertical(newPos.x, newPos.y)){
            actorPos.y = newPos.y;
        }

        actorsLocation.put(actor, actorPos);
    }

    public void startMovingActorTo(Actor actor, float x, float y) {
        PointF actorPos = actorsLocation.get(actor);
        float adjCat = x - actorPos.x;
        float opCat = y - actorPos.y;
        float hip = (float) Math.sqrt( Math.pow((x-actorPos.x), 2) +
                Math.pow((y-actorPos.y), 2) );

        float cos = (adjCat)/hip;
        float sin = (opCat)/hip;

        float newPosX = (actor.getSpeed()*cos) + actorPos.x;
        float newPosY = (actor.getSpeed()*sin) + actorPos.y;

        // Aparently, detecting if actor reached its destination is not working.

        float newHip = (float) Math.sqrt( Math.pow((newPosX-actorPos.x), 2) +
                Math.pow((newPosY-actorPos.y), 2) );

        if(newHip > hip) {
            actor.setDerivative(0,0);
        }else{
            actor.setDerivative(cos, sin);
        }
    }

    public boolean VerifyCollision(Actor actor1, Actor actor2){
        PointF actPos1 = actorsLocation.get(actor1);
        PointF actPos2 = actorsLocation.get(actor2);

        PointF mask1 = actor1.getMask();
        PointF mask2 = actor2.getMask();


        return false;
    }

}

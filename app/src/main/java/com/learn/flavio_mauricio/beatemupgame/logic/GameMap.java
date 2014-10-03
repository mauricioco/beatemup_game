package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * The world where all other game objects reside. It also is responsible for
 * controlling them. The world is self-aware...
 */
public class GameMap extends GameObject {
    private int width;
    private int height;
    private Background background;
    private Actor player = null;
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
        actor.setCurrentMap(this);
        actorsLocation.put(actor, new PointF(x, y));
    }

    public void putPlayerAt(Actor player, float x, float y) {
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

    public PointF getActorPosition(Actor actor) {
        return actorsLocation.get(actor);
    }

    public Background getBackground() {
        return background;
    }

    public float getHeight() {
        return height;
    }

    public Actor getPlayer() {
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

    public boolean isInside(float x, float y) {
        return isInsideHorizontal(x) && isInsideVertical(x, y);
    }

    public boolean isInsideHorizontal(float x) {
        return (x >= 0) && (x < width);
    }

    public boolean isInsideVertical(float x, float y) {
        Floor floor = getFloorAt(x);
        return (y >= floor.getFloorLimit()) && (y < height);
    }

    public Actor isColliding(Actor actor) {
        PointF actorMaskBegin = actor.getMaskBegin(this);
        PointF actorMaskEnd = actor.getMaskEnd(this);

        return isColliding(actor, actorMaskBegin, actorMaskEnd);
    }

    public Actor isColliding(Actor actor, PointF maskBegin, PointF maskEnd) {
        for(Actor otherActor : actorList) {
            PointF otherMaskBegin = otherActor.getMaskBegin(this);
            PointF otherMaskEnd = otherActor.getMaskEnd(this);
            if(maskBegin.y < otherMaskEnd.y && maskBegin.y > otherMaskBegin.y ||
                    maskEnd.y > otherMaskBegin.y && maskEnd.y < otherMaskEnd.y) {
                // they are colliding...
                if (maskEnd.x > otherMaskBegin.x && maskEnd.x < otherMaskEnd.x) {
                    // collision from the right
                    //if(actor.getId().equals("player")) System.out.println("from the right!");
                    return otherActor;
                } else if (maskBegin.x <= otherMaskEnd.x && maskBegin.x >= otherMaskBegin.x) {
                    // collision from the left
                    //if(actor.getId().equals("player")) System.out.println("from the left!");
                    return otherActor;
                }
            }

        }
        return null;
    }

    public Actor isAttacking(Actor actor) {
        PointF attackMaskBegin = actor.getMaskBegin(this);
        PointF attackMaskEnd = actor.getMaskEnd(this);
        int attackDir = actor.getDirection();

        attackMaskBegin.x += attackDir*actor.getWidth();
        attackMaskEnd.x += attackDir*actor.getWidth();

        return isColliding(actor, attackMaskBegin, attackMaskEnd);
    }

    public void update() {
        // Iterate over all actors and updates their states
        for(Actor actor : actorList) {
            // Check if it`s alive
            if(actor.getCurrentLife() <= 0) {
                actor.setState(ActorState.Unconscious);
                actor.setDerivative(0,0);
                continue;
            }

            // Check if it`s on hold (attacking, being attacked, etc)
            if(actor.isOnHold()) {
                actor.tickHoldTime();
                continue;
            }else if(actor.isHoldEnded()) {
                actor.tickHoldTime();
                actor.onHoldEnd();
            }else{
                actor.ai();
            }

            // Checks if it has just attacked
            if(actor.getState() == ActorState.Attacking) {
                Actor attackedActor = isAttacking(actor);
                actor.attack(attackedActor);
            }

            // Updates its position based on its derivative.
            updateActorPos(actor);

        }
    }

    private void updateActorPos(Actor actor) {
        PointF actorPos = actorsLocation.get(actor);
        PointF oldPos = new PointF(actorPos.x, actorPos.y);
        PointF newPos = new PointF(actorPos.x + actor.getDx(), actorPos.y);

        // change the location of this check.
        if(actor instanceof EnemyActor){
            if(!isInsideHorizontal(((EnemyActor) actor).getTarget().x) &&
                    !isInsideVertical(((EnemyActor) actor).getTarget().x,
                            ((EnemyActor) actor).getTarget().y)){
                ((EnemyActor) actor).setState(ActorState.Idle);
            }
        }


        if (isInside(newPos.x, newPos.y)) {
            actorPos.x = newPos.x;
        }

        newPos.x = actorPos.x;
        newPos.y = actorPos.y + actor.getDy();
        if (isInsideVertical(newPos.x, newPos.y)) {
            actorPos.y = newPos.y;
        }

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

}

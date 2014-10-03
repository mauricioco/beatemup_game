package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class Game {
/*
    private GameMap currentMap;
    private ArrayList<Actor> actorList;  // list of existing actors
    private Hashtable<Actor, PointF> actorsLocation;
        // each actor is mapped to its current position on current map.
    private Actor player;

    public Game(Actor player, GameMap currentMap) {
        this.actorList = new ArrayList<Actor>();
        this.actorsLocation = new Hashtable<Actor, PointF>();
        this.player = player;
        this.currentMap = currentMap;
    }

    public void putActorAt(Actor actor, float x, float y) {
        actorList.add(actor);
        actor.setCurrentMap(currentMap);
        actorsLocation.put(actor, new PointF(x, y));
    }

    public void putPlayerAt(Actor player, float x, float y) {
        this.player = player;
        this.putActorAt(player, x, y);
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

    public Actor getPlayer() {
        return player;
    }

    public boolean isInside(float x, float y) {
        return isInsideHorizontal(x) && isInsideVertical(x, y);
    }

    public boolean isInsideHorizontal(float x) {
        return (x >= 0) && (x < currentMap.getWidth());
    }

    public boolean isInsideVertical(float x, float y) {
        Floor floor = currentMap.getFloorAt(x);
        return (y >= floor.getFloorLimit()) && (y < currentMap.getHeight());
    }

    public Actor isColliding(Actor actor) {
        PointF actorMaskBegin = actor.getMaskBegin(currentMap);
        PointF actorMaskEnd = actor.getMaskEnd(currentMap);

        return isColliding(actor, actorMaskBegin, actorMaskEnd);
    }

    public Actor isColliding(Actor actor, PointF maskBegin, PointF maskEnd) {
        for(Actor otherActor : actorList) {
            PointF otherMaskBegin = otherActor.getMaskBegin(currentMap);
            PointF otherMaskEnd = otherActor.getMaskEnd(currentMap);
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
    }*/
}

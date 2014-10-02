package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.Random;

public class EnemyActor extends Actor{
    private Random random = new Random();
    private Point target;

    public EnemyActor(String id, int width, int height){
        super(id, width, height);
        target = new Point(0, 0);
        this.speed = 3.f;
        this.currentLife = 5;
    }

    public Point getTarget() {
        return target;
    }

    public boolean onHoldEnd() {
        this.state = state.getState(random.nextInt(4));
        return true;
    }

    public boolean ai() {

        PointF position = currentMap.getActorPos(this);
        Actor playerActor = currentMap.getPlayer();
        PointF player = currentMap.getActorPos(playerActor);

        if(position.x < player.x) {
            this.direction=1;
        }else{
            this.direction=-1;
        }

        if (random.nextInt(100) <= 1) {
            //System.out.println("Perdeu Playboy");
            state = ActorState.Idle;
        }

        if (state == ActorState.Idle){
            //System.out.println("Idle");
            this.target.x = random.nextInt((int) currentMap.getWidth());
            int start = (int) currentMap.getFloorAt(target.x).getFloorLimit();
            this.target.y = start + random.nextInt((int) (currentMap.getHeight() - start));
            setDerivative(0, 0);
            state = state.getState(random.nextInt(4));
        }else{
            switch(state.getId()){
                case 1:
                    //System.out.println("Moving");
                    target.set(30, 40);
                    setMoving(position);
                    return true;
                case 2:
                    //System.out.println("Chasing");
                    setChasing(position, player);
                    return true;
                case 3:
                    System.out.println("Tried to attack");
                    state = ActorState.Attacking;
                    return true;
                default:
                    state = state.getState(random.nextInt(4));
                    return true;
            }
        }

        return false;
    }

    public void setMoving(PointF position) {
        if (position.equals(target)){
            state = ActorState.Idle;
        }else{
            int diffX = (int) (target.x - position.x);
            int directionX;
            if (diffX != 0) {
                directionX = diffX / Math.abs(diffX);
            } else {
                directionX = 0;
            }

            if (Math.abs(diffX) < speed) {
                dx = diffX;
            } else {
                dx = speed*directionX;
            }

            int diffY = (int) (target.y - position.y);
            int directionY;
            if(diffY != 0){
                directionY = diffY/Math.abs(diffY);
            } else {
                directionY = 0;
            }
            if(Math.abs(diffY) < this.speed){
                dy = diffY;
            }else{
                dy = speed*directionY;
            }
        }
    }

    public void setChasing(PointF position, PointF player) {
        if (Math.abs(position.x - player.x) < 32) {
            state = ActorState.Idle;
        }else{
            currentMap.startMovingActorTo(this, player.x, player.y);
            /*
            int diffX = (int) (player.x - position.x);
            int directionX;
            if (diffX != 0) {
                directionX = diffX / Math.abs(diffX);
            } else {
                directionX = 0;
            }

            if (Math.abs(diffX) < speed) {
                dx = diffX;
            } else {
                dx = speed*directionX;
            }

            int diffY = (int) (player.y - position.y);
            int directionY;
            if(diffY != 0){
                directionY = diffY/Math.abs(diffY);
            } else {
                directionY = 0;
            }
            if(Math.abs(diffY) < this.speed){
                dy = diffY;
            }else{
                dy = speed*directionY;
            }
            */
        }
    }

}

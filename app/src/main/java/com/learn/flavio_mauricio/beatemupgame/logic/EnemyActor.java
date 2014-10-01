package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.Random;

enum States {
    Idle(0),
    Moving(1),
    Chasing(2),
    Attacking(3);

    private final int id;
    private static int size = 4;

    States(int value) {
        this.id = value;
    }

    public States getState(int value){
        for(States st : States.values()){
            if (value == st.id){
                return st;
            }
        }
        return null;
    }

    public int getId(){
        return id;
    }

    public static int getSize(){
        return size;
    }

}

public class EnemyActor extends Actor{
    private Random random = new Random();
    private States state = States.Idle;
    private PointF target;
    private GameMap map;

    public EnemyActor(String id, int x, int y, int width, int height){
        super(id, x, y, width, height);
        speed = 3;
        target = new PointF(0, 0);
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public PointF getTarget() {
        return target;
    }

    public Point RandomAI(Actor player){
        if (random.nextInt(100) <= 1) {
            state = States.Idle;

        }

        if (state == States.Idle){
            this.target.x = random.nextInt((int) map.getWidth());
            int start = (int) map.getFloorAt(target.x).getFloorLimit();
            this.target.y = start + random.nextInt((int) (map.getHeight() - start));
            System.out.println(target.x);
            System.out.println(target.y);
            setDerivative(0, 0);
            state = state.getState(random.nextInt(States.getSize()));
        }else{
            switch(state.getId()){
                case 1:
                    Move(target);
                    return null;
                case 2:
                    Chase(player);
                    return null;
                case 3:
                    return Attack(player);
                default:
                    state = state.getState(random.nextInt(States.getSize()));
                    return null;
            }
        }

        return null;
    }

    public void Move(PointF position) {
        if (position.equals(target)){
            state = States.Idle;
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

    public void Chase(Actor player) {
        if (position.equals(player)){
            state = States.Idle;
        }else{
            int diffX = (int) (player.getPositionX() - position.x);
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

            int diffY = (int) (player.getPositionY() - position.y);
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

    public Point Attack(Actor player) {
        int direction = (int) ((player.getPositionX() - position.x)/Math.abs(player.getPositionX() - position.x));
        return null;
    }

    public void setState(States state){
        this.state = state;
    }

}

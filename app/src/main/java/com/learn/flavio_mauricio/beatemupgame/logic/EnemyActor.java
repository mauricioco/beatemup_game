package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;

import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;

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
    private Point target;
    private GameMap map;

    public EnemyActor(String id, int width, int height){
        super(id, width, height);
        target = new Point(0, 0);
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public Point getTarget() {
        return target;
    }

    public Point RandomAI(PointF position, PointF player){
        if (random.nextInt(100) <= 1) {
            System.out.println("Perdeu Playboy");
            state = States.Idle;

        }

        if (state == States.Idle){
            System.out.println("Idle");
            this.target.x = random.nextInt((int) map.getWidth());
            int start = (int) map.getFloorAt(target.x).getFloorLimit();
            this.target.y = start + random.nextInt((int) (map.getHeight() - start));
            setDerivative(0, 0);
            state = state.getState(random.nextInt(States.getSize()));
        }else{
            switch(state.getId()){
                case 1:
                    System.out.println("Moving");
                    target.set(30, 40);
                    Move(position);
                    return null;
                case 2:
                    System.out.println("Chasing");
                    Chase(position, player);
                    return null;
                case 3:
                    System.out.println("Chasing");
                    return Attack(position, player);
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

    public void Chase(PointF position, PointF player) {
        if (position.equals(player)){
            state = States.Idle;
        }else{
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
        }
    }

    public Point Attack(PointF position, PointF player) {
        int direction = (int) ((player.x - position.x)/Math.abs(player.x - position.x));
        return null;
    }

    public void setState(States state){
        System.out.println("Change");
        this.state = state;
    }

}

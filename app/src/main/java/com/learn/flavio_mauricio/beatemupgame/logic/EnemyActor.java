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

    public EnemyActor(String id, int width, int height){
        super(id, width, height);
        target = new Point(0, 0);
    }

    public Point RandomAI(Point position, Point player){
        if (random.nextInt(100) <= 5) {
            state = States.Idle;
        }

        if (state == States.Idle){
            setDerivative(0, 0);
            state = state.getState(random.nextInt(States.getSize()));
        }else{
            switch(state.getId()){
                case 1:
                    target.set(30, 40);
                    Move(position);
                    return null;
                case 2:
                    Chase(position, player);
                    return null;
                case 3:
                    return Attack(position, player);
                default:
                    state = state.getState(random.nextInt(States.getSize()));
                    return null;
            }
        }

        return null;
    }

    public void Move(Point position) {
        if (position.equals(target)){
            state = States.Idle;
        }else{
            int diffX = target.x - position.x;
            int directionX = diffX/Math.abs(diffX);
            if (Math.abs(diffX) < speed) {
                dx = diffX;
            } else {
                dx = speed*directionX;
            }

            int diffY = target.y - position.y;
            int directionY = diffY/Math.abs(diffY);
            if(Math.abs(diffY) < this.speed){
                dy = diffY;
            }else{
                dy = speed*directionY;
            }
        }
    }

    public void Chase(Point position, Point player) {
        if (player.y == position.y){
            if(Math.abs(player.x - position.x) <= width){
                state = States.Attacking;
            } else {
                int direction = (player.x - position.x)/Math.abs(player.x - position.x);
                setDerivative(speed*direction, 0);
            }
        }else{
            int diffX = player.x - position.x;
            int directionX = diffX/Math.abs(diffX);
            if (Math.abs(diffX) < speed) {
                dx = diffX;
            } else {
                int diffY = target.y - position.y;
                int directionY = diffY/Math.abs(diffY);
                if(Math.abs(diffY) < this.speed){
                    dy = diffY;
                }else{
                    dy = speed*directionY;
                }
                if(Math.abs(player.x - position.x) > width){
                    int direction = (player.x - position.x)/Math.abs(player.x - position.x);
                    dx = speed*direction;
                }
            }
        }
    }

    public Point Attack(Point position, Point player) {
        int direction = (player.x - position.x)/Math.abs(player.x - position.x);
        return null;
    }

}

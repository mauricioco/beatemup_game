package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.Bitmap;

import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;

import java.util.Random;

public class EnemyActor extends Actor{
    private Random random;

    public EnemyActor(String id, int width, int height){ super(id, width, height); }

    public void Move(float x, float y) {}

    public void Chase(Actor actor) {}

}

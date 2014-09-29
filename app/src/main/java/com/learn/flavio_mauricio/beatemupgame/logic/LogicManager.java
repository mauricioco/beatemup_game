package com.learn.flavio_mauricio.beatemupgame.logic;

import android.content.res.Resources;

import com.learn.flavio_mauricio.beatemupgame.R;
import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;
import com.learn.flavio_mauricio.beatemupgame.graphic.Sprite;

/**
 * So far, this class is only used to create (statically) resources
 * for the classes in the logic package use.
 */
public class LogicManager {
    static public GameMap defaultInstance(Resources resources, float width, float height) {
        Background bg_sky = new Background("sky");
        Floor fl_grass = new Floor("grass", height/2+height/8, 320);
        IActor player = new IActor("player", 32, 32);
        Actor tree = new IActor("tree", 32, 32);

        GraphicManager.putSprite(bg_sky, new Sprite(bg_sky, resources, R.drawable.background_sky));
        GraphicManager.putSprite(fl_grass, new Sprite(fl_grass, resources, R.drawable.floor_grass));
        GraphicManager.putSprite(tree, new Sprite(tree, resources, R.drawable.tree));
        Sprite playerSprite = new Sprite(player, resources, R.drawable.ic_launcher);
        playerSprite.addBmpAnim(resources, R.drawable.ic_launcher_2);
        playerSprite.addBmpAnim(resources, R.drawable.ic_launcher_3);
        GraphicManager.putSprite(player, playerSprite);

        GameMap gameMap = new GameMap("mapTest", bg_sky, 1280, 720);
        gameMap.putPlayerAt(player, width-64, height-64);
        gameMap.putActorAt(tree, width, height-52);
        gameMap.putActorAt(tree.clone(), width+128, height-64);
        gameMap.putActorAt(tree.clone(), width+12, height-61);
        gameMap.putActorAt(tree.clone(), width+1000, height-2);
        gameMap.putActorAt(tree.clone(), width+1234, height-54);
        gameMap.putActorAt(tree.clone(), width+24, height-0);
        gameMap.putActorAt(tree.clone(), width+124, height-11);
        gameMap.putActorAt(tree.clone(), width+35, height-44);
        gameMap.putActorAt(tree.clone(), width+1200, height-23);
        gameMap.putActorAt(tree.clone(), width+546, height-12);


        gameMap.putFloor(fl_grass);
        gameMap.putFloor(fl_grass);
        gameMap.putFloor(fl_grass);
        gameMap.putFloor(fl_grass);

        return gameMap;
    }
}

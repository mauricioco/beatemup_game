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
    static public GameMap defaultInstance(Resources resources, int width, int height) {
        Background bg_sky = new Background("sky");
        Floor fl_grass = new Floor("grass", height-180, width);
        Floor fl_grass_to_sand = new Floor("grass_to_sand", height-120, width);
        Floor fl_sand = new Floor("sand", height-180, width);
        IActor player = new IActor("player", 32, 32);
        Actor tree = new IActor("tree", 32, 64);
        EnemyActor enemy1 = new EnemyActor("enemy", 32, 32);

        GraphicManager.putSprite(bg_sky, new Sprite(bg_sky, resources, R.drawable.background_sky));
        GraphicManager.putSprite(fl_grass, new Sprite(fl_grass, resources, R.drawable.floor_grass));
        GraphicManager.putSprite(fl_grass_to_sand, new Sprite(fl_grass, resources, R.drawable.floor_grass_to_sand));
        GraphicManager.putSprite(fl_sand, new Sprite(fl_grass, resources, R.drawable.floor_sand));
        GraphicManager.putSprite(tree, new Sprite(tree, resources, R.drawable.tree));
        Sprite playerSprite = new Sprite(player, resources, R.drawable.ic_launcher);
        playerSprite.addBmpAnim(resources, R.drawable.ic_launcher_2);
        playerSprite.addBmpAnim(resources, R.drawable.ic_launcher_3);
        playerSprite.setMaxUpdatesPerFrame(2);
        GraphicManager.putSprite(player, playerSprite);
        Sprite enemySprite = new Sprite(player, resources, R.drawable.ic_enemy);
        enemySprite.addBmpAnim(resources, R.drawable.ic_enemy_2);
        enemySprite.addBmpAnim(resources, R.drawable.ic_enemy_3);
        enemySprite.setMaxUpdatesPerFrame(2);
        GraphicManager.putSprite(enemy1, enemySprite);

        GameMap gameMap = new GameMap("mapTest", bg_sky, fl_grass, width*5, height);
        gameMap.putPlayerAt(player, width-64, height-64);
        gameMap.putActorAt(enemy1, width-10, height-10);
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


        gameMap.putFloor(fl_grass.clone());
        gameMap.putFloor(fl_grass_to_sand.clone());
        gameMap.putFloor(fl_sand.clone());


        return gameMap;
    }
}

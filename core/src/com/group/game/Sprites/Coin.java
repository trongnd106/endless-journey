package com.group.game.Sprites;


import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.group.game.Scenes.Hud;


public class Coin extends InteractiveObject {
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit(Actor mario) {
        Gdx.app.log("Coin", "Collision");
        Hud.addScore(50);
    }
}

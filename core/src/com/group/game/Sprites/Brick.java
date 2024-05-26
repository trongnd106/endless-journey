package com.group.game.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.group.game.RunGame;
import com.group.game.Scenes.Hud;
import com.group.game.Screens.PlayScreen;
import com.group.game.Sprites.InteractiveObject;

public class Brick extends InteractiveObject {
    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCatergoryFilter(RunGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Actor actor) {
        Gdx.app.log("Brick", "Collision");
        setCatergoryFilter(RunGame.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(50);
    }
}


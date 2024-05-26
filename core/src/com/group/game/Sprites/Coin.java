package com.group.game.Sprites;


import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.group.game.RunGame;
import com.group.game.Scenes.Hud;
import com.group.game.Screens.PlayScreen;
import com.group.game.Sprites.Items.ItemDefine;
import com.group.game.Sprites.Items.Mushroom;


public class Coin extends InteractiveObject {
    private final int BLANK_COIN=28;
    private static TiledMapTileSet tileset;
    public Coin(PlayScreen screen, MapObject object){
        super(screen,object);
        tileset=map.getTileSets().getTileSet("mapObject");
        fixture.setUserData(this);
        setCatergoryFilter(RunGame.COIN_BIT);
    }

    @Override
    public void onHeadHit(Actor actor) {
        Gdx.app.log("Coin", "Collision");

        if (getCell().getTile().getId() == BLANK_COIN) {
            //RunGame.manager.get("sound/bump.wav", Sound.class).play();
        }
        else{
            Hud.addScore(50);
            if(object.getProperties().containsKey("mushroom")) {
            screen.spawnItem(new ItemDefine(new Vector2(body.getPosition().x, body.getPosition().y + 16 / RunGame.RSF), Mushroom.class));
            }
        }
        getCell().setTile(tileset.getTile(BLANK_COIN));
    }
}


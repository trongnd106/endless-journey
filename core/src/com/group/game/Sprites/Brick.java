package com.group.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Rectangle;

public class Brick extends InteractiveObject {
    public Brick(World world, TiledMap map, Rectangle bound) {
        super(world, map, bound);
    }
}

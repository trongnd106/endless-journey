package com.group.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.*;
import com.group.game.RunGame;
import com.badlogic.gdx.math.Rectangle;

public abstract class InteractiveObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Body body;
    protected Rectangle bound;

    public InteractiveObject(World world, TiledMap map, Rectangle bound){
        this.world=world;
        this.map=map;
        this.bound=bound;
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        /*bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bound.getX() + bound.getWidth() / 2) / RunGame.RSF, (bound.getY() + bound.getHeight() / 2) / RunGame.RSF);

        body = world.createBody(bdef);

        shape.setAsBox(bound.getWidth() / 2 / RunGame.RSF, bound.getHeight() / 2 / RunGame.RSF);
        fdef.shape = shape;
        fdef.filter.categoryBits=RunGame.OBJECT_BIT;
        body.createFixture(fdef);
*/
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set( ((bound.getX() +bound.getWidth() / 2) / RunGame.RSF),  ((bound.getY() + bound.getHeight() / 2) / RunGame.RSF));

        body = world.createBody(bdef);

        shape.setAsBox( (bound.getWidth() / 2 / RunGame.RSF), (bound.getHeight() / 2 / RunGame.RSF));
        fdef.shape = shape;
        fdef.filter.categoryBits=RunGame.COIN_BIT;
        body.createFixture(fdef).setUserData(this);
    }


}

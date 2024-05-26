package com.group.game.Sprites;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;


public abstract class InteractiveObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;

    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected  FixtureDef fdef;
    protected PlayScreen screen;
    protected MapObject object;
    public InteractiveObject(PlayScreen screen, MapObject object) {
        this.screen=screen;
        this.world = screen.getWorld();
        this.map= screen.getMap();
        this.bounds=((RectangleMapObject) object).getRectangle();
        this.object=object;

        BodyDef bdef = new BodyDef();
         fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / RunGame.RSF, (bounds.getY() + bounds.getHeight() / 2) / RunGame.RSF);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / RunGame.RSF, bounds.getHeight() / 2 / RunGame.RSF);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onHeadHit(Actor actor);
    public void setCatergoryFilter(short filterBit){
        Filter filter=new Filter();
        filter.categoryBits=filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer=(TiledMapTileLayer)  map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x*RunGame.RSF/16),(int)(body.getPosition().y*RunGame.RSF/16));
    }
}



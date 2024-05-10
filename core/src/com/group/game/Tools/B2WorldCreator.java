package com.group.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;
import com.group.game.Sprites.Brick;
import com.group.game.Sprites.Coin;
import com.group.game.enemies.Deathcap;
import com.group.game.enemies.Enemy;
import com.group.game.enemies.Turtle;

public class B2WorldCreator {
    private Array<Deathcap> deathcaps;
    private static  Array<Turtle> turtles;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
//ground
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / RunGame.RSF, (rectangle.getY() + rectangle.getHeight() / 2) / RunGame.RSF);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / RunGame.RSF, rectangle.getHeight() / 2 / RunGame.RSF);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
//pipe
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / RunGame.RSF, (rectangle.getY() + rectangle.getHeight() / 2) / RunGame.RSF);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / RunGame.RSF, rectangle.getHeight() / 2 / RunGame.RSF);
            fdef.shape = shape;
            fdef.filter.categoryBits=RunGame.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create coin bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Coin(world,map,rectangle);
        }

        //create brick bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Brick(world,map,rectangle);
        }
        deathcaps=new Array<Deathcap>();
        for(MapObject object:map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject)object).getRectangle();
            deathcaps.add(new Deathcap(screen,rect.getX()/RunGame.RSF,rect.getY()/RunGame.RSF));
        }
        turtles=new Array<Turtle>();
        for(MapObject object:map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject)object).getRectangle();
            turtles.add(new Turtle(screen,rect.getX()/RunGame.RSF,rect.getY()/RunGame.RSF));
        }


    }

    public Array<Deathcap> getDeathcaps() {
        return deathcaps;
    }
    public static void removeTurtles(Turtle turtle){
        turtles.removeValue(turtle,true);
    }
    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(deathcaps);
        enemies.addAll(turtles);

        return enemies;
    }
}

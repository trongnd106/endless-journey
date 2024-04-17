package com.group.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;

public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / RunGame.RSF, (rectangle.getY() + rectangle.getHeight() / 2) / RunGame.RSF);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / RunGame.RSF, rectangle.getHeight() / 2 / RunGame.RSF);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / RunGame.RSF, (rectangle.getY() + rectangle.getHeight() / 2) / RunGame.RSF);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / RunGame.RSF, rectangle.getHeight() / 2 / RunGame.RSF);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create coin bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / RunGame.RSF, (rectangle.getY() + rectangle.getHeight() / 2) / RunGame.RSF);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / RunGame.RSF, rectangle.getHeight() / 2 / RunGame.RSF);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / RunGame.RSF, (rectangle.getY() + rectangle.getHeight() / 2) / RunGame.RSF);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / RunGame.RSF, rectangle.getHeight() / 2 / RunGame.RSF);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
}

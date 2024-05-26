package com.group.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;
import com.group.game.Tools.B2WorldCreator;
import com.group.game.enemies.FireBall;

public class pipe extends InteractiveObject {
    public pipe(PlayScreen screen, MapObject object) {
        super(screen,object);// sua dau vao
        this.object=object;
        fixture.setUserData(this);
        setCatergoryFilter(RunGame.OBJECT_BIT);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-bounds.getWidth()/2/RunGame.RSF, bounds.getHeight()/RunGame.RSF/2), new Vector2(bounds.getWidth()/2/RunGame.RSF, (bounds.getHeight()/2)/RunGame.RSF));
        fdef.filter.categoryBits=RunGame.PIPE_HEAD_BIT;
        fdef.shape = head;
        // is sensor ? no longer collide with anything
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void onHeadHit(Actor actor) {
        if(object.getProperties().containsKey("gate")){

        }
    }
}

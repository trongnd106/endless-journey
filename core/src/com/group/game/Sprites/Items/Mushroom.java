package com.group.game.Sprites.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.group.game.RunGame;
import com.group.game.Scenes.Hud;
import com.group.game.Screens.PlayScreen;
import com.group.game.Sprites.Actor;

public class Mushroom extends Item {
    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("mushroom"),0,0,16,16);
        velocity=new Vector2(0.7f,0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef=new BodyDef();
        bdef.type=BodyDef.BodyType.DynamicBody;
        bdef.position.set(getX(),getY() );
        body= world.createBody(bdef);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(5/ RunGame.RSF);
        fdef.filter.categoryBits=RunGame.ITEM_BIT;
        fdef.filter.maskBits=RunGame.ACTOR_BIT|
                RunGame.OBJECT_BIT|
                RunGame.GROUND_BIT|
                RunGame.COIN_BIT|
                RunGame.BRICK_BIT;
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        velocity.y=body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }

    @Override
    public void use(Actor actor) {
        // va chạm gọi destroy set destroyed=true sau đó update function chuẩn bị destroy
        destroy();
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        body.setLinearVelocity(velocity);
        Hud.addScore(100);
    }
    
    public void reverseVelocity(boolean x,boolean y){
        if(x)velocity.x=-velocity.x;
        if(y)velocity.y=-velocity.y;;
    }

}

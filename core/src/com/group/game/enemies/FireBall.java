package com.group.game.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;

public class FireBall extends Sprite {
    private World world;
   private PlayScreen screen;
    private Animation<TextureRegion> fire;
    public Body b2body;
    private float stateTime;

    public FireBall(PlayScreen screen,float x,float y){
        this.screen=screen;
        world=this.screen.getWorld();
        setPosition(x,y);
        Array<TextureRegion> frames;
        frames=new Array<TextureRegion>();
        for(int i=4;i<6;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("deathcap"),16*i,0,16,16));
        }
        fire=new Animation(0.2f,frames);
        setBounds(getX(),getY(),16/ RunGame.RSF,16/RunGame.RSF);
        stateTime=0f;
    }
    public void defineFireBall(){
        BodyDef bdef=new BodyDef();
        bdef.type=BodyDef.BodyType.DynamicBody;
        bdef.position.set(getX(),getY() );
        b2body= world.createBody(bdef);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(5/ RunGame.RSF);
        fdef.shape=shape;
        fdef.filter.categoryBits=RunGame.ENEMY_BIT;//vat the
        fdef.filter.maskBits=RunGame.GROUND_BIT |RunGame.BRICK_BIT|RunGame.COIN_BIT|RunGame.ENEMY_BIT|RunGame.OBJECT_BIT|RunGame.ACTOR_BIT;//cac vat the co the va cham

        b2body.createFixture(fdef).setUserData(this);

    }
    public TextureRegion getFrame(float dt) {
        stateTime+=dt;
        return fire.getKeyFrame(stateTime,true);
    }
    public void update(float dt,float x,float y){
        setRegion(getFrame(dt));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        b2body.setLinearVelocity(new Vector2(x,y));
    }


}

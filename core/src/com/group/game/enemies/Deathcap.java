package com.group.game.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;
import com.group.game.Sprites.Actor;

public class Deathcap extends Enemy{
    public enum State{WALKING,SMASH,DIED}
    public  State currentState;
    public State previousState;
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> smashAnimation;
    private TextureRegion stand;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Deathcap(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames=new Array<TextureRegion>();
        for(int i=3;i<6;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("deathcap"),16*i,0,16,16));
        }
        walkAnimation=new Animation(0.2f,frames);
        frames.clear();
        for(int i=6;i<8;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("deathcap"),16*i,0,16,16));
        }
        smashAnimation=new Animation(0.2f,frames);
        currentState=previousState=State.SMASH
        ;
        setBounds(getX(),getY(),16/ RunGame.RSF,16/RunGame.RSF);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef=new BodyDef();
        bdef.type=BodyDef.BodyType.DynamicBody;
        bdef.position.set(getX(),getY() );
        b2body= world.createBody(bdef);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(5/RunGame.RSF);
        fdef.shape=shape;
        fdef.filter.categoryBits=RunGame.ENEMY_BIT;//vat the
        fdef.filter.maskBits=RunGame.GROUND_BIT |RunGame.BRICK_BIT|RunGame.COIN_BIT|RunGame.ENEMY_BIT|RunGame.OBJECT_BIT|RunGame.ACTOR_BIT;//cac vat the co the va cham

       b2body.createFixture(fdef).setUserData(this);

        //create the head dof w=enemy
        PolygonShape head =new PolygonShape();
        Vector2[] vertice=new Vector2[4];
        vertice[0]=new Vector2(-4,8).scl(1/RunGame.RSF);
        vertice[1]=new Vector2(4,8).scl(1/RunGame.RSF);
        vertice[2]=new Vector2(-3,2).scl(1/RunGame.RSF);
        vertice[3]=new Vector2(3,2).scl(1/RunGame.RSF);
        head.set(vertice);

        fdef.shape=head;
        fdef.restitution=1.5f;//do dan hoi
        fdef.filter.categoryBits=RunGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case WALKING:
                region=walkAnimation.getKeyFrame(stateTime,true);
                break;
             case SMASH:
                region=walkAnimation.getKeyFrame(stateTime,true);
                break;
            default:
                region=new TextureRegion(screen.getAtlas().findRegion("deathcap"),16*7,0,16,16);
        }
        if(velocity.x>0&&region.isFlipX()==false){
            region.flip(true,false);
        }
        else if(velocity.x<0&&region.isFlipX()==true){
            region.flip(true,false);
        }
        stateTime=currentState==previousState?stateTime+dt:0;
        previousState=currentState;
        return region;
    }
    @Override
    public void update(float dt) {
        setRegion(getFrame(dt));
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        b2body.setLinearVelocity(velocity);
    }

    @Override
    public void hitOnHead(Actor mario) {

    }

    @Override
    public void onEnemyHit(Enemy enemy) {

    }
}

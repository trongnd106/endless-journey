package com.group.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;
import com.group.game.enemies.Enemy;
import com.group.game.enemies.Turtle;

public class Actor extends Sprite {
    public enum State {STANDING, JUMPING, RUNNING, FALLING, DEATH}
    public State currState;
    public State prevState;
    private Animation<TextureRegion> aRun;
    private TextureRegion aDead;
    private Animation<TextureRegion> aJump;
    private TextureRegion aStand;
    private boolean getRight;
    private float timeofState;

    private World world;
    public Body body;
    private boolean posi;
    //private TextureRegion stand;
    public Actor(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("fox-each"));
        this.world = world;
        currState = State.STANDING;
        prevState = State.STANDING;
        timeofState = 0;
        getRight = true;

        Array<TextureRegion> ani = new Array<TextureRegion>();
        for(int i = 0; i < 4; i++){
            ani.add(new TextureRegion(screen.getAtlas().findRegion("fox-each"),32*i,8,32,36));
            //ani.add(new TextureRegion(getTexture(), i*16, 10, 16,16));
        }
        aRun = new Animation<TextureRegion>(0.1f, ani);
        ani.clear();

        for(int i = 0; i < 4; i++){
            ani.add(new TextureRegion(screen.getAtlas().findRegion("fox-each"),32*i,8,32,36));
            //ani.add(new TextureRegion(getTexture(), i*16, 10, 16,16));
        }
        aJump = new Animation<TextureRegion>(0.1f, ani);

        aStand = new TextureRegion(screen.getAtlas().findRegion("fox-each"),0,8,32,36);
        //aStand = new TextureRegion(getTexture(),0,10,16,16);

        buildActor();

        setBounds(32,32,32/RunGame.RSF, 32/RunGame.RSF);
        setRegion(aStand);
        posi=false;
    }
    private void buildActor(){
        BodyDef bdf = new BodyDef();
        bdf.position.set(32/RunGame.RSF,32/RunGame.RSF);
        bdf.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdf);

        FixtureDef fdf = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/RunGame.RSF);
        fdf.shape = shape;
        fdf.filter.categoryBits=RunGame.ACTOR_BIT;//vat the
        fdf.filter.maskBits=RunGame.GROUND_BIT |RunGame.BRICK_BIT|RunGame.COIN_BIT
                |RunGame.OBJECT_BIT|RunGame.ENEMY_BIT|RunGame.ENEMY_HEAD_BIT|RunGame.ITEM_BIT|RunGame.PIPE_HEAD_BIT;//cac vat the co the va cham

        body.createFixture(fdf).setUserData(this);

        // identify collision objects - tạo cảm biến trên đầu -> nhảy lên va chạm
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/RunGame.RSF, 6/RunGame.RSF), new Vector2(2/RunGame.RSF, 6/RunGame.RSF));
        fdf.filter.categoryBits=RunGame.ACTOR_HEAD_BIT;
        fdf.shape = head;
        // is sensor ? no longer collide with anything
        fdf.isSensor = true;
        body.createFixture(fdf).setUserData(this);
    }

    public void update(float deltatime){
        // System.out.println(getX()+" "+getY());
        if(posi)body.setTransform(15.616586f ,0.30499923f, body.getAngle());
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRegion(getFrame(deltatime));
    }
    public TextureRegion getFrame(float deltatime){
        currState = getState();
        TextureRegion t_region;

        switch(currState){
            case STANDING:
                t_region = aStand;
                break;
            case JUMPING:
                t_region = (TextureRegion) aJump.getKeyFrame(timeofState);
                break;
            case RUNNING:
                t_region = (TextureRegion) aRun.getKeyFrame(timeofState, true);
                break;
            case FALLING:
            default:
                t_region = aStand;
                break;
        }

        // actor run left/right - facing?
        facing(t_region);
        if(currState == prevState)
            timeofState += deltatime;
        else timeofState = 0;

        prevState = currState;

        return t_region;
    }
    public State getState(){
        if(body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && prevState == State.JUMPING))
            return State.JUMPING;
        else if(body.getLinearVelocity().y < 0)
            return State.FALLING;
        else return State.STANDING;
    }
    public void facing(TextureRegion region){
        if((body.getLinearVelocity().x < 0 || !getRight) && !region.isFlipX()){
            region.flip(true, false);
            getRight = false;
        }
        else if((body.getLinearVelocity().x > 0 || getRight) && region.isFlipX()){
            region.flip(true, false);
            getRight = true;
        }
    }
    public void hit(Enemy enemy){
        if(enemy instanceof Turtle && ((Turtle) enemy).getCurrentState()==Turtle.State.STANDING_SHELL){
            ((Turtle)enemy).kick(this.getX()<enemy.getX()?Turtle.KICK_RIGHT_SPEED:Turtle.KICK_LEFT_SPEED);
        }
    }
    public void in(){
        posi=true;
    }
}
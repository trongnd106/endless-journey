package com.group.game.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    public enum State {STANDING, JUMPING, RUNNING, FALLING, DEAD}
    public State currState;
    public State prevState;
    private Animation<TextureRegion> aRun;
    private TextureRegion aDead;
    private Animation<TextureRegion> aJump;
    private TextureRegion aStand;
    private boolean getRight;
    private float timeofState;
    private boolean aIsDead;
    private World world;
    public Body body;
    private boolean chet;

    private float maxHeight;

    public Actor(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("fox-each"));
        this.world = world;
        currState = State.STANDING;
        prevState = State.STANDING;
        timeofState = 0;
        getRight = true;
        maxHeight = 200;  // Giá trị độ cao tối đa

        Array<TextureRegion> ani = new Array<TextureRegion>();
        for(int i = 0; i < 4; i++){
            ani.add(new TextureRegion(screen.getAtlas().findRegion("fox-each"),32*i,8,32,36));
        }
        aRun = new Animation<TextureRegion>(0.1f, ani);
        ani.clear();

        for(int i = 0; i < 4; i++){
            ani.add(new TextureRegion(screen.getAtlas().findRegion("fox-each"),32*i,8,32,36));
        }
        aJump = new Animation<TextureRegion>(0.1f, ani);

        aStand = new TextureRegion(screen.getAtlas().findRegion("fox-each"),0,8,32,36);
        aDead = new TextureRegion(screen.getAtlas().findRegion("fox-each"), 16, 0, 32, 36);
        buildActor();

        setBounds(32,32,32/RunGame.RSF, 32/RunGame.RSF);
        setRegion(aStand);
        aIsDead=false;
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
        // Vật thể
        fdf.filter.categoryBits=RunGame.ACTOR_BIT;
        fdf.filter.maskBits=RunGame.GROUND_BIT |RunGame.BRICK_BIT|RunGame.COIN_BIT
                |RunGame.OBJECT_BIT|RunGame.ENEMY_BIT|RunGame.ENEMY_HEAD_BIT|RunGame.ITEM_BIT|RunGame.PIPE_HEAD_BIT;//cac vat the co the va cham

        body.createFixture(fdf).setUserData(this);

        // identify collision objects - tạo cảm biến trên đầu -> nhảy lên va chạm
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/RunGame.RSF, 6/RunGame.RSF), new Vector2(2/RunGame.RSF, 6/RunGame.RSF));
        fdf.filter.categoryBits=RunGame.ACTOR_HEAD_BIT;
        fdf.shape = head;
        // Có cảm biến ? Va chạm
        fdf.isSensor = true;
        body.createFixture(fdf).setUserData(this);
    }

    public void update(float deltatime){
        if(getY() < 0)
            die();

        // Kiểm tra và giới hạn độ cao của nhân vật
        if(body.getPosition().y > maxHeight / RunGame.RSF) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0); // Dừng chuyển động lên
            body.setTransform(body.getPosition().x, maxHeight / RunGame.RSF, body.getAngle()); // Đưa về độ cao tối đa
        }

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
            case DEAD:
                t_region = aDead;
                break;
            case FALLING:
            default:
                t_region = aStand;
                break;
        }

        // Hướng nhìn của nhân vật chính khi chạy về bên trái/phải
        facing(t_region);
        if(currState == prevState)
            timeofState += deltatime;
        else timeofState = 0;

        prevState = currState;

        return t_region;
    }

    public State getState(){
        if(aIsDead)
            return State.DEAD;
        else if(body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 ))
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
    public void die() {

        if (!isDead()) {
            aIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = RunGame.NOTHING_BIT;

            for (Fixture fixture : body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return aIsDead;
    }

    public float getTimeofState(){
        return timeofState;
    }

    public void hit(Enemy enemy){
        if(enemy instanceof Turtle && ((Turtle) enemy).getCurrentState()==Turtle.State.STANDING_SHELL){
            ((Turtle)enemy).kick(this.getX()<enemy.getX()?Turtle.KICK_RIGHT_SPEED:Turtle.KICK_LEFT_SPEED);
        }
        else{
            die();
        }
    }

    public State getCurrState() {
        return currState;
    }

    public void died(){
        chet=true;
        Filter filter = new Filter();
        filter.maskBits = RunGame.NOTHING_BIT;
        for (Fixture fixture : body.getFixtureList()) {
            fixture.setFilterData(filter);
        }
        body.applyLinearImpulse(new Vector2(0, 2f), body.getWorldCenter(), false);
    }
}

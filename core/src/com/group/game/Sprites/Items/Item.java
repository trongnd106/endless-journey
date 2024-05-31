package com.group.game.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;
import com.group.game.Sprites.Actor;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;//use to destroy an intem
    protected boolean destroyed;
    protected Body body;
    public Item(PlayScreen screen,float x,float y){
        this.screen=screen;
        world=screen.getWorld();
        setPosition(x,y);
        setBounds(getX(),getY(),16/ RunGame.RSF,16/RunGame.RSF);
        defineItem();
        toDestroy=false;
        destroyed=false;
    }
    public abstract void defineItem();

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);//huy item tren screen
            destroyed=true;
        }
    }
    public abstract  void use(Actor actor);//use item
    public void draw(Batch batch ){
        if(!destroyed){
            super.draw(batch);
        }
    }
    public void destroy(){
        toDestroy=true;
    }
    public void reverseVelocity(boolean x,boolean y){
        if(x)velocity.x=-velocity.x;
        if(y)velocity.y=-velocity.y;;
    }
}

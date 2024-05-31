package com.group.game.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import com.group.game.Screens.PlayScreen;
import com.group.game.Sprites.Actor;


public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    public Enemy(PlayScreen screen, float x,float y){
        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        defineEnemy();
        velocity=new Vector2(0.7f,-1);
        // sleep body
        b2body.setActive(false);
    }
    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void hitOnHead(Actor actor);
    public abstract void onEnemyHit(Enemy enemy);
    public void reverseVelocity(boolean x,boolean y){
        if(x)velocity.x=-velocity.x;
        if(y)velocity.y=velocity.y;;
    }
}


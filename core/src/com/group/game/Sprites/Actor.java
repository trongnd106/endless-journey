package com.group.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.group.game.RunGame;
import com.group.game.Screens.PlayScreen;
import com.group.game.enemies.Enemy;

public class Actor extends Sprite {
    private World world;
    public Body body;
    private TextureRegion stand;
    public Actor(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("deathcap"));
        this.world = world;
        buildActor();
        // chưa tính được tọa độ x,y để lắp vào
        stand = new TextureRegion(getTexture(),338,110,16,16);
        setBounds(338,110,16/RunGame.RSF, 16/RunGame.RSF);
        setRegion(stand);
    }
    private void buildActor(){
        BodyDef bdf = new BodyDef();
        bdf.position.set(32/RunGame.RSF, 32/RunGame.RSF);
        bdf.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdf);

        FixtureDef fdf = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/RunGame.RSF);
        fdf.shape = shape;
        fdf.filter.categoryBits=RunGame.ACTOR_BIT;//vat the
        fdf.filter.maskBits=RunGame.GROUND_BIT |RunGame.BRICK_BIT|RunGame.COIN_BIT
                |RunGame.OBJECT_BIT|RunGame.ENEMY_BIT|RunGame.ENEMY_HEAD_BIT|RunGame.ITEM_BIT;//cac vat the co the va cham

        body.createFixture(fdf).setUserData(this);
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
    }
    public void hit(Enemy enemy){

    }
}

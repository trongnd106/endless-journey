package com.group.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.group.game.RunGame;
import com.group.game.Sprites.Actor;
import com.group.game.Sprites.InteractiveObject;
import com.group.game.enemies.Enemy;

    public class WorldContactListener implements ContactListener {//duoc goi tu
        @Override
        public void beginContact(Contact contact) {
            Fixture fixA=contact.getFixtureA();
            Fixture fixB= contact.getFixtureB();
            int cDef=fixA.getFilterData().categoryBits|fixB.getFilterData().categoryBits;

        if(fixA.getUserData()=="head"||fixB.getUserData()=="head"){
            Fixture head=fixA.getUserData()=="head"?fixA:fixB;
            Fixture object=fixA.getUserData()!="head"?fixA:fixB;

            if(object.getUserData()!=null&& InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveObject)object.getUserData()).onHeadHit();
            }
        }
            switch(cDef){

                case RunGame.ENEMY_BIT|RunGame.OBJECT_BIT:
                    if(fixA.getFilterData().categoryBits==RunGame.ENEMY_BIT){
                        ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                    }
                    else{
                        ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                    }
                    break;
                case RunGame.ENEMY_BIT|RunGame.ENEMY_BIT:

                    ((Enemy)fixA.getUserData()).onEnemyHit((Enemy)fixB.getUserData());
                    ((Enemy)fixB.getUserData()).onEnemyHit((Enemy)fixA.getUserData());

                    break;
                case RunGame.ENEMY_HEAD_BIT|RunGame.ACTOR_BIT:
                        if(fixA.getFilterData().categoryBits==RunGame.ENEMY_HEAD_BIT){
                            ((Enemy)fixA.getUserData()).hitOnHead((Actor) fixB.getUserData());
                        }
                        else {
                    ((Enemy)fixB.getUserData()).hitOnHead((Actor) fixA.getUserData());
                }
                        break;
                case RunGame.ENEMY_BIT|RunGame.ACTOR_BIT:
                    if(fixA.getFilterData().categoryBits==RunGame.ACTOR_BIT){
                        ((Actor)fixA.getUserData()).hit((Enemy)fixB.getUserData());
                    }
                    else  ((Actor)fixB.getUserData()).hit((Enemy)fixA.getUserData());
                    break;
            }
        }
        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold manifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse contactImpulse) {

        }


    }

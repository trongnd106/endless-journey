package com.group.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.group.game.RunGame;
import com.group.game.enemies.Enemy;

    public class WorldContactListener implements ContactListener {//duoc goi tu
        @Override
        public void beginContact(Contact contact) {
            Fixture fixA=contact.getFixtureA();
            Fixture fixB= contact.getFixtureB();
            int cDef=fixA.getFilterData().categoryBits|fixB.getFilterData().categoryBits;

//        if(fixA.getUserData()=="head"||fixB.getUserData()=="head"){
//            Fixture head=fixA.getUserData()=="head"?fixA:fixB;
//            Fixture object=fixA.getUserData()!="head"?fixA:fixB;
//
//            if(object.getUserData()!=null&& InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
//                ((InteractiveTileObject)object.getUserData()).onHeadHit();
//            }
//        }
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

                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);

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

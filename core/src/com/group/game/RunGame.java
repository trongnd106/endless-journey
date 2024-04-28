package com.group.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.group.game.Screens.PlayScreen;

public class RunGame extends Game {
	public static final float WIDTH = 400;
	public static final float HEIGHT = 240;
	public static final float RSF = 100;   // resize fit
	public static  final short NOTHING_BIT=0;
	public static final short GROUND_BIT =1;//1
	public static final short ACTOR_BIT=2;//10
	public static final short BRICK_BIT=4;//100
	public static final short COIN_BIT=8;

	public static final short ENEMY_BIT =16;
	public static final short DESTROYED_BIT=64;
	public static final short OBJECT_BIT=32;
	public static final short ENEMY_HEAD_BIT=128;
	public static final short 	ITEM_BIT=256;
	public static final short ACTOR_HEAD_BIT=512;

	public static AssetManager manager;   // for game music
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		// loading game music & sound for all
		manager = new AssetManager();
		manager.load("music/battleThemeA.mp3", Music.class);
//		manager.load("sound/breakblock.wav", Sound.class);
		manager.finishLoading();    // cho tất cả vào hàng đợi, chờ sử dụng - 'manager.get'

		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

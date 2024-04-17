package com.group.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.group.game.Screens.PlayScreen;

public class RunGame extends Game {
	public static final float WIDTH = 400;
	public static final float HEIGHT = 208;
	public static final float RSF = 100;   // resize fit
	public SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
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

//Tung
//test 2

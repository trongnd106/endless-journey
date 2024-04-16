package com.group.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group.game.RunGame;
import com.group.game.Scenes.Hud;

public class PlayScreen implements Screen {
    private RunGame game;
    private Texture texture;
    private Viewport gamePort;
    private OrthographicCamera  gameCam;
    private TmxMapLoader loader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Hud hud;
    public PlayScreen(RunGame game){
        this.game = game;

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(game.WIDTH, game.HEIGHT, gameCam);

        hud = new Hud(game.batch);

        loader = new TmxMapLoader();
        map = loader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
    }
    @Override
    public void show() {

    }

    public void update(float dt){
        handleInput(dt);

        gameCam.update();
        renderer.setView(gameCam);
    }

    public void handleInput(float dt){
        // tạm thời khi chưa có sprite chính
        if(Gdx.input.isTouched()){
            gameCam.position.x += 100*dt;
        }
    }

    @Override
    public void render(float dt) {
        update(dt);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

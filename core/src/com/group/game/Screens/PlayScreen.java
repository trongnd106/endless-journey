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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group.game.RunGame;
import com.group.game.Scenes.Hud;
import com.group.game.Tools.B2WorldCreator;

public class PlayScreen implements Screen {
    private RunGame game;
    private Texture texture;
    private Viewport gamePort;
    private OrthographicCamera  gameCam;
    private TmxMapLoader loader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator b2wc;
    private Hud hud;
    public PlayScreen(RunGame game){
        this.game = game;

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(game.WIDTH / game.RSF, game.HEIGHT / game.RSF, gameCam);

        hud = new Hud(game.batch);

        loader = new TmxMapLoader();
        map = loader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / game.RSF);

        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        b2wc = new B2WorldCreator(this);
    }
    @Override
    public void show() {

    }

    public void update(float dt){
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        gameCam.update();
        renderer.setView(gameCam);
    }

    public void handleInput(float dt){
        // tạm thời khi chưa có sprite chính
        if(Gdx.input.isTouched()){
            gameCam.position.x += 100*dt;
        }
    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void render(float dt) {
        update(dt);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);

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

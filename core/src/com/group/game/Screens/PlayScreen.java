package com.group.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.group.game.Sprites.Actor;
import com.group.game.Tools.B2WorldCreator;
import com.group.game.Tools.WorldContactListener;
import com.group.game.Transition.ScreenTransition;
import com.group.game.enemies.Enemy;
import com.group.game.enemies.FireBall;

public class PlayScreen implements Screen {
    private RunGame game;
    private Texture texture;
    private Viewport gamePort;
    private OrthographicCamera  gameCam;
    private TmxMapLoader loader;
    private TiledMap map;
    private TextureAtlas atlas;
    private OrthogonalTiledMapRenderer renderer;
    private Music music;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator b2wc;
    private Hud hud;
    private Actor actor;
    public static boolean trasition;
    private ScreenTransition st;
    private Animation<TextureRegion> img;
    private Viewport vp;
    private FireBall fireBall;
    private float delta;

    public PlayScreen(RunGame game){
        this.game = game;

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(game.WIDTH / game.RSF, game.HEIGHT / game.RSF, gameCam);

        atlas = new TextureAtlas("Human.pack");
        hud = new Hud(game.batch);

        loader = new TmxMapLoader();
        map = loader.load("map.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / game.RSF);

        gameCam.position.set(game.WIDTH/2/game.RSF, game.HEIGHT/2/game.RSF, 0);

        world = new World(new Vector2(0, -10), true);
        // tao actor sau khi co world -> tranh dao thu tu code gay loi
        actor = new Actor(world, this);
        b2dr = new Box2DDebugRenderer();

        b2wc = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());

        // get music
        music = RunGame.manager.get("music/battleThemeA.mp3", Music.class);
        music.setLooping(true);
        music.play();
        trasition=false;
       // st=new ScreenTransition()
        img=GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("you.gif").read());
        vp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        delta=0;
    }
    @Override
    public void show() {

    }

    public void update(float dt){
        handleInput(dt);

        world.step(1 / 60f, 6, 2);
        actor.update(dt);
        gameCam.position.x = actor.body.getPosition().x;

        for(Enemy enemy: b2wc.getEnemies()){
            enemy.update(dt);
            if (enemy.getX() < actor.getX() + 14 * 16 / RunGame.RSF) {
                enemy.b2body.setActive(true);//wake up
            }
        }
        gameCam.update();

        renderer.setView(gameCam);
        delta+=dt;
    }

    // xử lí sự kiện đầu vào click,press
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            actor.body.applyLinearImpulse(new Vector2(0,3f), actor.body.getWorldCenter(), true);

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && actor.body.getLinearVelocity().x <= 2)
            actor.body.applyLinearImpulse(new Vector2(0.1f, 0), actor.body.getWorldCenter(), true);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && actor.body.getLinearVelocity().x >= -2)
            actor.body.applyLinearImpulse(new Vector2(-0.1f, 0), actor.body.getWorldCenter(), true);
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
        game.batch.begin();
        game.batch.draw(img.getKeyFrame(delta), 0, 0f);
        game.batch.end();
        renderer.render();

        b2dr.render(world, gameCam.combined);


        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        actor.draw(game.batch);

        for(Enemy enemy:b2wc.getEnemies()){
            enemy.draw(game.batch);
        }
        game.batch.end();


        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public TextureAtlas getAtlas(){
        return atlas;
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

package com.group.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group.game.RunGame;

public class Menu implements Screen {
    private Viewport viewport;
    private Viewport vp;
    private Stage stage;
    private RunGame game;
    private Skin skin;
    private Animation<TextureRegion> anima ;
    private Texture img;
    private float dt;
    public Menu(RunGame game){
        this.game=game;
        viewport=new FitViewport(RunGame.WIDTH*1.4f,RunGame.HEIGHT*1.4f,new OrthographicCamera());
        stage=new Stage(viewport,((RunGame)game).batch);

        Gdx.input.setInputProcessor(stage);//listen
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label gameTitle = new Label("GAME MENU",skin,"big");
        gameTitle.setSize(RunGame.WIDTH/8, RunGame.HEIGHT/8);
        gameTitle.setPosition(RunGame.WIDTH/2 - gameTitle.getWidth()/2,RunGame.HEIGHT/2 + RunGame.HEIGHT/8);
        gameTitle.setAlignment(Align.center);

        TextButton playButton = new TextButton("Play", skin,"small");
        TextButton settingsButton = new TextButton("Introduce", skin,"small");
        TextButton exitButton = new TextButton("Exit", skin,"small");

        // Add listeners to buttons
        playButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                // Switch to play screen
                game.setScreen(new PlayScreen(game));
                // transition to the game screen, for example
            }
            return true;
        });

        settingsButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                // Switch to settings screen

                // transition to the settings screen
            }
            return true;
        });

        exitButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                // Exit the application
                Gdx.app.exit();
            }
            return true;
        });

        table.add(gameTitle);
        table.row().pad(10, 0, 10, 0);;
        // Add buttons to table
        table.add(playButton).uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(settingsButton).uniformX();
        table.row();
        table.add(exitButton).uniformX();

        img=new Texture("325020.jpg");
        vp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        anima = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("shin.gif").read());
        dt=0;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        dt+=v;
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(vp.getCamera().combined);

        game.batch.begin();
        //game.batch.draw(img, 0, 0, vp.getWorldWidth(), vp.getWorldHeight());
        game.batch.draw(anima.getKeyFrame(dt), 0, 0f,vp.getWorldWidth(), vp.getWorldHeight());
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        vp.update(width, height, true);;
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
        stage.dispose();
    }
}

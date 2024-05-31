package com.group.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group.game.RunGame;

import java.awt.*;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch batch;

    private Game game;
    private int currentScore;
    private static int highScore;
    private BitmapFont fontScore;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(RunGame.WIDTH, RunGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((RunGame) game).batch);
        batch = new SpriteBatch();
        fontScore = new BitmapFont();

        currentScore = ((PlayScreen) game.getScreen()).getScore();
        highScore = loadHighScore();

        // Tạo texture từ ảnh nền
        Texture backgroundTexture = new Texture("over.jpg");

        // Tạo ảnh nền từ texture 
        Image background = new Image(backgroundTexture);
        background.setFillParent(true);

        // Tạo table để chứa cả Label và Image
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        // Tạo style cho Label
        Label.LabelStyle font = new Label.LabelStyle();
        font.font = new BitmapFont();
        font.fontColor = new Color(0.8f, 1f, 0.7f, 1f);

        // Tạo Label "GAME OVER"
        Label gameOverLabel = new Label("GAME OVER", font);

        // Tạo Label "Click to Play Again"
        Label playAgainLabel = new Label("Click to Play Again", font);

        // Tạo Label "Score"
        Label scoreLabel = new Label("Score: " + currentScore, font);

        // Tạo Label "High Score"
        Label highScoreLabel = new Label("High Score: " + highScore, font);

        // Thêm Label và Image vào table
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);
        table.row();
        table.add(scoreLabel).expandX().padTop(10f);
        table.row();
        table.add(highScoreLabel).expandX().padTop(10f);

        // Thêm table và ảnh nền vào stage
        stage.addActor(background);
        stage.addActor(table);
    }

    private int loadHighScore(){
        if(currentScore > highScore)
        return currentScore;
        return highScore;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if(Gdx.input.justTouched()){
            game.setScreen(new PlayScreen((RunGame) game));
            dispose();
        }
        Gdx.gl.glClearColor( 0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
        batch.dispose();
        fontScore.dispose();
    }
}

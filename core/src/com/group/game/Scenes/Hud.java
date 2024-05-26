package com.group.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group.game.RunGame;

public class Hud implements Disposable {
    public Stage stage;
    private Integer worldTimer;
    public Integer minutes;
    public Integer seconds;
    private float timeCount;
    private static Integer score;
    private Viewport viewport;
    private OrthographicCamera cam;
    public Label countimelb;
    public static Label scorelb;
    private Label manlb;
    private Label timelb;
    public Hud(SpriteBatch sb){
        worldTimer = 0; timeCount = 0; score = 0;
        minutes = 0;
        seconds = 0;
        cam = new OrthographicCamera();
        viewport = new FitViewport(RunGame.WIDTH, RunGame.HEIGHT, cam);
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);


        countimelb = new Label(String.format("%02d:%02d", minutes, seconds), new Label.LabelStyle(new BitmapFont(), new Color(0.7f, 0.7f, 1f, 1f)));
        scorelb =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), new Color(0.7f, 0.7f, 1f, 1f)));
        timelb = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.BROWN));
        manlb = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.BROWN));


        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(manlb).expandX().padTop(10);
        table.add(timelb).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scorelb).expandX();
        table.add(countimelb).expandX();
        stage.addActor(table);
    }


    public void updateScore(int score) {
        scorelb.setText(String.format("%06d", score));
    }
    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            seconds++;
            if (seconds >= 60) {
                minutes++;
                seconds = 0;
            }
            countimelb.setText(String.format("%02d:%02d", minutes, seconds));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scorelb.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

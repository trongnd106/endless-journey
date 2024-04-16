package com.group.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group.game.RunGame;

public class Hud {
    public Stage stage;
    private int worldTimer;
    private float timeCount;
    private static int score;
    private Viewport viewport;
    private OrthographicCamera cam;
    private Label countdownlb;
    private Label timelb;
    private Label scorelb;
    private Label levellb;
    private Label worldlb;
    private Label manlb;
    public Hud(SpriteBatch sb){
        worldTimer = 300; timeCount = 0; score = 0;
        cam = new OrthographicCamera();
        viewport = new FitViewport(RunGame.WIDTH, RunGame.HEIGHT, cam);
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        stage.addActor(table);
    }
}

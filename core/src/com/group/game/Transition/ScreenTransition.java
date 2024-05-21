package com.group.game.Transition;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group.game.RunGame;

public class ScreenTransition {
    private float alpha;
    public static boolean isTransitioning;
    private RunGame game;
    private Screen currentScreen, nextScreen;

    public ScreenTransition(){

    }
    public ScreenTransition(Screen current, Screen next, RunGame game) {
        this.currentScreen = current;
        this.nextScreen = next;
        this.game=game;
        alpha = 0;
        isTransitioning = false;
    }

    public void update(float delta) {
        if (isTransitioning) {
            alpha += delta;
            if (alpha > 1) {
                isTransitioning = false;
                // Đặt màn hình tiếp theo là màn hình hiện tại
                // (Có thể thêm logic để dọn dẹp màn hình cũ ở đây)
            }
        }
    }

    public void render(float dt) {
        if (isTransitioning) {
            currentScreen.render(dt);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            game.batch.begin();
            game.batch.setColor(1, 1, 1, alpha);
            nextScreen.render(dt);
            game.batch.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } else {
            nextScreen.render(dt);
        }
    }

}

package com.group.game.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

public class ItemDefine {
    public Vector2 position;
    public Class<?> type;
    public ItemDefine(Vector2 position, Class<?> type){
        this.position=position;
        this.type=type;
    }
}

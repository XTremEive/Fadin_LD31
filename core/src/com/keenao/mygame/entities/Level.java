package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

public class Level extends Entity {

    public Level() {
        super("level", -1, -1, 0, 0);
    }

    @Override
    public void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager) {

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {

    }
    private int value;

    public Level increment()
    {
        // Set
        value += 1;

        // Return
        return this;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

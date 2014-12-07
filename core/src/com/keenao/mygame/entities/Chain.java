package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

public class Chain extends Entity{
    private int value;

    public Chain() {
        super("chain", -1, -1, 0, 0);
    }

    public void reset()
    {
        value = 1;
    }

    public void increment()
    {
        ++value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager) {

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {

    }
}

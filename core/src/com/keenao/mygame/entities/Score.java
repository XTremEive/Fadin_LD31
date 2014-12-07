package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

import java.util.ArrayList;

public class Score extends Entity {
    private double value;

    public Score() {
        super("score", -1, -1, 0, 0);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Score increment(double by)
    {
        // Set
        value += by;

        // Return
        return this;
    }

    @Override
    public void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager) {
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
    }
}

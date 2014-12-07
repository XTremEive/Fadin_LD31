package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

public class Effect extends Visual {
    public Effect(String asset, Color color, int positionX, int positionY, int sizeX, int sizeY) {
        super(asset, "effect", positionX, positionY, sizeX, sizeY);

        setColor(color);
    }

    @Override
    public void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager) {
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        getSprite().setSize(getSizeX(), getSizeY());
        getSprite().setCenter(getPositionX(), getPositionY());
        getSprite().draw(spriteBatch);
    }
}

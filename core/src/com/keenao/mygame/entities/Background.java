package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.Color;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

public class Background extends Visual {
    public Background(String asset, int positionX, int positionY, int sizeX, int sizeY) {
        super(asset, "background", positionX, positionY, sizeX, sizeY);
    }

    @Override
    public void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager) {

    }
}

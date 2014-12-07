package com.keenao.mygame.entities.items;

import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

public class Player extends Item {

    private static final int PLAYER_ADVANTAGE = 1;

    public Player(String asset, String backgroundAsset, int positionX, int positionY, int sizeX, int sizeY) {
        super(asset, backgroundAsset, "player", -1, positionX, positionY, sizeX, sizeY);
    }

    @Override
    public void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager) {
        // Controls
        if (inputManager.isDown("6"))
        {
            setVelocityX(MOVEMENT_SPEED + PLAYER_ADVANTAGE);
            setVelocityY(0);
        }
        if(inputManager.isDown("4"))
        {
            setVelocityX(-MOVEMENT_SPEED - PLAYER_ADVANTAGE);
            setVelocityY(0);
        }
        if(inputManager.isDown("8"))
        {
            setVelocityX(0);
            setVelocityY(+MOVEMENT_SPEED + PLAYER_ADVANTAGE);
        }
        if(inputManager.isDown("2"))
        {
            setVelocityX(0);
            setVelocityY(-MOVEMENT_SPEED - PLAYER_ADVANTAGE);
        }

        // Default
        super.update(inputManager, timeManager, soundManager);
    }
}

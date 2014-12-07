package com.keenao.mygame.entities.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.keenao.mygame.entities.Visual;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;
import com.keenao.mygame.utils.ColorUtil;

public abstract class Item extends Visual {
    public static final int MOVEMENT_SPEED = 10;
    private Sprite backgroundSprite;

    private int velocityX;
    private int velocityY;
    private int element;

    public static Color getElementColor(int element) {
        return element < 0 ? new Color(Color.WHITE) : ColorUtil.hsvToRgb(element / 360.0f, 0.6f, 0.8f);
    }

    protected Item(String asset, String backgroundAsset, String type, int element, int positionX, int positionY, int sizeX, int sizeY) {
        super(asset, type, positionX, positionY, sizeX, sizeY);

        this.element = element;

        // Initialize
        if (null != backgroundAsset) {
            this.backgroundSprite = new Sprite(new Texture(backgroundAsset + ".png"));
        }
    }

    public int getVelocityX() { return velocityX; }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void resetElement()
    {
        element = -1;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public int getElement() {
        return element;
    }

    @Override
    public void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager) {
        // Position
        setPositionX(getPositionX() + getVelocityX());
        setPositionY(getPositionY() + getVelocityY());

        // Update aspect
        setColor(getElementColor(getElement()));
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if (null != backgroundSprite)
        {
            backgroundSprite.setColor(getSprite().getColor());
            backgroundSprite.setCenter(backgroundSprite.getWidth() / 2, backgroundSprite.getHeight() / 2);
            backgroundSprite.setPosition(getPositionX() - backgroundSprite.getWidth() / 2 + getSprite().getWidth() / 2, getPositionY() - backgroundSprite.getHeight() / 2 + getSprite().getWidth() / 2);
            backgroundSprite.draw(spriteBatch);
        }

        // Default
        super.draw(spriteBatch);
    }
}

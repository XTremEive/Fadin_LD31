package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.MyGame;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

public abstract class Entity {
    private String type;
    private int positionX;
    private int positionY;
    private int sizeX;
    private int sizeY;
    private MyGame game;

    protected Entity(String type, int positionX, int positionY, int sizeX, int sizeY) {
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public abstract void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager);
    public abstract void draw(SpriteBatch spriteBatch);

    public boolean is(String type)
    {
        return getType().equals(type);
    }

    public Rectangle getRectangle() {
        return new Rectangle(positionX, positionY, sizeX, sizeY);
    }

    public void setGame(MyGame game) {
        this.game = game;
    }

    public MyGame getGame() {
        return game;
    }

    public String getType() {
        return type;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getSizeY() {
        return sizeY;
    }
}

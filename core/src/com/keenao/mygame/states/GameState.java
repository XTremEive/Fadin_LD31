package com.keenao.mygame.states;

import com.keenao.mygame.MyGame;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

public abstract class GameState {
    private MyGame game;

    protected GameState(MyGame game) {
        this.game = game;
    }

    public abstract void reset();
    public abstract void update();

    public MyGame getGame() {
        return game;
    }

    public void setGame(MyGame game) {
        this.game = game;
    }
}

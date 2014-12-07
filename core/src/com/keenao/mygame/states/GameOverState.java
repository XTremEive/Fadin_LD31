package com.keenao.mygame.states;

import com.keenao.mygame.MyGame;

public class GameOverState extends GameState {
    public GameOverState(MyGame game) {
        super(game);
    }

    @Override
    public void reset() {

    }

    @Override
    public void update() {
        if (getGame().getInputManager().isDown("a"))
        {
            getGame().switchToState("gameOver.end");
        }
    }
}

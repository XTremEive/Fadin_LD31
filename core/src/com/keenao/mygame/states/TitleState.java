package com.keenao.mygame.states;

import com.keenao.mygame.MyGame;

public class TitleState extends GameState {
    public TitleState(MyGame game) {
        super(game);
    }

    @Override
    public void reset() {

    }

    @Override
    public void update() {
        if (getGame().getInputManager().isDown("4")
                || getGame().getInputManager().isDown("8")
                || getGame().getInputManager().isDown("6")
                || getGame().getInputManager().isDown("2"))
        {
            getGame().switchToState("title.end");
        }
    }
}

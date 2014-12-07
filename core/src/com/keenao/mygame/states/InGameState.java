package com.keenao.mygame.states;

import com.badlogic.gdx.math.Intersector;
import com.keenao.mygame.MyGame;
import com.keenao.mygame.entities.Entity;
import com.keenao.mygame.entities.items.Block;
import com.keenao.mygame.entities.items.Gem;
import com.keenao.mygame.entities.items.Goal;
import com.keenao.mygame.entities.items.Item;

import java.util.ArrayList;

public class InGameState extends GameState {
    private static final double SCORE_PER_GOAL = 100;
    private final int additionalTime;
    private final int goalPerLevel;
    private int goalCount;

    public InGameState(MyGame game, int additionalTime, int goalPerLevel) {
        super(game);

        this.additionalTime = additionalTime;
        this.goalPerLevel = goalPerLevel;
        this.goalCount = 0;
    }

    @Override
    public void reset() {
        goalCount = 0;
    }

    @Override
    public void update() {
        runCollisionLogic();

        // Time over
        if (getGame().getTimer().getRemainingTime() <= 0)
        {
            getGame().gameOver();
        }

        // Feedback
        getGame().getSoundManager().setBgmVolume(getGame().getTimer().getRemainingTime() / (float)getGame().getTimer().getMaxTime());
    }

    private void runCollisionLogic() {
        // Initialize
        ArrayList<Entity> entitiesToCheck = new ArrayList<>();
        entitiesToCheck.addAll(getGame().getEntities());

        // Create
        for(Entity entity : entitiesToCheck)
        {
            // Collision with player
            if (entity != getGame().getPlayer()) {
                if (Intersector.overlaps(getGame().getPlayer().getRectangle(), entity.getRectangle())) {
                    switch (entity.getType()) {
                        case "goal":
                            collideWithGoal((Goal) entity);
                            break;
                        case "block":
                            collideWithBlock(getGame().getPlayer(), (Block) entity);
                            break;
                        case "gem":
                            collideWithGem((Gem) entity);
                            break;
                    }
                }
            }

            // Collision with blocks
            for (Gem gem : getGame().getGems()) {
                if (entity != gem) {
                    if (Intersector.overlaps(gem.getRectangle(), entity.getRectangle())) {
                        switch (entity.getType()) {
                            case "block":
                                collideWithBlock(gem, (Block) entity);

                                acquireElement(gem, (Block) entity);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void collideWithGoal(Goal goal)
    {
        // Discard unmatched goal requirements
        if (getGame().getPlayer().getElement() != goal.getElement())
        {
            return;
        }

        // Update
        getGame().getScore().increment(SCORE_PER_GOAL * getGame().getChain().getValue());
        getGame().getTimer().increaseRemainingTime(additionalTime * getGame().getChain().getValue());
        getGame().getChain().reset();
        ++goalCount;

        // logic
        getGame().destroyGoal();
        if (goalCount % goalPerLevel == 0) {
            getGame().levelUp();
        }
        getGame().createGoal();

        // Feedback
        getGame().createEffect(goal.getPositionX() + goal.getSizeX() / 2, goal.getPositionY() + goal.getSizeY() / 2, Item.getElementColor(goal.getElement()));
    }

    private void collideWithBlock(Item item, Block block)
    {
        // Velocity
        item.setPositionX(item.getPositionX() - item.getVelocityX());
        item.setPositionY(item.getPositionY() - item.getVelocityY());
        item.setVelocityX(-item.getVelocityX());
        item.setVelocityY(-item.getVelocityY());

        // Feedback
        getGame().getSoundManager().playFx("block_hit");
    }

    public void collideWithGem(Gem gem)
    {
        // Discard
        if (gem.getElement() < 0)
        {
            return;
        }

        getGame().destroyGem(gem);

        if (gem.getElement() == getGame().getPlayer().getElement())
        {
            getGame().getChain().increment();
        } else {
            getGame().getChain().reset();
        }

        acquireElement(getGame().getPlayer(), gem);
        getGame().createGem();
    }

    public void acquireElement(Item receiver, Item sender)
    {
        // Set
        receiver.setElement(sender.getElement());

        // Feedback
        getGame().createEffect(receiver.getPositionX() + receiver.getSizeX() / 2, receiver.getPositionY() + receiver.getSizeY() / 2, Item.getElementColor(receiver.getElement()));
    }
}

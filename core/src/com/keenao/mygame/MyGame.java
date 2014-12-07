package com.keenao.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.keenao.mygame.entities.*;
import com.keenao.mygame.entities.items.*;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;
import com.keenao.mygame.states.GameOverState;
import com.keenao.mygame.states.GameState;
import com.keenao.mygame.states.InGameState;
import com.keenao.mygame.states.TitleState;
import com.keenao.mygame.utils.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MyGame extends ApplicationAdapter {
    private static final int MAX_EFFECT_SIZE = 300;
    private static final int EFFECT_SPEED = 20;
    private static final float SCORE_POSITION_X = 15;
    private static final float SCORE_POSITION_Y = 15;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private InputManager inputManager;
    private TimeManager timeManager;
    private SoundManager soundManager;

    private Configuration configuration;
    private Player player;
    private Score score;
    private Timer timer;
    private Chain chain;
    private Level level;
    private Goal goal;
    private Background splash;
    private ArrayList<Entity> entities;
    private ArrayList<Block> blocks;
    private ArrayList<Gem> gems;
    private HashMap<String, GameState> states;
    private String currentStateIndex;
    private ArrayList<Effect> effects;
    private boolean isTextVisible;

    public int getScreenSizeX()
    {
        return Gdx.graphics.getWidth();
    }

    public int getScreenSizeY()
    {
        return Gdx.graphics.getHeight();
    }

    public void switchToState(String stateIndex)
    {
        // Set
        currentStateIndex = stateIndex;

        // Initialize
        if (states.containsKey(currentStateIndex)) {
            states.get(currentStateIndex).reset();
        }
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public Player getPlayer() {
        return player;
    }

    public Score getScore() {
        return score;
    }

    public Timer getTimer() {
        return timer;
    }

    public Chain getChain() {
        return chain;
    }

    public Level getLevel() {
        return level;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Gem> getGems() {
        return gems;
    }


    public void createPlayer()
    {
        // Compute goal position
        Random random = new Random();
        int positionX = random.nextInt(getScreenSizeX() / configuration.get("gridSize") - 3) * configuration.get("gridSize") + configuration.get("gridSize");
        int positionY = random.nextInt(getScreenSizeY() / configuration.get("gridSize") - 3) * configuration.get("gridSize") + configuration.get("gridSize");

        // Add goal
        add(player = new Player("player", "item", positionX, positionY, configuration.get("gridSize"), configuration.get("gridSize")));

        // Feedback
        soundManager.playFx("player_created");
    }

    public void createEffect(int positionX, int positionY, Color color) {
        Effect effect = new Effect("effect", color, positionX, positionY, 0, 0);
        effects.add(effect);
        add(effect);
    }

    public void destroyEffect(Effect effect)
    {
        effects.remove(effect);
        remove(effect);
    }

    public void destroyPlayer()
    {
        remove(player);
        player = null;
    }

    public void createGoal()
    {
        // Compute goal position
        Random random = new Random();
        int positionX = random.nextInt(getScreenSizeX() / configuration.get("gridSize") - 3) * configuration.get("gridSize") + configuration.get("gridSize");
        int positionY = random.nextInt(getScreenSizeY() / configuration.get("gridSize") - 3) * configuration.get("gridSize") + configuration.get("gridSize");

        // Add goal
        add(goal = new Goal("goal", "item", blocks.get(new Random().nextInt(blocks.size())).getElement(), positionX, positionY, configuration.get("gridSize"), configuration.get("gridSize")));
    }

    public void destroyGoal()
    {
        // Remove
        remove(goal);

        // Feedback
        soundManager.playFx("goal_destroyed");
    }

    public void createGem()
    {
        // Compute goal position
        Random random = new Random();
        int positionX = random.nextInt(getScreenSizeX() / configuration.get("gridSize") - 4) * configuration.get("gridSize") + configuration.get("gridSize") * 2;
        int positionY = random.nextInt(getScreenSizeY() / configuration.get("gridSize") - 4) * configuration.get("gridSize") + configuration.get("gridSize") * 2;

        // Add
        Gem gem = new Gem("gem", "item", blocks.get(new Random().nextInt(blocks.size())).getElement(), positionX, positionY, configuration.get("gridSize"), configuration.get("gridSize"));
        gems.add(gem);
        add(gem);

        // Initialize
        switch (random.nextInt(4))
        {
            case 0:
                gem.setVelocityX(Item.MOVEMENT_SPEED);
                break;
            case 1:
                gem.setVelocityX(-Item.MOVEMENT_SPEED);
                break;
            case 2:
                gem.setVelocityY(Item.MOVEMENT_SPEED);
                break;
            case 3:
                gem.setVelocityY(-Item.MOVEMENT_SPEED);
                break;
        }
    }

    private void refreshGems(int count) {
        // Clear
        remove("gem");

        // Refill
        for(int i = 0; i < count; ++i)
        {
            createGem();
        }
    }

    private void refreshBlocks(int count)
    {
        // Clear
        blocks.clear();
        remove("block");

        // Refill
        int sizeX = getScreenSizeX() / configuration.get("gridSize") - 1;
        int sizeY = getScreenSizeY() / configuration.get("gridSize") - 1;
        int perimeter =  sizeX * 2 + sizeY * 2;
        int elementOffset = new Random().nextInt(180);

        ArrayList<Integer> blocksToPlace = new ArrayList<>();
        for(int i = 0; i < perimeter; ++i)
        {
            blocksToPlace.add(elementOffset + 360 / count * (i % count));
        }

        int i1 = 0;
        while(i1 < sizeX)
        {
            int element = blocksToPlace.get(i1);
            int positionX = i1 * configuration.get("gridSize");
            int positionY = 0;
            Block block = new Block("block", null, element, positionX, positionY, configuration.get("gridSize"), configuration.get("gridSize"));
            blocks.add(block);
            add(block);
            ++i1;
        }

        int i2 = 0;
        while(i2 < sizeY)
        {
            int element = blocksToPlace.get(i1 + i2);
            int positionX = i1 * configuration.get("gridSize");
            int positionY = i2 * configuration.get("gridSize");
            Block block = new Block("block", null, element, positionX, positionY, configuration.get("gridSize"), configuration.get("gridSize"));
            blocks.add(block);
            add(block);
            ++i2;
        }

        int i3 = 0;
        while(i3 < sizeX)
        {
            int element = blocksToPlace.get(i1 + i2 + i3);
            int positionX = i1 * configuration.get("gridSize") - i3 * configuration.get("gridSize");
            int positionY = i2 * configuration.get("gridSize");
            Block block = new Block("block", null, element, positionX, positionY, configuration.get("gridSize"), configuration.get("gridSize"));
            blocks.add(block);
            add(block);
            ++i3;
        }

        int i4 = 0;
        while(i4 < sizeY)
        {
            int element = blocksToPlace.get(i1 + i2 + i3 + i4);
            int positionX = i1 * configuration.get("gridSize") - i3 * configuration.get("gridSize");
            int positionY = i2 * configuration.get("gridSize") - i4 * configuration.get("gridSize");
            Block block = new Block("block", null, element, positionX, positionY, configuration.get("gridSize"), configuration.get("gridSize"));
            blocks.add(block);
            add(block);
            ++i4;
        }
    }

    public MyGame destroyGem(Gem gem)
    {
        // Remove
        remove(gem);

        // Feedback
        soundManager.playFx("gem_destroyed");

        // Return
        return this;
    }


    public void levelUp()
    {
        // Logic
        level.increment();

        // Feedback
        soundManager.playFx("levelUp");

        buildLevel();
    }

    public void gameOver()
    {
        switchToState("inGame.end");
    }

    public void showText()
    {
        isTextVisible = true;
    }

    public void hideText()
    {
        isTextVisible = false;
    }

    private MyGame add(Entity entity) {
        // Add
        entities.add(entity);
        entity.setGame(this);

        // Return
        return this;
    }

    private void buildLevel() {
        refreshBlocks(level.getValue() / 2 + 1);
        refreshGems(level.getValue() / 2 + 1);
        player.resetElement();
    }

    private MyGame remove(String type) {
        ArrayList<Entity> entitiesToCheck = new ArrayList<>();
        entitiesToCheck.addAll(entities);

        for(Entity entity : entitiesToCheck)
        {
            // Filter
            if (!entity.is(type))
            {
                continue;
            }

            // Remove
            remove(entity);
        }

        // Return
        return this;
    }

    private MyGame remove(Entity entity) {
        // Add
        entities.remove(entity);
        entity.setGame(null);

        // Return
        return this;
    }


    @Override
    public void create () {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        // Initialize components
        inputManager = new InputManager();
        inputManager.add("4", Input.Keys.LEFT);
        inputManager.add("6", Input.Keys.RIGHT);
        inputManager.add("8", Input.Keys.UP);
        inputManager.add("2", Input.Keys.DOWN);
        inputManager.add("a", Input.Keys.Q);
        inputManager.add("b", Input.Keys.W);
        inputManager.add("c", Input.Keys.E);
        inputManager.add("s", Input.Keys.ESCAPE);
        inputManager.initialize();

        timeManager = new TimeManager();
        timeManager.initialize();

        soundManager = new SoundManager();
        soundManager.initialize();

        // Load configuration
        configuration = Configuration.parse(Gdx.files.local("configuration.txt").readString());
        configuration.setDefault("initialScore", 0);
        configuration.setDefault("initialLevel", 1);
        configuration.setDefault("initialTime", 25000);
        configuration.setDefault("maxTime", 30000);
        configuration.setDefault("gridSize", 40);
        configuration.setDefault("additionalTime", 7000);
        configuration.setDefault("goalPerLevel", 3);

        // Initialize states
        states = new HashMap<>();
        states.put("title", new TitleState(this));
        states.put("inGame", new InGameState(this, configuration.get("additionalTime"), configuration.get("goalPerLevel")));
        states.put("gameOver", new GameOverState(this));

        // Logic initialize
        entities = new ArrayList<>();
        effects = new ArrayList<>();
        blocks = new ArrayList<>();
        gems = new ArrayList<>();

        // Build
        add(new Background("background", 0, 0, getScreenSizeX(), getScreenSizeY()));
        add(timer = new Timer(configuration.get("maxTime")));
        add(score = new Score());
        add(level = new Level());
        add(chain = new Chain());

        switchToState("title.begin");
    }

    @Override
    public void render () {

        // Update components
        timeManager.update();
        inputManager.update();
        soundManager.update();

        // Logic
        switch(currentStateIndex)
        {
            case "title.begin":
                // Logic
                hideText();
                switchToState("title");
                add(splash = new Background("title", 0, 0, getScreenSizeX(), getScreenSizeY()));

                // Feedback
                soundManager.playFx("title_begin");
                break;
            case "title.end":
                // Logic
                switchToState("inGame.begin");
                showText();
                remove(splash);

                break;
            case "inGame.begin":
                // Logic
                level.setValue(configuration.get("initialLevel"));
                score.setValue(configuration.get("initialScore"));
                timer.setRemainingTime(configuration.get("initialTime"));


                createPlayer();
                buildLevel();
                createGoal();

                // Feedback
                soundManager.playBgm("bgm");

                switchToState("inGame");
                break;
            case "inGame.end":
                // Logic
                destroyPlayer();
                destroyGoal();
                switchToState("gameOver.begin");

                // Feedback
                soundManager.stopAllSounds();
                soundManager.playFx("gameOver_begin");
                break;
            case "gameOver.begin":
                // Logic
                add(splash = new Background("gameOver", 0, 0, getScreenSizeX(), getScreenSizeY()));
                switchToState("gameOver");
                break;
            case "gameOver.end":
                // Logic
                remove(splash);
                switchToState("title.begin");
                break;
            default:
                states.get(currentStateIndex).update();
                break;
        }

        // Exit game
        if (inputManager.isDown("s"))
        {
            Gdx.app.exit();
        }

        // Update
        ArrayList<Entity> entitiesToUpdate = new ArrayList<>();
        entitiesToUpdate.addAll(entities);
        for(Entity entity : entitiesToUpdate)
        {
            entity.update(inputManager, timeManager, soundManager);
        }
        updateEffects();

        // Draw
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        for(Entity entity : entities)
        {
            entity.draw(spriteBatch);
        }
        if (isTextVisible) {
            font.draw(spriteBatch, "Time: " + (timer.getRemainingTime() / 1000) + " | Level: " + getLevel().getValue() + " | Score: " + ((int) getScore().getValue())  + " | Chain: x" + getChain().getValue(), SCORE_POSITION_X, getScreenSizeY() - SCORE_POSITION_Y);
        }
        spriteBatch.end();
    }

    private void updateEffects() {
        ArrayList<Effect> effectToUpdate = new ArrayList<>();
        effectToUpdate.addAll(effects);

        for(Effect effect : effectToUpdate)
        {
            effect.setSizeX(effect.getSizeX() + EFFECT_SPEED);
            effect.setSizeY(effect.getSizeY() + EFFECT_SPEED);
            effect.setAlpha(1 - effect.getSizeX() / (float)MAX_EFFECT_SIZE);

            if (effect.getSizeX() >= MAX_EFFECT_SIZE)
            {
                destroyEffect(effect);
            }
        }
    }

}

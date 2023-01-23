package com.kaffekopp.game.screens.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kaffekopp.game.KurtMarioGame;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.constants.ScreenConstants;

public class Hud implements Disposable {
    private KurtMarioGame game;
    public Stage stage;
    public Viewport viewPort;
    private final SpriteBatch spriteBatch;

    public float timeCount;
    public Integer worldTimer;

    Label timeLabel;
    Label countdownLabel;

    TextButton activeMuteButton;

    // Main
    public int playerScore;
    public int level;

    // Labels
    Label scoreIntLabel;
    Label scoreTextLabel;
    Label levelIntLabel;
    Label levelTextLabel;
    Label itemCountLabel;

    // Itemcount
    public int currentCount;
    public int itemCount;

    // Player one labels
    Label playerOneHpLabel;
    public int playerOneHp;
    public int playerOneMaxHp;

    // Player two
    Label playerTwoHpLabel;
    public int playerTwoHp;
    public int playerTwoMaxHp;

    public Hud(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void initialiseHud(KurtMarioGame game) {
        this.game = game;
        worldTimer = game.getTime();
        timeCount = 0;
        viewPort = new FitViewport(ScreenConstants.V_WIDTH, ScreenConstants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, getSpriteBatch());
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Gdx.input.setInputProcessor(stage);

        // text buttons for mute button.
        TextButtonStyle pauseStyle = new TextButtonStyle();
        pauseStyle .up = new TextureRegionDrawable(new TextureRegion(new Texture(FilePaths.MUTE_BUTTON)));
        pauseStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture(FilePaths.MUTE_BUTTON)));
        pauseStyle.font = new BitmapFont();

        TextButtonStyle playStyle = new TextButtonStyle();
        playStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(FilePaths.PLAY_BUTTON)));
        playStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture(FilePaths.MUTE_BUTTON)));
        playStyle.font = new BitmapFont();
        if(game.isMuted())
            activeMuteButton = new TextButton("", pauseStyle);
        else
            activeMuteButton = new TextButton("", playStyle);
        this.activeMuteButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!game.isMuted()) {
                    activeMuteButton.setStyle(pauseStyle);
                    game.setMuted(true);
                }
                else {
                    activeMuteButton.setStyle(playStyle);
                    game.setMuted(false);
                }
            }
        });


        // soloHud buttons and Labels
        playerScore = 0;
        level = game.getLevel() + 1;
        playerOneHp = 0;
        playerOneMaxHp = 0;
        currentCount = 0;
        itemCount = 0;
        levelTextLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelIntLabel = new Label(String.format("%01d", level), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreIntLabel = new Label(String.format("%08d", playerScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreTextLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerOneHpLabel = new Label("HP: " + String.format("%02d", playerOneHp) + "/" + playerOneMaxHp, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        itemCountLabel = new Label(currentCount + "/" + itemCount, new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //duo hud buttons and labels
        playerTwoHp = 0;
        playerTwoMaxHp = 0;
        playerTwoHpLabel = new Label("HP: " + String.format("%02d", playerTwoHp) + "/" + playerTwoMaxHp, new Label.LabelStyle(new BitmapFont(), Color.WHITE));


    }

    public void createStage() {
        Table table = new Table();
        table.top(); // Aligns to the top of the screen
        table.setFillParent(true); // is the size of the stage
        table.add(new Label("ITEMS", new Label.LabelStyle(new BitmapFont(), Color.WHITE))).expandX().padTop(10);
        table.add(scoreTextLabel).expandX().padTop(10);
        table.add(levelTextLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(itemCountLabel).expandX();
        table.add(scoreIntLabel).expandX();
        table.add(levelIntLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(this.activeMuteButton).width(20f).height(20f).left();
        table.setDebug(false);
        stage.addActor(table);
        table = new Table();
        table.bottom();
        table.setFillParent(true);
        table.add(playerOneHpLabel).expandX();
        if(game.isMultiPlayer())
            table.add(playerTwoHpLabel).expandX();
        stage.addActor(table);
        table = new Table();
        table.setFillParent(true);
        table.add(this.activeMuteButton).width(20f).height(20f).left().top().expand().padTop(10);
        stage.addActor(table);



    }

    public void update(float dt) {
        timeCount += dt;
        if(timeCount >= 1) {
            worldTimer++;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
        updateScore();
        updateLevelLabel();
        updatePlayerOneHp();
        updateItemCount();
        if(game.isMultiPlayer())
            updatePlayerTwoHp();
    }

    public Integer getTime() {
        return worldTimer;
    }

    public void setTime(int time) { this.worldTimer = time; }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public int getScore() {
        return playerScore;
    }
    public void updateScore() {
        scoreIntLabel.setText(String.format("%08d", playerScore));
    }
    public void updateLevelLabel() {
        levelIntLabel.setText(String.format("%02d", level));
    }

    public void updatePlayerOneHp() {
        playerOneHpLabel.setText("HP: " + String.format("%02d", playerOneHp) + "/" + playerOneMaxHp);
    }
    private void updatePlayerTwoHp() {
        playerTwoHpLabel.setText("HP: " + String.format("%02d", playerTwoHp) + "/" + playerTwoMaxHp);
    }
    public void updateItemCount() {
        itemCountLabel.setText(currentCount + "/" + itemCount);
    }

}

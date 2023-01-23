package com.kaffekopp.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kaffekopp.game.KurtMarioGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.kaffekopp.game.constants.FilePaths;

public class MenuScreen implements Screen {

    private Stage stage;
    final KurtMarioGame game;
    final Texture soloButtonActive;
    final Texture soloButtonInactive;
    final Texture duosButtonActive;
    final Texture duosButtonInactive;
    final Texture quitButtonActive;
    final Texture quitButtonInactive;
    final Texture highScoreInactive;
    final Texture highScoreActive;
    final Texture background;

    public MenuScreen (KurtMarioGame game) {
        this.game = game;
        soloButtonActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_SP);
        soloButtonInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_SP);
        duosButtonActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_MP);
        duosButtonInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_MP);
        quitButtonActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_EXIT);
        quitButtonInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_EXIT);
        highScoreInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_HIGH_SCORE);
        highScoreActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_HIGH_SCORE);
        background = new Texture(FilePaths.SCREEN_BACKGROUND);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        float BUTTON_WIDTH =  game.xScaler(0.35);
        float BUTTON_HEIGHT = game.yScaler(0.15);

        TextButton duosButton, soloButton, quitButton, highScoreButton;
        Image backgroundImage = new Image(background);
        Table table = new Table();
        Table table2 = new Table();
        Table table3 = new Table();
        BitmapFont white = new BitmapFont();

        TextButtonStyle quitStyle = new TextButtonStyle();
        TextButtonStyle soloStyle = new TextButtonStyle();
        TextButtonStyle duosStyle = new TextButtonStyle();
        TextButtonStyle highScoreStyle = new TextButtonStyle();

        // Adding a mouseover function to the buttons by changing image
        highScoreStyle.up = new TextureRegionDrawable(new TextureRegion(highScoreInactive));
        highScoreStyle.over = new TextureRegionDrawable(new TextureRegion(highScoreActive));
        soloStyle.up = new TextureRegionDrawable(new TextureRegion(soloButtonInactive));
        soloStyle.over = new TextureRegionDrawable(new TextureRegion(soloButtonActive));
        duosStyle.up = new TextureRegionDrawable(new TextureRegion(duosButtonInactive));
        duosStyle.over = new TextureRegionDrawable(new TextureRegion(duosButtonActive));
        quitStyle.up = new TextureRegionDrawable(new TextureRegion(quitButtonInactive));
        quitStyle.over = new TextureRegionDrawable(new TextureRegion(quitButtonActive));

        duosStyle.font = white;
        soloStyle.font = white;
        quitStyle.font = white;
        highScoreStyle.font = white;

        quitButton = new TextButton("", quitStyle);
        soloButton = new TextButton("", soloStyle);
        duosButton = new TextButton("", duosStyle);
        highScoreButton = new TextButton("", highScoreStyle);

        table2.setFillParent(true);
        table3.setFillParent(true);
        table.setFillParent(true);
        table2.add(backgroundImage);
        table.add(soloButton).height(BUTTON_HEIGHT).width(BUTTON_WIDTH).expandX().fillX().padBottom(game.yScaler(0.05)).row();
        table.add(duosButton).height(BUTTON_HEIGHT).width(BUTTON_WIDTH).padBottom(game.yScaler(0.05)).row();
        table.add(quitButton).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);
        stage.addActor(table2);
        stage.addActor(table3);
        stage.addActor(table);
        table = new Table();
        table.bottom().right().padBottom(game.xScaler(0.02)).padRight(game.xScaler(0.02)).setFillParent(true);
        table.add(highScoreButton).height((float) (BUTTON_HEIGHT * 1.5)).width((float) (BUTTON_WIDTH * 0.45));
        stage.addActor(table);

        // Quit button's functionality; exits the app
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // Singleplayer button's functionality; calls on methods to reset variables saved in game from previous attempts
        // sets the multiplayer field to false, disposes of this screen and sets the screen to a new PlayScreen
        soloButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setMultiPlayer(false);
                game.setLevel(0);
                game.doRestart();
                game.setGameWon(false);
                game.initialize();
                game.setScreen(new PlayScreen(game));
                dispose();
            }
        });

        // Singleplayer button's functionality; calls on methods to reset variables saved in game from previous attempts
        // sets the multiplayer field to true, disposes of this screen and sets the screen to a new PlayScreen
        duosButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setMultiPlayer(true);
                game.setLevel(0);
                game.doRestart();
                game.setGameWon(false);
                game.initialize();
                game.setScreen(new PlayScreen(game));
                dispose();
            }
        });

        // High score button's functionality; disposes of this screen and changes screen to a new HighScoreScreen
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new HighScoreScreen(game));
                dispose();
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        stage.act(v);
        stage.getBatch().begin();
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

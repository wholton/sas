package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.game.SaSGame;
import com.mygdx.game.tween.ActorAccessor;
import com.mygdx.game.tween.MusicAccessor;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.game.util.Assets;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Allows the user to pick between multiple screens to transition to.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public class MainMenuScreen extends AbstractScreen {

  /**
   * The stage on which we will draw the table and its buttons. This must be disposed of.
   */
  protected Stage stage;

  /**
   * The table will hold all of the buttons and be placed on the stage. This will make it easier to
   * align things.
   */
  protected Table table;

  /**
   * The image that will fill the background.
   */
  protected Sprite background;

  /**
   * This manager controls transition effects.
   */
  protected TweenManager tweenManager;

  /**
   * The font used by the heading.
   */
  protected BitmapFont headingFont;

  /**
   * The font used by the buttons.
   */
  protected BitmapFont buttonFont;

  /**
   * The path to the font to be used by the heading and buttons.
   */
  private static final String FONT_PATH = "font/CRAYON__.TTF";

  /**
   * The size of the heading font.
   */
  protected static final int HEADING_FONT_SIZE = 64;

  /**
   * The size of the button font.
   */
  protected static final int BUTTON_FONT_SIZE = 32;

  /**
   * The time it takes for the transitions to complete.
   */
  protected static final float FADE_TIME = .75f;

  /**
   * The spacing between the heading the buttons.
   */
  protected static final float TITLE_SPACE = 64;

  /**
   * The spacing between the buttons.
   */
  protected static final float BUTTON_SPACE = 16;

  @Override
  public void dispose() {
    // TODO: Put these into a skin so that we can pass this off to the asset
    // manager.
    buttonFont.dispose();
    headingFont.dispose();
    stage.dispose();
    super.dispose();
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    // Draw any sprites using the batch
    game.getSpriteBatch().begin();
    background.draw(game.getSpriteBatch());
    game.getSpriteBatch().end();

    // Update the stage using the delta and re-draw it
    stage.act(delta);
    stage.draw();

    // Update the tween manager
    tweenManager.update(delta);
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    stage.getViewport().update(width, height);
    table.invalidateHierarchy();
  }

  @Override
  public void show() {
    super.show();

    // Setup music
    game.getAudioHelper().playMusic(game.getAssetManager().get(Assets.MAIN_MENU_MUSIC, Music.class),
        game.getGamePreferences().getAdjustedMusicVolume(), true, false);

    // Setup background texture
    background = new Sprite(
        game.getAssetManager().get(Assets.MAIN_MENU_BACKGROUND, Texture.class));
    background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    // Setup stage
    ScalingViewport scalingViewPort = new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(),
        Gdx.graphics.getHeight());
    stage = new Stage(scalingViewPort, game.getSpriteBatch());
    Gdx.input.setInputProcessor(stage);

    // Setup table to align elements
    table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    // Setup font
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
        Gdx.files.internal(FONT_PATH));
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = HEADING_FONT_SIZE;
    headingFont = generator.generateFont(parameter);
    parameter.size = BUTTON_FONT_SIZE;
    buttonFont = generator.generateFont(parameter);
    buttonFont.setColor(Color.GRAY);
    generator.dispose();

    // Setup styles
    LabelStyle headingLabelStyle = new LabelStyle();
    headingLabelStyle.font = headingFont;
    TextButtonStyle style = new TextButtonStyle();
    style.font = buttonFont;

    // Setup heading
    Label heading = new Label(SaSGame.NAME, headingLabelStyle);
    table.add(heading).spaceBottom(TITLE_SPACE);
    table.row();

    // Setup new game button
    final TextButton newGameButton = new TextButton("New Game", style);
    newGameButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        // Use tween engine to fade out music and screen
        Tween.registerAccessor(Music.class, new MusicAccessor());
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        // Disable all buttons.
        table.setTouchable(Touchable.disabled);

        TweenCallback nextScreen = new TweenCallback() {
          @Override
          public void onEvent(int type, BaseTween<?> source) {
            game.getAudioHelper().stopMusic();
            game.setScreen(new GameScreen());
          }
        };

        Timeline fadeoutTimeline = Timeline.createSequence().beginParallel();
          for (Actor actor : stage.getActors()) {
            fadeoutTimeline.push(Tween.to(actor, ActorAccessor.ALPHA, FADE_TIME)
                .target(alphaTransparent));
          }
          fadeoutTimeline.push(Tween.to(background, SpriteAccessor.ALPHA, FADE_TIME)
              .target(alphaTransparent))
            .push(Tween.to(game.getAudioHelper().getMusic(), MusicAccessor.VOLUME, FADE_TIME)
                .cast(Music.class))
            .end().setCallback(nextScreen).start(tweenManager);
        return true;
      }
    });
    table.add(newGameButton).spaceBottom(BUTTON_SPACE);
    table.row();

    // Setup continue game button
    final TextButton continueButton = new TextButton("Continue", style);
    continueButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        // Use tween engine to fade out music and screen
        Tween.registerAccessor(Music.class, new MusicAccessor());
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        // Disable all buttons.
        table.setTouchable(Touchable.disabled);

        TweenCallback nextScreen = new TweenCallback() {
          @Override
          public void onEvent(int type, BaseTween<?> source) {
            game.getAudioHelper().stopMusic();
            game.setScreen(new GameScreen());
          }
        };

        Timeline fadeoutTimeline = Timeline.createSequence().beginParallel();
          for (Actor actor : stage.getActors()) {
            fadeoutTimeline.push(Tween.to(actor, ActorAccessor.ALPHA, FADE_TIME)
                .target(alphaTransparent));
          }
          fadeoutTimeline.push(Tween.to(background, SpriteAccessor.ALPHA, FADE_TIME)
              .target(alphaTransparent))
            .push(Tween.to(game.getAudioHelper().getMusic(), MusicAccessor.VOLUME, FADE_TIME)
                .cast(Music.class))
            .end().setCallback(nextScreen).start(tweenManager);
        return true;
      }
    });
    table.add(continueButton).spaceBottom(BUTTON_SPACE);
    table.row();

    // Setup options button
    final TextButton optionsButton = new TextButton("Options", style);
    optionsButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        game.setScreen(new OptionsScreen());
        return true;
      }
    });
    table.add(optionsButton).spaceBottom(BUTTON_SPACE);
    table.row();

    // Setup exit button
    final TextButton exitButton = new TextButton("Exit", style);
    exitButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.exit();
        return true;
      }
    });
    table.add(exitButton).spaceBottom(BUTTON_SPACE);
    table.row();

    // Setup the tween effects
    tweenManager = new TweenManager();
    Tween.registerAccessor(Actor.class, new ActorAccessor());

    // Starts by disabling all buttons. A tween callback will be
    // used to re-enable them at the end.
    table.setTouchable(Touchable.disabled);

    TweenCallback enableButtons = new TweenCallback() {
      @Override
      public void onEvent(int type, BaseTween<?> source) {
        table.setTouchable(Touchable.enabled);
      }
    };

    // Makes a timeline of events, that runs sequentially, and pushes a
    // tween effect that changes the alpha
    // of the header and buttons from transparent to opaque over the fade
    // time.
    Timeline.createSequence().beginParallel()
      .push(Tween.set(newGameButton, ActorAccessor.ALPHA).target(alphaTransparent))
      .push(Tween.set(continueButton, ActorAccessor.ALPHA).target(alphaTransparent))
      .push(Tween.set(optionsButton, ActorAccessor.ALPHA).target(alphaTransparent))
      .push(Tween.set(exitButton, ActorAccessor.ALPHA).target(alphaTransparent))
      .end().start(tweenManager);
    Timeline.createSequence().beginSequence()
    .delay(1)
    .push(Tween.to(newGameButton, ActorAccessor.ALPHA, FADE_TIME).target(alphaOpaque))
    .push(Tween.to(continueButton, ActorAccessor.ALPHA, FADE_TIME).target(alphaOpaque))
    .push(Tween.to(optionsButton, ActorAccessor.ALPHA, FADE_TIME).target(alphaOpaque))
    .push(Tween.to(exitButton, ActorAccessor.ALPHA, FADE_TIME).target(alphaOpaque))
    .end().setCallback(enableButtons).start(tweenManager);

    // This is to get rid of the flicker caused by drawing with the batch
    // then
    // updating the tween in render.
    tweenManager.update(Float.MIN_VALUE);
  }

  @Override
  public boolean keyDown(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void disconnected(Controller controller) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean buttonDown(Controller controller, int buttonCode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean buttonUp(Controller controller, int buttonCode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean axisMoved(Controller controller, int axisCode, float value) {
    // TODO Auto-generated method stub
    return false;
  }
}

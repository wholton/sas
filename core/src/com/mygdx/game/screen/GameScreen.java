package com.mygdx.game.screen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.joystiq.XBox360ControllerCode;
import com.mygdx.game.sprite.AbstractCharacterSprite;
import com.mygdx.game.sprite.NonplayerCharacterSprite;
import com.mygdx.game.sprite.PlayerCharacterSprite;
import com.mygdx.game.sprite.movement.EightWayMovementAnimation;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.SoundEffect;
import com.mygdx.game.util.TextureAtlasHelper;

public class GameScreen extends AbstractScreen {

  private TiledMap map;
  private OrthographicCamera camera;
  private OrthogonalTiledMapRenderer renderer;

  /** The player's character. */
  private PlayerCharacterSprite player;
  
  /** A list of non-player characters. */
  private List<AbstractCharacterSprite> characters;
  
  /** The indices of the background layers. */
  private static final int[] BACKGROUND_LAYER_INDICES = new int[] { 0 }; 
  /** The indices of the foreground layers. */
  private static final int[] FOREGROUND_LAYERS_INDICES = new int[] { 1 };
  private static final int COLLISION_LAYER_INDEX = 0;
  
  /** A scalar for how much the width of the camera is zoomed in. */
  private float viewportWidthScalar;
  /** The default width that the camera is zoomed in. */
  private static final float DEFAULT_VIEWPORT_WIDTH_SCALAR = .25f;
  /** A scalar for how much the height of the camera is zoomed in. */
  private float viewportHeightScalar;
  /** The default height that the camera is zoomed in. */
  private static final float DEFAULT_VIEWPORT_HEIGHT_SCALAR = .25f;
  private float zoomIncrement;
  private static final float DEFAULT_ZOOM_INCREMENT = .02f;
  private static final float MAXIMUM_ZOOM = 1.24f;
  private static final float MINIMUM_ZOOM = .5f;
  private static final float DEFAULT_ZOOM = 1;
  
  private static final String MAP_PATH = "maps/map.tmx";

  public GameScreen() {
    viewportWidthScalar = DEFAULT_VIEWPORT_WIDTH_SCALAR;
    viewportHeightScalar = DEFAULT_VIEWPORT_HEIGHT_SCALAR;
    zoomIncrement = DEFAULT_ZOOM_INCREMENT;
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    // Point the camera at the player
    camera.position.set(player.getX(), player.getY(), 0);
    camera.update();

    // Update the renderer's view
    renderer.setView(camera);

    // Render the background layer
    renderer.render(BACKGROUND_LAYER_INDICES);

    // Draw the main layer
    game.getSpriteBatch().begin();
    for (AbstractCharacterSprite character : characters) {
      character.draw(game.getSpriteBatch());
    }
    game.getSpriteBatch().end();

    // Render the foreground layer
    renderer.render(FOREGROUND_LAYERS_INDICES);
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    camera.viewportWidth = width * viewportWidthScalar;
    camera.viewportHeight = height * viewportHeightScalar;
  }

  @Override
  public void show() {
    super.show();
    
    // Setup the map
    map = new TmxMapLoader().load(MAP_PATH);

    // Setup the camera
    camera = new OrthographicCamera();
    
    // Setup the renderer
    renderer = new OrthogonalTiledMapRenderer(map, game.getSpriteBatch());
   
    // Play music
    game.getAudioHelper().playMusic(game.getAssetManager().get(Assets.GAME_MUSIC, Music.class), 
        game.getGamePreferences().getAdjustedMusicVolume(), true, false);
    
    // Setup the characters
    characters = new LinkedList<AbstractCharacterSprite>();
    
    // Setup the player animation textures
    TextureAtlas textureAtlas = game.getAssetManager().get(Assets.PLAYER_ATLAS, TextureAtlas.class);
    
    Animation moveUp = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up"));
    moveUp.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleUp = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up"));
    idleUp.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveUpRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up right"));
    moveUpRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleUpRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up right"));
    idleUpRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move right"));
    moveRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move right"));
    idleRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveDownRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down right"));
    moveDownRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleDownRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down right"));
    idleDownRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveDown = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down"));
    moveDown.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleDown = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down"));
    idleDown.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveDownLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down left"));
    moveDownLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleDownLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down left"));
    idleDownLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move left"));
    moveLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move left"));
    idleLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveUpLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up left"));
    moveUpLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleUpLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up left"));
    idleUpLeft.setPlayMode(Animation.PlayMode.LOOP);

    EightWayMovementAnimation movementAnimation = new EightWayMovementAnimation(moveUp, 
        idleUp, moveDown, idleDown, moveLeft, idleLeft, moveRight, idleRight, 
        moveUpRight, idleUpRight, moveDownRight, idleDownRight, moveUpLeft, idleUpLeft, 
        moveDownLeft, idleDownLeft);
    
    // Setup the player's sound effects
    // TODO: not satisfied with this
    Map<String, SoundEffect> footstepSoundEffects = new HashMap<String, SoundEffect>();
    
    Sound footstepSound = game.getAssetManager().get(Assets.FOOTSTEP_GRASS_SOUND, Sound.class);
    footstepSoundEffects.put("grass", new SoundEffect(footstepSound, 40, .3f));
    
    footstepSound = game.getAssetManager().get(Assets.FOOTSTEP_ROCKS_SOUND, Sound.class);
    footstepSoundEffects.put("rocks", new SoundEffect(footstepSound, 40, .3f));
    
    TiledMapTileLayer backgroundLayer = (TiledMapTileLayer) map.getLayers()
        .get(COLLISION_LAYER_INDEX);
    
    player = new PlayerCharacterSprite(movementAnimation, footstepSoundEffects, 
        backgroundLayer, characters);
    player.setPosition(200, 200);
    characters.add(player);
    
    // Setup NPCs
    textureAtlas = game.getAssetManager().get(Assets.OGRE_ATLAS, TextureAtlas.class);
    
    moveUp = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up"));
    moveUp.setPlayMode(Animation.PlayMode.LOOP);
    idleUp = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up"));
    idleUp.setPlayMode(Animation.PlayMode.LOOP);
    moveUpRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up right"));
    moveUpRight.setPlayMode(Animation.PlayMode.LOOP);
    idleUpRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up right"));
    idleUpRight.setPlayMode(Animation.PlayMode.LOOP);
    moveRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move right"));
    moveRight.setPlayMode(Animation.PlayMode.LOOP);
    idleRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move right"));
    idleRight.setPlayMode(Animation.PlayMode.LOOP);
    moveDownRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down right"));
    moveDownRight.setPlayMode(Animation.PlayMode.LOOP);
    idleDownRight = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down right"));
    idleDownRight.setPlayMode(Animation.PlayMode.LOOP);
    moveDown = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down"));
    moveDown.setPlayMode(Animation.PlayMode.LOOP);
    idleDown = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down"));
    idleDown.setPlayMode(Animation.PlayMode.LOOP);
    moveDownLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down left"));
    moveDownLeft.setPlayMode(Animation.PlayMode.LOOP);
    idleDownLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move down left"));
    idleDownLeft.setPlayMode(Animation.PlayMode.LOOP);
    moveLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move left"));
    moveLeft.setPlayMode(Animation.PlayMode.LOOP);
    idleLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move left"));
    idleLeft.setPlayMode(Animation.PlayMode.LOOP);
    moveUpLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up left"));
    moveUpLeft.setPlayMode(Animation.PlayMode.LOOP);
    idleUpLeft = new Animation(1 / 5f, TextureAtlasHelper
        .findRegionsContains(textureAtlas.getRegions(), "move up left"));
    idleUpLeft.setPlayMode(Animation.PlayMode.LOOP);

    movementAnimation = new EightWayMovementAnimation(moveUp, 
        idleUp, moveDown, idleDown, moveLeft, idleLeft, moveRight, idleRight, 
        moveUpRight, idleUpRight, moveDownRight, idleDownRight, moveUpLeft, idleUpLeft, 
        moveDownLeft, idleDownLeft);
    
    // TODO: not satisfied with this
    footstepSoundEffects = new HashMap<String, SoundEffect>();
    
    footstepSound = game.getAssetManager().get(Assets.FOOTSTEP_GRASS_SOUND, Sound.class);
    footstepSoundEffects.put("grass", new SoundEffect(footstepSound, 40, .3f));
    
    footstepSound = game.getAssetManager().get(Assets.FOOTSTEP_ROCKS_SOUND, Sound.class);
    footstepSoundEffects.put("rocks", new SoundEffect(footstepSound, 40, .3f));
    
    AbstractCharacterSprite ogre = new NonplayerCharacterSprite(movementAnimation, 
        footstepSoundEffects, backgroundLayer, characters, 250, 250, 
        7 * backgroundLayer.getTileWidth(), 5 * backgroundLayer.getTileWidth(), 
        10 * backgroundLayer.getTileWidth(), player);
    characters.add(ogre);
  }

  @Override
  public void dispose() {
    map.dispose();
    renderer.dispose();
    game.getAudioHelper().stopMusic();
    super.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    Gdx.app.log(logName, "Key down: " + Keys.toString(keycode));
    if (!player.keyDown(keycode)) {
      switch (keycode) {
        case Keys.R:
          game.setScreen(new GameScreen());
          return true;
        case Keys.ESCAPE:
          game.setScreen(new MainMenuScreen());
          return true;
        case Keys.M:
          // resets the zoom
          camera.zoom = DEFAULT_ZOOM;
          return true;
        default:
          return false;
      }
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    Gdx.app.log(logName, "Key up: " + Keys.toString(keycode));
    return player.keyUp(keycode);
  }

  @Override
  public boolean scrolled(int amount) {
    Gdx.app.log(logName, "Scrolled: " + amount);
    camera.zoom += amount * zoomIncrement;
    if (camera.zoom < MINIMUM_ZOOM) {
      camera.zoom = MINIMUM_ZOOM;
    } else if (camera.zoom > MAXIMUM_ZOOM) {
      camera.zoom = MAXIMUM_ZOOM;
    }
    return true;
  }

  @Override
  public void disconnected(Controller controller) {
    Gdx.app.log(logName, "Disconnected");
    // TODO PAUSE if controller is main source
  }

  @Override
  public boolean buttonDown(Controller controller, int buttonCode) {
    Gdx.app.log(logName, "Button down: " 
        + XBox360ControllerCode.toString(buttonCode, false));
    if (!player.buttonDown(controller, buttonCode)) {
      switch (buttonCode) {
        case XBox360ControllerCode.START_BUTTON_CODE:
          game.setScreen(new GameScreen());
          return true;
        case XBox360ControllerCode.BACK_BUTTON_CODE:
          game.setScreen(new MainMenuScreen());
          return true;
        case XBox360ControllerCode.A_BUTTON_CODE:
          // resets the zoom
          camera.zoom = DEFAULT_ZOOM;
          return true;
        case XBox360ControllerCode.LB_BUTTON_CODE:
          camera.zoom -= zoomIncrement;
          if (camera.zoom < MINIMUM_ZOOM) {
            camera.zoom = MINIMUM_ZOOM;
          }
          return true;
        case XBox360ControllerCode.RB_BUTTON_CODE:
          camera.zoom += zoomIncrement;
          if (camera.zoom > MAXIMUM_ZOOM) {
            camera.zoom = MAXIMUM_ZOOM;
          }
          return true;
        default:
          return false;
      }
    }
    return true;
  }

  @Override
  public boolean buttonUp(Controller controller, int buttonCode) {
    Gdx.app.log(logName, "Button up: " 
        + XBox360ControllerCode.toString(buttonCode, false));
    return player.buttonUp(controller, buttonCode);
  }

  @Override
  public boolean axisMoved(Controller controller, int axisCode, float value) {
    return player.axisMoved(controller, axisCode, value);
  }
}

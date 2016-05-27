package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Assets;
import com.mygdx.game.XBox360ControllerCode;
import com.mygdx.game.character.EightWayMovementAnimation;
import com.mygdx.game.character.PlayerCharacter;

public class GameScreen extends AbstractScreen {

  private TiledMap map;
  private OrthographicCamera camera;
  private OrthogonalTiledMapRenderer renderer;

  private TextureAtlas playerAtlas;
  /** The player's character. */
  private PlayerCharacter player;
  
  /** The indices of the background layers. */
  private static final int[] BACKGROUND_LAYER_INDICES = new int[] {0}; 
  /** The indices of the foreground layers. */
  private static final int[] FOREGROUND_LAYERS_INDICES = new int[] {1};
  /** The index of the layer which holds all tiles with the collision property. */
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
  private static final float MAXIMUM_ZOOM = 1.25f;
  private static final float MINIMUM_ZOOM = .5f;

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
    player.draw(game.getSpriteBatch());
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
    map = new TmxMapLoader().load("maps/map.tmx");

    // Setup the camera
    camera = new OrthographicCamera();
    
    // Setup the renderer
    renderer = new OrthogonalTiledMapRenderer(map, game.getSpriteBatch());
   
    // Setup the player animation textures
    playerAtlas = game.getAssetManager().get(Assets.ATLAS, TextureAtlas.class);
    
    Animation moveUp = new Animation(1 / 5f, playerAtlas.findRegions("move up"));
    moveUp.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleUp = new Animation(1 / 5f, playerAtlas.findRegions("move up"));
    idleUp.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveUpRight = new Animation(1 / 5f, playerAtlas.findRegions("move up right"));
    moveUpRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleUpRight = new Animation(1 / 5f, playerAtlas.findRegions("move up right"));
    idleUpRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveRight = new Animation(1 / 5f, playerAtlas.findRegions("move right"));
    moveRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleRight = new Animation(1 / 5f, playerAtlas.findRegions("move right"));
    idleRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveDownRight = new Animation(1 / 5f, playerAtlas.findRegions("move down right"));
    moveDownRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleDownRight = new Animation(1 / 5f, playerAtlas.findRegions("move down right"));
    idleDownRight.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveDown = new Animation(1 / 5f, playerAtlas.findRegions("move down"));
    moveDown.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleDown = new Animation(1 / 5f, playerAtlas.findRegions("move down"));
    idleDown.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveDownLeft = new Animation(1 / 5f, playerAtlas.findRegions("move down left"));
    moveDownLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleDownLeft = new Animation(1 / 5f, playerAtlas.findRegions("move down left"));
    idleDownLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveLeft = new Animation(1 / 5f, playerAtlas.findRegions("move left"));
    moveLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleLeft = new Animation(1 / 5f, playerAtlas.findRegions("move left"));
    idleLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation moveUpLeft = new Animation(1 / 5f, playerAtlas.findRegions("move up left"));
    moveUpLeft.setPlayMode(Animation.PlayMode.LOOP);
    Animation idleUpLeft = new Animation(1 / 5f, playerAtlas.findRegions("move up left"));
    idleUpLeft.setPlayMode(Animation.PlayMode.LOOP);

    EightWayMovementAnimation movementAnimation = new EightWayMovementAnimation(moveUp, 
        idleUp, moveDown, idleDown, moveLeft, idleLeft, moveRight, idleRight, 
        moveUpRight, idleUpRight, moveDownRight, idleDownRight, moveUpLeft, idleUpLeft, 
        moveDownLeft, idleDownLeft);
    
    // Setup the player
    player = new PlayerCharacter(movementAnimation, 
        (TiledMapTileLayer) map.getLayers().get(COLLISION_LAYER_INDEX));
    player.setPosition(player.getCollisionLayer().getTileWidth(), 
        player.getCollisionLayer().getHeight() * player.getCollisionLayer().getTileHeight());
  }

  @Override
  public void dispose() {
    map.dispose();
    renderer.dispose();
    playerAtlas.dispose();
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
          camera.zoom = 1;
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
          camera.zoom = 1;
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

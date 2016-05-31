package com.mygdx.game.sprite;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.IntSet;
import com.mygdx.game.joystiq.XBox360ControllerCode;
import com.mygdx.game.sprite.movement.EightWayMovementAnimation;
import com.mygdx.game.util.SoundEffect;

public class PlayerCharacterSprite extends AbstractCharacterSprite {

  /** A set containing which keys are currently down. */
  protected final IntSet keysDown;
  
  public PlayerCharacterSprite(EightWayMovementAnimation movementAnimation,
      Map<String, SoundEffect> footstepSoundEffects, TiledMapTileLayer collisionLayer, 
      List<AbstractCharacterSprite> characters) {
    super(movementAnimation, footstepSoundEffects, collisionLayer, characters);
    keysDown = new IntSet();
  }
  
  /**
   * Applies Key Down events to the user.
   * @param keycode the keycode of the key which was pressed
   * @return  whether the input was processed
   */
  public boolean keyDown(int keycode) {
    Gdx.app.log(logName, "Key down: " + Keys.toString(keycode));
    keysDown.add(keycode);
    boolean handled = false;
    switch (keycode) {
      case Keys.W:
        velocity.y = speed;
        animationTime = 0;
        handled = true;
        break;
      case Keys.S:
        velocity.y = -speed;
        animationTime = 0;
        handled = true;
        break;
      case Keys.A:
        velocity.x = -speed;
        animationTime = 0;
        handled = true;
        break;
      case Keys.D:
        velocity.x = speed;
        animationTime = 0;
        handled = true;
        break;
      default:
    }
    // If two opposite movement keys are down, they cancel.
    if (keysDown.size > 1) {
      if (keysDown.contains(Keys.A) && keysDown.contains(Keys.D)) {
        velocity.x = 0;
        animationTime = 0; // TODO make sure WS works... r weird results
      }
      if (keysDown.contains(Keys.W) && keysDown.contains(Keys.S)) {
        velocity.y = 0;
        animationTime = 0;
      }
    }
    return handled;
  }

  /**
   * Applies Key Up events to the user.
   * @param keycode the keycode of the key which was pressed
   * @return  whether the input was processed
   */
  public boolean keyUp(int keycode) {
    Gdx.app.log(logName, "Key up: " + Keys.toString(keycode));
    keysDown.remove(keycode);
    boolean handled = false;
    switch (keycode) {
      case Keys.A:
      case Keys.D:
        velocity.x = 0;
        animationTime = 0;
        handled = true;
        break;
      case Keys.W:
      case Keys.S:
        velocity.y = 0;
        animationTime = 0;
        handled = true;
        break;
      default:
    }
    // If two opposite movement keys are down, they cancel. This undoes that when one comes up.
    if (keysDown.size > 0) {
      if (keycode == Keys.S && keysDown.contains(Keys.W)) {
        velocity.y = speed;
        animationTime = 0;
      } else if (keycode == Keys.W && keysDown.contains(Keys.S)) {
        velocity.y = -speed;
        animationTime = 0;
      } else if (keycode == Keys.A && keysDown.contains(Keys.D)) {
        velocity.x = speed;
        animationTime = 0;
      } else if (keycode == Keys.D && keysDown.contains(Keys.A)) {
        velocity.x = -speed;
        animationTime = 0;
      }
    }
    return handled;
  }
  
  /**
   * Applies Button Down events to the user, for controllers.
   * @param controller  the controller that pressed the button
   * @param buttonCode  the code of the button being pressed
   * @return  whether the input was processed
   */
  public boolean buttonDown(Controller controller, int buttonCode) {
    Gdx.app.log(logName, "Button down: " 
        + XBox360ControllerCode.toString(buttonCode, false));
    boolean handled = false;
    switch (buttonCode) {
      default:
    }
    return handled;
  }
  
  /**
   * Applies Button Up events to the user, for controllers.
   * @param controller  the controller that released the button
   * @param buttonCode  the code of the button being released
   * @return  whether the input was processed
   */
  public boolean buttonUp(Controller controller, int buttonCode) {
    Gdx.app.log(logName, "Button up: " 
        + XBox360ControllerCode.toString(buttonCode, false));
    boolean handled = false;
    switch (buttonCode) {
      default:
    }
    return handled;
  }
  
  public boolean axisMoved(Controller controller, int axisCode, float value) {
    boolean handled = false;
    switch (axisCode) {
      case XBox360ControllerCode.LEFT_Y_AXIS_CODE:
        velocity.y = Math.abs(value) < .2 ? 0 : -speed * value;
        animationTime = 0;
        handled = true;
        break;
      case XBox360ControllerCode.LEFT_X_AXIS_CODE:
        velocity.x = Math.abs(value) < .2 ? 0 : speed * value;
        animationTime = 0;
        handled = true;
        break;
      default:
    }
    return handled;
  }

}

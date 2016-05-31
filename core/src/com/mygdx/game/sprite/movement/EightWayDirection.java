package com.mygdx.game.sprite.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * An enum representing the 8 direction input system.
 */
public enum EightWayDirection {
  UP, UP_LEFT, UP_RIGHT, DOWN, DOWN_LEFT, DOWN_RIGHT, LEFT, RIGHT;
  
  /**
   * Returns the direction the character should be facing given their velocity.
   * @param velocity the velocity of the character.
   * @return the direction the player is facing, null if they have no velocity
   */
  public static EightWayDirection getDirection(Vector2 velocity) {
    if (velocity.x < 0 && velocity.y < 0) {
      return DOWN_LEFT;
    } else if (velocity.x < 0 && velocity.y > 0) {
      return UP_LEFT;
    } else if (velocity.x < 0 && velocity.y == 0) {
      return LEFT;
    } else if (velocity.x > 0 && velocity.y < 0) {
      return DOWN_RIGHT;
    } else if (velocity.x > 0 && velocity.y > 0) {
      return UP_RIGHT;
    } else if (velocity.x > 0 && velocity.y == 0) {
      return RIGHT;
    } else if (velocity.x == 0 && velocity.y < 0) {
      return DOWN;
    } else if (velocity.x == 0 && velocity.y > 0) {
      return UP;
    }
    return null;
  }
}

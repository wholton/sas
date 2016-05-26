package com.mygdx.game.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class EightWayMovementAnimation {
  
  /** The upward-facing animation for the character. */
  protected Animation moveUp;
  protected Animation idleUp;
  
  /** The downward-facing animation for the character. */
  protected Animation moveDown;
  protected Animation idleDown;
  
  /** The left-facing animation for the character. */
  protected Animation moveLeft; 
  protected Animation idleLeft;
  
  /** The right-facing animation for the character. */
  protected Animation moveRight;
  protected Animation idleRight;
  
  /** The right-facing animation for the character. */
  protected Animation moveUpRight;
  protected Animation idleUpRight;
  
  /** The right-facing animation for the character. */
  protected Animation moveDownRight;
  protected Animation idleDownRight;
  
  /** The right-facing animation for the character. */
  protected Animation moveUpLeft;
  protected Animation idleUpLeft;
  
  /** The right-facing animation for the character. */
  protected Animation moveDownLeft;
  protected Animation idleDownLeft;

  public EightWayMovementAnimation(Animation moveUp, Animation idleUp, Animation moveDown,
      Animation idleDown, Animation moveLeft, Animation idleLeft, Animation moveRight,
      Animation idleRight, Animation moveUpRight, Animation idleUpRight, Animation moveDownRight,
      Animation idleDownRight, Animation moveUpLeft, Animation idleUpLeft, Animation moveDownLeft,
      Animation idleDownLeft) {
    this.moveUp = moveUp;
    this.idleUp = idleUp;
    this.moveDown = moveDown;
    this.idleDown = idleDown;
    this.moveLeft = moveLeft;
    this.idleLeft = idleLeft;
    this.moveRight = moveRight;
    this.idleRight = idleRight;
    this.moveUpRight = moveUpRight;
    this.idleUpRight = idleUpRight;
    this.moveDownRight = moveDownRight;
    this.idleDownRight = idleDownRight;
    this.moveUpLeft = moveUpLeft;
    this.idleUpLeft = idleUpLeft;
    this.moveDownLeft = moveDownLeft;
    this.idleDownLeft = idleDownLeft;
  }

  /**
   * Gets the movement animation based on the facing and velocity of the sprite.
   * @param facing  Eight-way direction the sprite is facing.
   * @param velocity  X and Y velocity of the sprite.
   * @return  The movement animation corresponding to the sprite's facing and velocity.
   */
  public Animation getCurrentAnimation(EightWayDirection facing, Vector2 velocity) {
    if (velocity.x == 0 && velocity.y == 0) {
      switch (facing) {
        case UP:
          return idleUp;
        case DOWN:
          return idleDown;
        case LEFT:
          return idleLeft;
        case UP_LEFT:
          return idleUpLeft;
        case DOWN_LEFT:
          return idleDownLeft;
        case RIGHT:
          return idleRight;
        case UP_RIGHT:
          return idleUpRight;
        case DOWN_RIGHT:
          return idleDownRight;
        default:
          return idleDown;
      }
    } else {
      switch (facing) {
        case UP:
          return moveUp;
        case DOWN:
          return moveDown;
        case LEFT:
          return moveLeft;
        case UP_LEFT:
          return moveUpLeft;
        case DOWN_LEFT:
          return moveDownLeft;
        case RIGHT:
          return moveRight;
        case UP_RIGHT:
          return moveUpRight;
        case DOWN_RIGHT:
          return moveDownRight;
        default:
          return moveDown;
      }
    }
  }

  public Animation getMoveUp() {
    return moveUp;
  }

  public void setMoveUp(Animation moveUp) {
    this.moveUp = moveUp;
  }

  public Animation getIdleUp() {
    return idleUp;
  }

  public void setIdleUp(Animation idleUp) {
    this.idleUp = idleUp;
  }

  public Animation getMoveDown() {
    return moveDown;
  }

  public void setMoveDown(Animation moveDown) {
    this.moveDown = moveDown;
  }

  public Animation getIdleDown() {
    return idleDown;
  }

  public void setIdleDown(Animation idleDown) {
    this.idleDown = idleDown;
  }

  public Animation getMoveLeft() {
    return moveLeft;
  }

  public void setMoveLeft(Animation moveLeft) {
    this.moveLeft = moveLeft;
  }

  public Animation getIdleLeft() {
    return idleLeft;
  }

  public void setIdleLeft(Animation idleLeft) {
    this.idleLeft = idleLeft;
  }

  public Animation getMoveRight() {
    return moveRight;
  }

  public void setMoveRight(Animation moveRight) {
    this.moveRight = moveRight;
  }

  public Animation getIdleRight() {
    return idleRight;
  }

  public void setIdleRight(Animation idleRight) {
    this.idleRight = idleRight;
  }

  public Animation getMoveUpRight() {
    return moveUpRight;
  }

  public void setMoveUpRight(Animation moveUpRight) {
    this.moveUpRight = moveUpRight;
  }

  public Animation getIdleUpRight() {
    return idleUpRight;
  }

  public void setIdleUpRight(Animation idleUpRight) {
    this.idleUpRight = idleUpRight;
  }

  public Animation getMoveDownRight() {
    return moveDownRight;
  }

  public void setMoveDownRight(Animation moveDownRight) {
    this.moveDownRight = moveDownRight;
  }

  public Animation getIdleDownRight() {
    return idleDownRight;
  }

  public void setIdleDownRight(Animation idleDownRight) {
    this.idleDownRight = idleDownRight;
  }

  public Animation getMoveUpLeft() {
    return moveUpLeft;
  }

  public void setMoveUpLeft(Animation moveUpLeft) {
    this.moveUpLeft = moveUpLeft;
  }

  public Animation getIdleUpLeft() {
    return idleUpLeft;
  }

  public void setIdleUpLeft(Animation idleUpLeft) {
    this.idleUpLeft = idleUpLeft;
  }

  public Animation getMoveDownLeft() {
    return moveDownLeft;
  }

  public void setMoveDownLeft(Animation moveDownLeft) {
    this.moveDownLeft = moveDownLeft;
  }

  public Animation getIdleDownLeft() {
    return idleDownLeft;
  }

  public void setIdleDownLeft(Animation idleDownLeft) {
    this.idleDownLeft = idleDownLeft;
  }
  
}

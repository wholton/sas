package com.mygdx.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class AbstractCharacter extends Sprite {
  
  /** The name this object will be registered as inside the logger. */
  protected final String logName;

  /** the movement velocity. */
  protected final Vector2 velocity;

  /** speed of the character. */
  protected float speed;
  
  /** Time passed, used for finding what frame should be used. */
  protected float animationTime;
  
  /** If the animation is playing. */
  protected boolean animationPlaying;

  /** If the animation should be updated every time it's drawn. */
  protected boolean autoUpdate;
  
  /** Direction the sprite is currently facing, based off of their velocity. */
  protected EightWayDirection facing;

  /** A container for the character's movement animations. */
  EightWayMovementAnimation movementAnimation;
  
  /** The width of the character will be scaled by this value. */
  private float widthScale;
  private static final float DEFAULT_WIDTH_SCALE = .5f;
  
  /** The height of the character will be scaled by this value. */
  private float heightScale;
  private static final float DEFAULT_HEIGHT_SCALE = .5f;
  
  /** Layer for player to check collision against. */
  private TiledMapTileLayer collisionLayer; //TODO: need to be here?

  public static final String COLLISION_PROPERTY_KEY = "collision"; // TODO: move these to enums
  
  /** A set containing which keys are currently down. */
  protected final IntSet keysDown;
 
  /**
   * An abstract character class.
   * @param movementAnimation
   * @param collisionLayer
   */
  public AbstractCharacter(EightWayMovementAnimation movementAnimation, TiledMapTileLayer collisionLayer) {
    super(movementAnimation.getMoveDown().getKeyFrame(0));
    logName = getClass().getSimpleName();
    Gdx.app.log(logName, "Constructor called");
    widthScale = DEFAULT_WIDTH_SCALE;
    heightScale = DEFAULT_HEIGHT_SCALE;
    keysDown = new IntSet();
    velocity = new Vector2();
    speed = 40;
    autoUpdate = true;
    animationPlaying = true;
    this.movementAnimation = movementAnimation;
    this.collisionLayer = collisionLayer;
    facing = EightWayDirection.DOWN;
    setSize(getWidth() * widthScale, getHeight() * heightScale);
  }

  @Override
  public void draw(Batch batch) {
    if (autoUpdate) {
      update();
    }
    
    super.draw(batch);
  }

  /** Updates with the delta time fetched from Gdx.graphics.getDeltaTime() */
  public void update() {
    update(Gdx.graphics.getDeltaTime());
  }
  
  public void update(float delta) {
    // Move in the x direction.
    float oldX = getX();
    setX(getX() + velocity.x * delta);
    
    // check for X collision
    boolean collisionX = false;
    // increment is the distance between each collision point
    float increment = collisionLayer.getTileWidth();
    increment = getWidth() < increment ? getWidth() / 2 : increment / 2;
    if (velocity.x < 0) {
      collisionX = collidesLeft(increment);
    } else if (velocity.x > 0) {
      collisionX = collidesRight(increment);
    }

    // react to x collision
    float oldVelocityX = velocity.x;
    if (collisionX) {
      setX(oldX);
      velocity.x = 0;
    }

    // Move in the y direction.
    float oldY = getY();
    setY(getY() + velocity.y * delta);
    
    boolean collisionY = false;
    // increment is the distance between each collision point 
    increment = collisionLayer.getTileHeight();
    increment = getHeight() < increment ? getHeight() / 2 : increment / 2;
    if (velocity.y < 0) {
      collisionY = collidesBottom(increment);
    } else if (velocity.y > 0) {
      collisionY = collidesTop(increment);
    }
    
    // React to collision
    float oldVelocityY = velocity.y;
    if (collisionY) {
      setY(oldY);
      velocity.y = 0;
    }
    
    // Update which direction the character is facing
    if (velocity.x != 0 || velocity.y != 0) {
      facing = EightWayDirection.getDirection(velocity);
    }
    
    // Update the character's movement animation
    animationTime += delta;
    if (animationPlaying) { 
      setRegion(movementAnimation.getCurrentAnimation(facing, velocity).getKeyFrame(animationTime));
    }
    
    // The collision's velocity change should only apply to the animation logic above
    if (collisionX) {
      velocity.x = oldVelocityX;
    } else if (collisionY) {
      velocity.y = oldVelocityY;
    }
  }

  private boolean isCellBlocked(float x, float y) {
    int tilePositionX = (int) (x / collisionLayer.getTileWidth());
    int tilePositionY = (int) (y / collisionLayer.getTileHeight());
    Cell cell = collisionLayer.getCell(tilePositionX, tilePositionY);
    return cell != null && cell.getTile() != null 
        && cell.getTile().getProperties().containsKey(COLLISION_PROPERTY_KEY);
  }
  
  protected boolean collidesRight(float increment) {
    for (float i = 0; i <= getHeight(); i += increment) {
      if (isCellBlocked(getX() + getWidth(), getY() + i)) {
        return true;
      }
    }
    return false;
  }

  protected boolean collidesLeft(float increment) {
    for (float i = 0; i <= getHeight(); i += increment) {
      if (isCellBlocked(getX(), getY() + i)) {
        return true;
      }
    }
    return false;
  }

  protected boolean collidesTop(float increment) {
    for (float i = 0; i <= getWidth(); i += increment) {
      if (isCellBlocked(getX() + i, getY() + getHeight())) {
        return true;
      }
    }
    return false;
  }

  protected boolean collidesBottom(float increment) {
    for (float i = 0; i <= getWidth(); i += increment) {
      if (isCellBlocked(getX() + i, getY())) {
        return true;
      }
    }
    return false;
  }

  public Vector2 getVelocity() {
    return velocity;
  }

  public void setVelocity(Vector2 velocity) {
    this.velocity.x = velocity.x;
    this.velocity.y = velocity.y;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public TiledMapTileLayer getCollisionLayer() {
    return collisionLayer;
  }

  public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
    this.collisionLayer = collisionLayer;
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

}

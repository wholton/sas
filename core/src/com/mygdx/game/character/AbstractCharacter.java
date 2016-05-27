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

public abstract class AbstractCharacter extends Sprite {
  
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
  protected EightWayMovementAnimation movementAnimation;
  
  /** The width of the character will be scaled by this value. */
  protected float widthScale;
  protected static final float DEFAULT_WIDTH_SCALE = .5f;
  
  /** The height of the character will be scaled by this value. */
  protected float heightScale;
  protected static final float DEFAULT_HEIGHT_SCALE = .5f;
  
  /** Layer for player to check collision against. */
  protected TiledMapTileLayer collisionLayer; //TODO: need to be here?

  protected static final String COLLISION_PROPERTY_KEY = "collision"; // TODO: move these to enums
 
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

}

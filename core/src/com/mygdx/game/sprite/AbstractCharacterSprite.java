package com.mygdx.game.sprite;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.character.AbstractCharacter;
import com.mygdx.game.sprite.movement.EightWayDirection;
import com.mygdx.game.sprite.movement.EightWayMovementAnimation;
import com.mygdx.game.util.SoundEffect;

public abstract class AbstractCharacterSprite extends Sprite {
  
  /** The name this object will be registered as inside the logger. */
  protected final String logName;
  
  /** The character entity associated with the sprite. */
  protected AbstractCharacter character;

  /** the movement velocity. */
  protected final Vector2 velocity;

  /** speed of the character. */
  protected float speed;

  /** If the animation should be updated every time it's drawn. */
  protected boolean autoUpdate;
  
  /** Direction the sprite is currently facing, based off of their velocity. */
  protected EightWayDirection facing;

  /** A container for the character's movement animations. */
  protected EightWayMovementAnimation movementAnimation;
  
  /** Time passed, used for finding what frame should be used. */
  protected float animationTime;
  
  /** If the animation is playing. */
  protected boolean animationPlaying;
  
  /** The footstep sound effects for characters. */
  Map<String, SoundEffect> footstepSoundEffects;
  
  /** The width of the character will be scaled by this value. */
  protected float widthScale;
  protected static final float DEFAULT_WIDTH_SCALE = .5f;
  
  /** The height of the character will be scaled by this value. */
  protected float heightScale;
  protected static final float DEFAULT_HEIGHT_SCALE = .5f;
  
  /** Layer for player to check collision against. */
  protected TiledMapTileLayer backgroundLayer; //TODO: need to be here?
  protected static final String COLLISION_PROPERTY_KEY = "collision";
  
  /** The sprites the character can collide with. Includes itself. */
  protected List<AbstractCharacterSprite> characters;
  
  /**
   * An abstract character class.
   * @param movementAnimation animations representing the character's eight movement options
   * @param collisionLayer  the layer that the character can collide with
   */
  public AbstractCharacterSprite(EightWayMovementAnimation movementAnimation, 
      Map<String, SoundEffect> footstepSoundEffects, TiledMapTileLayer collisionLayer, 
      List<AbstractCharacterSprite> characters) {
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
    this.backgroundLayer = collisionLayer;
    facing = EightWayDirection.DOWN;
    setSize(getWidth() * widthScale, getHeight() * heightScale);
    this.footstepSoundEffects = footstepSoundEffects;
    this.characters = characters;
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
  
  /**
   * Updates the character.
   * @param delta the amount of time that has passed since the last frame.
   */
  public void update(float delta) {
    // Move in the x direction.
    float oldX = getX();
    setX(getX() + velocity.x * delta);
    
    // check for X collision
    boolean collisionX = false;
    // increment is the distance between each collision point
    float increment = backgroundLayer.getTileWidth();
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
    increment = backgroundLayer.getTileHeight();
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
    } // TODO: move as close as possible
    
    // Update which direction the character is facing
    if (isWalking()) {
      facing = EightWayDirection.getDirection(velocity);
    }
    
    updateFootstepSoundEffects();
    
    // Update the character's movement animation
    animationTime += delta;
    if (animationPlaying) { 
      setRegion(movementAnimation.getCurrentAnimation(facing, velocity).getKeyFrame(animationTime));
    }
    
    // The collision's velocity change should only apply to the animation logic above
    velocity.x = oldVelocityX;
    velocity.y = oldVelocityY;
  }
  
  private void updateFootstepSoundEffects() {
    // Play/Pause the footstep sound effect
    SoundEffect footstepSoundEffect = null;
    if (isWalking()) {
      // Get the type of ground being walked on
      int tilePositionX = (int) (getX() / backgroundLayer.getTileWidth());
      int tilePositionY = (int) (getY() / backgroundLayer.getTileHeight());
      Iterator<String> propertyIterator = backgroundLayer.getCell(tilePositionX, tilePositionY)
          .getTile().getProperties().getKeys();
      // The only property on the ground layer should be the ground type
      if (propertyIterator.hasNext()) {
        String footstepSoundEffectKey = propertyIterator.next();
        footstepSoundEffect = footstepSoundEffects.get(footstepSoundEffectKey);
        footstepSoundEffect.loop();
      }
    }
    // Pause all other footstep sound effects (or all if not walking)
    for (SoundEffect soundEffect : footstepSoundEffects.values()) {
      if (soundEffect != footstepSoundEffect) {
        soundEffect.pause();
      }
    }
  }

  private boolean isCellBlocked(float characterPositionX, float characterPositionY) {
    // Check the cell for the collision key
    int tilePositionX = (int) (characterPositionX / backgroundLayer.getTileWidth());
    int tilePositionY = (int) (characterPositionY / backgroundLayer.getTileHeight());
    Cell cell = backgroundLayer.getCell(tilePositionX, tilePositionY);
    if (cell != null && cell.getTile() != null 
        && cell.getTile().getProperties().containsKey(COLLISION_PROPERTY_KEY)) {
      return true;
    }

    // Check the cell for another character
    for (AbstractCharacterSprite character : characters) {
      if (character != this && character.containsPoint(characterPositionX, characterPositionY)) {
        return true;
      }
    }
    
    return false;
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
  
  public boolean containsPoint(float x, float y) {
    return containsPointX(x) && containsPointY(y);
  }
  
  public boolean containsPoint(Vector2 point) {
    return containsPointX(point.x) && containsPointY(point.y);
  }
  
  protected boolean containsPointX(float x) {
    return x > getX() && x < getX() + getWidth();
  }
  
  protected boolean containsPointY(float y) {
    return y > getY() && y < getY() + getHeight();
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
    return backgroundLayer;
  }

  public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
    this.backgroundLayer = collisionLayer;
  }
  
  public boolean isWalking() {
    return velocity.x != 0 || velocity.y != 0;
  }

}

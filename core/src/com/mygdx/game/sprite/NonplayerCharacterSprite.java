package com.mygdx.game.sprite;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprite.movement.EightWayMovementAnimation;
import com.mygdx.game.util.SoundEffect;

public class NonplayerCharacterSprite extends AbstractCharacterSprite {
  
  /** The character will patrol around inside of this circle, which stays constant around 
   * its starting position. Should be smaller than the leash circle. */
  protected Circle patrolCircle;
  /** The point the character is currently attempting to patrol to. */
  protected Vector2 patrolPoint;
  /** The character will not leave its leash circle, which stays constant around 
   * its starting position. Should be larger than the aggro circle. */
  protected Circle leashCircle;
  /** The character will attack anything within its aggro circle, which follows it as it moves. 
   * Should be smaller than the leash circle. */
  protected Circle aggroCircle;
  /** The amount of time the character will wait before another patrol. */
  protected float waitTimeSeconds;
  /** The player. */
  PlayerCharacterSprite player;
  
  protected static final Random RANDOM = new Random(System.currentTimeMillis());
  
  public NonplayerCharacterSprite(EightWayMovementAnimation movementAnimation,
      Map<String, SoundEffect> footstepSoundEffects, TiledMapTileLayer collisionLayer, 
      List<AbstractCharacterSprite> characters, int startingX, int startingY, float patrolRange,
      float aggroRange, float leashRange, PlayerCharacterSprite player) {
    super(movementAnimation, footstepSoundEffects, collisionLayer, characters);
    setX(startingX);
    setY(startingY);
    this.aggroCircle = new Circle(getX(), getY(), aggroRange);
    this.leashCircle = new Circle(getX(), getY(), leashRange);
    this.patrolCircle = new Circle(getX(), getY(), patrolRange);
    patrolPoint = getNewPatrolPoint();
    waitTimeSeconds = getNewWaitTime();
    this.player = player;
    // TODO: perhaps pass screen around rather than collision layer / player
  }
  
  @Override
  public void update() {
    super.update();
  }
  
  @Override
  public void update(float delta) {
    // Character is aggro'd if player is in its leash circle and in its aggro circle
    boolean aggro = leashCircle.contains(player.getX(), player.getY()) 
        && aggroCircle.contains(player.getX(), player.getY()); //TODO: check that it is in leash?
    
    // Move towards the player if aggro'd or the patrol point if not waiting
    if (aggro) {
      moveTowards(player.getX(), player.getY());
    } else {
      waitTimeSeconds -= delta;
      if (waitTimeSeconds <= 0) {
        moveTowards(patrolPoint.x, patrolPoint.y);
        if (velocity.x == 0 && velocity.y == 0) {
          patrolPoint = getNewPatrolPoint();
          waitTimeSeconds = getNewWaitTime();
        }
      }
    }
    
    // Move the character and update its animation as per normal.
    super.update(delta);
    
    // Update the aggro circle to the current position
    aggroCircle.setPosition(getX(), getY());
  }
  
  protected void moveTowards(float x, float y) { // TODO: pathfinding (not being able to get there, etc)
    if (!containsPointX(x)) {
      velocity.x = x > getX() ? speed : -speed;
    } else {
      velocity.x = 0;
    }
    
    if (!containsPointY(y)) {
      velocity.y = y > getY() ? speed : -speed;
    } else {
      velocity.y = 0;
    }
  }
  
  /**
   * Determines a new wait time, in seconds, between 0 and 10 seconds.
   * @return  an integer between 0 and 10, representing the wait time in seconds
   */
  protected int getNewWaitTime() {
    return RANDOM.nextInt(11);
  }
  
  /**
   * Generates uniform points along the patrol circle.
   * @return  a uniform point within the patrol circle.
   */
  protected Vector2 getNewPatrolPoint() {
    double angle = RANDOM.nextDouble() * Math.PI * 2;
    double radius = Math.sqrt(RANDOM.nextDouble()) * patrolCircle.radius;
    double x = patrolCircle.x + radius * Math.cos(angle);
    double y = patrolCircle.y + radius * Math.sin(angle);
    return new Vector2((float) x, (float) y);
  }

}

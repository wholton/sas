package com.mygdx.game.sprite;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.sprite.movement.EightWayMovementAnimation;
import com.mygdx.game.util.SoundEffect;

public class EnemyCharacterSprite extends AbstractCharacterSprite {

  private float aggroRange;
  
  public EnemyCharacterSprite(EightWayMovementAnimation movementAnimation,
      Map<String, SoundEffect> footstepSoundEffects, TiledMapTileLayer collisionLayer, 
      List<AbstractCharacterSprite> characters) {
    super(movementAnimation, footstepSoundEffects, collisionLayer, characters);
    // TODO Auto-generated constructor stub
  }

  
  
}

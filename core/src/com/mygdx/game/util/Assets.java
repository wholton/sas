package com.mygdx.game.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

public abstract class Assets {

  @Asset(Texture.class)
  public static final String COMPANY_SPLASH = "texture/company-splash1.png";

  @Asset(Texture.class)
  public static final String TRAILER_SPLASH = "texture/trailer-splash1.png";

  @Asset(Texture.class)
  public static final String MAIN_MENU_BACKGROUND = "texture/main-menu-background1.png";

  @Asset(Texture.class)
  public static final String OPTIONS_BACKGROUND = "texture/options-background1.png";

  @Asset(Texture.class)
  public static final String END_SPLASH = "texture/end-splash1.png";

  @Asset(Music.class)
  public static final String MAIN_MENU_MUSIC = "audio/track1.mp3";
  
  @Asset(Music.class)
  public static final String GAME_MUSIC = "audio/track2.mp3";
  
  @Asset(Sound.class)
  public static final String FOOTSTEP_ROCKS_SOUND = "audio/soundeffect/footstep_rocks.mp3";

  @Asset(Sound.class)
  public static final String FOOTSTEP_GRASS_SOUND = "audio/soundeffect/footstep_grass.mp3";
  
  @Asset(Skin.class)
  public static final String WIDGET_SKIN = "skin/uiskin.json";

  @Asset(TextureAtlas.class)
  public static final String PLAYER_ATLAS = "texture/character/player/player.atlas";
  
  @Asset(TextureAtlas.class)
  public static final String OGRE_ATLAS = "texture/character/ogre/ogre.atlas";

}

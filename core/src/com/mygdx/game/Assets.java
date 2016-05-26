package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

public abstract class Assets {

  @Asset(Texture.class)
  public static final String COMPANY_SPLASH1 = "texture/company-splash1.png";

  @Asset(Texture.class)
  public static final String TRAILER_SPLASH1 = "texture/trailer-splash1.png";

  @Asset(Texture.class)
  public static final String MAIN_MENU_BACKGROUND1 = "texture/main-menu-background1.png";

  @Asset(Texture.class)
  public static final String OPTIONS_BACKGROUND1 = "texture/options-background1.png";

  @Asset(Texture.class)
  public static final String END_SPLASH1 = "texture/end-splash1.png";

  @Asset(Music.class)
  public static final String TRACK1 = "audio/track1.mp3";

  @Asset(Skin.class)
  public static final String WIDGET_SKIN = "skin/uiskin.json";

  @Asset(TextureAtlas.class)
  public static final String ATLAS = "texture/character/player4/game.atlas";

}

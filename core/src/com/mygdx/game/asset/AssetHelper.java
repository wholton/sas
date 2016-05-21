package com.mygdx.game.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A helper class which holds descriptors of all assets used by the game and manager for said
 * assets.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public class AssetHelper {

  private AssetHelper() {
  }

  public static final AssetManager MANAGER = new AssetManager();

  public static final AssetDescriptor<Texture> COMPANY_SPLASH1 = new AssetDescriptor<Texture>(
      "texture/company-splash1.png", Texture.class);

  public static final AssetDescriptor<Texture> TRAILER_SPLASH1 = new AssetDescriptor<Texture>(
      "texture/trailer-splash1.png", Texture.class);

  public static final AssetDescriptor<Texture> MAIN_MENU_BACKGROUND1 = new AssetDescriptor<Texture>(
      "texture/main-menu-background1.png", Texture.class);

  public static final AssetDescriptor<Texture> OPTIONS_BACKGROUND1 = new AssetDescriptor<Texture>(
      "texture/options-background1.png", Texture.class);

  public static final AssetDescriptor<Music> TRACK1 = new AssetDescriptor<Music>("audio/track1.mp3",
      Music.class);

  public static final AssetDescriptor<Texture> END_SPLASH1 = new AssetDescriptor<Texture>(
      "texture/end-splash1.png", Texture.class);

  public static final AssetDescriptor<Skin> WIDGET_SKIN = new AssetDescriptor<Skin>(
      "skin/uiskin.json", Skin.class);

  public static final AssetDescriptor<TextureAtlas> ATLAS = new AssetDescriptor<TextureAtlas>(
      "texture/frog.pack", TextureAtlas.class);

  /**
   * Queues all of the game's assets to be loaded by the manager.
   */
  @SuppressWarnings("rawtypes")
  public static void loadAll() {
    AssetDescriptor[] descriptors = { ATLAS, TRAILER_SPLASH1, MAIN_MENU_BACKGROUND1,
        COMPANY_SPLASH1, COMPANY_SPLASH1, COMPANY_SPLASH1, OPTIONS_BACKGROUND1, TRACK1, WIDGET_SKIN,
        END_SPLASH1 };

    for (AssetDescriptor descriptor : descriptors) {
      MANAGER.load(descriptor);
    }

  }
}

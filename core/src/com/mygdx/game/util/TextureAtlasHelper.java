package com.mygdx.game.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class TextureAtlasHelper {

  public static Array<AtlasRegion> findRegionsContains(Array<AtlasRegion> regions, String name) {
    Array<AtlasRegion> matched = new Array<AtlasRegion>();
    for (AtlasRegion region : regions) {
      if (region.name.contains(name + " (")) {
        matched.add(new AtlasRegion(region));
      }
    }
    return matched;
  }
  
}

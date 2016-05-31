package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.mygdx.game.SaSGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = SaSGame.NAME + " - v" + SaSGame.VERSION;
		config.width = 1920;
		config.height = 1080;
		config.useGL30 = true;
		config.resizable = true;
		config.addIcon("icon/icon-128.png", FileType.Internal);
		config.addIcon("icon/icon-32.png", FileType.Internal);
		config.addIcon("icon/icon-16.png", FileType.Internal);
		TexturePacker.processIfModified("../assets/texture/character/player", "../assets/texture/character/player", "player");
		TexturePacker.processIfModified("../assets/texture/character/ogre", "../assets/texture/character/ogre", "ogre");
		new LwjglApplication(new SaSGame(), config);
	}
}

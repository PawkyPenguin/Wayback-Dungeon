package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.WaybackDungeon;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Wayback Dungeon";
		config.width = 800;
		config.height = 600;
		config.foregroundFPS = 30;
		config.backgroundFPS = 30;
		new LwjglApplication(new WaybackDungeon(), config);
	}
}

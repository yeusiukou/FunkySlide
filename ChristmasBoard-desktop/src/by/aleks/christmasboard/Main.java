package by.aleks.christmasboard;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ChristmasBoard";
		cfg.useGL20 = true;
//		cfg.width = 480;
//		cfg.height = 854;
		cfg.width = 400;
		cfg.height = 640;
//		cfg.width = 320;
//		cfg.height = 480;
		
		new LwjglApplication(new ChristmasBoard(new PSADesktop()), cfg);
	}
}

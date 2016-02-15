package by.aleks.christmasboard.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fonts {
	
	private static Texture mainTexture;
	private static Texture sagoescTexture;
	private static Texture sylfaenTexture;
	
	public static void load(){
		mainTexture = new Texture(Gdx.files.internal("data/fonts/mountains.png"), true); // true enables mipmaps                           
	    sagoescTexture = new Texture(Gdx.files.internal("data/fonts/segoesc.png"), true); // true enables mipmaps
	    sylfaenTexture = new Texture(Gdx.files.internal("data/fonts/sylfaen.png"), true); // true enables mipmaps

	}

	public static BitmapFont getMainFont() {
		return new BitmapFont(Gdx.files.internal("data/fonts/mountains.fnt"), new TextureRegion(mainTexture));
	}

	public static BitmapFont getSagoescFont() {
		return new BitmapFont(Gdx.files.internal("data/fonts/segoesc.fnt"), new TextureRegion(sagoescTexture));
	}

	public static BitmapFont getSylfaenFont() {
		return new BitmapFont(Gdx.files.internal("data/fonts/sylfaen.fnt"), new TextureRegion(sylfaenTexture));
	}

}

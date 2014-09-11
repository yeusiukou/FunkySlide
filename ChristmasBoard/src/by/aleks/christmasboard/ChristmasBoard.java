package by.aleks.christmasboard;

import java.lang.reflect.Constructor;

import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.PlayServiceActions;
import by.aleks.christmasboard.data.Sounds;
import by.aleks.christmasboard.screens.AbstractScreen;
import by.aleks.christmasboard.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChristmasBoard extends Game{
	
	private SpriteBatch batch;
	private float width;
	private float height;
	public static PlayServiceActions playServiceActions;
	
	
	ChristmasBoard(PlayServiceActions playServiceActions){
		ChristmasBoard.playServiceActions = playServiceActions;
	}
	
	
	@Override
	public void create() {		
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		batch = new SpriteBatch();

		setNewScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		getScreen().dispose();
		Gdx.app.exit();
	}


	@Override
	public void resume() {
		getScreen().resume(); 
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void loadResources(){
		GameData.loadAtlas();
		Sounds.load();
		Fonts.load();
	}
	
	/** Returns active SpriteBatch */
	public SpriteBatch getBatch(){
		return batch;
	}
	
	public void setNewScreen(Screen screen){		
		if(getScreen()!=null) getScreen().dispose();
		setScreen(screen);
	}
	
	/** Looks for a required class and runs it.
	 * @param name - name of a screen class */
	public void setScreenByString(String name){
		if(getScreen()!=null) getScreen().dispose();
		try {
			
			Constructor<?> c = Class.forName("by.aleks.christmasboard.screens."+name).getConstructor(ChristmasBoard.class);
			Screen screen = (AbstractScreen)c.newInstance(this);
			setScreen(screen);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	
	
}

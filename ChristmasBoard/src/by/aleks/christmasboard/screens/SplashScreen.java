package by.aleks.christmasboard.screens;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.GameData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends AbstractScreen{
	
	private static float LOGO_WIDTH = 492*GameData.scale;
	private static float LOGO_HEIGHT = 709*GameData.scale;
	private static float LOADING_WIDTH = 399*GameData.scale;
	private static float LOADING_HEIGHT = 100*GameData.scale;

	public SplashScreen(ChristmasBoard game) {
		super(game);
		this.game=game;

	}

	
	@Override
	public void show() {

		createSplash();
		
		Action loading = new Action(){

			@Override
			public boolean act(float delta) {
				game.loadResources();
				loadingPattern.setColor(0, 0, 0, 0); //Hide the texture when blending starts.
				loadingPattern2.setColor(0, 0, 0, 0);
				return true;
			}
			
		};

		fading = Actions.sequence(Actions.delay( 0.5f ), loading, Actions.fadeOut( 1.4f ),
				new Action(){ //After the fading effect MenuScreen starts;

					@Override
					public boolean act(float delta) {
						game.setScreen(new MainMenuScreen(game));
						return true;
					}
			
		});
		splash.addAction(fading);
		stage.addActor(splash);
		super.show();
	}
	
	private void createSplash(){
		TextureAtlas splashAtlas = new TextureAtlas(Gdx.files.internal("data/splash.pack"));
		TextureRegion logoRegion = splashAtlas.findRegion("logo");
		TextureRegion loadingRegion = splashAtlas.findRegion("loading");
		
		Image logo = new Image(logoRegion);
		logo.setSize(LOGO_WIDTH, LOGO_HEIGHT);
		logo.setPosition((Gdx.graphics.getWidth()-LOGO_WIDTH)/2, (Gdx.graphics.getHeight()-LOGO_HEIGHT)/2);
		
		loadingPattern = new Image(loadingRegion);
		loadingPattern.setSize(LOADING_WIDTH, LOADING_HEIGHT);
		loadingPattern.setPosition((Gdx.graphics.getWidth()-LOADING_WIDTH)/2, (Gdx.graphics.getHeight()-LOADING_HEIGHT)/2-150*GameData.scale);
		
		loadingPattern2 = new Image(loadingRegion);
		loadingPattern2.setSize(LOADING_WIDTH, LOADING_HEIGHT);
		loadingPattern2.setPosition((Gdx.graphics.getWidth()-LOADING_WIDTH)/2, loadingPattern.getY()+LOADING_HEIGHT);
		
		splash = new Group();

		splash.addActor(loadingPattern);
		splash.addActor(loadingPattern2);
		splash.addActor(logo);
		
		splash.addAction(new Action(){
			float distance = 0;
			float speed = 5f*GameData.scale;

			@Override
			public boolean act(float delta) {
				loadingPattern.setPosition(loadingPattern.getX(), loadingPattern.getY()-speed);
				loadingPattern2.setPosition(loadingPattern2.getX(), loadingPattern2.getY()-speed);
				distance += speed;
				//Cycled pattern moving
				if (distance >= LOADING_HEIGHT-speed){
					if(loadingPattern.getY() < loadingPattern2.getY())
						loadingPattern.setPosition(loadingPattern.getX(), loadingPattern2.getY()+LOADING_HEIGHT);
					else loadingPattern2.setPosition(loadingPattern2.getX(), loadingPattern.getY()+LOADING_HEIGHT);
					distance = 0;
				}
				return false;
			}
			
		});
		
	}


	private Image loadingPattern, loadingPattern2;
	private Group splash;
	private Action fading;
	private ChristmasBoard game;

}

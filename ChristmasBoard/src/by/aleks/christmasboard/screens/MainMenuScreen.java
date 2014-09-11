package by.aleks.christmasboard.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.gui.BottomButtons;
import by.aleks.christmasboard.gui.MenuButton;

public class MainMenuScreen extends AbstractScreen{
	
	//Game name displaying on the main menu
	public static final String GAME_NAME = "Funky Slide";
	private static final float LABEL_RELATIVE_HEIGHT = 0.7f;
	private static final float LABEL_WIDTH = 669*GameData.scale;
	private static final float LABEL_HEIGHT = 223*GameData.scale;	

	public MainMenuScreen(ChristmasBoard game) {
		super(game);
		this.game = game;
	}		
	
	
	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage); //Set our stage to process clicks
		fallingSnow();
		stage.addActor(new MenuButton("Start Game", Gdx.graphics.getHeight()*0.5f, "GameScreen", game));
		
		Button scoresButton = new MenuButton("Scores", Gdx.graphics.getHeight()*0.35f, "ScoreScreen", game);

		stage.addActor(scoresButton);

		gameLabel();
		addBottomButtons();


	}
	


	@Override
	public void keyBackIsPressed() {
		System.exit(0);
		super.keyBackIsPressed();
	}


	/** Creates label with the game name and adds it to the stage */
	private void gameLabel(){
		
		Image label = new Image(GameData.getRegion("logo"));
		label.setWidth(LABEL_WIDTH);
		label.setHeight(LABEL_HEIGHT);
		label.setPosition((game.getWidth()-label.getWidth())/2, game.getHeight()*LABEL_RELATIVE_HEIGHT);
		stage.addActor(label);
				
	}
	
	/** Creates and adds sound and info buttons */
	protected void addBottomButtons(){
		stage.addActor(BottomButtons.getSoundButton());
		
		final Button infoButton = BottomButtons.getInfoButton();
		infoButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setNewScreen(new InfoScreen(game));
			
		    }
		});
		stage.addActor(infoButton);
	}


	private ChristmasBoard game;

}

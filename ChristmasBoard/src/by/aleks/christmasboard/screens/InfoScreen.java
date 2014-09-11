package by.aleks.christmasboard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.gui.BottomButtons;
public class InfoScreen extends AbstractScreen {
	
	private static final float infoPageWidth = 730*GameData.scale;
	private static final float infoPageHeight = 880*GameData.scale;
	private static final float INFO_PAGE_Y_POS = 0.2f*Gdx.graphics.getHeight();


	public InfoScreen(ChristmasBoard game) {
		super(game);
		this.game = game;
	}
	


	@Override
	public void show() {
		
		ChristmasBoard.playServiceActions.showAds(true);

		Gdx.input.setInputProcessor(stage); //Set our stage to process clicks
		fallingSnow();
		addBottomButtons();
		addInfoPage();

	}


	@Override
	public void keyBackIsPressed() {
		game.setScreenByString("MainMenuScreen");
		super.keyBackIsPressed();
	}



	private void addInfoPage(){
		Image infoPage = new Image(GameData.getRegion("info_page"));
		infoPage.setWidth(infoPageWidth);
		infoPage.setHeight(infoPageHeight);
		infoPage.setPosition((Gdx.graphics.getWidth()-infoPageWidth)/2, INFO_PAGE_Y_POS);
		infoPage.addListener(new ClickListener(){
			

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("market://details?id=by.aleks.christmasboard");
			}
			
		});
		stage.addActor(infoPage);
	}
	
	
	/** Creates and adds sound and info buttons */
	private void addBottomButtons(){
		Button backButton = BottomButtons.getBackButton();
		backButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setNewScreen(new MainMenuScreen(game));
			}
			
		});
		stage.addActor(backButton);
		
//		Button infoButton = BottomButtons.getInfoButton();
//		infoButton.setChecked(false);
//		stage.addActor(infoButton);
	}

	
	private ChristmasBoard game;


}

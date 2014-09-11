package by.aleks.christmasboard.screens;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.ShaderLabel;
import by.aleks.christmasboard.gui.BottomButtons;

public class ScoreScreen extends AbstractScreen{
	
	private static final float MARGIN = 120 * GameData.scale;
	private static final float MARGIN_BOTTOM = 280 * GameData.scale;
	private static final float HEADER_Y = Gdx.graphics.getHeight()*0.82f;
	private static final float LINE_HEIGHT = 10 * GameData.scale;
	private static final BitmapFont HEADER_FONT = Fonts.getSagoescFont();
	private static final float HEADER_FONT_SIZE = 112;
	private static final BitmapFont SCORE_FONT = Fonts.getMainFont();
	private static final float SCORE_FONT_SIZE = 90;

	public ScoreScreen(ChristmasBoard game) {
		super(game);
		this.game = game;
	}
	
	@Override
	public void show() {
		
		ChristmasBoard.playServiceActions.showAds(true);

		Gdx.input.setInputProcessor(stage); //Set our stage to process clicks
		fallingSnow();
		addBlack();
		addBottomButtons();
		addHeader();
		addScores();

	}
	
	@Override
	public void keyBackIsPressed() {
		game.setNewScreen(new MainMenuScreen(game));
		super.keyBackIsPressed();
	}
	
	private void addHeader(){
		Label label = new ShaderLabel("Scores", Color.YELLOW, HEADER_FONT_SIZE, HEADER_FONT);
		label.setPosition((Gdx.graphics.getWidth()-label.getWidth())/2, HEADER_Y);
		stage.addActor(label);
	}
	
	private void addScores(){
		TreeMap<Integer, ArrayList<String>> scoreMap = GameData.getScoreMap();
		if(scoreMap == null){
			emptyMessage();
			return;
		}

		for (int score : scoreMap.descendingKeySet()){
			for(String name : scoreMap.get(score)){
				if (currentY<MARGIN_BOTTOM){
					counter = 1;
					return;
				}
				printScore(name, String.valueOf(score), currentY-LINE_HEIGHT);
			}
		}
	}
	
	/** Prints new line of scores */
	private void printScore(String playerName, String playerScore, float posY){
		
		Label name = new ShaderLabel(counter++ + ". " + playerName, Color.WHITE, SCORE_FONT_SIZE, SCORE_FONT);
		Label score = new ShaderLabel(playerScore, Color.WHITE, SCORE_FONT_SIZE, SCORE_FONT);
		
		currentY = posY - name.getHeight()/2;
		name.setPosition(MARGIN, currentY);
		score.setPosition(Gdx.graphics.getWidth()-MARGIN-score.getWidth(), currentY);
		stage.addActor(name);
		stage.addActor(score);
		
	}
	
	private void emptyMessage(){
		String message = "The list is empty...\n    Let's play!";
		Label label = new ShaderLabel(message, Color.YELLOW, SCORE_FONT_SIZE,SCORE_FONT);
		label.setPosition((Gdx.graphics.getWidth()-label.getWidth())/2, (Gdx.graphics.getHeight()-label.getHeight())/2);
		stage.addActor(label);
		
	}
	
	/** Adds the black semi-transparence to the background */
	private void addBlack(){
		Image black = new Image(GameData.getRegion("dot"));		
		black.setColor(new Color(0, 0, 0, 0.6f));
		black.setWidth(Gdx.graphics.getWidth());
		black.setHeight(Gdx.graphics.getHeight());
		black.setPosition(0, 0);
		stage.addActor(black);
	}
	
	private void addBottomButtons(){
		Button backButton = BottomButtons.getBackButton();
		backButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setNewScreen(new MainMenuScreen(game));
			}
			
		});
		
		stage.addActor(backButton);
		if(ChristmasBoard.playServiceActions.isPlayServiceAvailable())
			stage.addActor(BottomButtons.getGlobeButton());
	}
	
	private float currentY = HEADER_Y;//-50 * GameData.scale;
	private ChristmasBoard game;
	private int counter = 1;

}

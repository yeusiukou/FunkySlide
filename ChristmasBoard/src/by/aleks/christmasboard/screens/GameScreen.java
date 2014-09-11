package by.aleks.christmasboard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.entities.ActorsComparator;
import by.aleks.christmasboard.gui.Board;
import by.aleks.christmasboard.gui.Bonus;
import by.aleks.christmasboard.gui.LivesBox;
import by.aleks.christmasboard.gui.MessageBox;
import by.aleks.christmasboard.gui.PauseMenu;
import by.aleks.christmasboard.gui.Score;
import by.aleks.christmasboard.gui.StartScreen;


public class GameScreen extends AbstractScreen {
	
	private static float SCORE_X = 28*GameData.scale;
	private static float SCORE_RELATIVE_Y = 0.84f*Gdx.graphics.getHeight();
	
	private static float PAUSE_BUTTON_WIDTH = 140*GameData.scale;
	private static float PAUSE_BUTTON_HEIGHT = 145*GameData.scale;
	private static float PAUSE_BUTTON_X = Gdx.graphics.getWidth()-15*GameData.scale-PAUSE_BUTTON_WIDTH;
	private static float PAUSE_BUTTON_RELATIVE_Y = 0.87f*Gdx.graphics.getHeight();
	
	private static float BONUS_RELATIVE_Y = 0.90f*Gdx.graphics.getHeight();
	
	private static float SACK_WIDTH = 513*GameData.scale;
	public static final float SACK_HEIGHT = 296*GameData.scale;
	private static final float SACK_BACK_HEIGHT = 97*GameData.scale;
	//public static final float BOARD_UPPER_EDGE_Y = Gdx.graphics.getHeight()-SACK_HEIGHT-Board.BOARD_SIDE;
	
	private static float LIVES_BOX_RELATIVE_Y = 0.81f*Gdx.graphics.getHeight();
	//private static float LIVES_BOX_RELATIVE_Y = Gdx.graphics.getHeight()-BOARD_UPPER_EDGE_Y+15*GameData.scale;
	public static final float BOARD_UPPER_EDGE_Y = LIVES_BOX_RELATIVE_Y-40*GameData.scale;
	
	private static final String SCORE_Z = "7";
	private static final String LIVEBOX_Z = "7";
	private static final String PAUSE_BUTTON_Z = "7";
	private static final String HIDING_RECTANGLE_Z = "4";
	private static final String SACK_FRONT_Z = "8";
	private static final String SACK_BACK_Z = "3";

	public GameScreen(ChristmasBoard game) {
		super(game);
		this.game = game;
	}
	
	
	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage); //Set our stage to process clicks
		fallingSnow();	
		GameState.gameOver = false;
		addGameTextures();
		ChristmasBoard.playServiceActions.showAds(false);
	}	
	
	@Override
	public void keyBackIsPressed() {		
		runPause();

	}
	
	@Override
	public void pause() {
		runPause();
		super.pause();
	}
	

	@Override
	public void render(float delta) {

		livesBox.update();
		
		//If a player lose PauseScreen opens.
		if(GameState.lives<=0 && !GameState.gameOver) gameOver();
		
		stage.getActors().sort(new ActorsComparator());
		super.render(delta);
	}
	
	private void runPause(){
		if(!GameState.pause){
			stage.addActor(new PauseMenu(game, GameState.playerName, GameState.score, true));
			GameState.pause = true;
		}
	}
	
	private void gameOver(){
		stage.addActor(new PauseMenu(game, GameState.playerName, GameState.score, false));
		GameState.gameOver = true;
		GameData.writeScore();
	}


	private void addGameTextures(){
		
		score = new Score(SCORE_X, SCORE_RELATIVE_Y);
		score.setName(SCORE_Z); //I use name as z-index to sort objects on the stage.
		stage.addActor(score);
		
		bonus = new Bonus(BONUS_RELATIVE_Y);
		bonus.setName(SCORE_Z);
		stage.addActor(bonus);
		
		addPauseButton();
		livesBox = new LivesBox((Gdx.graphics.getWidth()-LivesBox.BOX_WIDTH)/2, LIVES_BOX_RELATIVE_Y);
		livesBox.setName(LIVEBOX_Z);
		stage.addActor(livesBox);
		
		
		board = new Board((Gdx.graphics.getWidth()-Board.BOARD_SIDE)/2, BOARD_UPPER_EDGE_Y-Board.BOARD_SIDE, stage);
		stage.addActor(board);
		
		Image sack = new Image(GameData.getRegion("sack"));
		sack.setSize(SACK_WIDTH, SACK_HEIGHT);
		float sackY = board.getY()-SACK_HEIGHT;
		if(sackY > 0)
			sackY = 0;
		sack.setPosition((Gdx.graphics.getWidth()-SACK_WIDTH)/2, sackY);
		sack.setName(SACK_FRONT_Z);
		stage.addActor(sack);
		
		Image sackBack = new Image(GameData.getRegion("sack_back"));
		sackBack.setSize(SACK_WIDTH, SACK_BACK_HEIGHT);
		sackBack.setPosition((Gdx.graphics.getWidth()-SACK_WIDTH)/2, board.getY()-SACK_BACK_HEIGHT-1*GameData.scale);
		sackBack.setName(SACK_BACK_Z);
		stage.addActor(sackBack);
		
		setSnowPileY(sack.getY());
		
		addHidingRectangle();
		
		if(!GameState.pause) //I run StartScreen with explanations only first time in the game.
			stage.addActor(new StartScreen(board.getX(), board.getY()-sack.getY()/3)); 
		//Here I substract the sack Y to make it working on screens with a different aspect ratio (320x480)
		
		stage.addActor(messageBox = new MessageBox());
	}
	
	private void addPauseButton(){
		
		ButtonStyle pauseButtonStyle = new ButtonStyle();
		TextureRegionDrawable btnDrawable = new TextureRegionDrawable(GameData.getRegion("pause_button"));
		pauseButtonStyle.up = btnDrawable;
		pauseButton = new Button(pauseButtonStyle);
		pauseButton.setPosition(PAUSE_BUTTON_X, PAUSE_BUTTON_RELATIVE_Y);
		pauseButton.setWidth(PAUSE_BUTTON_WIDTH);
		pauseButton.setHeight(PAUSE_BUTTON_HEIGHT);
		pauseButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				keyBackIsPressed();
			}
			
		});
		pauseButton.setName(PAUSE_BUTTON_Z);
		stage.addActor(pauseButton);
	}
	
	
	private void addHidingRectangle(){
		Image rect = new Image(GameData.getRegion("dot"));
		rect.setColor(AbstractScreen.BACKGROUND_COLOR);
		rect.setPosition(board.getX(), board.getY()+board.getHeight());
		rect.setWidth(board.getWidth());
		rect.setHeight(Gdx.graphics.getHeight()-rect.getY());
		rect.setName(HIDING_RECTANGLE_Z);
		stage.addActor(rect);
	}
	
	
	/** Show one string popup message */
	public static void showMessage(String text){
		messageBox.show(text);
	}
	
	
	/** Show two string popup message */
	public static void showMessage(String text1, String text2){
		messageBox.show(text1, text2);
	}
	


	private ChristmasBoard game;
	private Score score;
	private LivesBox livesBox;
	private Bonus bonus;
	private Board board;
	private Button pauseButton;
	private static MessageBox messageBox;

}

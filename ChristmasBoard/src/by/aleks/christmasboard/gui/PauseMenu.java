package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.data.ShaderLabel;
import by.aleks.christmasboard.data.ShaderTextField;
import by.aleks.christmasboard.data.Sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/** This menu shows when we tap the pause button */
public class PauseMenu extends Group {
	
	private static final float HEADER_Y = 1057*GameData.scale;
	
	private static final float BOX_WIDTH = 729*GameData.scale;
	private static final float BOX_HEIGHT = 474*GameData.scale;
	private static final float BOX_RELATIVE_Y = 0.4f;
	
	private static final float SCORE_RELATIVE_Y = 0.55f;
	
	private static final float BUTTON_WIDTH = 241*GameData.scale;
	private static final float BUTTON_HEIGHT = 77*GameData.scale;	
	private static final float SPACE_BETWEEN_BUTTONS = 143*GameData.scale;
	private static final float BUTTONS_Y = 90*GameData.scale;
	private static final float BUTTONS_SIDE_MARGIN = BOX_WIDTH-BUTTON_WIDTH*2-SPACE_BETWEEN_BUTTONS;
	
	private static final String TAPTOCHANGE_TEXT = "Tap to change the player name";
	private static final String LOGGEDIN_TEXT = "Google account. Tap to sign out";
	private static final float TAPTOCHANGE_RELATIVE_Y = 0.45f;
	private static final float TAPTOCHANGE_RELATIVE_X = BUTTONS_SIDE_MARGIN + 10*GameData.scale;
	
	private static final BitmapFont SCORE_FONT = Fonts.getMainFont();
	private static final float SCORE_FONT_SIZE = 105;
	private static final BitmapFont HEADER_FONT = Fonts.getSylfaenFont();
	private static final float HEADER_FONT_SIZE = 120;
	private static final BitmapFont TAPTOCHANGE_FONT = Fonts.getSylfaenFont();
	private static final float TAPTOCHANGE_FONT_SIZE = 36;
	private static final BitmapFont BUTTON_FONT = Fonts.getMainFont();
	private static final float BUTTON_FONT_SIZE = 50;
	
	private static final String Z = "20";
	
	
	
	public PauseMenu(ChristmasBoard game, String playerName, int score, boolean isPause){
		this.game = game;
		this.setName(Z);
		addBlack();
		addHeader(isPause);
		addBox(playerName, score, isPause);
		addBottomButtons();
		ChristmasBoard.playServiceActions.showAds(true);
	}
	
	
	private void addBox(String playerName, int points, final boolean isPause){
		Group boxGroup = new Group();
		boxGroup.setPosition((Gdx.graphics.getWidth()-BOX_WIDTH)/2, Gdx.graphics.getHeight()*BOX_RELATIVE_Y);
		
		//Dialog box
		Image box = new Image(GameData.getRegion("dialog_box"));
		box.setPosition(0,0);
		box.setSize(BOX_WIDTH, BOX_HEIGHT);
		boxGroup.addActor(box);
		
		//Score
		boxGroup.addActor(getScoreField(playerName, points));
		
		
		//Little text beyond the score
		ttc = new ShaderLabel(TAPTOCHANGE_TEXT, Color.WHITE, TAPTOCHANGE_FONT_SIZE, TAPTOCHANGE_FONT);
		ttc.setPosition(TAPTOCHANGE_RELATIVE_X, BOX_HEIGHT*TAPTOCHANGE_RELATIVE_Y);
		
		if(ChristmasBoard.playServiceActions.isSignedInGPGS()){
			ttc.setText(LOGGEDIN_TEXT);
		}

		boxGroup.addActor(ttc);
		
		//Buttons
		Button leftButton = getNewButton("MENU", BUTTONS_SIDE_MARGIN, BUTTONS_Y);
		leftButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				restartGame();
				GameState.pause = false;
				game.setScreenByString("MainMenuScreen");
				Gdx.input.setOnscreenKeyboardVisible(false); //Hide on-screen keyboard, which could be opened after the name changing
			}
			
		});
		boxGroup.addActor(leftButton);
		
		String rightButtonText;
		if(isPause) rightButtonText = "RESUME";
		else rightButtonText = "RESTART";
		Button rightButton = getNewButton(rightButtonText, BOX_WIDTH-BUTTONS_SIDE_MARGIN-BUTTON_WIDTH, BUTTONS_Y);
		rightButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(isPause){
					PauseMenu.this.remove();
					GameState.pause = false;
					Gdx.input.setOnscreenKeyboardVisible(false);//Hide on-screen keyboard
					ChristmasBoard.playServiceActions.showAds(false);
				} else restartGame();
				
			}
			
		});
		boxGroup.addActor(rightButton);
				
		this.addActor(boxGroup);
		
	}
		
	/** Returns the score label with player name and the points the player got */
	private Group getScoreField(String playerName, int points){
		scoreGroup = new Group();
		scoreGroup.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(ChristmasBoard.playServiceActions.isSignedInGPGS()){
					getTouchable();
					nameField.setTouchable(Touchable.disabled);
					ChristmasBoard.playServiceActions.logoutGPGS();
					nameField.setText("Player");
					nameField.setWidth(SCORE_FONT.getBounds(nameField.getText()).width);
					score.setPosition(nameField.getWidth(), 0);
					scoreGroup.setPosition((BOX_WIDTH - (nameField.getWidth() + score.getWidth())) / 2, BOX_HEIGHT* SCORE_RELATIVE_Y);
					GameState.playerName = "Player";
					ttc.setText(TAPTOCHANGE_TEXT);
					ttc.setPosition(TAPTOCHANGE_RELATIVE_X, BOX_HEIGHT*TAPTOCHANGE_RELATIVE_Y);
				}
			}
			
		});
		
		
		nameField = new ShaderTextField(playerName, Color.WHITE, SCORE_FONT_SIZE, SCORE_FONT);
		nameField.setWidth(SCORE_FONT.getBounds(nameField.getText()).width);
		nameField.setTextFieldListener(new TextFieldListener() {
			
			@Override
			public void keyTyped(TextField textField, char key) {
				// I want to erase a previous name as I typed a first character
				if (isFirstSym) {
					textField.setText(String.valueOf(key));
					textField.setCursorPosition(1);
					GameState.playerName = String.valueOf(key);
					isFirstSym = false;
				} else {
					//If backspace is pressed I delete the last character in GameState
					if(key=='\b'){
						if(GameState.playerName.length()!=0)
							GameState.playerName = GameState.playerName.substring(0, GameState.playerName.length()-1);
					} else{
						
						//I don't want my text to go out of the box...
						if (textField.getWidth() + score.getWidth() < BOX_WIDTH - 170 * GameData.scale) {
							GameState.playerName = GameState.playerName+key;
						} else {
							//so I delete every character typed after the limit.
							if(textField.getText().length()>0)
								textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
							textField.setCursorPosition(textField.getText().length());
						}
						
					}
					// Here I position the text.
					textField.setWidth(SCORE_FONT.getBounds(textField.getText()).width);
					score.setPosition(textField.getWidth(), 0);
					scoreGroup.setPosition((BOX_WIDTH - (textField.getWidth() + score.getWidth())) / 2, BOX_HEIGHT* SCORE_RELATIVE_Y);
				}
			}

			boolean isFirstSym = true;
		});
		
		scoreGroup.addActor(nameField);
		
		score = new ShaderLabel(": "+points, Color.WHITE, SCORE_FONT_SIZE, SCORE_FONT);
		score.setPosition(nameField.getWidth(), 0);
		scoreGroup.addActor(score);
		
		scoreGroup.setPosition((BOX_WIDTH-(nameField.getWidth()+score.getWidth()))/2, BOX_HEIGHT*SCORE_RELATIVE_Y);
		return scoreGroup;
				
	}
	
	
	/** Returns buttons for the dialog box */
	private Button getNewButton(String text, float x, float y){
		ButtonStyle buttonStyle = new ButtonStyle();
		TextureRegionDrawable btnDrawable = new TextureRegionDrawable(GameData.getRegion("dialog_button"));
		buttonStyle.up = btnDrawable;
		
		Button button = new Button(buttonStyle);		
		button.setPosition(x, y);
		button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		ShaderLabel label = new ShaderLabel(text, new Color(Color.valueOf("fde017")), BUTTON_FONT_SIZE, BUTTON_FONT);
		label.setPosition((button.getWidth()-label.getWidth())/2, button.getHeight()/2-label.getHeight()/2.5f);
		button.add(label);
		
		return button;
	}
	
	/** Creates and adds sound and info buttons */
	private void addBottomButtons(){
		Button restartButton = BottomButtons.getRestartButton();
		restartButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sounds.playClick();
				restartGame();
			}
			
		});
		this.addActor(BottomButtons.getSoundButton());
		this.addActor(restartButton);
	}
	

	
	/**Adds header (GameOver or Pause) */
	private void addHeader(boolean isPause){
		String text;
		if(isPause) text = "Pause";
		else text = "Game Over";
		
		ShaderLabel header = new ShaderLabel(text, Color.WHITE, HEADER_FONT_SIZE, HEADER_FONT);
		header.setPosition((Gdx.graphics.getWidth()-header.getWidth())/2, HEADER_Y);
		this.addActor(header);				
		
	}
	
	/** Adds the black semi-transparence to the background */
	private void addBlack(){
		Image black = new Image(GameData.getRegion("dot"));		
		black.setColor(new Color(0, 0, 0, 0.6f));
		black.setWidth(Gdx.graphics.getWidth());
		black.setHeight(Gdx.graphics.getHeight());
		black.setPosition(0, 0);
		this.addActor(black);
	}
	
	private void restartGame(){
		GameState.resetGame();
		game.setScreenByString("GameScreen");
	}
	
	private ChristmasBoard game;
	private Label score;
	private TextField nameField;
	private Group scoreGroup;
	private Label ttc;

}

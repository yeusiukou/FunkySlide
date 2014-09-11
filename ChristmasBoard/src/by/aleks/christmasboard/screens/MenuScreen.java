package by.aleks.christmasboard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.GameData;

public class MenuScreen extends AbstractScreen {
	
	
	private static final float BUTTON_RELATIVE_WIDTH = 0.7f;
	private static final int BUTTON_HEIGHT = (int) (150*GameData.scale);
	private static final Color BUTTON_TEXT_COLOR = new Color(0.50f, 0.14f, 0f, 1f);


	public MenuScreen(ChristmasBoard game) {
		super(game);
		this.game = game;
	}
	
	
	
	

	protected TextButtonStyle btnStyle;
	private ChristmasBoard game;

}

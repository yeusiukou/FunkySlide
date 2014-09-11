package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.ShaderLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuButton extends Button {
	
	private static final float BUTTON_RELATIVE_WIDTH = 0.7f;
	private static final int BUTTON_HEIGHT = (int) (150*GameData.scale);
	private static final Color BUTTON_TEXT_COLOR = new Color(0.50f, 0.14f, 0f, 1f);
	private static final float TEXT_FONT_SIZE = 70;

	/** Returns a menu button clicking on which opens given screen
	 * @param text - text on the button.
	 * @param posY - Y axis position of the button.
	 * @param screenClassName - name of the class we wanna run. */
	public MenuButton(String text, float posY, final String screenClassName, final ChristmasBoard game) {

		this.setSize(Gdx.graphics.getWidth() * BUTTON_RELATIVE_WIDTH, BUTTON_HEIGHT);
		this.setPosition((Gdx.graphics.getWidth() - this.getWidth()) / 2, posY);
		
		
		TextureRegionDrawable btnDrawable = new TextureRegionDrawable(GameData.getRegion("button"));
		TextureRegionDrawable btnDownDrawable = new TextureRegionDrawable(GameData.getRegion("button2"));

		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = btnDrawable;
		buttonStyle.down = btnDownDrawable;
		setStyle(buttonStyle);
		
		ShaderLabel label = new ShaderLabel(text, BUTTON_TEXT_COLOR, TEXT_FONT_SIZE, Fonts.getMainFont());
		label.setPosition((getWidth()-label.getWidth())/2, getHeight()/2-label.getHeight()/2.5f);

		addActor(label);

		if(screenClassName!=null){
			this.addListener(new ClickListener() {

				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);	
					game.setScreenByString(screenClassName);
				}
			});
		}
	}	

}

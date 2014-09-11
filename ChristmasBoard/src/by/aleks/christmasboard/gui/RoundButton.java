package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.Sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class RoundButton extends Button{
	
	private static final float BOTTOM_BUTTONS_SIDE_MARGIN = 64*GameData.scale;
	private static final float BOTTOM_BUTTONS_BOTTOM_MARGIN = 30*GameData.scale;
	private static final float BOTTOM_BUTTONS_SIZE = 184*GameData.scale;

	
	public RoundButton(boolean position, String iconUp, String iconDown, String iconChecked){

		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(GameData.getRegion(iconUp));
		buttonStyle.down = new TextureRegionDrawable(GameData.getRegion(iconDown));
		buttonStyle.checked = new TextureRegionDrawable(GameData.getRegion(iconChecked));
		setStyle(buttonStyle);
		create(position);		
	}
	
	public RoundButton(boolean position, String iconUp, String iconDown){
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(GameData.getRegion(iconUp));
		buttonStyle.down = new TextureRegionDrawable(GameData.getRegion(iconDown));
		setStyle(buttonStyle);
		create(position);		
	}
	
	public void clicked(){
		Sounds.playClick();
	}
	
	private void create(boolean position){
		setSize(BOTTOM_BUTTONS_SIZE, BOTTOM_BUTTONS_SIZE);
		if(position)
			setPosition(Gdx.graphics.getWidth()-BOTTOM_BUTTONS_SIDE_MARGIN-BOTTOM_BUTTONS_SIZE, BOTTOM_BUTTONS_BOTTOM_MARGIN);
		else
			setPosition(BOTTOM_BUTTONS_SIDE_MARGIN, BOTTOM_BUTTONS_BOTTOM_MARGIN);
		
		addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				RoundButton.this.clicked();
			}
			
		});
	}

}

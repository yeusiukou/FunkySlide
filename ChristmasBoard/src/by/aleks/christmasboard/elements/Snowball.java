package by.aleks.christmasboard.elements;

import by.aleks.christmasboard.data.GameData;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Snowball extends TouchableElement{
	
	private static final float SNOWBALL_SIDE = 141*GameData.scale;
	public static final int POINTS = -200; 

	public Snowball(BoardLogic boardLogic) {
		super(boardLogic);
		this.setSize(SNOWBALL_SIDE, SNOWBALL_SIDE);
		TextureRegionDrawable style = new TextureRegionDrawable(GameData.getRegion("snowball"));
		this.setDrawable(style);

	}
	
	

}

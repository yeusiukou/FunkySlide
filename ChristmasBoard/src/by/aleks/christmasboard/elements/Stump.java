package by.aleks.christmasboard.elements;

import by.aleks.christmasboard.data.GameData;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Stump extends TouchableElement{
	
	private static final float STUMP_WIDTH = 141*GameData.scale;
	private static final float STUMP_HEIGHT = 141*GameData.scale;

	public Stump(BoardLogic boardL) {
		super(boardL);
		this.setSize(STUMP_WIDTH, STUMP_HEIGHT);
		TextureRegionDrawable style = new TextureRegionDrawable(GameData.getRegion("stump"));
		this.setDrawable(style);
	}

	
	


}

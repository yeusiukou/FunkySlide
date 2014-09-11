package by.aleks.christmasboard.elements;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;

public class Gift extends TouchableElement implements GoodElement{
	
	private static final float GIFT_SIDE = 141*GameData.scale;
	public static final int POINTS = 200; 
	
	public Gift(BoardLogic boardLogic){
		super(boardLogic);
		this.setSize(GIFT_SIDE, GIFT_SIDE);
		Random rand = new Random();
		TextureRegionDrawable style = new TextureRegionDrawable(GameData.getRegion("gift"+(rand.nextInt(4)+1)));
		this.setDrawable(style);

	}
	
	@Override
	public void inSack() {
		GameState.score+=POINTS;
		super.inSack();
	}

}

package by.aleks.christmasboard.elements;

import java.util.Random;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Gingerman extends TouchableElement implements GoodElement{
	
	public static final int POINTS = 400; 
	private static float WIDTH = 120*GameData.scale;
	private static float HEIGHT = 141*GameData.scale;
	
	
	public Gingerman(BoardLogic boardLogic){
		super(boardLogic);
		this.setSize(WIDTH, HEIGHT);
		Random rand = new Random();
		TextureRegionDrawable style = new TextureRegionDrawable(GameData.getRegion("gingerman"+(rand.nextInt(2)+1)));
		this.setDrawable(style);

	}


	@Override
	public void inSack() {
		GameState.score+=POINTS;
		super.inSack();
	}
	
	

}

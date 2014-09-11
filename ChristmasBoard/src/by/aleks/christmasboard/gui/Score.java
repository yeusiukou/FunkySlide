package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.data.ShaderLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Score extends Group {
		
	private static float SCORE_SIGN_WIDTH = 96*GameData.scale;
	private static float SCORE_SIGN_HEIGHT = 180*GameData.scale;
	
	private static final float POINTS_X = 65*GameData.scale;
	private static final float POINTS_RELATIVE_Y = 0.03f*Gdx.graphics.getHeight();
	private static final BitmapFont FONT = Fonts.getMainFont();
	private static final float FONT_SIZE = 105;
	
	
	public Score(float posX, float posY){
		
		this.posX = posX;
		this.posY = posY;
		
		addScoreSign();
		addScorePoints();
	}
	
	
	
	@Override
	public void act(float delta) {
		if(tempScore<GameState.score){
			if(multiple==0)
				multiple = (GameState.score - tempScore)/10;
			score.setText(String.valueOf((Math.round(tempScore+=multiple))));
		} else if (GameState.score == 0)
			score.setText("0");
		super.act(delta);
	}



	private void addScoreSign(){
		Image scoreSign = new Image(GameData.getRegion("score_symbol"));
		scoreSign.setSize(SCORE_SIGN_WIDTH, SCORE_SIGN_HEIGHT);
		scoreSign.setPosition(posX, posY);
		this.addActor(scoreSign);
	}
	
	private void addScorePoints(){
		float pointsX = POINTS_X +posX;
		float pointsY = POINTS_RELATIVE_Y + posY;		
		
		score = new ShaderLabel(String.valueOf(GameState.score), Color.YELLOW, FONT_SIZE, FONT);
		score.setPosition(pointsX, pointsY);
		this.addActor(score);
	}
	
	
	private ShaderLabel score;
	private float posX, posY;
	private float tempScore = 0;
	private float multiple= 0;
	
	

}

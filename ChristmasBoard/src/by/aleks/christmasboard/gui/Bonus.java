package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.data.ShaderLabel;
import by.aleks.christmasboard.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/** Describes the bonus counter in the top middle of the screen. */
public class Bonus extends ShaderLabel{
	
	private static final float AWAY_SPEED = 50f*GameData.scale;
	private static final int BONUS_SCORE = 300;
	private static final float WAITING_TIME = 150;
	private static final int GARANTEED_ACHIEVEMENT_BONUS = 4;
	private static final BitmapFont FONT = Fonts.getMainFont();
	private static final float FONT_SIZE = 80;

	public Bonus (float posY) {
		super("x"+bonusCounter, Color.YELLOW, FONT_SIZE, FONT);
		setPosition((Gdx.graphics.getWidth()-getWidth())/2, posY);
		setColor(1, 1, 1, 0);
	}
	
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(GameState.score>scoreOld){
			heat();
			scoreOld = GameState.score;
		}

		estimatedTime = (int) ((System.nanoTime() - startTime)/10000000.0f);//in milliseconds
		
		if (estimatedTime>WAITING_TIME){
			
			if (firstTimeMessageFlag){
				showMessage();
				firstTimeMessageFlag = false;
			}
			
			if (bonusCounter > 1)
				goAwayEffect();
			disappear();
			if (transp <= 0)
				resetAndCount();

		}
		
		super.draw(batch, parentAlpha);
	}
	
	
	private void heat(){
		if (estimatedTime>WAITING_TIME){
			setColor(1, 1, 1, 1);
			transp = 1;
		} else bonusCounter++;
		setText("x"+bonusCounter);
		startTime = System.nanoTime();
	}
	
	private void goAwayEffect(){
		setPosition(getX()-AWAY_SPEED*GameData.scale, getY());

	}
	
	private void disappear(){
		setColor(1, 1, 1, transp-=0.1f);
	}
	
	//Centers position and counts the bonus score;
	private void resetAndCount() {

		setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, getY());

		if (bonusCounter > 1) {
			GameState.score += BONUS_SCORE * bonusCounter;
			scoreOld = GameState.score;
			bonusCounter = 1;
		}
		
		firstTimeMessageFlag = true;
	}
	
	private static void showMessage(){
		if(bonusCounter > GameState.bonusRecord){
			GameScreen.showMessage("New record!", bonusCounter+" presents at a run");
			GameState.bonusRecord = bonusCounter;
		} else if(bonusCounter >= GARANTEED_ACHIEVEMENT_BONUS){
			GameScreen.showMessage("Well done!", bonusCounter+" presents at a run");
			GameState.bonusRecord = bonusCounter;
		}
	}



	private int estimatedTime = (int) ((System.nanoTime() - startTime)/10000000.0f);
	private static long startTime = System.nanoTime();
	private float transp = 0;
	private static int bonusCounter = 1;
	private int scoreOld = 0;
	private boolean firstTimeMessageFlag = true;
	

}

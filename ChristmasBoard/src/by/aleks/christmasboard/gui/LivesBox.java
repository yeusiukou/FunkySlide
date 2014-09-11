package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class LivesBox extends Group{
	
	public static final float BOX_WIDTH = 396*GameData.scale;
	private static final float BOX_HEIGHT = 94*GameData.scale;	
	private static final float UNIT_WIDTH = 52*GameData.scale;
	private static final float UNIT_HEIGHT = 58*GameData.scale;
	private static final float FIRST_UNIT_X = -35*GameData.scale;
	private static final float SPACE_BETWEEN_UNITS = 18*GameData.scale;
	
	public LivesBox(float posX, float posY){
		
		setWidth(BOX_WIDTH);
		setHeight(BOX_HEIGHT);
		setPosition(posX, posY);
		
		Image box = new Image(GameData.getRegion("lives_box"));
		box.setWidth(BOX_WIDTH);
		box.setHeight(BOX_HEIGHT);
		this.addActor(box);
		
		lives = new Icon[GameState.MAX_LIVES];
		float unitX = FIRST_UNIT_X;
		for(int i=0; i<lives.length; i++){
			lives[i] = new Icon();
			lives[i].setSize(UNIT_WIDTH, UNIT_HEIGHT);
			lives[i].setPosition(unitX+=(SPACE_BETWEEN_UNITS+UNIT_WIDTH), (BOX_HEIGHT-UNIT_HEIGHT)/2);
			this.addActor(lives[i]);
		}
		update();
	}
	
	/** Draws lives(presents left in the box). */
	public void update(){
		if(lastNumLives!=GameState.lives){
			lastNumLives = GameState.lives;
			
			int i;
			for(i=0; i<GameState.lives; i++){
				lives[i].makeActive();
			}
			for(; i<GameState.MAX_LIVES; i++){
				lives[i].switchOff();
			}
		}
		lastNumLives = GameState.lives;
	}

	
	private class Icon extends Image{
		
		private boolean switched;
		private long estimatedTime;
		private long startTime;
		private TextureRegionDrawable lifeActive = new TextureRegionDrawable(GameData.getRegion("life_active"));
		private TextureRegionDrawable lifeInactive = new TextureRegionDrawable(GameData.getRegion("life_inactive"));
		
		
		public Icon() {

			addAction(new Action(){
				//Blinking when the player loses a gift.
				
				private final int blinkTimes = 4;
				private final int blinkDelay = 100;
				int counter = 0;

				@Override
				public boolean act(float delta) {
					
					if(switched){
						estimatedTime = (int) ((System.nanoTime() - startTime)/1000000.0f);
						if(estimatedTime >= blinkDelay && counter < blinkTimes){
							setDrawable(getDrawable() == lifeActive ? lifeInactive : lifeActive);
							startTime = System.nanoTime();
							counter++;
						} else if (counter >= blinkTimes){
							makeInactive(); //Turn it for sure.
							return true;
						}
					}

					return false;
				}
				
			});
			
		}
		
		public void makeActive(){
			setDrawable(lifeActive);
		}
		
		public void makeInactive(){
			setDrawable(lifeInactive);
		}
		
		public void switchOff(){
			switched = true;
			startTime = System.nanoTime();
			makeInactive();
		}
		
	}



	private Icon[] lives;
	private int lastNumLives = 0;

}

package by.aleks.christmasboard.elements;

import java.util.Iterator;

import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.data.ShaderLabel;
import by.aleks.christmasboard.data.Sounds;
import by.aleks.christmasboard.entities.Point;
import by.aleks.christmasboard.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Bomb extends TouchableElement {
	
	private static final float BOMB_SIDE = 141*GameData.scale;
	private static final int TIMER = 9;
	private static final float EXPLOSION_SIZE = 250*GameData.scale;
	private static final BitmapFont TIMER_FONT = Fonts.getSylfaenFont();
	private static final float TIMER_FONT_SIZE = 50;
	
	public Bomb(BoardLogic boardLogic){
		super(boardLogic);
		this.setSize(BOMB_SIDE, BOMB_SIDE);
		TextureRegionDrawable style = new TextureRegionDrawable(GameData.getRegion("bomb"));
		this.setDrawable(style);
		addTimer();
		
		bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("data\\effects\\bang.p"), Gdx.files.internal("data\\effects"));
		//Setting the exlosion size.
		bombEffect.getEmitters().get(0).getScale().setHigh(EXPLOSION_SIZE);
		bombEffect.getEmitters().get(0).getScale().setLow(EXPLOSION_SIZE);
		bombEffect.getEmitters().get(1).getScale().setHigh(EXPLOSION_SIZE);
		bombEffect.getEmitters().get(1).getScale().setLow(EXPLOSION_SIZE);
	}


	public void addTimer(){
		
		timeLeftLabel = new ShaderLabel(String.valueOf(TIMER), Color.WHITE, TIMER_FONT_SIZE, TIMER_FONT);
		timeLeftLabel.setVisible(false);
		boardL.addActor(timeLeftLabel);

		Action bombTimer = new Action(){			

			@Override
			public boolean act(float delta) {
				bombEffect.setPosition(Bomb.this.getX()+Bomb.this.getWidth()/2, Bomb.this.getY()+Bomb.this.getHeight()/2);
				bombEffect.update(delta);
				
				//Pause timer
				if(GameState.pause){
					startTime=System.nanoTime()-Integer.valueOf(timeLeftLabel.getText().toString());
					pauseTime = TIMER-Integer.valueOf(timeLeftLabel.getText().toString());
				} 
				estimatedTime = (int) ((System.nanoTime() - startTime)/1000000000.0f); //Time past from bomb creating in seconds.
				
				if(timeLeft==0 && !explosed){
					bang();
					return false;
				}

				timeLeft = TIMER-pauseTime-estimatedTime;
				timeLeftLabel.setPosition(Bomb.this.getX()+Bomb.this.getWidth()-timeLeftLabel.getWidth(), Bomb.this.getY());
				if (timeLeft!=0)
					timeLeftLabel.setText(String.valueOf(timeLeft));
				
				//I have to wait until the explosion effect is completed
				//and only after this destroy the element.
				if(explosed && bombEffect.isComplete()){
					Bomb.this.delete();
					boardL.touchUP(null);
					
					if(badElementsDestroyed>3)
						GameScreen.showMessage("Neat shot!", badElementsDestroyed+" bad presents destroyed");
					badElementsDestroyed = 0;
				}
				return false;
			}
			
			public void bang(){
				Sounds.playBang();
				explosed = true;
				bombEffect.start();
				Bomb.this.setDrawable(null);//At the time of the explosion effect the bomb should already be invisible.
				timeLeftLabel.remove();
				

				Iterator<Element> itr = Element.getElementsIterator();
				while(itr.hasNext()){
					Element element = itr.next();
					if(element!=null && element.getActualCell()!=null && (
							
							//I want my bomb to destroy nearest vertical and horizontal elements (the explosion looks like a cross)
							( Math.abs(element.getActualCell().getX()-Bomb.this.getActualCell().getX())<=1
							&& element.getActualCell().getY()==Bomb.this.getActualCell().getY() )
							
							|| (Math.abs(element.getActualCell().getY()-Bomb.this.getActualCell().getY())<=1
							&& element.getActualCell().getX()==Bomb.this.getActualCell().getX() )) ){
						
						if(element instanceof GoodElement){
							GameState.lives--;
							vibrate();
						}
						 
						else if(element!=Bomb.this)
							badElementsDestroyed++;
						
						itr.remove();
						if(element!=Bomb.this)
							element.delete();
					}
				}
				
				boardL.setFall(false);//if an element was destroyed before it got to the board, change the flag.

			}
			
		};
		
		this.addAction(bombTimer);
	}
	
	private void vibrate(){
		if (!vibrationFlag){ //I want it to vibrate only once.
			Gdx.input.vibrate(500);
			vibrationFlag = true;
		}
	}
	
	
	@Override
	public void delete() {
		if(!explosed && !falling){ //If the bomb is deleted(blown) by another bomb, there must be a chain reaction
			timeLeft=0;
			badElementsDestroyed--;
			return;
		}

		System.out.println("DELETE");
		timeLeftLabel.remove();
		super.delete();
	}


	@Override
	public void draw(Batch batch, float parentAlpha) {
		bombEffect.draw(batch);
		super.draw(batch, parentAlpha);

	}


	@Override
	public void inSack() {
		explosed = true;
		Gdx.input.vibrate(500);
		Sounds.playBang2();
		GameState.lives = 0; // If a bomb gets to the sack player loses.
	}

  
	@Override
	public void setNewCell(Point cellNum) {
		if(getPreviousCell()==null && startTime==0){
			startTime = System.nanoTime();
			timeLeftLabel.setVisible(true);
		}
		super.setNewCell(cellNum);
	}


	private ShaderLabel timeLeftLabel;
	private long timeLeft = -1;
	private long startTime = 0;
	private int estimatedTime = 0;
	private long pauseTime;
	private ParticleEffect bombEffect;
	private boolean explosed;
	private static int badElementsDestroyed = 0;
	private boolean vibrationFlag;

}

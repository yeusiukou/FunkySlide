package by.aleks.christmasboard.elements;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.Sounds;
import by.aleks.christmasboard.entities.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class TouchableElement extends Element {	
	
	private static final float MOVING_SPEED = 20*GameData.scale;
	
	public TouchableElement(BoardLogic boardLogic){
		
		super(boardLogic);

		this.addListener(new DragListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				startX = Gdx.input.getX();
				startY = Gdx.input.getY();
				startElementX = TouchableElement.this.getX();
				startElementY = TouchableElement.this.getY();
				touchedUP = false;
				noObstacles = true;
				return true; //We return true because only in this case touchDragged is gonna work.
			}
			
			

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				
				//Instead of using coordinates from the method's input I use the global GDX.input.* ones
				//becouse the former work weirdly.
				if (dir == null) dir=getDirection();

				if(boardL.isFall()){
					startX = Gdx.input.getX();
					startY = Gdx.input.getY();
					return;
				}

				if ( dir == Direction.RIGHT || dir == Direction.LEFT ){
					move(Gdx.input.getX()-startX, 0, TouchableElement.this);
				} else move(0, startY - Gdx.input.getY(), TouchableElement.this);
				
				startX = Gdx.input.getX();
				startY = Gdx.input.getY();


			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				touchedUP = true;
				if (reachedNextCell())
					finishSlide();
			}				
			
		});		
				
	}
	
	
	@Override
	public void act(float delta) {

		// For a short swipe the elements go to th next cell.
		if (dir!=null && touchedUP && !reachedNextCell()) {
			if (noObstacles) {

				switch (dir) {
				case RIGHT:
					noObstacles = move(MOVING_SPEED, 0, TouchableElement.this);
					break;
				case LEFT:
					noObstacles = move(-MOVING_SPEED, 0, TouchableElement.this);
					break;
				case UP:
					noObstacles = move(0, MOVING_SPEED, TouchableElement.this);
					break;
				case DOWN:
					noObstacles = move(0, -MOVING_SPEED, TouchableElement.this);
					break;
				default:
					throw new RuntimeException("dir is " + dir.toString());
				}

			}

			if (!noObstacles)
				finishSlide();
		} else if (dir!=null && touchedUP && reachedNextCell())
			finishSlide();

		super.act(delta);

	}

	/** Returns direction elements will move on. */
	private Direction getDirection(){
		float distX = Gdx.input.getX()-startX;
		float distY = Gdx.input.getY()-startY;
		if( Math.abs(distX) > Math.abs(distY) ){
			if(distX>=0) return Direction.RIGHT;
			else return Direction.LEFT;
		}
		else{
			if(distY>=0) return Direction.DOWN;
			else return Direction.UP;
		}
	}
	
	/** Check if the element haven't reached the next cell when the finger was released. */
	private boolean reachedNextCell(){
		if(Math.abs(startElementX - TouchableElement.this.getX()) > CellLogic.SIDE 
				|| Math.abs(startElementY - TouchableElement.this.getY()) > CellLogic.SIDE) return true;
		else return false;
	}
	
	/** Finishing the element movement. */
	private void finishSlide(){
		boardL.touchUP(dir);
		dir=null;
		Sounds.playSlide();
	}
	
	
	@Override public void inSack() {}


	
	private float startX, startY, startElementX, startElementY;
	private Direction dir;
	private boolean touchedUP, noObstacles;

}

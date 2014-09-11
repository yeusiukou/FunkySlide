package by.aleks.christmasboard.elements;

import java.util.Iterator;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.entities.Point;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Penguin extends TouchableElement implements GoodElement{
	
	private static final float WIDTH = 120*GameData.scale;
	private static final float HEIGHT = 141*GameData.scale;
	private static final int POINTS = 400;
	
	public Penguin(BoardLogic boardL) {
		super(boardL);
		this.setSize(WIDTH, HEIGHT);
		TextureRegionDrawable style = new TextureRegionDrawable(GameData.getRegion("penguin_front"));
		this.setDrawable(style);
	}
	
	@SuppressWarnings("unused")
	// TODO: Finish the penguin moving algorithm.
	private void addAction(){
		Action walking = new Action(){
			
			@Override
			public boolean act(float delta) {
				if(upIsFree()) Penguin.this.move(0, 1, Penguin.this);
				else if(downIsFree()) Penguin.this.move(0, -1, Penguin.this);
				else if(leftIsFree()) Penguin.this.move(-1, 0, Penguin.this);
				else if(rightIsFree()) Penguin.this.move(1, 0, Penguin.this);
				CellLogic actualCell= boardL.locateCell(Penguin.this);
				setNewCell(new Point(actualCell.getNumX(), actualCell.getNumY()));
				return false;
			}
			
			private boolean upIsFree(){
				Iterator<Element> itr = Element.getElementsIterator();
				Element element;
				while(itr.hasNext()){
					element = itr.next();
					if(element.getActualCell().getX()==Penguin.this.getActualCell().getX()
							&& element.getActualCell().getY()==Penguin.this.getActualCell().getY()+1)
						return false;
				}
				return true;

			}
			private boolean downIsFree(){
				Iterator<Element> itr = Element.getElementsIterator();
				Element element;
				while(itr.hasNext()){
					element = itr.next();
					if(element.getActualCell().getX()==Penguin.this.getActualCell().getX()
							&& element.getActualCell().getY()==Penguin.this.getActualCell().getY()+1)
						return false;
				}
				return true;
			}
			private boolean leftIsFree(){
				Iterator<Element> itr = Element.getElementsIterator();
				Element element;
				while(itr.hasNext()){
					element = itr.next();
					if(element.getActualCell().getY()==Penguin.this.getActualCell().getY()
							&& element.getActualCell().getX()==Penguin.this.getActualCell().getX()-1)
						return false;
				}
				return true;
			}
			private boolean rightIsFree(){
				Iterator<Element> itr = Element.getElementsIterator();
				Element element;
				while(itr.hasNext()){
					element = itr.next();
					if(element.getActualCell().getY()==Penguin.this.getActualCell().getY()
							&& element.getActualCell().getX()==Penguin.this.getActualCell().getX()+1)
						return false;
				}
				return true;
			}			
			
		};
		this.addAction(walking);
	}

	@Override
	public void inSack() {
		GameState.score+=POINTS;
		super.inSack();
	}
	
	

}

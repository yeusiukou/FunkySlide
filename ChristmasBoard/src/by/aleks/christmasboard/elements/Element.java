package by.aleks.christmasboard.elements;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.data.Sounds;
import by.aleks.christmasboard.entities.Point;
import by.aleks.christmasboard.gui.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Element extends Image {
	
	protected static float ELEMENT_MARGIN = 24*GameData.scale;
	//I need to limit maximal sliding speed otherwise I have no enough time to push other elements.
	private static final float MAX_SPEED = 27*GameData.scale;
	private static final float START_FALL_SPEED = 10*GameData.scale;
	private static final float ACCELERATION = 0.02f*GameData.scale;
	//private static final int PENALTY_POINTS = 300;
		
	
	public Element(BoardLogic boardL){
		this.boardL = boardL;
		addActions();
		elementsOnBoard.add(this);
	}
	

	public void setPosition(Point pos) {
		this.setPosition(pos.getX(), pos.getY());
	}
	


	public boolean move(float distX, float distY, Element pushingElement){

		if(distX>MAX_SPEED) distX=MAX_SPEED;
		else if(distX<-MAX_SPEED) distX=-MAX_SPEED;
		if(distY>MAX_SPEED) distY=MAX_SPEED;
		else if(distY<-MAX_SPEED) distY=-MAX_SPEED;
		
		if(checkEdges()) return false;
		Element intersectedElement = getIntersectedElement(pushingElement);
		if(intersectedElement==null){
			
			if(checkEdges()) return false;
			
			//avoid intersected elements sticking.
			if ( getActualCell() == null || pushingElement.getActualCell() == null
					|| distX > 0 && pushingElement.getActualCell().getX() <= actualCell.getX()
					|| distX < 0 && pushingElement.getActualCell().getX() >= actualCell.getX()
					|| distY > 0 && pushingElement.getActualCell().getY() <= actualCell.getY()
					|| distY < 0 && pushingElement.getActualCell().getY() >= actualCell.getY())

				setPosition(this.getX() + distX, this.getY() + distY);
			
			return true;
		}
		if(intersectedElement.getActualCell()==null) return true;
		else
			try {
				if (intersectedElement.move(distX, distY, this)){
					setPosition(this.getX()+distX, this.getY()+distY);
					return true;
				} else return false;
			} catch (StackOverflowError e) {
				return false;
			}
	}
	
		
	
	private boolean checkEdges(){
		//Check if the element is new (it would be out of the board).
		if(this.getActualCell()==null) return false;
		//Check the door.
		if( getX()>=boardL.getDoorXStart() && getX()+getWidth()<=boardL.getDoorXEnd() && getY()+getHeight()<boardL.getY()+getHeight())
			return false;
		//Check the board edges.
		if( getX()<boardL.getX() || getX()+getWidth()>boardL.getX()+boardL.getWidth()
				|| getY()<boardL.getY() || getY()+getHeight()>boardL.getY()+boardL.getHeight() )
			return true;
		return false;
	}
	
	
	public abstract void inSack();

	
	private boolean intersects(Element element){
		//X not intersects.
		if( getX()+getWidth()+ ELEMENT_MARGIN<element.getX() || getX()>element.getX()+element.getWidth()+ELEMENT_MARGIN )
			return false;
		//Y not intersects.
		if (getY()+getHeight()+ELEMENT_MARGIN<element.getY() || getY()>element.getY()+element.getHeight()+ELEMENT_MARGIN )
			return false;
		return true;
	}
	
	public Element getIntersectedElement(Element pushingElement){
		Iterator<Element> itr = elementsOnBoard.iterator();
		while(itr.hasNext()){
			Element element = itr.next();
			if(element==pushingElement) continue;
			if (element!=this  && element.intersects(this))
				return element;
		}
		return null;
	}
	
	//--------------------------------------------------Actions-------------------------------------------------------//
	private void addActions(){
		//Acts when an element falls to the sack.
		Action goToSack = new Action() {
			
			@Override
			public boolean act(float delta) {
				
				if(getY()+getHeight()/2<boardL.getY()){
					setPosition(getX(), getY()-(fallSpeed+=ACCELERATION));
					Element.this.setNewCell(null); //make the door cell available.
					
					//If an element reached the sack.
					if(getY()<-200*GameData.scale){
						Element.this.delete();
						Sounds.playFall2();
						
						if (!(Element.this instanceof GoodElement)){
							//GameState.score-=PENALTY_POINTS;
							GameState.lives--;
							Gdx.input.vibrate(500);
						}
						inSack();
					}
				}
				return false;
			}
		};
		this.addAction(goToSack);
		
		//Acts when we create a new element out of the board.
		Action goToBoard = new Action() {

			@Override
			public boolean act(float delta) {
				
				if(Element.this.getY()+170*GameData.scale>Board.BOARD_SIDE){ //170 - is the most optimal length (tested experementally).
					if(fallSpeed>MAX_SPEED) fallSpeed = MAX_SPEED-2;
					if(!move(0, -(fallSpeed+=ACCELERATION), Element.this) || yOld == Element.this.getY()){
						delete();
						return true;
					}
					yOld = Element.this.getY(); //fixes the problem when elements just hang somwhere above the board.
					falling = true;
					boardL.setFall(true);
					//System.out.println(id+" => x:"+Element.this.getX()+", y:"+Element.this.getY());
					return false;
				}
				if(falling){
					Sounds.playFall1();
					boardL.alignAllElements(true);
					falling=false;
					boardL.setFall(false);
				}
				return false;
			}
		};
		this.addAction(goToBoard);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void delete(){
		elementsOnBoard.remove(Element.this);
		Element.this.remove();
		if (!(Element.this instanceof GoodElement) && !(Element.this instanceof Bomb))
			ElementsBase.decreaseBadPresentCounter(1);
		if(falling)
			boardL.setFall(false);//if the element was destroyed before it got to the board, change the flag.
	}


	public void setNewCell(Point cellNum){
		if(actualCell!=null)previousCell = actualCell;
		actualCell = cellNum;
	}
	
	public Point getActualCell(){
		return actualCell;
	}
	
	public Point getPreviousCell(){
		return previousCell;
	}
	
	public String toString(){
		if(previousCell!=null) return this.getClass().getSimpleName()+". Actual cell: "+actualCell.getX()+" , "+actualCell.getY()+". Previous cell"
	+previousCell.getX()+" , "+previousCell.getY();
		else return this.getClass().getSimpleName()+". Actual cell: "+actualCell.getX()+" , "+actualCell.getY();
	}
	


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static ListIterator<Element> getElementsIterator(){
		//Sorting elements by cells they are located on.
		Collections.sort(elementsOnBoard, new Comparator<Element>() {
	        @Override
	        public int compare(Element el1, Element el2)
	        {
	        	try{
		            return  el1.getActualCell().compareTo(el2.getActualCell());
	        	}catch (NullPointerException e){}
	        	return 0;
	        }
	    });
		return elementsOnBoard.listIterator();
	}
	
	public static void clearElements(){
		Iterator<Element> itr = elementsOnBoard.iterator();
		while(itr.hasNext())
			itr.next().remove();
		elementsOnBoard.clear();
	}
	
	public static int getElementsAmount(){
		return elementsOnBoard.size();
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	


	protected BoardLogic boardL;
	protected boolean falling=false;
	private static ArrayList<Element> elementsOnBoard = new ArrayList<Element>();
	private float fallSpeed = START_FALL_SPEED;
	private Point actualCell;
	private Point previousCell;
	private float yOld = 0;

}

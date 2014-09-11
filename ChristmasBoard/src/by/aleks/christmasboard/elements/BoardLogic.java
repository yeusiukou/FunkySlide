package by.aleks.christmasboard.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.entities.Direction;
import by.aleks.christmasboard.entities.Point;
import by.aleks.christmasboard.gui.Board;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class BoardLogic extends Group{
	
	private static final int ROWS = 4;
	private static final int COLUMNS = 4;
	public static final int DOOR_NUM_X = 2;
	public static final int DOOR_NUM_Y = 0;
	
	public BoardLogic (){
		Element.clearElements();

		this.setWidth(COLUMNS*CellLogic.SIDE);
		this.setHeight(ROWS*CellLogic.SIDE);
		cells = new CellLogic[ROWS][COLUMNS];
		for (int i=0; i<ROWS; i++){
			for (int k=0; k<COLUMNS; k++){
				cells[k][i] = new CellLogic(k,i);
			}
		}
	}
	
	
	/** Places elements on the board */
	public void loadElements(){
		int emptyNum = rand.nextInt(15);
		startEmptyCell = new Point((int)(emptyNum/COLUMNS), emptyNum%ROWS);
		int counter = 0;
		for(int i=0; i<cells.length; i++){
			for(int k=0; k<cells[i].length; k++){
				if(counter++!=emptyNum){
					Element element = ElementsBase.newRandomElement(this);
					element.setPosition(cells[i][k].getPosition(element.getWidth(), element.getHeight()));
					element.setNewCell(new Point(i,k));
					this.addActor(element);
				}
				
			}
		}
	}
	
	/** Aligns all elements on the board. */
	public void alignAllElements(boolean fromFirst){
		List<Point> filledCells = new ArrayList<Point>();
		ListIterator<Element> iter = Element.getElementsIterator();
		//Align starting from first element
		if(fromFirst){
			while(iter.hasNext()){
				Element el = iter.next();
				alignElement(el);
				if (avoidOverlapping(el, filledCells)) {
					iter.remove();
					el.delete();
				}
			}
		}
		//Align starting from last element
		else{
			while(iter.hasNext()) iter.next();
			while(iter.hasPrevious()){
				Element el = iter.previous();
				alignElement(el);
				if (avoidOverlapping(el, filledCells)) {
					iter.remove();
					el.delete();
				}
			}
		}
	}
	
	
	private boolean avoidOverlapping(Element el, List<Point> filledCells){
		//Checking if there already is an element placed in this cell.
		for (Point p:filledCells){				
			//Here I avoid of elements overlapping 
			try {
				if (p.equals(el.getActualCell())) {
					
					if(el.getPreviousCell()==null||el.getPreviousCell().equals(el.getActualCell())){
						return true;
					}

					int elCellX = (int) el.getActualCell().getX();
					int elCellY = (int) el.getActualCell().getY();
					
					// push to right
					if (elCellX > el.getPreviousCell().getX()) {
						el.setPosition(cells[elCellX -1 ][elCellY].getPosition(el.getWidth(), el.getHeight()));
						el.setNewCell(new Point(elCellX - 1, elCellY));
					} else
					// push to left
					if (elCellX < el.getPreviousCell().getX()) {
						el.setPosition(cells[elCellX + 1][elCellY].getPosition(el.getWidth(), el.getHeight()));
						el.setNewCell(new Point(elCellX + 1, elCellY));
					} else
					// push to up
					if (elCellY > el.getPreviousCell().getY()) {
						el.setPosition(cells[elCellX][elCellY-1].getPosition(el.getWidth(),el.getHeight()));
						el.setNewCell(new Point(elCellX, elCellY-1));
					} else
					// push to down
					if (elCellY < el.getPreviousCell().getY()) {
						el.setPosition(cells[elCellX][elCellY + 1].getPosition(el.getWidth(), el.getHeight()));
						el.setNewCell(new Point(elCellX, elCellY + 1));
					}
				}
			} catch (NullPointerException e) {}

		}
		filledCells.add(el.getActualCell());
		return false;
	}
	
	/** Aligns given element in the cell. */
	public void alignElement(Element element){
		CellLogic actualCell = locateCell(element);
		if(actualCell!=null){
			element.setPosition(actualCell.getPosition(element.getWidth(), element.getHeight()));
			element.setNewCell(new Point(actualCell.getNumX(),actualCell.getNumY()));
		}
	}
	
	public CellLogic locateCell(Element element){
		for (int i=0; i<ROWS; i++){
			for (int k=0; k<COLUMNS; k++){
				if( cells[k][i].hasPoint(element.getX()+element.getWidth()/2, element.getY()+element.getHeight()/2 ))
					return cells[k][i];
			}
		}
		return null;
	}
	
	
	private void elementCreator(){

		int counter2 = 0;
		for (int i=0; i<COLUMNS; i++){
			if(Element.getElementsAmount()>=ROWS*COLUMNS-1)
				return;
			Iterator<Element> itr = Element.getElementsIterator();
			while(itr.hasNext()){
				Element element = itr.next();
				if (element==null) continue;
				try {
					if(element.getActualCell()!=null && element.getActualCell().getX()==i)
						counter2++;
				} catch (Exception e) {
					//we do nothing, the problem fixes itself. 
					counter2++;
				}
			}

			for(int k=0; k<ROWS-counter2; k++){
				if(Element.getElementsAmount()>=ROWS*COLUMNS-1)
					return;
				sendElement(i);
			}
			queueCounter=0;
			counter2=0;
		}
	}
	
	private void sendElement(int numX){
		Element element = ElementsBase.newRandomElement(this);
		element.setPosition(cells[numX][0].getPosition(element.getWidth(), element.getHeight()).getX(), Board.BOARD_SIDE+queueCounter*CellLogic.SIDE*2);
		//this counter is needed to avoid overlaping of new elements out of the board.
		queueCounter++;
		this.addActor(element);
	}
	
	
	
	public float getDoorXStart(){
		return getX()+DOOR_NUM_X+2*CellLogic.SIDE;
	}
	public float getDoorXEnd(){
		return getX()+DOOR_NUM_X+3*CellLogic.SIDE;
	}
	
	public void touchUP(Direction dir){
		if(dir==Direction.UP||dir==Direction.RIGHT||dir==null)
			alignAllElements(true);
		else alignAllElements(false);
		
		//Before running the elementCreator I need to wait for a while for the case some element falling to the sack.
		float delay = 0.1f; // seconds
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	queueCounter=0;
				if(!GameState.gameOver)
					elementCreator();
		    }
		}, delay);
		fall = false;
	}

	public void setFall(boolean fall){ //Here I register the momement of falling an element on the board.
		this.fall = fall;
	}
	
	public boolean isFall(){
		return fall;
	}
	
	
	
	private CellLogic[][] cells;
	private Random rand = new Random();
	private int queueCounter=0;
	private boolean fall;
	public static Point startEmptyCell;

}

package by.aleks.christmasboard.elements;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.entities.Point;

public class CellLogic {
	
	public static final float SIDE = 166*GameData.scale;
	private static final float MARGIN = 0*GameData.scale;
	
	public CellLogic(int numX, int numY){
		
		this.numX=numX;
		this.numY=numY;
		
		posX = SIDE*numX;
		posY = SIDE*numY;
		
	}
	
	public CellLogic(Point num) {

		numX = (int) num.getX();
		numY = (int) num.getY();

		posX = SIDE * numX;
		posY = SIDE * numY;

	}
	
	/** Returns point to center an element in the cell
	 * @param width - element's width
	 * @param height - element's height */
	public Point getPosition(float width, float height){
		return new Point( posX+(SIDE-width), posY+(SIDE-height));
	}
	
	/** Checks if the cell contains the given point. */
	public boolean hasPoint(Point point){
		if(point.getX()>=posX+MARGIN && point.getX()<=(posX+SIDE-MARGIN)
				&& point.getY()>=posY+MARGIN && point.getY()<=(posY+SIDE-MARGIN)
				) return true;
		return false;
	}
	public boolean hasPoint(float x, float y){
		if(x>=posX+MARGIN && x<=(posX+SIDE-MARGIN)
				&& y >=posY+MARGIN && y<=(posY+SIDE-MARGIN)
				) return true;
		return false;
	}	
	
	
	
	public int getNumX() {
		return numX;
	}

	public int getNumY() {
		return numY;
	}
	
	public String toString(){
		return "Cell: "+(numX+1)+"."+(numY+1);
	}


	private float posX, posY;
	private int numX, numY;

}


package by.aleks.christmasboard.elements;

public class UntouchableElement extends Element{

	public UntouchableElement(BoardLogic boardL) {
		super(boardL);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public boolean move(float distX, float distY, Element pushingElement) {
		
		if(pushingElement!=this) //Untouchable element can't be pushed by any element.
			if(!boardL.isFall()) return false;
		if(checkEdges()) return false;
		Element intersectedElement = getIntersectedElement(pushingElement);
		if(intersectedElement==null){
			
			if(checkEdges()) return false;
			setPosition(this.getX()+distX, this.getY()+distY);
			return true;
		}
		if(intersectedElement.getActualCell()==null) return true;

		else if (intersectedElement.move(distX, distY, this)){
			setPosition(this.getX()+distX, this.getY()+distY);
			return true;
		} else return false;
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



	@Override public void inSack() {}

}

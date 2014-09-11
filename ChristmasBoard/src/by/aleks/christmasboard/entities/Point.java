package by.aleks.christmasboard.entities;

public class Point {
	
	/** Just a point */
	public Point(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public boolean equals(Point p){
		if(x==p.getX()&&y==p.getY())
			return true;
		else return false;
	}
	
	public int compareTo(Point p) {
		if(y<p.getY()) return 1;
		else if(y>p.getY()) return -1;
		else if(x<p.getX()) return 1;
		else if(x>p.getX()) return -1;
		else return 0;
	}

	@Override
	public String toString() {
		return "Point: x "+x+", y "+y;
	}



	private float x;
	private float y;
	

}

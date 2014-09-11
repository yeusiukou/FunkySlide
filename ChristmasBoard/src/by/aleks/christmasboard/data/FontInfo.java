package by.aleks.christmasboard.data;

import java.util.ArrayList;
import java.util.Iterator;

public class FontInfo {
	
	private String name;
	private int size;
	
	public FontInfo(String name, int size){
		this.name = name;
		this.size = size;
		fontList.add(this);
	}
	
	public String getName(){
		return name;
	}
	public int getSize(){
		return size;
	}
	
	public static Iterator<FontInfo> getFontIterator(){
		return fontList.iterator();
	}
	
	public String toString(){
		return name+"-"+size;
	}
	
	private static ArrayList<FontInfo> fontList = new ArrayList<FontInfo>();

}

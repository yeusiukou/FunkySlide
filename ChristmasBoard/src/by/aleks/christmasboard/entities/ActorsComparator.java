package by.aleks.christmasboard.entities;

import java.util.Comparator;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorsComparator implements Comparator<Actor>{
	
	//There is no way to sort actors on the stage by z-index, so I sort it by name.

	@Override
	public int compare(Actor a, Actor b) {
		if(a.getName()==null){
			if(b.getName()==null) return 0;
			else return -1;
		} else if(b.getName()==null) return 1;
		
		if( Integer.parseInt(a.getName()) > Integer.parseInt(b.getName()) )
			return 1;
		else if( Integer.parseInt(a.getName()) < Integer.parseInt(b.getName()) )
			return -1;
		else return 0;
		
	}

}

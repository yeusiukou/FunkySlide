package by.aleks.christmasboard.elements;

import java.util.Random;

import by.aleks.christmasboard.data.GameState;


public class ElementsBase {
	private static final int ELEMENTS_AMOUNT = 6;
	private static final int BOMB_APPEARING_FREQUENCE = 1;
	private static final int PILE_APPEARING_FREQUENCE = 1;
	
	public static Element newRandomElement(BoardLogic boardL) {
		int estimatedTime = (int) ((System.nanoTime() - startTime)/1000000000.0f);
		
		if(badPresentsCounter == 14)
			return new Bomb(boardL); //Eleminates the case when only bad presents are on the board.
		
		Random rand = new Random();
		switch(rand.nextInt(ELEMENTS_AMOUNT)){
		
		  case 0: return new Gift(boardL);
		  case 1: {
			  badPresentsCounter++;
			  return new Snowball(boardL);
		  }
		  case 2: {
			  if( estimatedTime > PILE_APPEARING_FREQUENCE && GameState.score!=0 ){
				  startTime = System.nanoTime();
				  badPresentsCounter++;
				  return new Pile(boardL);
			  } else return new Penguin(boardL);
		  }
		  case 3: {
			  badPresentsCounter++;
			  return new Stump(boardL);
		  }
		  case 4: return new Gingerman(boardL);
		  case 5: {
			  if( estimatedTime > BOMB_APPEARING_FREQUENCE && GameState.score!=0 ){
				  startTime = System.nanoTime();
				  return new Bomb(boardL);
			  } else return new Gingerman(boardL);
		  }
		  default: return null;
		}
		
	}
	
	public static void decreaseBadPresentCounter(int badPresentsDestroyed){
		badPresentsCounter -= badPresentsDestroyed;
		System.out.println(badPresentsCounter+"bad presents left");
	}
	
	public static void resetBadPresentCounter(){
		badPresentsCounter = 0;
	}
	
	private static long startTime = System.nanoTime();
	private static int badPresentsCounter = 0;

}

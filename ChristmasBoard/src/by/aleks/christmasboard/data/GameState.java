package by.aleks.christmasboard.data;

import by.aleks.christmasboard.elements.ElementsBase;

public class GameState {
	
	public static final int MAX_LIVES = 5; 
	
	public static String playerName = "Player";
	public static int score = 0;
	public static boolean pause = false;
	public static int lives = 5;
	public static boolean sound = true;
	public static int bonusRecord = 3;
	public static int level = 0;
	public static boolean gameOver = false;
	
	public static void resetGame(){
		GameData.writeScore();
		GameState.lives = MAX_LIVES;
		GameState.score = 0;
		GameState.level = 0;
		GameState.pause = false;
		ElementsBase.resetBadPresentCounter();
	}
	

}

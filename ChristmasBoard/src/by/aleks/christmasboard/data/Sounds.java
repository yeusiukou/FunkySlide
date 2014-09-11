package by.aleks.christmasboard.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

	
	public static void load(){
		fall1 = loadSound("fall_to_board.mp3");
		fall2 = loadSound("fall_into_sack.mp3");
		explosion1 = loadSound("explosion.mp3");
		explosion2 = loadSound("explosion_in_sack.mp3");
		slide = loadSound("slide.ogg");
		click = loadSound("click.mp3");
		level = loadSound("new_level.mp3");
		achievement = loadSound("achievement.mp3");
		
	}
	
	private static Sound loadSound (String filename) {
		return Gdx.audio.newSound(Gdx.files.internal("data/sounds/" + filename));
	}
	
	public static void playSlide(){
		if(GameState.sound){
			slide.play();
		}
	}
	
	public static void playFall1(){
		if(GameState.sound){
			fall1.play(0.8f);
		}
	}
	
	public static void playFall2(){
		if(GameState.sound){
			fall2.play(0.2f);
		}
	}
	
	public static void playClick(){
		if(GameState.sound){
			click.play(0.5f);
		}
	}
	
	public static void playBang(){
		if(GameState.sound){
			explosion1.play(0.5f);
		}
	}
	
	public static void playBang2(){
		if(GameState.sound){
			explosion2.play();
		}
	}
	
	public static void playNewLevel(){
		if(GameState.sound){
			level.play();
		}
	}
	
	public static void playAchievement(){
		if(GameState.sound){
			achievement.play(0.5f);
		}
	}


	
	
	
	private static Sound fall1; //On the board.
	private static Sound fall2; //On the sack.
	private static Sound explosion1; //On the board.
	private static Sound explosion2; //In the sack.
	private static Sound slide;
	private static Sound click;
	private static Sound level;
	private static Sound achievement;

}

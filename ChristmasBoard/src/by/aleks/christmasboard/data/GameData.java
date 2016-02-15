package by.aleks.christmasboard.data;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import by.aleks.christmasboard.ChristmasBoard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



/** This class contains different game resources like textures or fonts*/
public class GameData {
	
	private final static float ORIGINAL_WIDTH = 800;
	public static float scale = Gdx.graphics.getWidth()/ORIGINAL_WIDTH; //Scale for drawing objects with the same size on different resolutions
	private static String MENU_ATLAS_PATH = "data/menu.pack";
	private static String GAME_ATLAS_PATH = "data/game.pack";
	
	
	
	/**Here I use a very neat extension called Gdx-Freetype. It automatically converts ttf fonts to
	 * BitmapFont understandable by LibGdx */
	public static BitmapFont getFont(FontInfo fontInfo) {		
		
		return fontsCache.get(fontInfo.toString());
	}

		
	public static void loadAtlas(){
		menuAtlas = new TextureAtlas(MENU_ATLAS_PATH);
		gameAtlas = new TextureAtlas(GAME_ATLAS_PATH);
	}
	
	
	
	/** Finds in TextureAtlas and returns a TextureRegions by name*/
	public static TextureRegion getRegion(String name){
		TextureRegion region = menuAtlas.findRegion(name);
		if(region==null){
			region = gameAtlas.findRegion(name);
			if(region==null)
				System.out.println("No regions with the name \""+name+"\"");
		}
		return region;
	}
	
	/** Writes score to a file and sends to Google Play Services */
	public static void writeScore(){
		if(GameState.score!=0)
			scoreFile.writeString(Integer.toString(GameState.score)+"="+GameState.playerName+"\n", true);
		if(ChristmasBoard.playServiceActions.isSignedInGPGS())
			ChristmasBoard.playServiceActions.submitScoreGPGS(GameState.score);
	}
	
	
	/** Returns TreeMap with score as a key and player's name as a value or null if file doesn't exist. */
	public static TreeMap<Integer, ArrayList<String>> getScoreMap() {
		
		//As scorefile can have duplicate names with different scores, I suppose to use the multimap technique
		TreeMap<Integer, ArrayList<String>> scoreMap = new TreeMap<Integer, ArrayList<String>>();

		if (scoreFile.exists()) {

			try {
				String line;
				BufferedReader reader = new BufferedReader(scoreFile.reader());
				while ((line = reader.readLine()) != null){
					int separatorIndex = line.indexOf("=");
					if(separatorIndex == -1) //If in the files appeares a string without "=" we skip it.
						continue;
					String name = line.substring(separatorIndex+1);
					int score = Integer.parseInt(line.substring(0, separatorIndex));
					
					if(!scoreMap.containsKey(score))
						scoreMap.put(score, new ArrayList<String>());
					if(!scoreMap.get(score).contains(name))
						scoreMap.get(score).add(name);

				}
				reader.close();
			} catch (Exception E) {
				E.printStackTrace();
				System.out.println("Something is wrong in the file reading!");
			}

			return scoreMap;
			
		} else
			return null;
	}

		
	
	private static TextureAtlas menuAtlas = new TextureAtlas(MENU_ATLAS_PATH);
	private static TextureAtlas gameAtlas = new TextureAtlas(GAME_ATLAS_PATH);
	private static Map<String, BitmapFont> fontsCache;
	private static FileHandle scoreFile = Gdx.files.local("savefile/highscore.txt");

}

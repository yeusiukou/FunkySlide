package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.GameState;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class BottomButtons {
	
	public static Button getSoundButton(){
		if(soundButton == null){
			soundButton = new RoundButton(false, "sound", "sound_pressed", "nosound"){

				@Override
				public void clicked() {
					if(soundButton.isChecked())
						GameState.sound = false;
					else GameState.sound = true;
					super.clicked();
				}				
			};
		}
		return soundButton;
	}
	
	public static Button getInfoButton() {
		if (infoButton == null) {
			infoButton = new RoundButton(true, "info", "info_pressed");
			infoButton.setChecked(true);
		}
		return infoButton;
	}
	
	public static Button getRestartButton(){
		if(restartButton == null)
			restartButton = new RoundButton(true, "restart", "restart_pressed");
		return restartButton;
	}
	
	public static Button getBackButton(){
		return new RoundButton(false, "back", "back_pressed");
	}
	
	public static RoundButton getGlobeButton(){
		RoundButton globeButton = new RoundButton(true, "globe", "globe_pressed"){

			@Override
			public void clicked() {				
				//Open the leader board in Google play services
				ChristmasBoard.playServiceActions.runLeaderboardGPGS();
				super.clicked();
			}
			
		};
		
		return globeButton;
	}
	
	private static RoundButton soundButton;
	private static RoundButton infoButton;
	private static RoundButton restartButton;
	
}

package by.aleks.christmasboard;

import by.aleks.christmasboard.data.PlayServiceActions;

public class PSADesktop implements PlayServiceActions{

	@Override
	public boolean isSignedInGPGS() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loginGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		System.out.println("Submit score");
		
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runLeaderboardGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runAchievementsGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPlayServiceAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logoutGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}

}

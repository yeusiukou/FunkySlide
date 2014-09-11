package by.aleks.christmasboard.data;

public interface PlayServiceActions {
	
	public boolean isSignedInGPGS();
	public void loginGPGS();
	public void logoutGPGS();
	public void submitScoreGPGS(int score);
	public void unlockAchievementGPGS(String achievementId);
	public void runLeaderboardGPGS();
	public void runAchievementsGPGS();
	public boolean isPlayServiceAvailable();
	public void showAds(boolean show);

}

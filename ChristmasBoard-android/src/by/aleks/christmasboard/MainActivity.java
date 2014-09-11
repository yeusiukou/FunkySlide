package by.aleks.christmasboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.data.PlayServiceActions;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

public class MainActivity extends AndroidApplication implements GameHelperListener, PlayServiceActions
{
	
	private GameHelper gameHelper;
	private boolean openLeaderboard;
	private static final String AD_UNIT_ID = "ca-app-pub-4054710358219681/6760760650";
	protected AdView adView;
	protected View gameView;
	
	private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;

    @SuppressLint("HandlerLeak")
	protected Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                	if(adView.getVisibility() != View.VISIBLE){
                        adView.setVisibility(View.VISIBLE);
                        startAdvertising(adView);
                	}
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //prevent device from sleeping.
        
        //Initialilze Google Play Services
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.setup(this);
        gameHelper.setMaxAutoSignInAttempts(0); //I don't want it to sign in every time the game is started.

        
        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        // Create the libgdx View
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        
        View gameView = initializeForView(new ChristmasBoard(this), cfg);

        // Create and setup the AdMob view
        adView = createAdView();

        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view

        layout.addView(adView);
        showAds(false);

        // Hook it all up
        setContentView(layout);

    }
                     
    
  //-----------------------------------------AdMob-------------------------------------------//
    
	private AdView createAdView() {
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(AD_UNIT_ID);
		adView.setId(12345); // this is an arbitrary id, allows for relative positioning in createGameView()

        RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
            adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		adView.setLayoutParams(adParams);
		adView.setBackgroundColor(Color.TRANSPARENT);
		return adView;
	}


	private void startAdvertising(AdView adView) {
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}
	
	
	
	 @Override
	public void showAds(boolean show) {
		 handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);		
	}


	@Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) adView.resume();
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) adView.pause();
	    super.onPause();
	  }

	  @Override
	  public void onDestroy() {
	    if (adView != null) adView.destroy();
	    super.onDestroy();
	  }
      
      
    //-----------------------------------------Google Play Service-------------------------------------------//
    
    
    @Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
        	
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public boolean isSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIpLr6-e4dEAIQAQ", score);
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}
	
	@Override
	public void runLeaderboardGPGS() {
		if(!isSignedInGPGS()){ //If the player is not signed it, it will firstly sign in and then run the Leaderboard.
			openLeaderboard = true;
			loginGPGS();
			return;
		}
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIpLr6-e4dEAIQAQ"), 100);
	}

	@Override
	public void runAchievementsGPGS() {
		startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
	}


	@Override
	public void onSignInFailed() {
		System.out.println("sign in failed");
		
	}


	@Override
	public void onSignInSucceeded() {
		writePlayerName();
		if(openLeaderboard){
			runLeaderboardGPGS();
			openLeaderboard = false;
		}
		
	}
	
	@Override
	public boolean isPlayServiceAvailable(){
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		if(status == ConnectionResult.SUCCESS){
			return true;
		}
		else return false;
	}
	
	private void writePlayerName(){
        String name = Games.Players.getCurrentPlayer(gameHelper.getApiClient()).getDisplayName();
        int separatorIndex = name.indexOf(" ");
        if(separatorIndex != -1)
        	GameState.playerName = name.substring(0, name.indexOf(" ")); //I want to show just a name.
        else GameState.playerName = name;
        
	}


	@Override
	public void logoutGPGS() {
		gameHelper.signOut();		
	}
	
}
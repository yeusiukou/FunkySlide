package by.aleks.christmasboard.screens;

import by.aleks.christmasboard.ChristmasBoard;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.gui.Snowflake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class AbstractScreen implements Screen {
	
	private static final int SNOW_FLAKES_NUMBER = 100; //Number of falling snowflakes of every type (transparet and not)
	public static final Color BACKGROUND_COLOR = new Color(Color.rgba8888(0.08f, 0.12f, 0.33f, 1));
	private static final String SNOW_PILE_Z = "3";
	
	private Game game;
	protected SpriteBatch batch;
	protected Stage stage;
	private Image snowPileImage;
	
	public AbstractScreen(ChristmasBoard game){
		this.game = game;
		batch = game.getBatch();
		Gdx.input.setCatchBackKey(true);
		stage = new Stage(){
			//We make our stage listen for the back key pressing here.
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Keys.BACK) {
					keyBackIsPressed();
				}
				return super.keyDown(keyCode);
			}
			
		};
	}
	
	/** Does whatever you want, when the back key is pressed */
	public void keyBackIsPressed(){}

	@Override
	public void render(float delta) {
		
		//In dependence on an active screen choses an apropriate background color
		if(game.getScreen().getClass().getName().equals("by.aleks.christmasboard.screens.SplashScreen")){
			Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		} else Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		
	}
	
	@Override
	public void dispose() {
		stage.clear(); //Destroys all object added to the stage.		
	}
	
	
	
	/** Creates falling snowflakes and a snowdrift*/
	protected void fallingSnow(){
		snowPileImage = new Image(GameData.getRegion("snow"));
		snowPileImage.setWidth(Gdx.graphics.getWidth());
		
		double snowRatio = GameData.getRegion("snow").getRegionWidth()/GameData.getRegion("snow").getRegionHeight();
		snowPileImage.setHeight((int)(snowPileImage.getWidth()/snowRatio));
		snowPileImage.setName(SNOW_PILE_Z);
		
		for(int i=0; i<SNOW_FLAKES_NUMBER; i++){
			stage.addActor(new Snowflake(GameData.getRegion("snowflake"), 1f, true, stage));
			stage.addActor(new Snowflake(GameData.getRegion("snowflake"), 0.5f, false, stage));
		}
		stage.addActor(snowPileImage);

	}
	
	protected void setSnowPileY(float y){
		snowPileImage.setY(y);
	}

	@Override public void show() {}
    @Override public void hide() {}
	@Override public void pause() {}
	@Override public void resume() {}

	

}

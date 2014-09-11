package by.aleks.christmasboard.gui;

import java.util.Random;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Snowflake extends Image{
	private static final float SNOWFLAKE_SIZE = 25*GameData.scale;
	private static final float SNOWFLAKE_SPEED = 1;
	
	/**@param direction - True - down, False - up*/
	public Snowflake(TextureRegion snowflakeRegion, float transp, boolean direction, Stage stage){
		super(snowflakeRegion);
		setColor(1, 1, 1, transp);
		this.direction = direction;
		rand = new Random();

		//Actual position
		posX = rand.nextInt(Gdx.graphics.getWidth());
		posY = rand.nextInt(Gdx.graphics.getHeight());
		setPosition(posX, posY);
		setWidth(SNOWFLAKE_SIZE);
		setHeight(SNOWFLAKE_SIZE);
		newMoveAction();
		stage.addActor(this);

	}		
	


	private void newMoveAction(){
		move = new Action(){

			@Override
			public boolean act(float delta) {
				
				//Checks if the snowflake is out of the screen
				if(getY()<0) {
					setY(Gdx.graphics.getHeight());
					setX(rand.nextInt(Gdx.graphics.getWidth()));
				}
				else if(getY()>Gdx.graphics.getHeight()){
					setY(0);
					setX(rand.nextInt(Gdx.graphics.getWidth()));
				}
				//Here I hide snowflakes behind the board.
				if(getY() + SNOWFLAKE_SIZE > GameScreen.BOARD_UPPER_EDGE_Y){
					setName("5");
				} else{
					setName("0");
				}
				
				if(direction)setY(getY()-SNOWFLAKE_SPEED*GameData.scale);
				else setY(getY()+SNOWFLAKE_SPEED*GameData.scale);

				return false;
			}			
		};
		
		this.addAction(move);
	}



	private float posX, posY;
	private Random rand;
	private Action move;
	private boolean direction;

}

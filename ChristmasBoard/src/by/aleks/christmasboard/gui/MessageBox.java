package by.aleks.christmasboard.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.ShaderLabel;
import by.aleks.christmasboard.data.Sounds;
import by.aleks.christmasboard.screens.GameScreen;

public class MessageBox extends Group {
	
	private static final BitmapFont FONT1 = Fonts.getMainFont();
	private static final float FONT1_SIZE = 70;
	private static final BitmapFont FONT2 = Fonts.getMainFont();
	private static final float FONT2_SIZE = 105;
	private static final BitmapFont FONT3 = Fonts.getMainFont();
	private static final float FONT3_SIZE = 125;
	
	private static final float GRADIENT_HEIGHT = 522*GameData.scale;
	private static final String Z_INDEX = "15";
	
	public MessageBox(){
		setName(Z_INDEX);
		setSize(Gdx.graphics.getWidth(), GRADIENT_HEIGHT);
		setTouchable(Touchable.disabled);
		setColor(1, 1, 1, 0);
		addGradient();
	}
	
	
	private void addGradient(){

		Image gradient = new Image(GameData.getRegion("gradient"));
		gradient.setWidth(Gdx.graphics.getWidth());
		gradient.setHeight(GRADIENT_HEIGHT);
		gradient.setPosition(0, 0);
		this.addActor(gradient);
	}
	
	private void addOneStringText(String text){
		labelGroup = new Group();
		ShaderLabel label = new ShaderLabel(text, Color.YELLOW, FONT3_SIZE, FONT3);
		label.setPosition((Gdx.graphics.getWidth()-label.getWidth())/2, GameScreen.SACK_HEIGHT/2-label.getHeight()/2);
		labelGroup.addActor(label);
		this.addActor(labelGroup);
	}
	
	private void addTwoStringText(String text1, String text2){
		labelGroup = new Group();
		
		ShaderLabel label1 = new ShaderLabel(text1, Color.YELLOW, FONT2_SIZE, FONT2);
		ShaderLabel label2 = new ShaderLabel(text2, Color.YELLOW, FONT1_SIZE, FONT1);
		
		label1.setPosition((Gdx.graphics.getWidth()-label1.getWidth())/2, GameScreen.SACK_HEIGHT/2-(label1.getHeight()+label2.getHeight())/6);
		labelGroup.addActor(label1);		
		label2.setPosition((Gdx.graphics.getWidth()-label2.getWidth())/2, GameScreen.SACK_HEIGHT/2-(label1.getHeight()+label2.getHeight())/2.5f);
		labelGroup.addActor(label2);	
		this.addActor(labelGroup);
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		//Appearing animation
		if(appearing && !appeared){
			setColor(1, 1, 1, transp+=0.1);
			if(transp >3) appeared = true;
		}

		else if(appearing && appeared){
			setColor(1, 1, 1, transp-=0.02);
			if(transp<=0){
				labelGroup.remove();
				appearing = false;
				appeared = false;
			}
		}

		super.draw(batch, parentAlpha);
	}
	
	public void show(String text){
		addOneStringText(text);
		appearing = true;
		Sounds.playNewLevel();
	}
	
	public void show(String text1, String text2){
		addTwoStringText(text1, text2);
		appearing = true;
		Sounds.playAchievement();
	}
	
	private boolean appearing, appeared;
	private float transp = 0;
	private Group labelGroup;

}

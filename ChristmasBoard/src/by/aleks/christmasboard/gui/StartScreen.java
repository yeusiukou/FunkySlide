package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.data.Fonts;
import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.data.GameState;
import by.aleks.christmasboard.data.ShaderLabel;
import by.aleks.christmasboard.elements.BoardLogic;
import by.aleks.christmasboard.elements.CellLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class StartScreen extends Group{
	
	private static final float GRADIENT_HEIGHT = 522*GameData.scale;
	private static final String Z_INDEX = "15";
	private static final float HEADER_Y = 1020*GameData.scale;
	private static final float HAND_WIDTH = 77*GameData.scale;
	private static final float HAND_HEIGHT = 81*GameData.scale;
	private static final float HAND_SPEED = 1*GameData.scale;
	
	private static final float ARROW1_HEIGHT = 27*GameData.scale;
	private static final float ARROW1_WIDTH = 169*GameData.scale;
	
	private static final float POINTER_WIDTH = 205*GameData.scale;
	private static final float POINTER_HEIGHT = 175*GameData.scale;
	
	private static final float ARROW2_WIDTH = 70*GameData.scale;
	private static final float ARROW2_HEIGHT = 92*GameData.scale;
	private static final float ARROW2_X = 450*GameData.scale;
	
	private static final float ICONS_MARGIN = 33*GameData.scale;
	private static final float ICONS_SPACE = 10*GameData.scale;
	private static final float ICONS_SIZE = 58*GameData.scale;
	
	private static final float START1_FONT_SIZE = 112;
	private static final float START2_FONT_SIZE = 35;
	private static final float START3_FONT_SIZE = 50;

	
	
	public StartScreen(float boardX, float boardY){
		this.boardX = boardX;
		this.boardY = boardY;

		setName(Z_INDEX);
		setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		setTouchable(Touchable.disabled);
		addBackground();
		addHeader();
		addPointer1();
		addPointer2();
		addIcons();
		
		GameState.pause = true;
		addAction(new Action(){

			@Override
			public boolean act(float delta) {
				//Fading out if the screen is touched
				if(Gdx.input.isTouched()||Gdx.input.isKeyPressed(Keys.BACK))
					touched = true;
				if(touched){
					StartScreen.this.setColor(1, 1, 1, transp-=0.01f);
					if(transp<=0)
						StartScreen.this.remove();
					GameState.pause = false;
				}
				return false;
			}
			
		});

	}
	
	private void addBackground(){
		Image black = new Image(GameData.getRegion("dot"));		
		black.setColor(new Color(0, 0, 0, 0.5f));
		black.setWidth(Gdx.graphics.getWidth());
		black.setHeight(Gdx.graphics.getHeight());
		black.setPosition(0, 0);
		this.addActor(black);
		
		//Bottom gradient
		Image gradient1 = new Image(GameData.getRegion("gradient"));
		gradient1.setWidth(Gdx.graphics.getWidth());
		gradient1.setHeight(GRADIENT_HEIGHT);
		gradient1.setPosition(0, 0);
		this.addActor(gradient1);
		
		//Top gradient
		TextureRegion reg = new TextureRegion(GameData.getRegion("gradient"));
		reg.flip(false, true);
		Image gradient2 = new Image(reg);
		gradient2.setWidth(Gdx.graphics.getWidth());
		gradient2.setHeight(GRADIENT_HEIGHT);
		gradient2.setPosition(0, Gdx.graphics.getHeight()-GRADIENT_HEIGHT);
		this.addActor(gradient2);
	}
	
	private void addHeader(){
		String text = "Get Ready";
		ShaderLabel header = new ShaderLabel(text, Color.WHITE, START1_FONT_SIZE, Fonts.getSagoescFont());
		header.setPosition((Gdx.graphics.getWidth()-header.getWidth())/2, HEADER_Y);
		this.addActor(header);
	}
	
	private void addPointer1(){
		pointer = new Group();
		pointer.setSize(POINTER_WIDTH, POINTER_HEIGHT);
		
		//MAGIC!!! I don't know why, but it works only if I divide cell side by 2. There might be something wrong with positioning.
		if(BoardLogic.startEmptyCell.getX() > 0){
			pointer.setPosition(boardX+(BoardLogic.startEmptyCell.getX()-1)*CellLogic.SIDE/2+CellLogic.SIDE/4,
					boardY+(BoardLogic.startEmptyCell.getY()-1)*CellLogic.SIDE/2-CellLogic.SIDE/4);
		}else pointer.setPosition(boardX+BoardLogic.startEmptyCell.getX()*CellLogic.SIDE/2+CellLogic.SIDE/4,
				boardY+(BoardLogic.startEmptyCell.getY()-1)*CellLogic.SIDE/2-CellLogic.SIDE/4);

		hand = new Image(GameData.getRegion("hand"));
		hand.setPosition(pointer.getX()-HAND_WIDTH/2, pointer.getY());
		hand.setSize(HAND_WIDTH, HAND_HEIGHT);
		hand.addAction(new Action(){

			@Override
			public boolean act(float delta) {
				
				if(BoardLogic.startEmptyCell.getX()>0){
					if(hand.getX()<pointer.getX()+CellLogic.SIDE-HAND_WIDTH/2)
						hand.setX(hand.getX()+HAND_SPEED);
					else hand.setX(pointer.getX()-HAND_WIDTH/2);
				}else{
					if(hand.getX()>pointer.getX()-HAND_WIDTH/2)
						hand.setX(hand.getX()-HAND_SPEED);
					else hand.setX(pointer.getX()+CellLogic.SIDE-HAND_WIDTH/2);
				}


				return false;
			}
			
		});
		pointer.addActor(hand);
		
		TextureRegion arrowRegion = GameData.getRegion("arrow1");
		//In the case an empty cell is the first left one I want to point the direction from right to left,
		//while in the other cases - from left to right.
		if(BoardLogic.startEmptyCell.getX()==0&&!arrowRegion.isFlipX())
			arrowRegion.flip(true, false);
		else if (BoardLogic.startEmptyCell.getX() > 0 && arrowRegion.isFlipX()){
			arrowRegion.flip(true, false);
		}
		Image arrow = new Image(arrowRegion);
		arrow.setSize(ARROW1_WIDTH, ARROW1_HEIGHT);
		arrow.setPosition(pointer.getX(), pointer.getY()+HAND_HEIGHT);
		if(BoardLogic.startEmptyCell.getX()==0)
			arrow.setX(arrow.getX()-HAND_HEIGHT/2);
		pointer.addActor(arrow);
		

		Label slideLabel = new ShaderLabel("SLIDE!", Color.WHITE, START2_FONT_SIZE, Fonts.getSagoescFont());
		slideLabel.setPosition(pointer.getX(), arrow.getY()+ARROW1_HEIGHT);
		pointer.addActor(slideLabel);
		
		this.addActor(pointer);
	}
	
	private void addPointer2(){
		Image arrow2 = new Image(GameData.getRegion("arrow2"));
		arrow2.setSize(ARROW2_WIDTH, ARROW2_HEIGHT);
		arrow2.setPosition(ARROW2_X, boardY-ARROW2_HEIGHT+17*GameData.scale);
		addActor(arrow2);
		
		Label throwLabel = new ShaderLabel("THROW!", Color.WHITE, START2_FONT_SIZE, Fonts.getSagoescFont());
		throwLabel.setPosition(ARROW2_X+ARROW2_WIDTH, arrow2.getY());
		addActor(throwLabel);
	}
	
	private void addIcons(){
		BitmapFont iconFont = Fonts.getSagoescFont();

		//DANGEROUS
		Label dang = new ShaderLabel("DANGEROUS:", Color.WHITE, START3_FONT_SIZE, iconFont);
		dang.setPosition(ICONS_MARGIN, ICONS_MARGIN/2);
		addActor(dang);
		
		Image bomb = new Image(GameData.getRegion("dangerous1"));
		bomb.setPosition(dang.getX()+dang.getWidth()+ICONS_SPACE, ICONS_MARGIN);
		bomb.setSize(ICONS_SIZE, ICONS_SIZE);
		addActor(bomb);
		
		//BAD
		Label bad = new ShaderLabel("BAD:", Color.WHITE, START3_FONT_SIZE, iconFont);
		bad.setPosition(ICONS_MARGIN, dang.getY()+START3_FONT_SIZE*GameData.scale+ICONS_SPACE);
		addActor(bad);
		
		Image[] badIcons = new Image[2];
		for(int i=0;i<2;i++){
			badIcons[i] = new Image(GameData.getRegion("bad"+(i+1)));
			badIcons[i].setPosition(ICONS_MARGIN+bad.getWidth()+i*(ICONS_SIZE+ICONS_SPACE)+ICONS_SPACE*4,
					dang.getY()+START3_FONT_SIZE*GameData.scale+ICONS_SPACE*2);
			badIcons[i].setSize(ICONS_SIZE, ICONS_SIZE);
			addActor(badIcons[i]);
		}
		
		//GOOD
		Label good = new ShaderLabel("GOOD:", Color.WHITE, START3_FONT_SIZE, iconFont);
		good.setPosition(ICONS_MARGIN, dang.getY()+(START3_FONT_SIZE*GameData.scale+ICONS_SPACE)*2);
		addActor(good);
		
		Image[] goodIcons = new Image[3];
		for(int i=0;i<3;i++){
			goodIcons[i] = new Image(GameData.getRegion("good"+(i+1)));
			goodIcons[i].setPosition(ICONS_MARGIN+good.getWidth()+i*(ICONS_SIZE+ICONS_SPACE)+ICONS_SPACE*2,
					dang.getY()+(START3_FONT_SIZE*GameData.scale+ICONS_SPACE)*2+ICONS_SPACE*2);
			goodIcons[i].setSize(ICONS_SIZE, ICONS_SIZE);
			addActor(goodIcons[i]);
		}
	}
	
	private Image hand;
	private Group pointer;
	private float boardX, boardY;
	private float transp = 1;
	private boolean touched;

}

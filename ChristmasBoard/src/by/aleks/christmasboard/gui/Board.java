package by.aleks.christmasboard.gui;

import by.aleks.christmasboard.data.GameData;
import by.aleks.christmasboard.elements.BoardLogic;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Board extends Group{
	
	public static final float BOARD_SIDE = 732*GameData.scale;
	private static final String BOARD_Z = "4";
	private static final String FRAME_Z = "10";
	
	
	public Board(float posX, float posY, Stage stage){

		this.setPosition(posX, posY);
		this.setSize(BOARD_SIDE, BOARD_SIDE);
		this.setName(BOARD_Z);
		
		Image boardBack = new Image(GameData.getRegion("board"));
		boardBack.setSize(BOARD_SIDE, BOARD_SIDE);
		boardBack.setTouchable(Touchable.disabled);
		this.addActor(boardBack);
		
		Image boardFrame = new Image(GameData.getRegion("board_frame"));
		boardFrame.setSize(BOARD_SIDE, BOARD_SIDE);
		boardFrame.setPosition(getX(), getY());
		boardFrame.setName(FRAME_Z);
		boardFrame.setTouchable(Touchable.disabled);
		stage.addActor(boardFrame);
		
		BoardLogic logicBoard = new BoardLogic();
		logicBoard.setPosition((getWidth()-logicBoard.getWidth())/4, (getHeight()-logicBoard.getHeight())/4);
		this.addActor(logicBoard);
		logicBoard.loadElements();
		
	}

}

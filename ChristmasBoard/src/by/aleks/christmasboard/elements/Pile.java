package by.aleks.christmasboard.elements;

import by.aleks.christmasboard.data.GameData;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Pile extends UntouchableElement{
	
	private static final float PILE_WIDTH = 141*GameData.scale;
	private static final float PILE_HEIGHT = 141*GameData.scale;

	public Pile(BoardLogic boardL) {
		super(boardL);
		this.setSize(PILE_WIDTH, PILE_HEIGHT);
		TextureRegionDrawable style = new TextureRegionDrawable(GameData.getRegion("pile"));
		this.setDrawable(style);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		//If a pile is on the door place it fades out:
		if(getActualCell()!=null && getActualCell().getX()==BoardLogic.DOOR_NUM_X && getActualCell().getY()==BoardLogic.DOOR_NUM_Y){
			Pile.this.setColor(1, 1, 1, transp-=0.01f);
			if(transp<=0)
				Pile.this.delete();
		}
		super.draw(batch, parentAlpha);
	}

	
	private float transp = 1;
	

}

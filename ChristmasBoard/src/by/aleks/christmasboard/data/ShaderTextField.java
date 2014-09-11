package by.aleks.christmasboard.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ShaderTextField extends TextField {

	private float smoothing;
	private DistanceFieldShader shader;

	public ShaderTextField(String text, Color color, float size, BitmapFont font) {

		super(text, new ShaderTextFieldStyle(font, color, size));

		this.shader = new DistanceFieldShader();
		
		smoothing = getStyle().font.getBounds(text).width;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		if (getStyle().font.getScaleX() != 0) {
			batch.setShader(shader);
			shader.setTransparency(parentAlpha, getColor().a);
			shader.setSmoothing(smoothing / this.getScaleX());
		}

		super.draw(batch, parentAlpha);
		batch.setShader(null);
	}

	
	private static class ShaderTextFieldStyle extends TextFieldStyle {
		
		private final Texture.TextureFilter minFilter = Texture.TextureFilter.MipMapLinearNearest;
		private final Texture.TextureFilter magFilter = Texture.TextureFilter.Linear;
		private final float FONTS_SIZE = 64;
		
		public ShaderTextFieldStyle(BitmapFont font, Color color, float size){
			this.font = font;
			this.fontColor = color;
			font.setScale(size*GameData.scale/FONTS_SIZE);
			font.getRegion().getTexture().setFilter(minFilter, magFilter);
		}
	}

}

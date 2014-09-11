package by.aleks.christmasboard.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

public class BitmapFontShader extends BitmapFont {

	private final Texture.TextureFilter minFilter = Texture.TextureFilter.MipMapLinearNearest;
	private final Texture.TextureFilter magFilter = Texture.TextureFilter.Linear;

	private DistanceFieldShader distanceFieldShader;
	private float smoothing;

	public BitmapFontShader(FileHandle fontFile, TextureRegion textureRegion, Boolean flip, float shaderSmooth) {
		super(fontFile, textureRegion, flip);
		this.smoothing = shaderSmooth;
		distanceFieldShader = new DistanceFieldShader();
		getRegion().getTexture().setFilter(minFilter, magFilter);
		//getRegion().getTexture().setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); // linear filtering in nearest mipmap image 
	}

	@Override
	public TextBounds draw(Batch batch, java.lang.CharSequence str, float x, float y) {
		smoothing = this.getBounds(str).width;

		if (this.getScaleX() != 0) {
			batch.setShader(distanceFieldShader);
			//distanceFieldShader.setSmoothing(smoothing / this.getScaleX());
		}

		TextBounds textBounds = super.draw(batch, str, x, y + this.getScaleY() * getBaselineShift(this));
		
		batch.setShader(null);
		return textBounds;
	}



	private float getBaselineShift(BitmapFont font) {
		if (font == this) {
			// We set -8 paddingAdvanceY in Hiero to compensate for 4 padding on
			// each side.
			// Unfortunately the padding affects the baseline inside the font
			// description.
			return -8;
		} else {
			return 0;
		}
	}
	

	private class DistanceFieldShader extends ShaderProgram {                                                            
	    public DistanceFieldShader () {                                                                                         
	        // The vert and frag files are copied from http://git.io/yK63lQ (vert) and http://git.io/hAcw9Q (the frag)          
	        super(Gdx.files.internal("data\\shaders\\distancefield.vert"), Gdx.files.internal("data\\shaders\\distancefield.frag"));
	        if (!isCompiled()) {                                                                                                
	            throw new RuntimeException("Shader compilation failed:\n" + getLog());                                          
	        }                                                                                                                   
	    }                                                                                                                

	    /** @param smoothing a value between 0 and 1 */                                                                         
	    public void setSmoothing (float smoothing) {     
	        float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);

	        setUniformf("u_lower", 0.5f - delta); 
	        setUniformf("u_upper", 0.5f + delta);  

	    }      
	    
	}
}

package by.aleks.christmasboard.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

public class DistanceFieldShader extends ShaderProgram {
	public DistanceFieldShader() {
		// The vert and frag files are copied from http://git.io/yK63lQ (vert) and http://git.io/hAcw9Q (the frag)
		super(Gdx.files.internal("data/shaders/distancefield.vert"), Gdx.files.internal("data/shaders/distancefield.frag"));
		if (!isCompiled()) {
			throw new RuntimeException("Shader compilation failed:\n" + getLog());
		}
	}

	/**
	 * @param smoothing a value between 0 and 1
	 */
	public void setSmoothing(float smoothing) {
		
		float delta = 0.15f * MathUtils.clamp(smoothing, 0, 1);

		setUniformf("u_lower", 0.5f - delta);
		setUniformf("u_upper", 0.5f + delta);

	}
	
	public void setTransparency(float parentAlpha, float ownAlpha){
		setUniformf("u_transp", parentAlpha*ownAlpha);
	}

}

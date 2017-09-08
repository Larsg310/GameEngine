package modules.sky;

import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;

public class AtmosphereShader extends Shader
{
    private static AtmosphereShader instance = new AtmosphereShader();
    
    protected AtmosphereShader()
    {
        super();
        addVertexShader(ResourceLoader.loadShader("shaders/atmosphere/vertexShader.glsl"));
        addFragmentShader(ResourceLoader.loadShader("shaders/atmosphere/fragmentShader.glsl"));
        compileShader();
        
        addUniform("mvpMatrix");
        addUniform("worldMatrix");
    }
    
    @Override
    public void updateUniforms(GameObject object)
    {
        setUniform("mvpMatrix", object.getWorldTransform().getMVPMatrix());
        setUniform("worldMatrix", object.getWorldTransform().getWorldMatrix());
    }
    
    public static AtmosphereShader getInstance()
    {
        return instance;
    }
}

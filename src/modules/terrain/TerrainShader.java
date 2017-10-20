package modules.terrain;

import core.kernel.Camera;
import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;
import org.lwjgl.opengl.GL13;

public class TerrainShader extends Shader
{
    private static TerrainShader instance = null;
    
    public static TerrainShader getInstance()
    {
        if (instance == null) instance = new TerrainShader();
        return instance;
    }
    
    public TerrainShader()
    {
        super();
        
        addVertexShader(ResourceLoader.loadShader("shaders/terrain/vertexShader.glsl"));
        addTessellationControlShader(ResourceLoader.loadShader("shaders/terrain/tessControlShader.glsl"));
        addTessellationEvaluationShader(ResourceLoader.loadShader("shaders/terrain/tessEvalShader.glsl"));
        addGeometryShader(ResourceLoader.loadShader("shaders/terrain/geometryShader.glsl"));
        addFragmentShader(ResourceLoader.loadShader("shaders/terrain/fragmentShader.glsl"));
        
        compileShader();
        
        addUniform("localMatrix");
        addUniform("worldMatrix");
        addUniform("m_ViewProjection");
        
        addUniform("index");
        addUniform("gap");
        addUniform("lod");
        addUniform("scaleY");
        addUniform("location");
        addUniform("cameraPosition");
        
        addUniform("tessellationFactor");
        addUniform("tessellationSlope");
        addUniform("tessellationShift");
        
        addUniform("heightmap");
        addUniform("normalmap");
        
        for (int i = 0; i < 8; i++) addUniform("lod_morph_area[" + i + "]");
    }
    
    @Override
    public void updateUniforms(GameObject object)
    {
        TerrainNode terrainNode = (TerrainNode) object;
        
        setUniformf("scaleY", terrainNode.getConfig().getScaleY());
        setUniformi("lod", terrainNode.getLod());
        setUniform("index", terrainNode.getIndex());
        setUniform("location", terrainNode.getLocation());
        setUniformf("gap", terrainNode.getGap());
        
        for (int i = 0; i < 8; i++) setUniformi("lod_morph_area[" + i + "]", terrainNode.getConfig().getLodMorphingArea(i));
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        terrainNode.getConfig().getHeightMap().bind();
        setUniformi("heightmap", 0);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        terrainNode.getConfig().getNormalMap().bind();
        setUniformi("normalmap", 1);
        
        setUniformi("tessellationFactor", terrainNode.getConfig().getTessellationFactor());
        setUniformf("tessellationSlope", terrainNode.getConfig().getTessellationSlope());
        setUniformf("tessellationShift", terrainNode.getConfig().getTessellationShift());
        
        setUniform("cameraPosition", Camera.getCurrentCamera().getPosition());
        setUniform("m_ViewProjection", Camera.getCurrentCamera().getViewProjectionMatrix());
        setUniform("localMatrix", object.getLocalTransform().getWorldMatrix());
        setUniform("worldMatrix", object.getWorldTransform().getWorldMatrix());
    }
}

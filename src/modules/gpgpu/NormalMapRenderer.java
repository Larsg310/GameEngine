package modules.gpgpu;

import core.texturing.Texture2D;
import org.lwjgl.opengl.*;

public class NormalMapRenderer
{
    private float strength;
    private Texture2D normalmap;
    private NormalMapShader shader;
    private int N;
    
    public NormalMapRenderer(int N)
    {
        this.N = N;
        this.shader = NormalMapShader.getInstance();
        normalmap = new Texture2D();
        normalmap.generate();
        normalmap.bind();
        normalmap.bilinearFilter();
        GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, (int) (Math.log(N) / Math.log(2)), GL30.GL_RGBA32F, N, N);
    }
    
    public void render(Texture2D heightmap)
    {
        shader.bind();
        shader.updateUniforms(heightmap, N, strength);
        GL42.glBindImageTexture(0, normalmap.getId(), 0, false, 0, GL15.GL_WRITE_ONLY, GL30.GL_RGBA32F);
        GL43.glDispatchCompute(N / 16, N / 16, 1);
        GL11.glFinish();
        normalmap.bind();
        normalmap.bilinearFilter();
    }
    
    public float getStrength()
    {
        return strength;
    }
    
    public void setStrength(float strength)
    {
        this.strength = strength;
    }
    
    public Texture2D getNormalmap()
    {
        return normalmap;
    }
    
    public void setNormalmap(Texture2D normalmap)
    {
        this.normalmap = normalmap;
    }
}

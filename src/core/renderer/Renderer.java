package core.renderer;

import core.buffers.VBO;
import core.scene.Component;

public class Renderer extends Component
{
    private VBO vbo;
    private RenderInfo info;
    
    public void render()
    {
        info.getConfig().enable();
        info.getShader().bind();
        info.getShader().updateUniforms(getParent());
        vbo.draw();
        info.getConfig().disable();
    }
    
    public VBO getVbo()
    {
        return vbo;
    }
    
    public void setVbo(VBO vbo)
    {
        this.vbo = vbo;
    }
    
    public RenderInfo getInfo()
    {
        return info;
    }
    
    public void setInfo(RenderInfo info)
    {
        this.info = info;
    }
}

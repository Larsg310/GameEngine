package core.configs;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Default implements RenderConfig
{
    
    public void enable()
    {
    
    }
    
    public void disable()
    {
    
    }
    
    public static void init()
    {
        GL11.glFrontFace(GL11.GL_CW);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL30.GL_FRAMEBUFFER_SRGB);
    }
    
    public static void clearScreen()
    {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClearDepth(1.0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }
}

package core.texturing;

import core.utils.ImageLoader;
import org.lwjgl.opengl.GL11;

public class Texture2D
{
    private int id;
    private int width;
    private int height;
    
    public Texture2D(String file)
    {
        id = ImageLoader.loadImage(file);
    }
    
    public void bind()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }
    
    public void generate()
    {
        id = GL11.glGenTextures();
    }
    
    public void delete()
    {
        GL11.glDeleteTextures(id);
    }
    
    public void unbind()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
}

package core.texturing;

import core.utils.ImageLoader;
import org.lwjgl.opengl.GL11;

public class Texture2D
{
    private int id;
    private int width;
    private int height;
    
    public Texture2D() {}
    
    public Texture2D(String file)
    {
        int[] data = ImageLoader.loadImage(file);
        id = data[0];
        width = data[1];
        height = data[2];
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
    
    public void noFilter()
    {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
    }
    
    public void bilinearFilter()
    {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
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

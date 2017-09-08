package core.kernel;

import core.configs.Default;
import modules.sky.Skydome;
import modules.terrain.Terrain;

public class RenderingEngine
{
    private Window window;

    private Skydome skydome;
    private Terrain terrain;
    
    public RenderingEngine()
    {
        window = Window.getInstance();
        skydome = new Skydome();
        terrain = new Terrain();
    }

    public void init()
    {
        window.init();
        terrain.init("./res/settings/terrain_settings.txt");
    }

    public void render()
    {
        Camera.getCurrentCamera().update();
    
        Default.clearScreen();
        
        skydome.render();
        
        terrain.updateQuadTree();
        terrain.render();
        
        window.render();
    }

    public void update() {}

    public void shutdown() {}
}

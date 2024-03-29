package core.kernel;

import core.configs.Default;
import core.utils.Constants;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

public class CoreEngine
{
    
    private static int FPS;
    private static float frameRate = 500;
    private static float frameTime = 1.0F / frameRate;
    private boolean isRunning;
    private RenderingEngine renderingEngine;
    
    public void createWindow(int width, int height)
    {
        GLFW.glfwInit();
        GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
        Window.getInstance().create(width, height);
        renderingEngine = new RenderingEngine();
        getDeviceProperties();
    }
    
    public void init()
    {
        Default.init();
        renderingEngine.init();
    }
    
    public void start()
    {
        if (isRunning) return;
        run();
    }
    
    public void run()
    {
        this.isRunning = true;
        
        int frames = 0;
        long frameCounter = 0;
        
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;
        
        // Rendering Loop
        while (isRunning)
        {
            boolean render = false;
            
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;
            
            unprocessedTime += passedTime / (double) Constants.NANOSECOND;
            frameCounter += passedTime;
            
            while (unprocessedTime > frameTime)
            {
                
                render = true;
                unprocessedTime -= frameTime;
                
                if (Window.getInstance().isCloseRequested()) stop();
                
                update();
                
                if (frameCounter >= Constants.NANOSECOND)
                {
                    setFPS(frames);
                    System.out.println("FPS: "+FPS);
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if (render)
            {
                render();
                frames++;
            }
            else
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        cleanUp();
    }
    
    private void stop()
    {
        if (!isRunning) return;
        isRunning = false;
    }
    
    private void render()
    {
        renderingEngine.render();
    }
    
    private void update()
    {
        Input.getInstance().update();
        Camera.getCurrentCamera().update();
        renderingEngine.update();
    }
    
    private void cleanUp()
    {
        Window.getInstance().dispose();
        GLFW.glfwTerminate();
    }
    
    private void getDeviceProperties()
    {
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION) + " bytes");
        System.out.println("Max Geometry Uniform Blocks: " + GL31.GL_MAX_GEOMETRY_UNIFORM_BLOCKS + " bytes");
        System.out.println("Max Geometry Shader Invocations: " + GL40.GL_MAX_GEOMETRY_SHADER_INVOCATIONS + " bytes");
        System.out.println("Max Uniform Buffer Bindings: " + GL31.GL_MAX_UNIFORM_BUFFER_BINDINGS + " bytes");
        System.out.println("Max Uniform Block Size: " + GL31.GL_MAX_UNIFORM_BLOCK_SIZE + " bytes");
        System.out.println("Max SSBO Block Size: " + GL43.GL_MAX_SHADER_STORAGE_BLOCK_SIZE + " bytes");
    }
    
    public static float getFrameTime()
    {
        return frameTime;
    }
    
    public static int getFPS()
    {
        return FPS;
    }
    
    public static void setFPS(int fps)
    {
        CoreEngine.FPS = fps;
    }
}

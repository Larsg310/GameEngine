package core.kernel;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Window
{
    private static Window instance = null;

    private long window;
    private int width;
    private int height;

    public static Window getInstance()
    {
        if (instance == null)instance = new Window();
        return instance;
    }

    public void init() {}

    public void create(int width, int height)
    {
        setWidth(width);
        setHeight(height);

        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        window = GLFW.glfwCreateWindow(width, height, "GAME ENGINE", 0, 0);

        if (window == 0) throw new RuntimeException("Failed to create window");

//        ByteBuffer bufferedImage = ImageLoader.loadImageToByteBuffer("./res/logo/oreon_lwjgl_icon32.png");
//        GLFWImage image = GLFWImage.malloc();
//        image.set(32, 32, bufferedImage);
//        GLFWImage.Buffer images = GLFWImage.malloc(1);
//        images.put(0, image);
//        GLFW.glfwSetWindowIcon(window, images);

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLFW.glfwShowWindow(window);
    }

    public void render()
    {
        GLFW.glfwSwapBuffers(window);
    }

    public void dispose()
    {
        GLFW.glfwDestroyWindow(window);
    }

    public boolean isCloseRequested()
    {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void setWindowSize(int x, int y)
    {
        GLFW.glfwSetWindowSize(window, x, y);
        setHeight(y);
        setWidth(x);
        Camera.getCurrentCamera().setProjection(70, x, y);
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public long getWindow()
    {
        return window;
    }

    public void setWindow(long window)
    {
        this.window = window;
    }
}

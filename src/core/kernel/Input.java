package core.kernel;

import core.math.Vec2f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class Input
{
    
    private static Input instance = null;
    
    private ArrayList<Integer> pushedKeys = new ArrayList<>();
    private ArrayList<Integer> keysHolding = new ArrayList<>();
    private ArrayList<Integer> releasedKeys = new ArrayList<>();
    
    private ArrayList<Integer> pushedButtons = new ArrayList<>();
    private ArrayList<Integer> buttonsHolding = new ArrayList<>();
    private ArrayList<Integer> releasedButtons = new ArrayList<>();
    
    private Vec2f cursorPosition;
    private Vec2f lockedCursorPosition;
    private float scrollOffset;
    
    private boolean pause = false;
    
    public static Input getInstance()
    {
        if (instance == null)
        {
            instance = new Input();
        }
        return instance;
    }
    
    protected Input()
    {
        cursorPosition = new Vec2f();
        
        GLFW.glfwSetFramebufferSizeCallback(Window.getInstance().getWindow(), (window, width, height) -> Window.getInstance().setWindowSize(width, height));
        
        GLFW.glfwSetKeyCallback(Window.getInstance().getWindow(), (window, key, scancode, action, mods) ->
        {
            if (action == GLFW.GLFW_PRESS)
            {
                if (!pushedKeys.contains(key))
                {
                    pushedKeys.add(key);
                    keysHolding.add(key);
                }
            }
            
            if (action == GLFW.GLFW_RELEASE)
            {
                keysHolding.remove(new Integer(key));
                releasedKeys.add(key);
            }
            
        });
        
        GLFW.glfwSetMouseButtonCallback(Window.getInstance().getWindow(), (window, button, action, mods) ->
        {
            if (button == 2 && action == GLFW.GLFW_PRESS)
            {
                lockedCursorPosition = new Vec2f(cursorPosition);
                GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
            }
            
            if (button == 2 && action == GLFW.GLFW_RELEASE)
            {
                GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
            }
            
            if (action == GLFW.GLFW_PRESS)
            {
                if (!pushedButtons.contains(button))
                {
                    pushedButtons.add(button);
                    buttonsHolding.add(button);
                }
            }
            
            if (action == GLFW.GLFW_RELEASE)
            {
                releasedButtons.add(button);
                buttonsHolding.remove(new Integer(button));
            }
            
        });
        
        GLFW.glfwSetCursorPosCallback(Window.getInstance().getWindow(), (windows, xPos, yPos) ->
        {
            cursorPosition.setX((float) xPos);
            cursorPosition.setY((float) yPos);
            
        });
        
        GLFW.glfwSetScrollCallback(Window.getInstance().getWindow(), (window, xOffset, yOffset) -> setScrollOffset((float) yOffset));
    }
    
    public void update()
    {
        setScrollOffset(0);
        pushedKeys.clear();
        releasedKeys.clear();
        pushedButtons.clear();
        releasedButtons.clear();
        
        GLFW.glfwPollEvents();
    }
    
    public boolean isKeyPushed(int key)
    {
        return pushedKeys.contains(key);
    }
    
    public boolean isKeyReleased(int key)
    {
        return releasedKeys.contains(key);
    }
    
    public boolean isKeyHold(int key)
    {
        return keysHolding.contains(key);
    }
    
    public boolean isButtonPushed(int key)
    {
        return pushedButtons.contains(key);
    }
    
    public boolean isButtonreleased(int key)
    {
        return releasedButtons.contains(key);
    }
    
    public boolean isButtonHolding(int key)
    {
        return buttonsHolding.contains(key);
    }
    
    public boolean isPause()
    {
        return pause;
    }
    
    public void setPause(boolean pause)
    {
        this.pause = pause;
    }
    
    public Vec2f getCursorPosition()
    {
        return cursorPosition;
    }
    
    public void setCursorPosition(Vec2f cursorPosition)
    {
        this.cursorPosition = cursorPosition;
        
        GLFW.glfwSetCursorPos(Window.getInstance().getWindow(), cursorPosition.getX(), cursorPosition.getY());
    }
    
    public Vec2f getLockedCursorPosition()
    {
        return lockedCursorPosition;
    }
    
    public void setLockedCursorPosition(Vec2f lockedCursorPosition)
    {
        this.lockedCursorPosition = lockedCursorPosition;
    }
    
    public ArrayList<Integer> getPushedKeys()
    {
        return pushedKeys;
    }
    
    public ArrayList<Integer> getButtonsHolding()
    {
        return buttonsHolding;
    }
    
    public float getScrollOffset()
    {
        return scrollOffset;
    }
    
    public void setScrollOffset(float scrollOffset)
    {
        this.scrollOffset = scrollOffset;
    }
    
    public ArrayList<Integer> getKeysHolding()
    {
        return keysHolding;
    }
    
    public ArrayList<Integer> getPushedButtons()
    {
        return pushedButtons;
    }
}

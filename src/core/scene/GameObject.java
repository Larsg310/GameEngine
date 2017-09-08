package core.scene;

import java.util.HashMap;
import java.util.Map;

public class GameObject extends Node
{
    private Map<String, Component> components;
    
    public GameObject()
    {
        components = new HashMap<>();
    }
    
    public void input()
    {
        components.forEach((s, c) -> c.input());
        super.input();
    }
    
    public void update()
    {
        components.forEach((s, c) -> c.update());
        super.update();
    }
    
    public void render()
    {
        components.forEach((s, c) -> c.render());
        super.render();
    }
    
    public void addComponent(String name, Component component)
    {
        component.setParent(this);
        components.put(name, component);
    }
    
    public Map<String, Component> getComponents()
    {
        return components;
    }
    
    public void setComponents(Map<String, Component> components)
    {
        this.components = components;
    }
}

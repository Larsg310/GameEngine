package core.scene;

import core.math.Transform;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private Node parent;
    private List<Node> children;
    private Transform worldTransform;
    private Transform localTransform;
    
    public Node()
    {
        worldTransform = new Transform();
        localTransform = new Transform();
        children = new ArrayList<>();
    }
    
    public void addChild(Node child)
    {
        child.setParent(this);
        children.add(child);
    }
    
    public void update()
    {
        children.forEach(Node::update);
    }
    
    public void input()
    {
        children.forEach(Node::input);
    }
    
    public void render()
    {
        children.forEach(Node::render);
    }
    
    public void shutdown()
    {
        children.forEach(Node::shutdown);
    }
    
    public Node getParent()
    {
        return parent;
    }
    
    public void setParent(Node parent)
    {
        this.parent = parent;
    }
    
    public List<Node> getChildren()
    {
        return children;
    }
    
    public void setChildren(List<Node> children)
    {
        this.children = children;
    }
    
    public Transform getWorldTransform()
    {
        return worldTransform;
    }
    
    public void setWorldTransform(Transform worldTransform)
    {
        this.worldTransform = worldTransform;
    }
    
    public Transform getLocalTransform()
    {
        return localTransform;
    }
    
    public void setLocalTransform(Transform localTransform)
    {
        this.localTransform = localTransform;
    }
}

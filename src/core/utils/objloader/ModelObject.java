package core.utils.objloader;

import java.util.ArrayDeque;
import java.util.Deque;

public class ModelObject
{
    private Deque<PolygonGroup> polygonGroups;
    private String name = "";
    
    public ModelObject()
    {
        polygonGroups = new ArrayDeque<>();
    }
    
    public Deque<PolygonGroup> getPolygonGroups()
    {
        return polygonGroups;
    }
    
    public void setPolygonGroups(Deque<PolygonGroup> polygonGroups)
    {
        this.polygonGroups = polygonGroups;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}

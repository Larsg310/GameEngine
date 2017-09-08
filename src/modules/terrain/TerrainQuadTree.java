package modules.terrain;

import core.buffers.PatchVBO;
import core.math.Vec2f;
import core.scene.Node;

public class TerrainQuadTree extends Node
{
    
    public static final int ROOT_NODES = 8;
    
    public TerrainQuadTree(TerrainConfig terrConfig)
    {
        PatchVBO buffer = new PatchVBO();
        buffer.allocate(generatePatch());
        
        for (int x = 0; x < ROOT_NODES; x++)
        {
            for (int y = 0; y < ROOT_NODES; y++)
            {
                addChild(new TerrainNode(buffer, terrConfig, new Vec2f(x / (float) ROOT_NODES, y / (float) ROOT_NODES), 0, new Vec2f(x, y)));
            }
        }
    }
    
    public void updateQuadTree()
    {
        for (Node node : getChildren()) ((TerrainNode) node).updateQuadTree();
    }
    
    public Vec2f[] generatePatch()
    {
        // 16 vertices for each patch
        Vec2f[] vertices = new Vec2f[16];
        
        int index = 0;
        
        vertices[index++] = new Vec2f(0, 0);
        vertices[index++] = new Vec2f(0.333f, 0);
        vertices[index++] = new Vec2f(0.666f, 0);
        vertices[index++] = new Vec2f(1, 0);
        
        vertices[index++] = new Vec2f(0, 0.333f);
        vertices[index++] = new Vec2f(0.333f, 0.333f);
        vertices[index++] = new Vec2f(0.666f, 0.333f);
        vertices[index++] = new Vec2f(1, 0.333f);
        
        vertices[index++] = new Vec2f(0, 0.666f);
        vertices[index++] = new Vec2f(0.333f, 0.666f);
        vertices[index++] = new Vec2f(0.666f, 0.666f);
        vertices[index++] = new Vec2f(1, 0.666f);
        
        vertices[index++] = new Vec2f(0, 1);
        vertices[index++] = new Vec2f(0.333f, 1);
        vertices[index++] = new Vec2f(0.666f, 1);
        vertices[index] = new Vec2f(1, 1);
        
        return vertices;
    }
}

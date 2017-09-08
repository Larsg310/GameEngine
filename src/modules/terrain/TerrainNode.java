package modules.terrain;

import core.buffers.PatchVBO;
import core.configs.Default;
import core.kernel.Camera;
import core.math.Vec2f;
import core.math.Vec3f;
import core.renderer.RenderInfo;
import core.renderer.Renderer;
import core.scene.GameObject;
import core.scene.Node;
import core.utils.Constants;

public class TerrainNode extends GameObject
{
    
    private boolean isLeaf;
    private TerrainConfig config;
    private int lod;
    private Vec2f location;
    private Vec3f worldPos;
    private Vec2f index;
    private float gap;
    private PatchVBO buffer;
    
    public TerrainNode(PatchVBO patchVBO, TerrainConfig terrConfig, Vec2f location, int lod, Vec2f index)
    {
        
        this.buffer = patchVBO;
        this.isLeaf = true;
        this.index = index;
        this.lod = lod;
        this.location = location;
        this.config = terrConfig;
        this.gap = 1f / (TerrainQuadTree.ROOT_NODES * (float) (Math.pow(2, lod)));
        
        Vec3f localScaling = new Vec3f(gap, 0, gap);
        Vec3f localTranslation = new Vec3f(location.getX(), 0, location.getY());
        
        getLocalTransform().setScaling(localScaling);
        getLocalTransform().setTranslation(localTranslation);
        
        getWorldTransform().setScaling(new Vec3f(terrConfig.getScaleXZ(), terrConfig.getScaleY(), terrConfig.getScaleXZ()));
        getWorldTransform().setTranslation(new Vec3f(-config.getScaleXZ() / 2F, 0, -config.getScaleXZ() / 2F));
        
        Renderer renderer = new Renderer();
        renderer.setVbo(buffer);
        renderer.setInfo(new RenderInfo(new Default(), TerrainShader.getInstance()));
        
        addComponent(Constants.RENDERER_COMPONENT, renderer);
        
        computeWorldPos();
        updateQuadTree();
    }
    
    public void render()
    {
        if (isLeaf) getComponents().get(Constants.RENDERER_COMPONENT).render();
        
        getChildren().forEach(Node::render);
    }
    
    public void updateQuadTree()
    {
        if (Camera.getCurrentCamera().getPosition().getY() > (config.getScaleY())) worldPos.setY(config.getScaleY());
        else worldPos.setY(Camera.getCurrentCamera().getPosition().getY());
        
        updateChildNodes();
        
        for (Node node : getChildren()) ((TerrainNode) node).updateQuadTree();
        
    }
    
    private void updateChildNodes()
    {
        float distance = (Camera.getCurrentCamera().getPosition().substract(worldPos)).length();
        
        if (distance < config.getLodRange(lod)) addChildNodes(lod + 1);
        else if (distance >= config.getLodRange(lod)) removeChildNodes();
    }
    
    private void addChildNodes(int lod)
    {
        if (isLeaf) isLeaf = false;
        
        if (getChildren().size() == 0)
        {
            for (int x = 0; x < 2; x++)
            {
                for (int y = 0; y < 2; y++)
                {
                    addChild(new TerrainNode(buffer, config, location.add(new Vec2f(x * gap / 2f, y * gap / 2f)), lod, new Vec2f(x, y)));
                }
            }
        }
    }
    
    private void removeChildNodes()
    {
        if (!isLeaf) isLeaf = true;
        if (getChildren().size() != 0) getChildren().clear();
        
    }
    
    public void computeWorldPos()
    {
        Vec2f loc = location.add(gap / 2f).multiply(config.getScaleXZ()).substract(config.getScaleXZ() / 2f);
        
        this.worldPos = new Vec3f(loc.getX(), 0, loc.getY());
    }
    
    public Vec3f getWorldPos()
    {
        return worldPos;
    }
    
    public void setWorldPos(Vec3f worldPos)
    {
        this.worldPos = worldPos;
    }
    
    public Vec2f getLocation()
    {
        return location;
    }
    
    public void setLocation(Vec2f location)
    {
        this.location = location;
    }
    
    public TerrainConfig getConfig()
    {
        return config;
    }
    
    public void setConfig(TerrainConfig terrConfig)
    {
        this.config = terrConfig;
    }
    
    public int getLod()
    {
        return lod;
    }
    
    public void setLod(int lod)
    {
        this.lod = lod;
    }
    
    public Vec2f getIndex()
    {
        return index;
    }
    
    public void setIndex(Vec2f index)
    {
        this.index = index;
    }
    
    public float getGap()
    {
        return this.gap;
    }
    
    public TerrainNode getQuadtreeParent()
    {
        return (TerrainNode) getParent();
    }
}

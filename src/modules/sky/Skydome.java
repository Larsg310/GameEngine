package modules.sky;

import core.buffers.MeshVBO;
import core.configs.CCW;
import core.math.Vec3f;
import core.model.Mesh;
import core.renderer.RenderInfo;
import core.renderer.Renderer;
import core.scene.GameObject;
import core.utils.Constants;
import core.utils.objloader.OBJLoader;

public class Skydome extends GameObject
{
    public Skydome()
    {
        Mesh mesh = new OBJLoader().load("./res/models/dome", "dome.obj", null)[0].getMesh();
        MeshVBO meshBuffer = new MeshVBO();
        meshBuffer.allocate(mesh);
        
        Renderer renderer = new Renderer();
        renderer.setVbo(meshBuffer);
        renderer.setInfo(new RenderInfo(new CCW(), AtmosphereShader.getInstance()));
        addComponent(Constants.RENDERER_COMPONENT, renderer);
        
        getWorldTransform().setScaling(new Vec3f(Constants.Z_FAR * 0.5F, Constants.Z_FAR * 0.5F, Constants.Z_FAR * 0.5F));
    }
}

package core.math;

import core.kernel.Camera;

public class Transform
{
    private Vec3f translation;
    private Vec3f rotation;
    private Vec3f scaling;
    
    public Transform()
    {
        setTranslation(new Vec3f(0, 0, 0));
        setRotation(new Vec3f(0, 0, 0));
        setScaling(new Vec3f(1, 1, 1));
    }
    
    public Matrix4f getWorldMatrix()
    {
        Matrix4f translationMatrix = Matrix4f.translation(translation);
        Matrix4f rotationMatrix = Matrix4f.rotation(rotation);
        Matrix4f scalingMatrix = Matrix4f.scaling(scaling);
        
        return translationMatrix.multiply(scalingMatrix.multiply(rotationMatrix));
    }
    
    public Matrix4f getModelMatrix()
    {
        return Matrix4f.rotation(rotation);
    }
    
    public Matrix4f getMVPMatrix()
    {
        return Camera.getCurrentCamera().getViewProjectionMatrix().multiply(getWorldMatrix());
    }
    
    public Vec3f getTranslation()
    {
        return translation;
    }
    
    public void setTranslation(Vec3f translation)
    {
        this.translation = translation;
    }
    
    public Vec3f getRotation()
    {
        return rotation;
    }
    
    public void setRotation(Vec3f rotation)
    {
        this.rotation = rotation;
    }
    
    public Vec3f getScaling()
    {
        return scaling;
    }
    
    public void setScaling(Vec3f scaling)
    {
        this.scaling = scaling;
    }
}

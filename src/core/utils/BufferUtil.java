package core.utils;

import core.math.Matrix4f;
import core.math.Quaternion;
import core.math.Vec2f;
import core.math.Vec3f;
import core.model.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil
{
    
    public static FloatBuffer createFloatBuffer(int size)
    {
        return BufferUtils.createFloatBuffer(size);
    }
    
    public static IntBuffer createIntBuffer(int size)
    {
        return BufferUtils.createIntBuffer(size);
    }
    
    public static DoubleBuffer createDoubleBuffer(int size)
    {
        return BufferUtils.createDoubleBuffer(size);
    }
    
    public static IntBuffer createFlippedBuffer(int... values)
    {
        IntBuffer buffer = createIntBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBuffer(float... values)
    {
        FloatBuffer buffer = createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        
        return buffer;
    }
    
    public static DoubleBuffer createFlippedBuffer(double... values)
    {
        DoubleBuffer buffer = createDoubleBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBufferAOS(Vertex[] vertices)
    {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.FLOATS);
        
        for (Vertex vertex : vertices)
        {
            buffer.put(vertex.getPos().getX());
            buffer.put(vertex.getPos().getY());
            buffer.put(vertex.getPos().getZ());
            buffer.put(vertex.getNormal().getX());
            buffer.put(vertex.getNormal().getY());
            buffer.put(vertex.getNormal().getZ());
            buffer.put(vertex.getTextureCoord().getX());
            buffer.put(vertex.getTextureCoord().getY());
            
            if (vertex.getTangent() != null && vertex.getBiTangent() != null)
            {
                buffer.put(vertex.getTangent().getX());
                buffer.put(vertex.getTangent().getY());
                buffer.put(vertex.getTangent().getZ());
                buffer.put(vertex.getBiTangent().getX());
                buffer.put(vertex.getBiTangent().getY());
                buffer.put(vertex.getBiTangent().getZ());
            }
        }
        
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBufferSOA(Vertex[] vertices)
    {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.FLOATS);
        
        for (Vertex vertex : vertices)
        {
            buffer.put(vertex.getPos().getX());
            buffer.put(vertex.getPos().getY());
            buffer.put(vertex.getPos().getZ());
        }
        
        for (Vertex vertex : vertices)
        {
            buffer.put(vertex.getNormal().getX());
            buffer.put(vertex.getNormal().getY());
            buffer.put(vertex.getNormal().getZ());
        }
        
        for (Vertex vertex : vertices)
        {
            buffer.put(vertex.getTextureCoord().getX());
            buffer.put(vertex.getTextureCoord().getY());
        }
        
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBuffer(Vec3f[] vectors)
    {
        FloatBuffer buffer = createFloatBuffer(vectors.length * Float.BYTES * 3);
        
        for (Vec3f vector : vectors)
        {
            buffer.put(vector.getX());
            buffer.put(vector.getY());
            buffer.put(vector.getZ());
        }
        
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBuffer(Quaternion[] quaternions)
    {
        FloatBuffer buffer = createFloatBuffer(quaternions.length * Float.BYTES * 4);
        
        for (Quaternion quaternion : quaternions)
        {
            buffer.put(quaternion.getX());
            buffer.put(quaternion.getY());
            buffer.put(quaternion.getZ());
            buffer.put(quaternion.getW());
        }
        
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBuffer(Vec3f vector)
    {
        FloatBuffer buffer = createFloatBuffer(Float.BYTES * 3);
        
        buffer.put(vector.getX());
        buffer.put(vector.getY());
        buffer.put(vector.getZ());
        
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBuffer(Quaternion quaternion)
    {
        FloatBuffer buffer = createFloatBuffer(Float.BYTES * 4);
        
        buffer.put(quaternion.getX());
        buffer.put(quaternion.getY());
        buffer.put(quaternion.getZ());
        buffer.put(quaternion.getW());
        
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBuffer(Vec2f[] vectors)
    {
        FloatBuffer buffer = createFloatBuffer(vectors.length * Float.BYTES * 2);
        
        for (Vec2f vector : vectors)
        {
            buffer.put(vector.getX());
            buffer.put(vector.getY());
        }
        
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBuffer(Matrix4f matrix)
    {
        FloatBuffer buffer = createFloatBuffer(4 * 4);
        
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                buffer.put(matrix.get(i, j));
        
        buffer.flip();
        
        return buffer;
    }
    
    public static FloatBuffer createFlippedBuffer(Matrix4f[] matrices)
    {
        FloatBuffer buffer = createFloatBuffer(4 * 4 * matrices.length);
        
        for (Matrix4f matrix : matrices)
        {
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                    buffer.put(matrix.get(i, j));
        }
        
        buffer.flip();
        
        return buffer;
    }
}

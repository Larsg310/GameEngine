package core.shaders;

import core.math.Matrix4f;
import core.math.Quaternion;
import core.math.Vec2f;
import core.math.Vec3f;
import core.scene.GameObject;
import core.utils.BufferUtil;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import java.util.HashMap;

public abstract class Shader
{
    
    private int program;
    private HashMap<String, Integer> uniforms;
    
    public Shader()
    {
        program = GL20.glCreateProgram();
        uniforms = new HashMap<>();
        
        if (program == 0)
        {
            System.err.println("Shader creation failed");
            System.exit(1);
        }
    }
    
    public void bind()
    {
        GL20.glUseProgram(program);
    }
    
    public void addUniform(String uniform)
    {
        int uniformLocation = GL20.glGetUniformLocation(program, uniform);
        
        if (uniformLocation == 0xFFFFFFFF)
        {
            System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + uniform);
            new Exception().printStackTrace();
            System.exit(1);
        }
        
        uniforms.put(uniform, uniformLocation);
    }
    
    public void addUniformBlock(String uniform)
    {
        int uniformLocation = GL31.glGetUniformBlockIndex(program, uniform);
        if (uniformLocation == 0xFFFFFFFF)
        {
            System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + uniform);
            new Exception().printStackTrace();
            System.exit(1);
        }
        
        uniforms.put(uniform, uniformLocation);
    }
    
    public void addVertexShader(String text)
    {
        addProgram(text, GL20.GL_VERTEX_SHADER);
    }
    
    public void addGeometryShader(String text)
    {
        addProgram(text, GL32.GL_GEOMETRY_SHADER);
    }
    
    public void addFragmentShader(String text)
    {
        addProgram(text, GL20.GL_FRAGMENT_SHADER);
    }
    
    public void addTessellationControlShader(String text)
    {
        addProgram(text, GL40.GL_TESS_CONTROL_SHADER);
    }
    
    public void addTessellationEvaluationShader(String text)
    {
        addProgram(text, GL40.GL_TESS_EVALUATION_SHADER);
    }
    
    public void addComputeShader(String text)
    {
        addProgram(text, GL43.GL_COMPUTE_SHADER);
    }
    
    public void compileShader()
    {
        GL20.glLinkProgram(program);
        
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0)
        {
            System.out.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }
        
        GL20.glValidateProgram(program);
        
        if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == 0)
        {
            System.err.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }
    }
    
    private void addProgram(String text, int type)
    {
        int shader = GL20.glCreateShader(type);
        
        if (shader == 0)
        {
            System.err.println(this.getClass().getName() + " Shader creation failed");
            System.exit(1);
        }
        
        GL20.glShaderSource(shader, text);
        GL20.glCompileShader(shader);
        
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0)
        {
            System.err.println(this.getClass().getName() + " " + GL20.glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }
        
        GL20.glAttachShader(program, shader);
    }
    
    public void setUniformi(String uniformName, int value)
    {
        GL20.glUniform1i(uniforms.get(uniformName), value);
    }
    
    public void setUniformf(String uniformName, float value)
    {
        GL20.glUniform1f(uniforms.get(uniformName), value);
    }
    
    public void setUniform(String uniformName, Vec2f value)
    {
        GL20.glUniform2f(uniforms.get(uniformName), value.getX(), value.getY());
    }
    
    public void setUniform(String uniformName, Vec3f value)
    {
        GL20.glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
    }
    
    public void setUniform(String uniformName, Quaternion value)
    {
        GL20.glUniform4f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ(), value.getW());
    }
    
    public void setUniform(String uniformName, Matrix4f value)
    {
        GL20.glUniformMatrix4fv(uniforms.get(uniformName), true, BufferUtil.createFlippedBuffer(value));
    }
    
    public void bindUniformBlock(String uniformBlockName, int uniformBlockBinding)
    {
        GL31.glUniformBlockBinding(program, uniforms.get(uniformBlockName), uniformBlockBinding);
    }
    
    public void bindFragDataLocation(String name, int index)
    {
        GL30.glBindFragDataLocation(program, index, name);
    }
    
    public int getProgram()
    {
        return this.program;
    }
    
    public void updateUniforms(GameObject object){}
}

package core.math;

import core.kernel.Window;

public class Matrix4f
{
    private float[][] matrix;
    
    private Matrix4f()
    {
        setMatrix(new float[4][4]);
    }
    
    public static Matrix4f zero()
    {
        Matrix4f m = new Matrix4f();
        
        m.matrix[0][0] = 0;
        m.matrix[0][1] = 0;
        m.matrix[0][2] = 0;
        m.matrix[0][3] = 0;
        m.matrix[1][0] = 0;
        m.matrix[1][1] = 0;
        m.matrix[1][2] = 0;
        m.matrix[1][3] = 0;
        m.matrix[2][0] = 0;
        m.matrix[2][1] = 0;
        m.matrix[2][2] = 0;
        m.matrix[2][3] = 0;
        m.matrix[3][0] = 0;
        m.matrix[3][1] = 0;
        m.matrix[3][2] = 0;
        m.matrix[3][3] = 0;
        
        return m;
    }
    
    public Matrix4f identity()
    {
        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = 0;
        matrix[1][3] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;
        
        return this;
    }
    
    public Matrix4f orthographic2D(int width, int height)
    {
        matrix[0][0] = 2f / (float) width;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = -1;
        matrix[1][0] = 0;
        matrix[1][1] = 2f / (float) height;
        matrix[1][2] = 0;
        matrix[1][3] = -1;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;
        
        return this;
    }
    
    public Matrix4f orthographic2D()
    {
        //Z-Value 1: depth of orthographic OOB between 0 and -1
        
        matrix[0][0] = 2f / (float) Window.getInstance().getWidth();
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = -1;
        matrix[1][0] = 0;
        matrix[1][1] = 2f / (float) Window.getInstance().getHeight();
        matrix[1][2] = 0;
        matrix[1][3] = -1;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;
        
        return this;
    }
    
    public static Matrix4f translation(Vec3f translation)
    {
        Matrix4f m = new Matrix4f();
        
        m.matrix[0][0] = 1;
        m.matrix[0][1] = 0;
        m.matrix[0][2] = 0;
        m.matrix[0][3] = translation.getX();
        m.matrix[1][0] = 0;
        m.matrix[1][1] = 1;
        m.matrix[1][2] = 0;
        m.matrix[1][3] = translation.getY();
        m.matrix[2][0] = 0;
        m.matrix[2][1] = 0;
        m.matrix[2][2] = 1;
        m.matrix[2][3] = translation.getZ();
        m.matrix[3][0] = 0;
        m.matrix[3][1] = 0;
        m.matrix[3][2] = 0;
        m.matrix[3][3] = 1;
        
        return m;
    }
    
    public static Matrix4f rotation(Vec3f rotation)
    {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();
        
        float x = (float) Math.toRadians(rotation.getX());
        float y = (float) Math.toRadians(rotation.getY());
        float z = (float) Math.toRadians(rotation.getZ());
        
        rz.matrix[0][0] = (float) Math.cos(z);
        rz.matrix[0][1] = -(float) Math.sin(z);
        rz.matrix[0][2] = 0;
        rz.matrix[0][3] = 0;
        rz.matrix[1][0] = (float) Math.sin(z);
        rz.matrix[1][1] = (float) Math.cos(z);
        rz.matrix[1][2] = 0;
        rz.matrix[1][3] = 0;
        rz.matrix[2][0] = 0;
        rz.matrix[2][1] = 0;
        rz.matrix[2][2] = 1;
        rz.matrix[2][3] = 0;
        rz.matrix[3][0] = 0;
        rz.matrix[3][1] = 0;
        rz.matrix[3][2] = 0;
        rz.matrix[3][3] = 1;
        
        rx.matrix[0][0] = 1;
        rx.matrix[0][1] = 0;
        rx.matrix[0][2] = 0;
        rx.matrix[0][3] = 0;
        rx.matrix[1][0] = 0;
        rx.matrix[1][1] = (float) Math.cos(x);
        rx.matrix[1][2] = -(float) Math.sin(x);
        rx.matrix[1][3] = 0;
        rx.matrix[2][0] = 0;
        rx.matrix[2][1] = (float) Math.sin(x);
        rx.matrix[2][2] = (float) Math.cos(x);
        rx.matrix[2][3] = 0;
        rx.matrix[3][0] = 0;
        rx.matrix[3][1] = 0;
        rx.matrix[3][2] = 0;
        rx.matrix[3][3] = 1;
        
        ry.matrix[0][0] = (float) Math.cos(y);
        ry.matrix[0][1] = 0;
        ry.matrix[0][2] = (float) Math.sin(y);
        ry.matrix[0][3] = 0;
        ry.matrix[1][0] = 0;
        ry.matrix[1][1] = 1;
        ry.matrix[1][2] = 0;
        ry.matrix[1][3] = 0;
        ry.matrix[2][0] = -(float) Math.sin(y);
        ry.matrix[2][1] = 0;
        ry.matrix[2][2] = (float) Math.cos(y);
        ry.matrix[2][3] = 0;
        ry.matrix[3][0] = 0;
        ry.matrix[3][1] = 0;
        ry.matrix[3][2] = 0;
        ry.matrix[3][3] = 1;
        
        return rz.multiply(ry.multiply(rx));
    }
    
    public static Matrix4f scaling(Vec3f scaling)
    {
        Matrix4f m = new Matrix4f();
        
        m.matrix[0][0] = scaling.getX();
        m.matrix[0][1] = 0;
        m.matrix[0][2] = 0;
        m.matrix[0][3] = 0;
        m.matrix[1][0] = 0;
        m.matrix[1][1] = scaling.getY();
        m.matrix[1][2] = 0;
        m.matrix[1][3] = 0;
        m.matrix[2][0] = 0;
        m.matrix[2][1] = 0;
        m.matrix[2][2] = scaling.getZ();
        m.matrix[2][3] = 0;
        m.matrix[3][0] = 0;
        m.matrix[3][1] = 0;
        m.matrix[3][2] = 0;
        m.matrix[3][3] = 1;
        
        return m;
    }
    
    public Matrix4f orthographicProjection(float l, float r, float b, float t, float n, float f)
    {
        matrix[0][0] = 2.0f / (r - l);
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = -(r + l) / (r - l);
        matrix[1][0] = 0;
        matrix[1][1] = 2.0f / (t - b);
        matrix[1][2] = 0;
        matrix[1][3] = -(t + b) / (t - b);
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 2.0f / (f - n);
        matrix[2][3] = -(f + n) / (f - n);
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;
        
        return this;
    }
    
    public static Matrix4f perspectiveProjection(float fovY, float width, float height, float zNear, float zFar)
    {
        Matrix4f m = new Matrix4f();
        float tanFOV = (float) Math.tan(Math.toRadians(fovY / 2));
        float aspectRatio = width / height;
        
        m.matrix[0][0] = 1 / (tanFOV * aspectRatio);
        m.matrix[0][1] = 0;
        m.matrix[0][2] = 0;
        m.matrix[0][3] = 0;
        m.matrix[1][0] = 0;
        m.matrix[1][1] = 1 / tanFOV;
        m.matrix[1][2] = 0;
        m.matrix[1][3] = 0;
        m.matrix[2][0] = 0;
        m.matrix[2][1] = 0;
        m.matrix[2][2] = zFar / (zFar - zNear);
        m.matrix[2][3] = zFar * zNear / (zFar - zNear);
        m.matrix[3][0] = 0;
        m.matrix[3][1] = 0;
        m.matrix[3][2] = 1;
        m.matrix[3][3] = 1;
        
        return m;
    }
    
    public static Matrix4f view(Vec3f forward, Vec3f up)
    {
        Matrix4f m = new Matrix4f();
        Vec3f r = up.cross(forward);
        
        m.matrix[0][0] = r.getX();
        m.matrix[0][1] = r.getY();
        m.matrix[0][2] = r.getZ();
        m.matrix[0][3] = 0;
        m.matrix[1][0] = up.getX();
        m.matrix[1][1] = up.getY();
        m.matrix[1][2] = up.getZ();
        m.matrix[1][3] = 0;
        m.matrix[2][0] = forward.getX();
        m.matrix[2][1] = forward.getY();
        m.matrix[2][2] = forward.getZ();
        m.matrix[2][3] = 0;
        m.matrix[3][0] = 0;
        m.matrix[3][1] = 0;
        m.matrix[3][2] = 0;
        m.matrix[3][3] = 1;
        
        return m;
    }
    
    public Matrix4f multiply(Matrix4f r)
    {
        Matrix4f res = new Matrix4f();
        
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                res.set(i, j, matrix[i][0] * r.get(0, j) + matrix[i][1] * r.get(1, j) + matrix[i][2] * r.get(2, j) + matrix[i][3] * r.get(3, j));
            }
        }
        
        return res;
    }
    
    public Quaternion multiply(Quaternion v)
    {
        Quaternion res = new Quaternion(0, 0, 0, 0);
        
        res.setX(matrix[0][0] * v.getX() + matrix[0][1] * v.getY() + matrix[0][2] * v.getZ() + matrix[0][3] * v.getW());
        res.setY(matrix[1][0] * v.getX() + matrix[1][1] * v.getY() + matrix[1][2] * v.getZ() + matrix[1][3] * v.getW());
        res.setZ(matrix[2][0] * v.getX() + matrix[2][1] * v.getY() + matrix[2][2] * v.getZ() + matrix[2][3] * v.getW());
        res.setW(matrix[3][0] * v.getX() + matrix[3][1] * v.getY() + matrix[3][2] * v.getZ() + matrix[3][3] * v.getW());
        
        return res;
    }
    
    public Matrix4f transpose()
    {
        Matrix4f result = new Matrix4f();
        
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                result.set(i, j, get(j, i));
            }
        }
        return result;
    }
    
    public Matrix4f invert()
    {
        float s0 = get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1);
        float s1 = get(0, 0) * get(1, 2) - get(1, 0) * get(0, 2);
        float s2 = get(0, 0) * get(1, 3) - get(1, 0) * get(0, 3);
        float s3 = get(0, 1) * get(1, 2) - get(1, 1) * get(0, 2);
        float s4 = get(0, 1) * get(1, 3) - get(1, 1) * get(0, 3);
        float s5 = get(0, 2) * get(1, 3) - get(1, 2) * get(0, 3);
        
        float c5 = get(2, 2) * get(3, 3) - get(3, 2) * get(2, 3);
        float c4 = get(2, 1) * get(3, 3) - get(3, 1) * get(2, 3);
        float c3 = get(2, 1) * get(3, 2) - get(3, 1) * get(2, 2);
        float c2 = get(2, 0) * get(3, 3) - get(3, 0) * get(2, 3);
        float c1 = get(2, 0) * get(3, 2) - get(3, 0) * get(2, 2);
        float c0 = get(2, 0) * get(3, 1) - get(3, 0) * get(2, 1);
        
        float div = (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
        if (div == 0) System.err.println("not invertible");
        
        float invdet = 1.0f / div;
        
        Matrix4f invM = new Matrix4f();
        
        invM.set(0, 0, (get(1, 1) * c5 - get(1, 2) * c4 + get(1, 3) * c3) * invdet);
        invM.set(0, 1, (-get(0, 1) * c5 + get(0, 2) * c4 - get(0, 3) * c3) * invdet);
        invM.set(0, 2, (get(3, 1) * s5 - get(3, 2) * s4 + get(3, 3) * s3) * invdet);
        invM.set(0, 3, (-get(2, 1) * s5 + get(2, 2) * s4 - get(2, 3) * s3) * invdet);
        
        invM.set(1, 0, (-get(1, 0) * c5 + get(1, 2) * c2 - get(1, 3) * c1) * invdet);
        invM.set(1, 1, (get(0, 0) * c5 - get(0, 2) * c2 + get(0, 3) * c1) * invdet);
        invM.set(1, 2, (-get(3, 0) * s5 + get(3, 2) * s2 - get(3, 3) * s1) * invdet);
        invM.set(1, 3, (get(2, 0) * s5 - get(2, 2) * s2 + get(2, 3) * s1) * invdet);
        
        invM.set(2, 0, (get(1, 0) * c4 - get(1, 1) * c2 + get(1, 3) * c0) * invdet);
        invM.set(2, 1, (-get(0, 0) * c4 + get(0, 1) * c2 - get(0, 3) * c0) * invdet);
        invM.set(2, 2, (get(3, 0) * s4 - get(3, 1) * s2 + get(3, 3) * s0) * invdet);
        invM.set(2, 3, (-get(2, 0) * s4 + get(2, 1) * s2 - get(2, 3) * s0) * invdet);
        
        invM.set(3, 0, (-get(1, 0) * c3 + get(1, 1) * c1 - get(1, 2) * c0) * invdet);
        invM.set(3, 1, (get(0, 0) * c3 - get(0, 1) * c1 + get(0, 2) * c0) * invdet);
        invM.set(3, 2, (-get(3, 0) * s3 + get(3, 1) * s1 - get(3, 2) * s0) * invdet);
        invM.set(3, 3, (get(2, 0) * s3 - get(2, 1) * s1 + get(2, 2) * s0) * invdet);
        
        return invM;
    }
    
    public boolean equals(Matrix4f m)
    {
        return this.matrix[0][0] == m.getMatrix()[0][0] && this.matrix[0][1] == m.getMatrix()[0][1] && this.matrix[0][2] == m.getMatrix()[0][2] && this.matrix[0][3] == m.getMatrix()[0][3] && this.matrix[1][0] == m.getMatrix()[1][0] && this.matrix[1][1] == m.getMatrix()[1][1] && this.matrix[1][2] == m.getMatrix()[1][2] && this.matrix[1][3] == m.getMatrix()[1][3] && this.matrix[2][0] == m.getMatrix()[2][0] && this.matrix[2][1] == m.getMatrix()[2][1] && this.matrix[2][2] == m.getMatrix()[2][2] && this.matrix[2][3] == m.getMatrix()[2][3] && this.matrix[3][0] == m.getMatrix()[3][0] && this.matrix[3][1] == m.getMatrix()[3][1] && this.matrix[3][2] == m.getMatrix()[3][2] && this.matrix[3][3] == m.getMatrix()[3][3];
    }
    
    public void set(int x, int y, float value)
    {
        this.matrix[x][y] = value;
    }
    
    public float get(int x, int y)
    {
        return this.matrix[x][y];
    }
    
    public float[][] getMatrix()
    {
        return matrix;
    }
    
    public void setMatrix(float[][] matrix)
    {
        this.matrix = matrix;
    }
    
    public String toString()
    {
        return "|" + matrix[0][0] + " " + matrix[0][1] + " " + matrix[0][2] + " " + matrix[0][3] + "|\n" + "|" + matrix[1][0] + " " + matrix[1][1] + " " + matrix[1][2] + " " + matrix[1][3] + "|\n" + "|" + matrix[2][0] + " " + matrix[2][1] + " " + matrix[2][2] + " " + matrix[2][3] + "|\n" + "|" + matrix[3][0] + " " + matrix[3][1] + " " + matrix[3][2] + " " + matrix[3][3] + "|";
    }
}

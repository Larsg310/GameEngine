package core.kernel;

import core.math.Matrix4f;
import core.math.Quaternion;
import core.math.Vec3f;
import core.utils.Constants;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class Camera
{
    private static Camera currentCamera;
    
    private final Vec3f yAxis = new Vec3f(0, 1, 0);
    
    private Vec3f position;
    private Vec3f previousPosition;
    private Vec3f forward;
    private Vec3f previousForward;
    private Vec3f up;
    
    private float movAmt = 0.5f;
    private float rotAmt = 0.8f;
    
    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;
    private Matrix4f viewProjectionMatrix;
    private Matrix4f previousViewMatrix;
    private Matrix4f previousViewProjectionMatrix;
    
    private boolean cameraMoved;
    private boolean cameraRotated;
    
    private float width;
    private float height;
    private float fovY;
    
    private float rotYStride;
    private float rotYAmt;
    private float rotYCounter;
    private boolean rotYInitiated = false;
    private float rotXStride;
    private float rotXAmt;
    private float rotXCounter;
    private boolean rotXInitiated = false;
    private float mouseSensitivity = 2F;
    
    private Quaternion[] frustumPlanes = new Quaternion[6];
    private Vec3f[] frustumCorners = new Vec3f[8];
    
    public Camera()
    {
        this(new Vec3f(0, 20, 0));
    }
    
    public Camera(Vec3f position)
    {
        this(position, new Vec3f(1, 0, -1).normalize(), new Vec3f(0, 1, 0));
    }
    
    private Camera(Vec3f position, Vec3f forward, Vec3f up)
    {
        setPosition(position);
        setForward(forward);
        setUp(up);
        up.normalize();
        forward.normalize();
        
        setProjection(70, Window.getInstance().getWidth(), Window.getInstance().getHeight());
        setViewMatrix(Matrix4f.view(this.getForward(), this.getUp()).multiply(Matrix4f.translation(this.getPosition().multiply(-1))));
        previousViewMatrix = Matrix4f.zero();
        viewProjectionMatrix = Matrix4f.zero();
        previousViewProjectionMatrix = Matrix4f.zero();
    }
    
    public void update()
    {
        setPreviousPosition(new Vec3f(position));
        setPreviousForward(new Vec3f(forward));
        cameraMoved = false;
        cameraRotated = false;
        
        movAmt += (0.04f * Input.getInstance().getScrollOffset());
        movAmt = Math.max(0.02f, movAmt);
        
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_W)) move(getForward(), movAmt);
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_S)) move(getForward(), -movAmt);
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_A)) move(getLeft(), movAmt);
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_D)) move(getRight(), movAmt);
        
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_UP)) rotateX(-rotAmt / 8f);
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_DOWN)) rotateX(rotAmt / 8f);
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_LEFT)) rotateY(-rotAmt / 8f);
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_RIGHT)) rotateY(rotAmt / 8f);
        
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_SPACE)) move(yAxis, 0.5F);
        if (Input.getInstance().isKeyHold(GLFW.GLFW_KEY_LEFT_SHIFT)) move(yAxis, -0.5F);
        
        // free mouse rotation
        if (Input.getInstance().isButtonHolding(GLFW.GLFW_MOUSE_BUTTON_MIDDLE))
        {
            float dy = Input.getInstance().getLockedCursorPosition().getY() - Input.getInstance().getCursorPosition().getY();
            float dx = Input.getInstance().getLockedCursorPosition().getX() - Input.getInstance().getCursorPosition().getX();
            
            // y-axis rotation
            if (dy != 0)
            {
                rotYStride = Math.abs(dy * 0.01f);
                rotYAmt = -dy;
                rotYCounter = 0;
                rotYInitiated = true;
            }
            
            if (rotYInitiated)
            {
                // up-rotation
                if (rotYAmt < 0)
                {
                    if (rotYCounter > rotYAmt)
                    {
                        rotateX(-rotYStride * mouseSensitivity);
                        rotYCounter -= rotYStride;
                        rotYStride *= 0.98;
                    }
                    else rotYInitiated = false;
                }
                // down-rotation
                else if (rotYAmt > 0)
                {
                    if (rotYCounter < rotYAmt)
                    {
                        rotateX(rotYStride * mouseSensitivity);
                        rotYCounter += rotYStride;
                        rotYStride *= 0.98;
                    }
                    else rotYInitiated = false;
                }
            }
            
            // x-axis rotation
            if (dx != 0)
            {
                rotXStride = Math.abs(dx * 0.01f);
                rotXAmt = dx;
                rotXCounter = 0;
                rotXInitiated = true;
            }
            
            if (rotXInitiated)
            {
                // up-rotation
                if (rotXAmt < 0)
                {
                    if (rotXCounter > rotXAmt)
                    {
                        rotateY(rotXStride * mouseSensitivity);
                        rotXCounter -= rotXStride;
                        rotXStride *= 0.96;
                    }
                    else rotXInitiated = false;
                }
                // down-rotation
                else if (rotXAmt > 0)
                {
                    if (rotXCounter < rotXAmt)
                    {
                        rotateY(-rotXStride * mouseSensitivity);
                        rotXCounter += rotXStride;
                        rotXStride *= 0.96;
                    }
                    else rotXInitiated = false;
                }
            }
            GLFW.glfwSetCursorPos(Window.getInstance().getWindow(), Input.getInstance().getLockedCursorPosition().getX(), Input.getInstance().getLockedCursorPosition().getY());
        }
        
        if (!position.equals(previousPosition)) cameraMoved = true;
        if (!forward.equals(previousForward)) cameraRotated = true;
        
        setPreviousViewMatrix(viewMatrix);
        setPreviousViewProjectionMatrix(viewProjectionMatrix);
        setViewMatrix(Matrix4f.view(this.getForward(), this.getUp()).multiply(Matrix4f.translation(this.getPosition().multiply(-1))));
        setViewProjectionMatrix(projectionMatrix.multiply(viewMatrix));
    }
    
    public void move(Vec3f dir, float amount)
    {
        Vec3f newPos = position.add(dir.multiply(amount));
        setPosition(newPos);
    }
    
    public void rotateY(float angle)
    {
        Vec3f hAxis = yAxis.cross(forward).normalize();
        forward.rotate(angle, yAxis).normalize();
        up = forward.cross(hAxis).normalize();
    }
    
    public void rotateX(float angle)
    {
        Vec3f hAxis = yAxis.cross(forward).normalize();
        forward.rotate(angle, hAxis).normalize();
        up = forward.cross(hAxis).normalize();
    }
    
    public Vec3f getLeft()
    {
        Vec3f left = forward.cross(up);
        left.normalize();
        return left;
    }
    
    public Vec3f getRight()
    {
        Vec3f right = up.cross(forward);
        right.normalize();
        return right;
    }
    
    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }
    
    public void setProjectionMatrix(Matrix4f projectionMatrix)
    {
        this.projectionMatrix = projectionMatrix;
    }
    
    public void setProjection(float fovY, float width, float height)
    {
        this.fovY = fovY;
        this.width = width;
        this.height = height;
        this.projectionMatrix = Matrix4f.perspectiveProjection(fovY, width, height, Constants.Z_NEAR, Constants.Z_FAR);
    }
    
    public Matrix4f getViewMatrix()
    {
        return viewMatrix;
    }
    
    public void setViewMatrix(Matrix4f viewMatrix)
    {
        this.viewMatrix = viewMatrix;
    }
    
    public Vec3f getPosition()
    {
        return position;
    }
    
    public void setPosition(Vec3f position)
    {
        this.position = position;
    }
    
    public Vec3f getForward()
    {
        return forward;
    }
    
    public void setForward(Vec3f forward)
    {
        this.forward = forward;
    }
    
    public Vec3f getUp()
    {
        return up;
    }
    
    public void setUp(Vec3f up)
    {
        this.up = up;
    }
    
    public Quaternion[] getFrustumPlanes()
    {
        return frustumPlanes;
    }
    
    public float getFovY()
    {
        return this.fovY;
    }
    
    public float getWidth()
    {
        return this.width;
    }
    
    public float getHeight()
    {
        return this.height;
    }
    
    public void setViewProjectionMatrix(Matrix4f viewProjectionMatrix)
    {
        this.viewProjectionMatrix = viewProjectionMatrix;
    }
    
    public Matrix4f getViewProjectionMatrix()
    {
        return viewProjectionMatrix;
    }
    
    public Matrix4f getPreviousViewProjectionMatrix()
    {
        return previousViewProjectionMatrix;
    }
    
    public void setPreviousViewProjectionMatrix(Matrix4f previousViewProjectionMatrix)
    {
        this.previousViewProjectionMatrix = previousViewProjectionMatrix;
    }
    
    public Matrix4f getPreviousViewMatrix()
    {
        return previousViewMatrix;
    }
    
    public void setPreviousViewMatrix(Matrix4f previousViewMatrix)
    {
        this.previousViewMatrix = previousViewMatrix;
    }
    
    public Vec3f[] getFrustumCorners()
    {
        return frustumCorners;
    }
    
    public boolean isCameraMoved()
    {
        return cameraMoved;
    }
    
    public boolean isCameraRotated()
    {
        return cameraRotated;
    }
    
    public Vec3f getPreviousPosition()
    {
        return previousPosition;
    }
    
    public void setPreviousPosition(Vec3f previousPosition)
    {
        this.previousPosition = previousPosition;
    }
    
    public Vec3f getPreviousForward()
    {
        return previousForward;
    }
    
    private void setPreviousForward(Vec3f previousForward)
    {
        this.previousForward = previousForward;
    }
    
    public static Camera getCurrentCamera()
    {
        if (currentCamera == null) currentCamera = new Camera();
        return currentCamera;
    }
    
    public static void setCurrentCamera(Camera camera)
    {
        currentCamera = camera;
    }
}
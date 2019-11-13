package basic;

import javax.swing.*;

public class Camera {

    private Vec3 lowerLeftCorner= new Vec3(-2.0f, -1.0f, -1.0f);
    private Vec3 horizontal = new Vec3(4.0f, 0.0f, 0.0f);
    private Vec3 vertical = new Vec3(0.0f, 2.0f, 0.0f);
    private Vec3 origin = new Vec3(0.0f, 0.0f, 0.0f);

    public Camera(){}
    public Camera(float vfov, float aspect)
    {
        float theta = (float) (vfov * Math.PI/180);
        float halfHeight = (float) Math.tan(theta/2);
        float halfWidth = aspect * halfHeight;
        lowerLeftCorner = new Vec3(-halfWidth,-halfHeight,-1.0f);
        vertical = new Vec3(0,2*halfHeight,0);
        origin = new Vec3(0,0,0);
    }
    public Camera(Vec3 lookfrom, Vec3 lookat, Vec3 vup, float vfov, float aspect)
    {
        Vec3 u,v,w;
        float theta = (float) (vfov * Math.PI/180);
        float halfWidth = (float) Math.tan(theta/2);
        float halfHeight = halfWidth * aspect;
        origin = lookfrom;
        w = lookfrom.Subtract(lookat).unit();
        u = vup.cross(w).unit();
        v = u.cross(w);
        lowerLeftCorner = new Vec3(-halfWidth,-halfHeight,-1.0f);
        lowerLeftCorner = origin.Subtract(u.Scale(halfWidth)).Subtract(v.Scale(halfHeight)).Subtract(w);
        horizontal = u.Scale(2 * halfWidth);
        vertical = v.Scale(2 * halfHeight);
    }


    /**
     *
     * @param u     距离左下角的x
     * @param v     距离左下角的y
     * @return      向量ux+vy表示的点
     */
    public Ray getRay(float u,float v)
    {
        return new Ray(origin, lowerLeftCorner.Plus(horizontal.Scale(u)).Plus(vertical.Scale(v)));
    }


}

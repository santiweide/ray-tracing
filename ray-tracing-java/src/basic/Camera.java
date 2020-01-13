package basic;

import javax.swing.*;

public class Camera {

    private Vec3 lower_left;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Vec3 origin;
    private float lens_radius;
    private Vec3 u = new Vec3();
    private Vec3 v = new Vec3();
    private Vec3 w = new Vec3();

    public Camera() {
        lower_left = new Vec3(-2.0f, -1.0f, -1.0f);
        horizontal = new Vec3(4.0f, 0.0f, 0.0f);
        vertical = new Vec3(0.0f, 2.0f, 0.0f);
        origin = new Vec3(0.0f, 0.0f, 0.0f);
    }
    /**
     *
     * @param lookfrom 相机位置
     * @param lookat 观察点
     * @param vup 相机的倾斜方向 view up
     * @param vfov 视野 field of view
     * @param aspect 宽高比
     */
    public Camera(Vec3 lookfrom, Vec3 lookat, Vec3 vup, float vfov, float aspect){

        Vec3 u, v, w;
        float theta = (float)(vfov * Math.PI / 180);
        float half_height = (float)( Math.tan(theta/2) );
        float half_width = aspect * half_height;
        origin = lookfrom;
        w = lookfrom.Subtract(lookat).unit();      //相当于新的z
        u = vup.cross(w).unit();                   //相当于新的x
        v = w.cross(u).unit();                     //相当于新的y
        lower_left = origin.Subtract(u.Scale(half_width)).Subtract(v.Scale(half_height)).Subtract(w);
        horizontal = u.Scale(2*half_width);
        vertical = v.Scale(2*half_height);
    }


    /**
     *
     * @param u     距离左下角的x
     * @param v     距离左下角的y
     * @return      向量ux+vy表示的点
     */
    public Ray getRay(float u,float v)
    {
        return new Ray(origin, lower_left.Plus(horizontal.Scale(u)).Plus(vertical.Scale(v)).Subtract(origin));
    }


}


package basic;

import materials.Lambertian;
import textures.ConstantTexture;

import java.io.*;
import java.util.ArrayList;

public class Render {
    protected int nx = 200;
    protected   int ny = 100;
    protected String picName = "Mine.ppm";

    public Vec3 color(Ray r,HitableList world)
    {
        HitRecord rec = new HitRecord();
        if(world.hit(r,0.0f,Float.MAX_VALUE, rec))
        {
            return new Vec3(rec.norm.x()+1,rec.norm.y()+1,rec.norm.z()+1).Scale(0.5f);
        }
        else
        {
            Vec3 unitDirection = r.direction().unit();
            float t = (float) (unitDirection.y()+1.0f)*0.5f;
            return new Vec3(1,1,1).Scale(1.0f-t).Plus(new Vec3(0.5f,0.7f,1.0f).Scale(t));
        }
    }

    public void doRendering() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(new File(picName));
        PrintWriter pw = new PrintWriter(fos);
        pw.println("P3");
        pw.println(nx + " " + ny + "\n255");
        ArrayList<Hitable> list = new ArrayList<Hitable>();
        list.add(new Sphere(new Vec3(0.0f,0.0f,-1.0f), 0.5f,new Lambertian(new ConstantTexture(new Vec3(1,1,1)))));
        list.add(new Sphere(new Vec3(0.3f,0.0f,-1.0f), 0.3f,new Lambertian(new ConstantTexture(new Vec3(1,1,1)))));
        list.add(new Sphere(new Vec3(0.0f,-100.5f,-1.0f), 100f,new Lambertian(new ConstantTexture(new Vec3(1,1,1)))));
        HitableList world = new HitableList(list);
        Camera camera = new Camera();
        for(int j = ny - 1;j >= 0;j --)
        {
            for(int i = 0;i < nx;i ++)
            {
                Vec3 col = new Vec3(0,0,0);
                int ns = 100;
                for(int s = 0;s < ns;s ++)
                {
                    float u = (float)(i + Math.random())/(float)nx;
                    float v = (float)(j + Math.random())/(float)ny;
                    Ray r = camera.getRay(u,v);
                    col = col.Plus(color(r,world));
                }
                col = col.Scale((float)1/ns);
                int ir = (int) ((int)255.99*col.x());
                int ig = (int) ((int)255.99*col.y());
                int ib = (int) ((int)255.99*col.z());
                pw.println(ir+" "+ig+" "+ib);
            }
        }
    }

    

}

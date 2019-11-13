package materials;


import basic.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MaterialRender extends Render {

    public Vec3 color(Ray r, HitableList world, int depth)
    {
        HitRecord rec = new HitRecord();
        if(world.hit(r,0.001f,Float.MAX_VALUE, rec))
        {
            Emergent config = new Emergent();
            if(depth < 50 && rec.mat.scatter(r,rec,config)){
                return color(config.scattered,world,depth + 1).Multiply(config.attenuation);
            }
            else
            {
                return new Vec3(1,1,1);
            }
        }
        else
        {
            Vec3 unitDirection = r.direction().unit();
            float t = (unitDirection.y()+1.0f)*0.5f;
            return new Vec3(1,1,1).Scale(1.0f-t).Plus(new Vec3(0.5f,0.7f,1.0f).Scale(t));
        }
    }
    public void doRendering() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(new File(picName));
        PrintWriter pw = new PrintWriter(fos);
        pw.println("P3");
        pw.println(nx + " " + ny + "\n255");
        ArrayList<Hitable> objList = new ArrayList<Hitable>();
        objList.add(new Sphere(new Vec3(0.0f,0.0f,-1.0f), 0.5f, new Lambertian(new Vec3(0.1f, 0.2f, 0.5f))));
        objList.add(new Sphere(new Vec3(0.0f,-100.5f,-1.0f), 100f, new Lambertian(new Vec3(0.8f, 0.8f, 0.0f))));
        objList.add(new Sphere(new Vec3(1,0,-1), 0.5f, new Metal(new Vec3(0.8f, 0.6f, 0.2f), 0.1f)));
        objList.add(new Sphere(new Vec3(-1,0,-1), 0.5f, new Dielectric(1.5f)));
        HitableList world = new HitableList(objList);

        float aspect = (float)nx /(float) ny;
        //Camera camera = new Camera(new Vec3(-2,2,1), new Vec3(0,0,-1),new Vec3(0,1,0), 40, aspect);
        Camera camera = new Camera();
        for(int j = ny - 1;j >= 0;j --)
        {
            for(int i = 0;i < nx;i ++)
            {
                int ns = 100;
                Vec3 col = new Vec3(0,0,0);
                for(int s = 0;s < ns;s ++)
                {
                    float u = (float)(i + Math.random())/(float)nx;
                    float v = (float)(j + Math.random())/(float)ny;
                    Ray r = camera.getRay(u,v);
                    col = col.Plus(color(r,world,0));
                }
                col = col.Scale(1.0f/(float)ns);
                col = new Vec3((float)Math.sqrt(col.x()),(float)Math.sqrt(col.y()),(float)Math.sqrt(col.z()));
                int ir = (int) ((int)255.99f*col.x());
                int ig = (int) ((int)255.99f*col.y());
                int ib = (int) ((int)255.99f*col.z());
                pw.println(ir+" "+ig+" "+ib);
            }
        }
        pw.close();
    }


}

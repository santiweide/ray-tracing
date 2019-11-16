package textures;

import basic.*;
import materials.Dielectric;
import materials.Emergent;
import materials.Lambertian;
import materials.Metal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TextureRendener extends Render {

    public Vec3 color(Ray r, HitableList world, int depth)
    {
        HitRecord rec = new HitRecord();
        if(world.hit(r,0.001f,Float.MAX_VALUE, rec))
        {
            Emergent emergent = new Emergent();
            if(depth < 50 && rec.mat.scatter(r,rec,emergent)){
                return color(emergent.scattered,world,depth + 1).Multiply(emergent.attenuation);
            }
            else {
                return new Vec3(1,1,1);
            }
        }
        else {
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
        Texture checker = new CheckerTexture(new ConstantTexture(new Vec3(0.2f, 0.3f, 0.1f)),
                new ConstantTexture(new Vec3(0.9f, 0.9f, 0.9f)));

//        HitableList world = new HitableList(this.random_scene());
        HitableList world = new HitableList(this.two_spheres());

        Vec3 lookfrom = new Vec3(13,2,3);
        Vec3 lookat = new Vec3(0,0,0);
        Camera camera = new Camera(lookfrom, lookat,new Vec3(0,1,0),20,(float) nx/(float)ny);
        //Camera camera = new Camera();
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

    public ArrayList<Hitable> two_spheres()
    {
        ArrayList<Hitable> objList = new ArrayList<Hitable>();
        Texture checker = new CheckerTexture(new ConstantTexture(new Vec3(0.2f, 0.3f, 0.1f)),
                new ConstantTexture(new Vec3(0.9f, 0.9f, 0.9f)));
        int tmpx = 0 ,tmpy = 0;
        Texture Image = new ImageTexture("earthmap.png");
        objList.add(new Sphere(new Vec3(0.0f,-1000f,0.0f), 999.2f, new Metal(
                new ConstantTexture(new Vec3(0.8f,0.2f,0.1f)),0f)));
        objList.add(new Sphere(new Vec3(0.0f,0.0f,0.0f), 0.8f, new Lambertian(Image)));
        return  objList;
    }
    public ArrayList<Hitable> random_scene() {

        ArrayList<Hitable> objList = new ArrayList<Hitable>();
        //超大漫反射球作为地板
        Texture checker = new CheckerTexture(new ConstantTexture(new Vec3(0.2f, 0.3f, 0.1f)),
                new ConstantTexture(new Vec3(0.9f, 0.9f, 0.9f)));

        objList.add(new Sphere(new Vec3(0.0f,-1000.0f,0.0f), 1000.0f, new Lambertian(checker)));
        //定义三大球
        objList.add(new Sphere(new Vec3(0, 1, 0), 1.0f, new Dielectric(1.5f)));
        objList.add(new Sphere(new Vec3(-4, 1, 0), 1.0f, new Lambertian(new ConstantTexture(new Vec3(0.4f, 0.2f, 0.1f)))));
        objList.add(new Sphere(new Vec3(4, 1, 0), 1.0f, new Metal(new ConstantTexture(new Vec3(0.7f, 0.6f, 0.5f)), 0.0f)));

        //生成地面小球
        int i = 1;
        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                float choose_mat = (float)Math.random();
                Vec3 center = new Vec3((float)( a+0.9*(Math.random()) ), 0.2f, (float) ( b+0.9*(Math.random() )));
                if ((center.Subtract(new Vec3(4,0.2f,0))).length() > 0.9) {
                    if (choose_mat < 0.8) {     //diffuse
                        objList.add(
                                new Sphere(center, 0.2f, new Lambertian( new ConstantTexture(
                                        new Vec3((float)( (Math.random())*(Math.random()) ),
                                                (float)( (Math.random())*(Math.random()) ),
                                                (float)( (Math.random())*(Math.random()) )))
                                ))
                        );
                    }
                    else if (choose_mat < 0.95) {
                        objList.add(
                                new Sphere(center, 0.2f, new Metal( new ConstantTexture(
                                            new Vec3((float)(0.5f*(1+(Math.random()))), (float)( 0.5f*(1+(Math.random())) ), (float)( 0.5f*(1+(Math.random()))) )),
                                        (float)( 0.5*(1+(Math.random())))
                                ))
                        );
                    }
                    else {
                        objList.add(
                                new Sphere(center, 0.2f, new Dielectric(1.5f))
                        );
                    }
                }
            }
        }
        return objList;
    }
}

package materials;


import basic.*;
import textures.ConstantTexture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MaterialRender extends Render {

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
        ArrayList<Hitable> objList = new ArrayList<Hitable>();
        objList.add(new Sphere(new Vec3(0.0f,0.0f,-1.0f), 0.5f, new Lambertian(new ConstantTexture(new Vec3(0.1f, 0.2f, 0.5f)))));
        objList.add(new Sphere(new Vec3(0.0f,-100.5f,-1.0f), 100f, new Lambertian(new ConstantTexture(new Vec3(0.8f, 0.8f, 0.0f)))));
        objList.add(new Sphere(new Vec3(1,0,-1), 0.5f, new Metal(new ConstantTexture(new Vec3(0.8f, 0.6f, 0.2f)), 0.1f)));
        objList.add(new Sphere(new Vec3(-1,0,-1), 0.5f, new Dielectric(1.5f)));
        HitableList world = new HitableList(this.random_scene());

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

    public ArrayList<Hitable> random_scene() {

        ArrayList<Hitable> objList = new ArrayList<Hitable>();
        //超大漫反射球作为地板
        objList.add(new Sphere(new Vec3(0.0f,-1000.0f,0.0f), 1000.0f, new Lambertian(new ConstantTexture(new Vec3(0.5f, 0.5f, 0.5f)))));
        //定义三大球
        objList.add(new Sphere(new Vec3(0, 1, 0), 1.0f, new Dielectric(1.5f)));
        objList.add(new Sphere(new Vec3(-4, 1, 0), 1.0f, new Lambertian(new ConstantTexture(new Vec3(0.4f, 0.2f, 0.1f)))));
        objList.add(new Sphere(new Vec3(4, 1, 0), 1.0f, new Metal(new ConstantTexture(new Vec3(0.7f, 0.6f, 0.5f)), 0.0f)));

        //生成地面小球
        int i = 1;
        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                /*两个for循环中会产生（11+11）*(11+11)=484个随机小球*/
                float choose_mat = (float)Math.random();
                /*产生一个（0，1）的随机数，作为设置小球的材质的阀值*/
                Vec3 center = new Vec3((float)( a+0.9*(Math.random()) ), 0.2f, (float) ( b+0.9*(Math.random() )));
                /*球心的x,z坐标散落在是（-11，11）之间的随机数*/
                if ((center.Subtract(new Vec3(4,0.2f,0))).length() > 0.9) {
                    /*避免小球的位置和最前面的大球的位置太靠近*/
                    if (choose_mat < 0.8) {     //diffuse
                        /*材料阀值小于0.8，则设置为漫反射球，漫反射球的衰减系数x,y,z都是（0，1）之间的随机数的平方*/
                        objList.add(
                                new Sphere(center, 0.2f, new Lambertian( new ConstantTexture(
                                        new Vec3((float)( (Math.random())*(Math.random()) ),
                                                (float)( (Math.random())*(Math.random()) ),
                                                (float)( (Math.random())*(Math.random()) )))
                                ))
                        );
                    }
                    else if (choose_mat < 0.95) {
                        /*材料阀值大于等于0.8小于0.95，则设置为镜面反射球，镜面反射球的衰减系数x,y,z及模糊系数都是（0，1）之间的随机数加一再除以2*/
                        objList.add(
                                new Sphere(center, 0.2f, new Metal( new ConstantTexture(
                                        new Vec3((float)( 0.5f*(1+(Math.random())) ), (float)( 0.5f*(1+(Math.random())) ), (float)( 0.5f*(1+(Math.random()))) )),
                                        (float)( 0.5*(1+(Math.random())))
                                ))
                        );
                    }
                    else {
                        /*材料阀值大于等于0.95，则设置为介质球*/
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

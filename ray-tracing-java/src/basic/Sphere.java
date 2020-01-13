package basic;


import javafx.util.Pair;
import materials.Material;

public class Sphere implements Hitable{
    private Vec3 center;
    private float radius;
    private Material material;

    public Sphere(Vec3 cen, float r,Material mat)
    {
        center = cen;
        radius = r;
        material = mat;
    }

    /**
     * 判断与球体是否有撞击
     * @param r 光线
     * @param t_min 范围
     * @param t_max 范围
     * @param rec 撞击点
     * @return 是否有撞击
     */
    @Override
    public boolean hit(Ray r, float t_min, float t_max, HitRecord rec) {
        Vec3 oc = r.origin().Subtract(center);
        float a = r.direction().dot(r.direction());
        float b = oc.dot(r.direction());
        float c = oc.dot(oc) - radius * radius;
        float discriminant = b*b-a*c;
        if(discriminant > 0){
            float fac = (float)Math.sqrt(discriminant);
            float tmp = (-b-fac)/a;
            if(t_min < tmp && tmp < t_max)
            {
                rec.t = tmp;
                rec.p = r.pointAtParameter(rec.t);
                rec.norm = rec.p.Subtract(center).Scale(1.0f/radius);
                rec.mat = this.material;
                Pair<Float,Float> p = getSphereUV(rec.norm);
                rec.u = p.getKey();
                rec.v = p.getValue();
                return true;
            }
            tmp = (-b+fac)/a;
            if(t_min < tmp && tmp < t_max)
            {
                rec.t = tmp;
                rec.p = r.pointAtParameter(rec.t);
                rec.norm = rec.p.Subtract(center).Scale(1.0f/radius);
                rec.mat = material;
                Pair<Float,Float> p = getSphereUV(rec.norm);
                rec.u = p.getKey();
                rec.v = p.getValue();
                return true;
            }
        }
        return false;
    }

    private Pair<Float,Float> getSphereUV(Vec3 p)
    {
        float phi = (float) Math.atan2(p.z(),p.x());
        float theta = (float) Math.asin(p.y());
        return  new Pair<>( (float) (1-(phi+Math.PI)/(2*Math.PI)),(float) ((theta+Math.PI/2)/Math.PI));
    }
}

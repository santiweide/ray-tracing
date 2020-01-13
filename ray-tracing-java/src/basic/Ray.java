package basic;


public class Ray {
    private Vec3 o;
    private Vec3 d;

    public Ray(){}
    public Ray(Vec3 origin,Vec3 direction){o = origin;d = direction;}
    public Vec3 origin(){return o;}
    public Vec3 direction(){return d;}
    public Vec3 pointAtParameter(float t){return o.Plus(d.Scale(t));}
}

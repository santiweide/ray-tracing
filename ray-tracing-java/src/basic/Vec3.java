package basic;

public class Vec3 {
    public float[] e = new float[3];
    public Vec3(float x,float y,float z)
    {
        e[0] = x;e[1] = y;e[2] = z;
    }

    public Vec3() {
        e[0] = e[1] = e[2] = 0;
    }

    public float x(){return e[0];}
    public float y(){return e[1];}
    public float z(){return e[2];}
    public Vec3 Scale(float t) {
        return new Vec3(e[0]*t,e[1]*t,e[2]*t);
    }

    public Vec3 Plus(Vec3 v) {
        return new Vec3(e[0] + v.e[0], e[1] + v.e[1], e[2] + v.e[2]);
    }
    public Vec3 Subtract(Vec3 v) {
        return new Vec3(e[0] - v.e[0], e[1] - v.e[1], e[2] - v.e[2]);
    }
    public float dot(Vec3 v) {
        return e[0]* v.e[0] + e[1] * v.e[1] + e[2] * v.e[2];
    }

    public float length()
    {
        return (float) Math.sqrt(e[0]*e[0]+e[1]*e[1]+e[2]*e[2]);
    }
    public Vec3 unit() {
        float len = length();
        return new Vec3(e[0]/len,e[1]/len,e[2]/len);
    }
    public Vec3 cross(Vec3 v)
    {
        return new Vec3(e[1] * v.z() - e[2] * v.y(),
                e[2] * v.x() - e[0] * v.z() ,
                e[0] * v.y() - e[1] * v.x());
    }
    public Vec3 Multiply(Vec3 v) {
        return new Vec3(e[0]*v.e[0], e[1]*v.e[1], e[2]*v.e[2]);
    }
    public String toString()
    {
        return "["+e[0]+","+e[1]+","+e[2]+"]";
    }
}

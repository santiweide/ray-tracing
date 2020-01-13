package materials;

import basic.HitRecord;
import basic.Ray;
import basic.Vec3;

public abstract class Material {
    public abstract boolean scatter(Ray r, HitRecord rec, Emergent emergent);

    public Vec3 random_in_unit_sphere()
    {
        Vec3 p;
        do{
            //随机坐标 区间[-1,+1]
            p =new Vec3((float)(Math.random()), (float)(Math.random()), (float)(Math.random())).Scale(2.0f).Subtract(new Vec3(1.0f, 1.0f, 1.0f));
        }while (p.dot(p) >= 1.0f);
        return p;
    }
}

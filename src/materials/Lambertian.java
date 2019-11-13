package materials;

import basic.HitRecord;
import basic.Ray;
import basic.Vec3;

public class Lambertian extends Material{
    private Vec3 albedo;    //
    public Lambertian(Vec3 a)
    {
        albedo = a;
    }

    /**
     *
     * @param r         入射光线
     * @param rec       入射点
     * @param emergent  封装反射光线信息，包括反射光线&衰减系数
     * @return
     */
    @Override
    public boolean scatter(Ray r, HitRecord rec, Emergent emergent) {
        Vec3 target = rec.p.Plus(rec.norm).Plus(random_in_unit_sphere());
        emergent.scattered = new Ray(rec.p, target.Subtract(rec.p));
        emergent.attenuation = albedo;
        return true;
    }
}

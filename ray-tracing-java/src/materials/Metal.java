package materials;

import basic.HitRecord;
import basic.Ray;
import basic.Vec3;
import textures.Texture;

public class Metal extends Material {
    private Texture albedo;    //镜面反射最终颜色
    private float fuzz;
    public Metal(Texture a,float f)
    {
        this.albedo = a;
        if(f < 1)
            fuzz = f;
        else fuzz = 1;
    }

    /**
     *
     * @param r         入射光线
     * @param rec       入射点
     * @param emergent    反射光线配置
     * @return
     */
    public boolean scatter(Ray r, HitRecord rec, Emergent emergent)
    {
        Vec3 reflected = reflect(r.direction(),rec.norm.unit());
        emergent.scattered = new Ray(rec.p,reflected.Plus(random_in_unit_sphere().Scale(fuzz)));//向量起点+一个广义的向量
        emergent.attenuation = albedo.Value(rec.u,rec.v,rec.p);//再给他一个材料系数
        return (reflected.dot(rec.norm) > 0);
    }
    public static Vec3 reflect(Vec3 v,Vec3 n)
    {
        return v.Subtract(n.Scale(2.0f*v.dot(n)));
    }

}

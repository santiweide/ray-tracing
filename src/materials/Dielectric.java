package materials;

import basic.HitRecord;
import basic.Ray;
import basic.Vec3;

public class Dielectric extends Material {
    private float ref_idx;

    public Dielectric(float f) {
        ref_idx = f;
    }

    /**
     *
     * @param r         入射光线
     * @param rec       入射点
     * @param emergent    出射光线信息
     * @return
     */
    @Override
    public boolean scatter(Ray r, HitRecord rec, Emergent emergent) {
        Vec3 outnorm;
        float niOverNt;
        float reflect_prob;
        float cosine;

        if (r.direction().dot(rec.norm) > 0) {
            outnorm = rec.norm.Scale(-1);
            niOverNt =ref_idx;
            cosine = ref_idx * r.direction().dot(rec.norm) / r.direction().length();
        }
        else {
            outnorm = rec.norm;
            niOverNt = 1.0f / ref_idx;
            cosine = - ref_idx * r.direction().dot(rec.norm) / r.direction().length();
        }


        emergent.attenuation = new Vec3(1,1,1);
        if (refract(r.direction(), outnorm, niOverNt, emergent)) {
            reflect_prob = schlick(cosine,ref_idx);
        }
        else {
            reflect_prob = 1.0f;
        }

        if(Math.random() < reflect_prob) {
            Vec3 reflected = Metal.reflect(r.direction(), rec.norm);
            emergent.scattered = new Ray(rec.p, reflected);
        }
        else {
            emergent.scattered = new Ray(rec.p, emergent.refracted);
        }
        return true;
    }

    /**
     *
     * @param cosine
     * @param ref_idx
     * @return
     */
    public float schlick(float cosine, float ref_idx)
    {
        float r0 = (1-ref_idx)/(1+ref_idx);
        r0 = r0*r0;
        return (float) (r0+(1-r0)*Math.pow((1-cosine),5));
    }

    /**
     *
     * @param v             入射光线
     * @param n             法线
     * @param niOverNt      sin_r/sin_i
     * @param emergent      出射光线信息
     * @return
     */
    private boolean refract(Vec3 v,Vec3 n,float niOverNt, Emergent emergent)
    {
        Vec3 uv = v.unit();
        float cos_i = uv.dot(n);
        float discriminant = 1.0f-niOverNt*niOverNt*(1.0f - cos_i * cos_i);
        if(discriminant > 0.0f)
        {
            float cos_r = (float) Math.sqrt(discriminant);
            emergent.refracted = uv.Scale(niOverNt).Plus(n.Scale(-cos_r - niOverNt*cos_i));
            return true;
        }
        return false;
    }
}

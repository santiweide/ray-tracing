package materials;

import basic.Ray;
import basic.Vec3;
import sun.security.krb5.Config;

public class Emergent {
    Ray scattered;          //反射光线
    Vec3 refracted;          //折射光线
    Vec3 attenuation;       //材料颜色
    public Emergent()
    {
        scattered = new Ray();
        refracted = new Vec3();
        attenuation = new Vec3();
    }
}

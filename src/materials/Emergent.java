package materials;

import basic.Ray;
import basic.Vec3;
import sun.security.krb5.Config;
import textures.Texture;

public class Emergent {
    public Ray scattered;          //反射光线
    public Vec3 refracted;          //折射光线
    public Vec3 attenuation;       //材料颜色
    public Emergent()
    {
        scattered = new Ray();
        refracted = new Vec3();
    }
}

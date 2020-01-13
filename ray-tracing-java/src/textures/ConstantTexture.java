package textures;

import basic.Vec3;
import sun.security.krb5.Config;

public class ConstantTexture implements Texture {
    private Vec3 color;
    public ConstantTexture(Vec3 c)
    {
        color = c;
    }
    @Override
    public Vec3 Value(float u, float v, Vec3 p) {
        return color;
    }
}

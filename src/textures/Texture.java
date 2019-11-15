package textures;

import basic.Vec3;

public interface Texture {
    public Vec3 Value(float u, float v, Vec3 p);
}

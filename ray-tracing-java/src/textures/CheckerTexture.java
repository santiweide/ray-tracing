package textures;

import basic.Vec3;

public class CheckerTexture implements Texture {
    private Texture odd;
    private Texture even;

    public CheckerTexture(Texture t0,Texture t1)
    {
        even = t0;
        odd = t1;
    }

    @Override
    public Vec3 Value(float u, float v, Vec3 p) {
        float sines = (float) (Math.sin(10 * p.x()) * Math.sin(10 * p.y()) * Math.sin(10 * p.z()));
        if(sines < 0)
        {
            return  odd.Value(u,v,p);
        }
        else
        {
            return  even.Value(u,v,p);
        }
    }
}

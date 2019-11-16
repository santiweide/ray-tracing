package textures;

import basic.Vec3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageTexture implements Texture {
    private int mx;
    private int my;
    private int nx;
    private int ny;
    private ArrayList<Integer> data = new ArrayList<>();
    public ImageTexture(String picPath)
    {
        try {
            getImagePixel(picPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vec3 Value(float u, float v, Vec3 p) {
        int i = (int) (u  * nx);
        int j = (int)((1 - v) * ny - 0.001);
        if(i < mx)
            i = mx;
        if(j < my)
            j = my;
        if(i > nx -1 )
            i = nx - 1;
        if(j > ny - 1)
            j = ny - 1;
        float r = (float)(data.get(3*i+3*nx*j)/255.0);
        float g = (float)(data.get(3*i+3*nx*j + 1)/255.0);
        float b = (float)(data.get(3*i+3*nx*j + 2)/255.0);
        System.out.println(u+","+v+":"+data.get(3*i+3*nx*j)+","+data.get(3*i+3*nx*j+1)+","+data.get(3*i+3*nx*j+1));
        System.out.println(r+","+g+","+b);
        return new Vec3(r,g,b);
    }

    public void getImagePixel(String image) {
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nx = bi.getWidth();
        ny = bi.getHeight();
        mx = bi.getMinX();
        my = bi.getMinY();
        for (int i = mx; i < nx; i++) {
            for (int j = my; j < ny; j++) {
                int pixel = bi.getRGB(i, j);
                data.add((pixel & 0xff0000) >> 16);
                data.add((pixel & 0xff00) >> 8);
                data.add((pixel & 0xff));

            }
        }
    }

}

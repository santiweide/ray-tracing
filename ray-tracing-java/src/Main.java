import basic.Render;
import materials.MaterialRender;
import textures.TextureRendener;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String args[])
    {
        try {
            Render render = new MaterialRender();
            render.doRendering();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

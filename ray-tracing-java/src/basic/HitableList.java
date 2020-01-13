package basic;
import java.util.*;

public class HitableList implements Hitable{
    private List<Hitable> list;
    public HitableList(){}
    public HitableList(List<Hitable> l)
    {
        list = l;
    }

    /**
     *
     * @param r 光线
     * @param t_min
     * @param t_max
     * @param rec   撞击点
     * @return  某个球是否撞击
     */
    @Override
    public boolean hit(Ray r, float t_min, float t_max, HitRecord rec) {
        HitRecord tmpRec = new HitRecord();
        boolean ret = false;
        float closest = t_max;
        for(int i =0 ;i < list.size();i ++)
        {
            if(list.get(i).hit(r,t_min, closest,tmpRec))
            {
                ret = true;
                closest = tmpRec.t;
                //传地址所以不得不从头来过xxx
                rec.t = tmpRec.t;
                rec.norm = tmpRec.norm;
                rec.p = tmpRec.p;
                rec.mat = tmpRec.mat;
                rec.u = tmpRec.u;
                rec.v = tmpRec.v;
            }
        }
        return ret;
    }

}

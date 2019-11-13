package basic;

public interface Hitable {
    boolean hit(Ray r,float t_min,float t_max, HitRecord rec);
}

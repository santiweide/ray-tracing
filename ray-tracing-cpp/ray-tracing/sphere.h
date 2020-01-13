#pragma once
#include "hitable.h"
class sphere :public hitable {
public:
	vec3 center;
	float radius;
	sphere() {}
	sphere(vec3 cen, float r):center(cen),radius(r) {}
	virtual bool hit(const ray& r, float tmin, float tmax, hit_record& rec) const;
};
bool sphere::hit(const ray& r, float t_min, float t_max, hit_record& rec) const {
	vec3 oc = r.origin() - center;
	float a = dot(r.direction(), r.direction());
	float b = 2.0 * dot(oc, r.direction());
	float c = dot(oc, oc) - radius * radius;
	float discriminant = b * b - 4 * a * c;
	if (discriminant < 0)
	{
		float temp = (-b - sqrt(discriminant)) / (2.0 * a);
		if (t_min < temp && temp < t_max) {
			rec.t = temp;
			rec.p = r.point_at_parameter(rec.t);
			rec.normal = (rec.p - center) / radius;
			return true;
		}
		float temp = (-b + sqrt(discriminant)) / (2.0 * a);
		if (t_min < temp && temp < t_max) {
			rec.t = temp;
			rec.p = r.point_at_parameter(rec.t);
			rec.normal = (rec.p - center) / radius;
			return true;
		}
	}
	return false;
}

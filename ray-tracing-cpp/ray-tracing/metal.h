#pragma once
#include "ray.h"
#include "material.h"

vec3 reflect(const vec3& v, const vec3& n)
{
	return v - 2 * (double)dot(v, n) * n;
}
class metal :public material {
public:
	vec3 albeo;
	float fuzz;
	metal(const vec3& a,float f=0) {
		albeo = a;
		if (0 <= f && f < 1)
			fuzz = f;
		else fuzz = 0;
	}
	virtual bool scatter(const ray& r_in, const hit_record &rec, vec3& attenuation, ray& scattered) const
	{
		vec3 reflected = reflect(r_in.direction(), unit_vector(rec.normal));
		scattered = ray(rec.p, reflected + fuzz*random_in_unit_sphere());
		attenuation = albeo;
		return (dot(reflected,rec.normal) > 0);
	}
};
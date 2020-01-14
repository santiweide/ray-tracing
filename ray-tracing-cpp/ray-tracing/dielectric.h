#pragma once
#include "material.h"

bool refract(const vec3& v, const vec3& n, float ni_over_nt, vec3& refracted)
{
	vec3 uv = unit_vector(v);
	float cos_i = dot(uv, n);
	float discriminant = 1.0 - (double)ni_over_nt * ni_over_nt * (1 - (double)cos_i*cos_i);
	if (discriminant > 0)
	{
		float cos_r = sqrt(discriminant);
		refracted = ni_over_nt * uv + (-cos_r - ni_over_nt * cos_i)*n;
		return true;
	}
	return false;
}
float schlick(float cosine, float ref_idx)
{
	float r0 = (1 - ref_idx) / (1 + ref_idx);
	r0 = r0 * r0;
	return (float)(r0 + (1 - r0) * pow((1 - cosine), 5));
}
class dielectric : public material {
public:
	float ref_idx;
	dielectric(float ri) :ref_idx(ri) {}

	virtual bool scatter(const ray& r_in, const hit_record& rec, vec3& attenuation, ray& scattered) const
	{
		vec3 outward_normal;
		float ni_over_nt;
		float cosine;
		float reflect_prob;

		if (dot(r_in.direction(), rec.normal) > 0){
			outward_normal = -rec.normal;
			ni_over_nt = ref_idx;
			cosine = ref_idx * dot(r_in.direction(), rec.normal) / r_in.direction().length();
		}
		else{
			outward_normal = rec.normal;
			ni_over_nt = 1/ref_idx;
			cosine = - ref_idx * dot(r_in.direction(), rec.normal) / r_in.direction().length();
		}
		attenuation = vec3(1, 1, 1);
		vec3 refracted;
		vec3 reflected = reflect(r_in.direction(), rec.normal);
		if (refract(r_in.direction(), outward_normal, ni_over_nt, refracted)) {
			reflect_prob = schlick(cosine, ref_idx);
		}
		else {
			reflect_prob = 1.0f;
		}

		if (rand()/(float)RAND_MAX < reflect_prob) {
			vec3 reflected = reflect(r_in.direction(), rec.normal);
			scattered = ray(rec.p, reflected);
		}
		else {
			scattered = ray(rec.p,refracted);
		}
		return true;

	}
};
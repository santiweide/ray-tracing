#pragma once
#include "hitable.h"

struct hit_record;
class material {
public:
	virtual bool scatter(const ray& r_in, const hit_record &rec, vec3& attenuation, ray& scattered) const = 0;
};
vec3 reflect(const vec3& v, const vec3& n)
{
	return v - 2 * (double)dot(v, n) * n;
}


#pragma once
#include "ray.h"
bool refract(const vec3& v ,const vec3& n, float ni_over_nt, vec3& refracted)
{
	vec3 uv = unit_vector(v);
}
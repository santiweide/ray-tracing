#pragma once
#include "ray.h"
#define PI 3.1415926535

vec3 random_in_unit_disk()
{
	vec3 p;
	do {
		p = 2.0 * vec3(rand() / (float)RAND_MAX, rand() / (float)RAND_MAX, 0) - vec3(1, 1, 0);
	} while (dot(p, p) >= 1.0);
	return p;
}
class camera {
public:
	vec3 lower_left_corner;
	vec3 origin;
	vec3 horizontal;
	vec3 vertical;
	float lens_radius;
	vec3 u,v,w;
	camera() {
		lower_left_corner = vec3(-2.0, -1.0, -1.0);
		horizontal = vec3(4.0, 0.0, 0.0);
		vertical = vec3(0.0, 2.0, 0.0);
		origin = vec3(0, 0, 0);
	}
	camera(float vfov, float aspect)
	{
		float theta = vfov * PI / 180.0;
		float half_height = tan(theta/2);
		float half_width = aspect * half_height;
		lower_left_corner = vec3(-half_width, -half_height, -1.0);
		horizontal = vec3(2 * half_width, 0.0, 0.0);
		vertical = vec3(0.0, 2 * half_height, 0.0);
		origin = vec3(0.0, 0.0, 0.0);
	}
	camera(vec3 lookfrom, vec3 lookat, vec3 vup, float vfov, float aspect)
	{
		float theta = vfov * PI / 180.0;
		float half_height = tan(theta / 2);
		float half_width = aspect * half_height;
		
		origin = lookfrom;
		vec3 w = unit_vector(lookfrom - lookat);
		vec3 u = unit_vector(cross(vup, w));
		vec3 v = cross(w,u);
		
		lower_left_corner = origin - half_width * u - half_height * v - w;;
		horizontal = 2 * half_width * u;
		vertical = 2 * half_height * v;

	}

	camera(vec3 lookfrom, vec3 lookat, vec3 vup, float vfov, float aspect,float aperture, float focus_dist)
	{
		lens_radius = aperture / 2;
		float theta = vfov * PI / 180.0;
		float half_height = tan(theta / 2);
		float half_width = aspect * half_height;

		origin = lookfrom;
		w = unit_vector(lookfrom - lookat);
		u = unit_vector(cross(vup, w));
		v = cross(w, u);

		lower_left_corner = origin - half_width*focus_dist * u - half_height * focus_dist * v - focus_dist*w;;
		horizontal = 2 * half_width * focus_dist * u;
		vertical = 2 * half_height * focus_dist * v;

	}
	ray get_ray(float s, float t) {
		vec3 rd = lens_radius * random_in_unit_disk();
		vec3 offset = rd.x()*u + rd.y()*v;
		return ray(origin + offset, lower_left_corner + s * horizontal + t * vertical - origin - offset);
	}
};
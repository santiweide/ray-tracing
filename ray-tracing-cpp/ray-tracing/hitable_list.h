#pragma once
#include "hitable.h"

static vec3 random_in_unit_sphere()
{
	vec3 p;
	do {
		p = 2.0 * vec3(rand() / double(RAND_MAX), rand() / double(RAND_MAX), rand() / double(RAND_MAX)) - vec3(1, 1, 1);
	} while (dot(p, p) >= 1.0);
	return p;
}
class hitable_list:public hitable {
public:
	hitable ** list;
	int list_size;
	hitable_list() {}
	hitable_list(hitable** l, int n){ list = l; list_size = n; }
	virtual bool hit(const ray& r, float t_min, float t_max, hit_record& rec)const;
};

bool hitable_list::hit(const ray& r, float t_min, float t_max, hit_record& rec) const
{
	hit_record temp_rec;
	bool ret = false;
	double closest_so_far = t_max;
	for (int i = 0; i < list_size;i++)
	{
		if (list[i]->hit(r, t_min, closest_so_far, temp_rec))
		{
			ret = true;
			closest_so_far = temp_rec.t;
			rec = temp_rec;
		}
	}
	return ret;
}

#include <cstdio>
#include <float.h>

#include "sphere.h"
#include "hitable_list.h"
#include "camera.h"

vec3 color(const ray& r, hitable * world) {
	hit_record rec;
	if (world->hit(r, 0.0, FLT_MAX, rec)) {
		return 0.5 * vec3(rec.normal.x() + 1, rec.normal.y() + 1, rec.normal.z() + 1);
	}
	vec3 unit_direction = r.direction().unit_vector();
	float t = 0.5 * (unit_direction.y() + 1.0);
	return (1.0 - t) * vec3(1.0, 1.0, 1.0) + t * vec3(0.5, 0.7, 1.0);
}
int main()
{
	int nx = 200;
	int ny = 100;
	int ns = 100;
	FILE* stream;
	freopen_s(&stream, "2.ppm", "w", stdout);
	printf("P3\n%d %d\n255\n", nx, ny);
	vec3 lower_left_corner;
	vec3 origin;
	vec3 horizontal;
	vec3 vertical;
	lower_left_corner = vec3(-2.0, -1.0, -1.0);
	horizontal = vec3(4.0, 0.0, 0.0);
	vertical = vec3(0.0, 2.0, 0.0);
	origin = vec3(0, 0, 0);
	
	hitable* list[2];
	list[0] = new sphere(vec3(0, 0, -1), 0.5);
	list[1] = new sphere(vec3(0, -100.5, -1), 100);
	hitable* world = new hitable_list(list, 2);
	for (int j = ny - 1; j >= 0; j--) {
		for (int i = 0; i < nx; i++) {
			vec3 col(0, 0, 0);
			float u = float(i) / float(nx);
			float v = float(j) / float(ny);
			ray r = ray(origin, lower_left_corner + u * horizontal + v * vertical - origin);	
			col = color(r, world);
			int ir = int(255.99 * col[0]);
			int ig = int(255.99 * col[1]);
			int ib = int(255.99 * col[2]);
			printf("%d %d %d\n", ir, ig, ib);
		}
	}
	fclose(stdout);
	return 0;
}

int main_c()
{
	int nx = 200;
	int ny = 100;
	int ns = 100;
	FILE* stream;
	freopen_s(&stream,"2.ppm","w",stdout);
	printf("P3\n%d %d\n255\n",nx,ny);
	hitable* list[2];
	list[0] = new sphere(vec3(0,0,-1),0.5);
	list[1] = new sphere(vec3(0, -100.5, -1), 100);
	hitable* world = new hitable_list(list, 2);
	camera cam;
	for (int j = ny - 1; j >= 0; j--) {
		for (int i = 0; i < nx; i++) {
			vec3 col(0, 0, 0);
			for (int s = 0; s < ns; s++)
			{
				float u = float(i + rand()) / float(nx);
				float v = float(j + rand()) / float(ny);
				ray r = cam.get_ray(u,v);
				vec3 p = r.point_at_parameter(2.0);
				col = col + color(r, world);
			}
			col = col / float(ns);
			int ir = int(255.99 * col[0]);
			int ig = int(255.99 * col[1]);
			int ib = int(255.99 * col[2]);
			printf("%d %d %d\n",ir,ig,ib);
		}
	}
	fclose(stdout);
	return 0;
}


#pragma once
#include <cmath>
class vec3
{
public:
	float e[3];
	vec3() {}
	vec3(float e0 , float e1, float e2) 
	{
		e[0] = e0; e[1] = e1; e[2] = e2;
	}
	inline float x() const { return e[0]; }
	inline float y() const { return e[1]; }
	inline float z() const { return e[2]; }
	inline float operator[](int i) const {
		return e[i];
	}
	inline float& operator[](int i) {
		return e[i];
	}

	inline friend vec3 operator+(const vec3& a, const vec3& b) {
		return vec3(a[0]+b[0],a[1]+b[1],a[2]+b[2]);
	}
	inline friend vec3 operator-(const vec3& a, const vec3& b) {
		return vec3(a[0] - b[0], a[1] - b[1], a[2] - b[2]);
	}

	inline friend float dot(const vec3& a, const vec3& b)
	{
		return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
	}
	inline friend vec3 operator*(double t,const vec3& v) {
		return vec3(t*v[0], t * v[1], t * v[2]);
	}
	inline friend vec3 operator/( const vec3& v, double t) {
		return vec3(v[0]/t, v[1]/t, v[2]/t);
	}

	inline double length() {
		return sqrt(e[0] * e[0] + e[1] * e[1] + e[2] * e[2]);
	}
	inline vec3 unit_vector()
	{
		double len = length();
		return vec3(float(e[0] / len),float(e[1] / len),float(e[2] / len));
	}
}; 


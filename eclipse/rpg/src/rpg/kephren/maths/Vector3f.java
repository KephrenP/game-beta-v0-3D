package rpg.kephren.maths;

public class Vector3f {
	
	/*
	 * La classe Vecteur
	 */

	private float x, y, z;

	public Vector3f() {
		this(0, 0, 0);
	}

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f(Vector3f v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	/*
	 * Pythagore 3D
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/*
	 * operations vectoriels
	 */
	public Vector3f normalize() {
		x /= length();
		y /= length();
		z /= length();

		return this;
	}

	public Vector3f add(Vector3f vec) {
		x += vec.getX();
		y += vec.getY();
		z += vec.getZ();

		return this;
	}

	public Vector3f sub(Vector3f vec) {
		x -= vec.getX();
		y -= vec.getY();
		z -= vec.getZ();

		return this;
	}

	public Vector3f mul(Vector3f vec) {
		x *= vec.getX();
		y *= vec.getY();
		z *= vec.getZ();

		return this;
	}

	public Vector3f div(Vector3f vec) {
		x /= vec.getX();
		y /= vec.getY();
		z /= vec.getZ();

		return this;
	}

	// G/S X
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public Vector3f addX(float f) {
		x += f;
		return this;
	}

	public Vector3f subX(float f) {
		x -= f;
		return this;
	}

	// G/S Y
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Vector3f addY(float f) {
		y += f;
		return this;
	}

	public Vector3f subY(float f) {
		y -= f;
		return this;
	}

	// G/S Z
	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public Vector3f addZ(float f) {
		z += f;
		return this;
	}

	public Vector3f subZ(float f) {
		z -= f;
		return this;
	}
}

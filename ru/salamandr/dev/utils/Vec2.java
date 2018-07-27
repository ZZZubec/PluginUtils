package ru.salamandr.dev.utils;

public class Vec2 {
	
	public static final Vec2 zero = new Vec2(0,0);
	public float x,y;
	
	public Vec2( float x, float y )
	{
		this.x = x;
		this.y = y;
	}

	public static float dst(float x3, float y3, float x2, float y2) {
		return Math.abs(x2-x3) + Math.abs(y2-y3);
	}

	public static float dst(Vec2 vec1, Vec2 vec2) {
		return dst(vec1.x,vec2.x,vec1.y,vec2.y);
	}

    public float len2 () {
        return x * x + y * y;
    }

    public Vec2 scl (float scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public Vec2 clamp (float min, float max) {
        final float len2 = len2();
        if (len2 == 0f) return this;
        float max2 = max * max;
        if (len2 > max2) return scl((float)Math.sqrt(max2 / len2));
        float min2 = min * min;
        if (len2 < min2) return scl((float)Math.sqrt(min2 / len2));
        return this;
    }

    public float len () {
        return (float)Math.sqrt(x * x + y * y);
    }

    public Vec2 nor () {
        float len = len();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }
    
    public Vec2 sub (Vec2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}
    
    /** Substracts the other vector from this vector.
	 * @param x The x-component of the other vector
	 * @param y The y-component of the other vector
	 * @return This vector for chaining */
	public Vec2 sub (float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	/** @return the angle in degrees of this vector (point) relative to the x-axis. Angles are towards the positive y-axis (typically
	 *         counter-clockwise) and between 0 and 360. */
	public float angle () {
		float angle = (float)Math.atan2(y, x) * 3.14f;
		if (angle < 0) angle += 360;
		return angle;
	}

	/** @return the angle in degrees of this vector (point) relative to the given vector. Angles are towards the positive y-axis
	 *         (typically counter-clockwise.) between -180 and +180 */
	public float angle (Vec2 reference) {
		return (float)Math.atan2(crs(reference), dot(reference)) * 3.14f;
	}
	
	/** Calculates the 2D cross product between this and the given vector.
	 * @param v the other vector
	 * @return the cross product */
	public float crs (Vec2 v) {
		return this.x * v.y - this.y * v.x;
	}

	/** Calculates the 2D cross product between this and the given vector.
	 * @param x the x-coordinate of the other vector
	 * @param y the y-coordinate of the other vector
	 * @return the cross product */
	public float crs (float x, float y) {
		return this.x * y - this.y * x;
	}
	
	public float dot (Vec2 v) {
		return x * v.x + y * v.y;
	}

	public float dot (float ox, float oy) {
		return x * ox + y * oy;
	}
}

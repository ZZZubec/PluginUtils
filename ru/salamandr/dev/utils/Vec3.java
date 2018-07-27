package ru.salamandr.dev.utils;

public class Vec3 {
	
	public static final Vec3 zero = new Vec3(0,0,0);
	public float x,y,z;

    public Vec3( float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(int x, int z)
    {
        this.x = x;
        this.y = 0;
        this.z = z;
    }

    public float Length()
    {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public static float dst( Vec3 vec1, Vec3 vec2 )
    {
        Vec3 vec = vec1.minus( vec2 );
        return (float)Math.sqrt(vec.x*vec.x + vec.y * vec.y + vec.z * vec.z);
    }

    public Vec3 Normalize()
    {
        float len = Length();
        x /= len;
        y /= len;
        z /= len;
        return this;
    }

    public Vec3 normalized()
    {
        Vec3 vec = new Vec3(x, y, z);
        return vec.Normalize();
    }

    public static float dot( Vec3 a, Vec3 b )
    {
        return (a.x * b.x + a.y * b.y + a.z * b.z);
    }

    /*
    public static Vec3 operator -(Vec3 vec1, Vec3 vec2)
    {
        Vec3 vec = new Vec3(0,0,0);

        if (vec1.x >= vec2.x)
            vec.x = vec1.x - vec2.x;
        else
            vec.x = vec2.x - vec1.x;

        if (vec1.y >= vec2.y)
            vec.y = vec1.y- vec2.y;
        else
            vec.y = vec2.y - vec1.y;

        if (vec1.z >= vec2.z)
            vec.z = vec1.z - vec2.z;
        else
            vec.z = vec2.z - vec1.z;

        return vec;
    }
    */
    
    public Vec3 plus(final Vec3 vec_p) {
    	Vec3 vec = new Vec3(x+vec_p.x, y+vec_p.y, z+vec_p.z);
        return vec;
    }
    
    public Vec3 minus(final Vec3 vec_p) {
    	Vec3 vec = new Vec3(x-vec_p.x, y-vec_p.y, z-vec_p.z);
        return vec;
    }
	
	public String toString() {
		return "(" + x + "," + y + "," + z + ")" ;
	}
}

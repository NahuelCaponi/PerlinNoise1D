package com.caponinahuel.demo.framework.core.math;

public final class FastMath {
	public static final float PI = 3.141592653589793238f;
	public static final float PI_DOUBLE = PI * 2;
	public static final float PI_2 = PI / 2;
	public static final float PI_4 = PI / 4;
	public static final float angle45 = PI_4;
	public static final float angle90 = PI_2;
	public static final float angle180 = PI;
	public static final float angle270 = PI+angle90;
	public static final float angle360 = PI_DOUBLE;
	public static final float toDegrees = 180 / PI;
	public static final float toRadian = PI / 180;
	public static final float floatsTolerance = 0.000001f;

	private static final float PI3_4 = 3.0f * PI_4;

	public static float abs(float v) {
		return v > 0 ? v : -v;
	}

	public static float atan2(float y, float x) {
		float r;
		float angle;
		float abs_y = abs(y);
		if (x < 0.0) {
			r = (x + abs_y) / (abs_y - x);
			angle = PI3_4;
		} else {
			r = (x - abs_y) / (x + abs_y);
			angle = PI_4;
		}
		angle += (0.1963 * r * r - 0.9817) * r;

		return y < 0 ? -angle : angle;
	}

	public static float sin(float x) {
		x = normalizeAngle(x);

		float sin;
		if (x < 0)
			sin = 1.27323954f * x + 0.405284735f * x * x;
		else
			sin = 1.27323954f * x - 0.405284735f * x * x;

		if (sin < 0)
			sin = 0.225f * (sin * -sin - sin) + sin;
		else
			sin = 0.225f * (sin * sin - sin) + sin;

		return sin;
	}

	public static float cos(float x) {
		return sin(x + FastMath.PI_2);
	}

	/**
	 * Use to compare two floats without caring about float point inaccuracy.
	 * 
	 * Tolerance of how much inaccuracy is acceptable can be set in FastMath.floatsRange
	 */
	public static boolean equals(float f1, float f2) {
		return f1 < f2 + floatsTolerance && f1 > f2 - floatsTolerance;
	}

	/**
	 * Use to compare two floats without caring about float point inaccuracy.
	 * 
	 * Tolerance of how much inaccuracy is acceptable can be set in FastMath.floatsRange
	 */
	public static boolean equals(float f1, float f2, float tolerance) {
		return f1 < f2 + tolerance && f1 > f2 - tolerance;
	}

	/**
	 * Use to compare a float with zero, without caring about float point inaccuracy.
	 * 
	 * Tolerance of how much inaccuracy is acceptable can be set in FastMath.floatsRange
	 */
	public static boolean isZero(float f) {
		return f < 0 + floatsTolerance && f > 0 - floatsTolerance;
	}

	/**
	 * Returns the sign of the value
	 */
	public static int sign(float x) {
		return x < 0 ? -1 : 1;
	}/**
	 * Returns the sign of the value
	 */
	public static int sign(long x) {
		return x < 0 ? -1 : 1;
	}
	/**
	 * Fix the module giving negative numbers
	 */
	public static float realModule(float value, float mod) {
		float m = value % mod;
		if (m < 0)
			m += mod;

		return m;
	}

	/**
	 * Fix the module giving negative numbers
	 */
	public static int realModule(int value, int mod) {
		int m = value % mod;
		if (m < 0)
			m += mod;

		return m;
	}
	/**
	 * Fix the module giving negative numbers
	 */
	public static long realModule(long value, long mod) {
		long m = value % mod;
		if (m < 0)
			m += mod;

		return m;
	}

	public static float limit(float f, float max) {
		return f > max ? max : f;
	}

	public static float lowerLimit(float f, float min) {
		return f < min ? min : f;
	}

	public static float clamp(float f, float min, float max) {
		return f < min ? min : (f > max ? max : f);
	}

	public static int clamp(int f, int min, int max) {
		return f < min ? min : (f > max ? max : f);
	}

	public static float normalizeAngle(float angle) {
		float range = PI_DOUBLE;

		while (angle <= -PI)
			angle += range;
		while (angle >= PI)
			angle -= range;

		return angle;
	}

	/**
	 * When value touches max, it starts at min again When value touches min, it starts at max again
	 */
	public static float wrap(float value, float min, float max) {
		float range = max - min;

		while (value <= min)
			value += range;
		while (value >= max)
			value -= range;

		return value;
	}

	/**
	 * When value touches max, it starts at min again When value touches min, it starts at max again
	 */
	public static int wrap(int value, int min, int max) {
		float range = max - min;

		while (value <= min)
			value += range;
		while (value >= max)
			value -= range;

		return value;
	}

	/**
	 * Lerp transforms a [0, 1] range to [starts, ends]
	 * 
	 * Is the inverse of Normalize
	 */
	public static float lerp(float rate, float starts, float ends) {
		return starts + (ends - starts) * rate;
	}

	/**
	 * LerpInt transforms a [0, 1] range to [starts, ends]
	 */
	public static int lerp(float rate, int starts, int ends) {
		return (int) (starts + (ends - starts) * rate);
	}

	/**
	 * Normalize transforms a value inside of the range [starts, ends] into a percent [0, 1]
	 * 
	 * Is the inverse of Lerp
	 */
	public static float normalize(float value, float starts, float ends) {
		return (value - starts) / (ends - starts);
	}

	/**
	 * Map transforms a (min, max) range to [starts, ends]
	 */
	public static float map(float rate, float min, float max, float starts, float ends) {
		return lerp((rate - min) / (max - min), starts, ends);
	}

	/**
	 * MapInt transforms a [min, max] range to [starts, ends]
	 */
	public static int map(float rate, float min, float max, int starts, int ends) {
		return lerp((rate - min) / (max - min), starts, ends);
	}

	public static int max(int... values) {
		int max = values[0];

		for (int i = 0; i < values.length; i++) {
			int j = values[i];
			if (j > max)
				max = j;
		}

		return max;
	}

	public static float max(float... values) {
		float max = values[0];

		for (int i = 0; i < values.length; i++) {
			float j = values[i];
			if (j > max)
				max = j;
		}

		return max;
	}

}

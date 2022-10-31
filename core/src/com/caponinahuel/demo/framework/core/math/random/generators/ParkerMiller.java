package com.caponinahuel.demo.framework.core.math.random.generators;

import com.caponinahuel.demo.framework.core.math.FastMath;

public class ParkerMiller extends RandomGenerator {
	static final int modus = 0x7FFFFFFF;
	static final int multiply = 48271;

	@Override
	public void setSeed(String seed) {
		super.setSeed(seed);
		if (FastMath.abs(generator) == modus || generator == 0)
			generator -= 2;
	}

	/**
	 * Get a random float between (0, 1)
	 */
	@Override
	public float random() {
		generator = FastMath.realModule(multiply * generator, modus);
		return generator * 1.0f / modus;
	}

}

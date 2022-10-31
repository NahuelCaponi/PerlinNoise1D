package com.caponinahuel.demo.framework.core.math.random.shapers;

import com.caponinahuel.demo.framework.core.math.random.generators.RandomGenerator;

public class Gaussian {
	RandomGenerator generator;

	public Gaussian(RandomGenerator randomGenerator) {
		generator = randomGenerator;
	}

	public void setGenerator(RandomGenerator randomGenerator) {
		generator = randomGenerator;
	}

	public float random(int factor) {
		float rand = 0.0f;
		for (int i = 0; i < factor; i++) {
			rand += generator.random();
		}
		return rand / factor;
	}
}

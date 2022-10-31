package com.caponinahuel.demo.framework.core.math.random.generators;

public abstract class RandomGenerator {
	protected String seed;
	protected int generator;

	RandomGenerator() {
		setSeed("Lucifer");
	}

	public abstract float random();

	public void setSeed(String seed) {
		this.seed = seed;
		this.generator = seed.hashCode();
	}

	public String getSeed() {
		return seed;
	}
}

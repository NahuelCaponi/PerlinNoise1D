package com.caponinahuel.demo.framework.core.math.random.generators;

import com.badlogic.gdx.utils.IntArray;

public class MersenneTwister extends RandomGenerator {
	IntArray data;
	int index = 0;

	@Override
	public void setSeed(String seed) {
		if (this.seed == seed)
			return;
		super.setSeed(seed);

		data = new IntArray(624);
		data.setSize(624);
		data.set(624 - 1, 0);
		data.set(0, generator);
		for (int i = 1; i < 624; i++) {
			data.set(i, 0x6c078965 * (data.get(i - 1) ^ (data.get(i - 1 >> 30)) + i));
		}
	}

	private void generateNumbers() {
		for (int i = 0; i < 624; i++) {
			int y = (data.get(i) & 1) + (data.get((i + 1) % 624)) & 0x7fffffff;
			data.set(i, data.get((i + 397) % 624) ^ (y >> 1));
			if ((y % 2) != 0)
				data.set(i, data.get(i) ^ 0x9908b0df);
		}
	}

	/**
	 * Get a random float between (0, 1)
	 */
	@Override
	public float random() {
		if (index == 0)
			generateNumbers();
		int x = data.get(index);
		x = x ^ (x >> 11);
		x = x ^ ((x << 7) & (0x9d2c5680));
		x = x ^ ((x << 15) & (0xefc60000));
		x = x ^ (x >> 18);
		index = (index + 1) % 624;
		return x * 1.0f / 0x7ffffffe;
	}
}
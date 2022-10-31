package com.caponinahuel.demo.framework.core.math.random;

import com.badlogic.gdx.utils.Array;
import com.caponinahuel.demo.framework.core.math.FastMath;
import com.caponinahuel.demo.framework.core.math.random.generators.ParkerMiller;
import com.caponinahuel.demo.framework.core.math.random.generators.RandomGenerator;
import com.caponinahuel.demo.framework.core.math.random.shapers.Gaussian;
import com.caponinahuel.demo.framework.core.math.random.shapers.PerlinNoise;
import com.caponinahuel.demo.framework.core.math.random.shapers.SimplexNoise;

import java.util.function.Function;

public class Random {
    static RandomGenerator generator = new ParkerMiller(); // ParkerMiller - MersenneTwister
    static SimplexNoise simplex = new SimplexNoise(generator);
    static PerlinNoise perlin = new PerlinNoise(generator);
    static Gaussian gaussianGenerator = new Gaussian(generator);

    public static void setSeed(String seed) {
        generator.setSeed(seed);
    }

    public static String getSeed() {
        return generator.getSeed();
    }

    public static void randomizeSeed() {
        String randomSeed = String.valueOf((long) (Math.random() * Integer.MAX_VALUE));
        generator.setSeed(randomSeed);
        simplex.generateSet();
        perlin.generateSet();
    }

    public static float generate() {
        return generator.random();
    }

    public static boolean happens(float chance) {
        return generate() <= chance;
    }

    /**
     * Return a value betwen [0, max)
     */
    public static int getUpTo(int max) {
        return (int) (generator.random() * max);
    }

    /**
     * Return a value betwen (min, max)
     */
    public static float range(float min, float max) {
        return FastMath.lerp(generator.random(), min, max);
    }

    /**
     * Return a value betwen [min, max]
     */
    public static int range(int min, int max) {
        return FastMath.lerp(generator.random(), min, max);
    }

    /**
     * Get a random angle between [0 and 2π)
     */
    public static float angle() {
        return generator.random() * FastMath.angle360;
    }

    /**
     * Return a value betwen (0, 1) with a median of 0.5
     */
    public static float normalDistribution() {
        return normalDistribution(2);
    }

    /**
     * Return a value betwen (0, 1) with a median of 0.5
     * <p>
     * The factor affects how with the distribution is
     */
    public static float normalDistribution(int factor) {
        return gaussianGenerator.random(factor);
    }

    /**
     * Return a value between (-1 and 1)
     * The median is 0
     * The factor is how close to the common value it stays
     */
    public static float gaussian() {
        return gaussian(2);
    }

    /**
     * Return a value between (-1 and 1)
     * The median is 0
     * The factor is how close to the common value it stays
     */
    public static float gaussian(int factor) {
        return gaussianGenerator.random(factor) * 2 - 1;
    }

    /**
     * Return a value betwen (0, 1)
     */
    public static float noise(float x) {
        return perlin.noise(x, 0, 0);
    }

    /**
     * Return a value betwen (0, 1)
     */
    public static float noise(float x, float y) {
        return perlin.noise(x, y, 0);
    }

    /**
     * Return a value betwen (0, 1)
     */
    public static float noise(float x, float y, float z) {
        return perlin.noise(x, y, z);
    }

    /**
     * Return a value betwen (0, 1)
     */
    public static float noiseSmooth(float x) {
        return simplex.noise(x);
    }

    /**
     * Return a value betwen (0, 1)
     */
    public static float noiseSmooth(float x, float y) {
        return simplex.noise(x, y);
    }

    /**
     * Return a value betwen (0, 1)
     */
    public static float noiseSmooth(float x, float y, float z) {
        return simplex.noise(x, y, z);
    }

    /**
     * Get a random float between (0, 1) according to the probabilityDistribution function The void should be an equation in
     * the form of f(x) = x% (probability of x)
     */
    public static float customDistribution(Function<Float, Float> function) {
        float x;
        do {
            x = generate();
        } while (!happens(function.apply(x)));
        return x;
    }

    /**
     * It returns an element from the array according to the probabilities.
     * Probabilities should always add up to 1
     */
    public static <T> T fromArray(Array<T> arr, Array<Float> probabilities) {
        float r = generator.random();
        int accumulated = 0;
        for (int i = 0; i < probabilities.size; i++) {
            accumulated += probabilities.get(i);
            if (r < accumulated)
                return arr.get(i);
        }
        if (accumulated < 1) {
            throw new Error("The probabilities are not filled, to total probabilities accounts for " + accumulated);
        }
        return null; //Should never happen
    }
}
package com.caponinahuel.demo.framework.core.math.random.shapers;

import com.badlogic.gdx.utils.FloatArray;
import com.caponinahuel.demo.framework.core.math.FastMath;
import com.caponinahuel.demo.framework.core.math.random.generators.RandomGenerator;

public class PerlinNoise {
    static int PERLIN_YWRAPB = 4;
    static int PERLIN_YWRAP = 1 << PERLIN_YWRAPB;
    static int PERLIN_ZWRAPB = 8;
    static int PERLIN_ZWRAP = 1 << PERLIN_ZWRAPB;
    static int PERLIN_SIZE = 4095;
    static int perlin_octaves = 4; // default to medium smooth
    static float perlin_amp_falloff = 0.5f; // 50% reduction/octave

    FloatArray perlin;
    RandomGenerator generator;

    public PerlinNoise(RandomGenerator randomGenerator) {
        generator = randomGenerator;

        perlin = new FloatArray(PERLIN_SIZE + 1);
        perlin.setSize(PERLIN_SIZE + 1);
        generateSet();
    }

    public void generateSet() {
        for (int i = 0; i < PERLIN_SIZE + 1; i++) {
            perlin.set(i, this.generator.random());
        }
    }

    public float noise(float x, float y, float z) {
        if (perlin == null) {
            perlin = new FloatArray(PERLIN_SIZE + 1);
            perlin.setSize(PERLIN_SIZE + 1);
            for (int i = 0; i < PERLIN_SIZE + 1; i++) {
                perlin.set(i, generator.random());
            }
        }

        x = FastMath.abs(x);
        y = FastMath.abs(y);
        z = FastMath.abs(z);

        int xi = (int) x;
        int yi = (int) y;
        int zi = (int) z;

        float xf = x - xi;
        float yf = y - yi;
        float zf = z - zi;
        float rxf, ryf;

        float r = 0.0f;
        float ampl = 0.5f;

        float n1, n2, n3;

        for (int o = 0; o < perlin_octaves; o++) {
            int of = xi + (yi << PERLIN_YWRAPB) + (zi << PERLIN_ZWRAPB);

            rxf = scaledCosine(xf);
            ryf = scaledCosine(yf);

            n1 = perlin.get(of & PERLIN_SIZE);
            n1 += rxf * (perlin.get((of + 1) & PERLIN_SIZE) - n1);
            n2 = perlin.get((of + PERLIN_YWRAP) & PERLIN_SIZE);
            n2 += rxf * (perlin.get((of + PERLIN_YWRAP + 1) & PERLIN_SIZE) - n2);
            n1 += ryf * (n2 - n1);

            of += PERLIN_ZWRAP;
            n2 = perlin.get(of & PERLIN_SIZE);
            n2 += rxf * (perlin.get((of + 1) & PERLIN_SIZE) - n2);
            n3 = perlin.get((of + PERLIN_YWRAP) & PERLIN_SIZE);
            n3 += rxf * (perlin.get((of + PERLIN_YWRAP + 1) & PERLIN_SIZE) - n3);
            n2 += ryf * (n3 - n2);

            n1 += scaledCosine(zf) * (n2 - n1);

            r += n1 * ampl;
            ampl *= perlin_amp_falloff;
            xi <<= 1;
            xf *= 2;
            yi <<= 1;
            yf *= 2;
            zi <<= 1;
            zf *= 2;

            if (xf >= 1.0) {
                xi++;
                xf--;
            }
            if (yf >= 1.0) {
                yi++;
                yf--;
            }
            if (zf >= 1.0) {
                zi++;
                zf--;
            }
        }
        return r;
    }

    float scaledCosine(float i) {
        return (float) (0.5 * (1.0 - FastMath.cos(i * FastMath.PI)));
    }
}
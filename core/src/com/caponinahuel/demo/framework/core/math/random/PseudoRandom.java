package com.caponinahuel.demo.framework.core.math.random;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntFloatMap;

public class PseudoRandom {
    private static IntFloatMap table = new IntFloatMap();

    {
        table.put(5, 0.003801658304f);
        table.put(10, 0.01474584478f);
        table.put(15, 0.03222091437f);
        table.put(20, 0.05570404295f);
        table.put(25, 0.08474409185f);
        table.put(30, 0.1189491927f);
        table.put(35, 0.1579830981f);
        table.put(40, 0.2015474136f);
        table.put(45, 0.2493069984f);
        table.put(50, 0.3021030253f);
        table.put(55, 0.3603978509f);
        table.put(60, 0.4226497308f);
        table.put(65, 0.4811254783f);
        table.put(70, 0.5714285714f);
        table.put(75, 0.6666666667f);
        table.put(80, 0.75f);
        table.put(85, 0.8235294118f);
        table.put(90, 0.8888888889f);
        table.put(95, 0.9473684211f);
        table.put(100, 1);
    }


    private float c; // Constant chance of proc
    private int n; //Consecutive numbers without proc
    private float chance; // Virtual chance of proc

    public PseudoRandom(float chance) {
        this.chance = chance;
        int i = MathUtils.floor(chance * 20 + 0.0001f) * 5; // Transform 0-1 -> 0-100 just jumps of 5. The adding is to avoid error of 0.5 rounding to 45 instead of 50
        c = table.get(i, 0.85f);
    }

    public float getChance() {
        return chance;
    }

    public boolean proc() {
        n++;
        float procChance = c * n;

        boolean proc = Random.happens(procChance);
        if (proc)
            n = 0; //Reset fail chain

        return proc;
    }

}

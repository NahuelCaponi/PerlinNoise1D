package com.caponinahuel.demo.framework.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ShapeDrawerFactory {

    private static Texture texture;

    static public ShapeDrawer getNew(Batch batch) {
        if(texture == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.drawPixel(0, 0);
            texture = new Texture(pixmap);
            pixmap.dispose();
        }
        TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
        return new ShapeDrawer(batch, region);
    }
}

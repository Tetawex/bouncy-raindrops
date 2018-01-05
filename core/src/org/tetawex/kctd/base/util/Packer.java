package org.tetawex.kctd.base.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Packer {
    public static void main(String[] args) throws Exception {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.filterMag = Texture.TextureFilter.MipMapLinearNearest;
        TexturePacker.process(settings, "assets/textures", "assets", "atlas");
        TexturePacker.process(settings, "assets/pl_textures", "assets", "pl_atlas");
    }
}

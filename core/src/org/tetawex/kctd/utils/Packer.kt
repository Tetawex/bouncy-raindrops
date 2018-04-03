package org.tetawex.kctd.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import com.badlogic.gdx.utils.GdxRuntimeException


object Packer {
    @JvmStatic
    fun main(args: Array<String>) {
        val settings = TexturePacker.Settings()
        settings.filterMag = Texture.TextureFilter.MipMapLinearNearest
        TexturePacker.process(settings, "assets/textures", "assets", "atlas")
    }
}

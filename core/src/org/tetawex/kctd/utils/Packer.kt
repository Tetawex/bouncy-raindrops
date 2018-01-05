package org.tetawex.kctd.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import com.badlogic.gdx.utils.GdxRuntimeException
import com.github.czyzby.lml.parser.LmlParser
import com.github.czyzby.lml.parser.impl.tag.Dtd


object Packer {
    @JvmStatic
    fun main(args: Array<String>) {
        val settings = TexturePacker.Settings()
        settings.filterMag = Texture.TextureFilter.MipMapLinearNearest
        TexturePacker.process(settings, "assets/textures", "assets", "atlas")
    }

    fun saveDtdSchema(parser: LmlParser) {
        parser.isStrict = false

        try {
            val writer = Gdx.files.internal("lml.dtd")
                    .writer(false)
            Dtd.saveSchema(parser, writer)
            writer.close()
        } catch (exception: Exception) {
            throw GdxRuntimeException(exception)
        }
    }
}

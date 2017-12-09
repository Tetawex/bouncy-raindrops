package org.tetawex.kctd.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.I18NBundle
import com.github.czyzby.lml.parser.impl.tag.Dtd
import com.github.czyzby.lml.util.Lml

import java.io.Writer

object Packer {
    @JvmStatic
    fun main(args: Array<String>) {
        val parser = Lml
                .parser()
                .i18nBundle(I18NBundle.createBundle(
                        Gdx.files.internal("assets/bundle")))
                .build()
        parser.isStrict = false

        try {
            val writer = Gdx.files.absolute("lml.dtd")
                    .writer(false)
            Dtd.saveSchema(parser, writer)
            writer.close()
        } catch (exception: Exception) {
            throw GdxRuntimeException(exception)
        }

    }

}

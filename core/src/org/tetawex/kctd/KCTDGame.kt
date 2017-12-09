package org.tetawex.kctd

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.utils.I18NBundle
import com.github.czyzby.lml.parser.LmlParser
import com.github.czyzby.lml.util.Lml
import com.github.czyzby.lml.util.LmlApplicationListener
import ktx.inject.Context

class KCTDGame : LmlApplicationListener() {
    val context = Context() //Not an android context
    lateinit var batch: Batch

    override fun create() {
        batch = SpriteBatch()
        initInjectorContext()
    }

    override fun createParser(): LmlParser =
            Lml.parser()
                    .i18nBundle(context.inject())
                    .build()

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }

    private fun initInjectorContext() {
        context.register {
            bindSingleton(batch)
            bindSingleton(RandomXS128().let { it.setSeed(123) })
            bindSingleton(I18NBundle
                    .createBundle(Gdx.files.internal("assets/i18n")))
            bindSingleton(AssetManager().let { it.loadedAssets })//TODO: insert init code
        }
    }
}

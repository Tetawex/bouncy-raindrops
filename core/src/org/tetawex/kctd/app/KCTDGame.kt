package org.tetawex.kctd.app

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.utils.I18NBundle
import ktx.inject.Context
import org.tetawex.kctd.app.view.KCTDAssetManager
import java.util.*

class KCTDGame : Game() {
    val context = Context() //TODO: Possibly replace with a wrapper class
    lateinit var gameStateManager: GameStateManager
    lateinit var batch: Batch


    override fun create() {
        batch = SpriteBatch()
        initInjectorContext()
        gameStateManager = GameStateManager(this)
    }

    override fun render() {
        Gdx.gl.glClearColor(0.95f, 0.98f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        gameStateManager.renderCurrentScreen(deltaTime = Gdx.graphics.deltaTime)
        batch.end()
    }

    override fun dispose() {
        context.dispose()
    }

    override fun resize(width: Int, height: Int) {
        gameStateManager.currentScreen.resize(width, height)
    }

    private fun initInjectorContext() {
        context.register {
            bindSingleton<Random>(RandomXS128().also {  })
            bindSingleton(KCTDAssetManager().also { it.finishLoading() })
            bind {
                val am: KCTDAssetManager = context.inject()
                am.get("i18n/bundle", I18NBundle::class.java)
            }
        }
    }
}

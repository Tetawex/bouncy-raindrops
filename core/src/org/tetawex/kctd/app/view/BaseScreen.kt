package org.tetawex.kctd.app.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.I18NBundle
import com.badlogic.gdx.utils.viewport.ExtendViewport
import org.tetawex.kctd.app.KCTDGame
import org.tetawex.kctd.base.util.Bundle

open class BaseScreen(val game: KCTDGame, val bundle: Bundle) : ScreenAdapter() {
    val stage: Stage

    val localizer: I18NBundle
    val assetManager: KCTDAssetManager
    val camera = OrthographicCamera(320f, 140f)

    open fun initUi() {}

    fun getLocalizedString(id: String) = localizer.get(id)

    init {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
        stage = Stage(ExtendViewport(320f, 140f, camera))
        Gdx.input.inputProcessor = stage
        localizer = game.context.inject()
        assetManager = game.context.inject()
        initUi()
    }

    override fun render(delta: Float) {
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.camera.update()
        stage.viewport.update(width, height, true)
    }
}
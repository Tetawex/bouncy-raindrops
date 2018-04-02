package org.tetawex.kctd.app.actor

import com.badlogic.gdx.scenes.scene2d.Actor
import org.tetawex.kctd.app.KCTDGame
import org.tetawex.kctd.app.view.KCTDAssetManager

open class GameObject(val game: KCTDGame) : Actor() {
    val assetManager: KCTDAssetManager

    init {
        assetManager = game.context.inject()
    }
}
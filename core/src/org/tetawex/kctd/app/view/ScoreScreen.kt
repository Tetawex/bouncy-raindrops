package org.tetawex.kctd.app.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import ktx.actors.plus
import org.tetawex.kctd.app.GameStateManager
import org.tetawex.kctd.app.KCTDGame
import org.tetawex.kctd.app.actor.StyleFactory
import org.tetawex.kctd.base.util.Bundle
import org.tetawex.kctd.base.util.ImmutableBundle

class ScoreScreen(game: KCTDGame, bundle: Bundle) : BaseScreen(game, bundle) {
    override fun initUi() {
        super.initUi()
        val table = Table()
        stage + table
        table.setFillParent(true)

        table.add(Label("Score: ", StyleFactory.generateScoreBoardStyle(assetManager)))
        table.add(Label(bundle.getItem("score", String::class.java, "0"),
                StyleFactory.generateScoreBoardStyle(assetManager))).row()
        table.add().size(0f, 32f).row()
        table.add(TextButton("Retry", StyleFactory.generateMenuButtonStyle(assetManager))
                .also {
                    it.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            game.gameStateManager.setState(GameStateManager.GameState.GAME, ImmutableBundle.get())
                        }

                    })
                }).row()
        table.add().size(0f, 16f).row()
        table.add(TextButton("Quit", StyleFactory.generateMenuButtonStyle(assetManager))
                .also {
                    it.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            game.gameStateManager.setState(GameStateManager.GameState.MAIN_MENU, ImmutableBundle.get())
                        }

                    })
                })
    }
}
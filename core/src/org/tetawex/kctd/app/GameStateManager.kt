package org.tetawex.kctd.app

import com.badlogic.gdx.Screen
import com.badlogic.gdx.ScreenAdapter
import org.tetawex.kctd.app.view.BaseScreen
import org.tetawex.kctd.app.view.GameScreen
import org.tetawex.kctd.app.view.MainMenuScreen
import org.tetawex.kctd.app.view.ScoreScreen
import org.tetawex.kctd.base.util.Bundle
import org.tetawex.kctd.base.util.ImmutableBundle

/**
 * Created by Tetawex on 30.12.2016.
 */
class GameStateManager(private val game: KCTDGame) {

    var currentScreen: BaseScreen = BaseScreen(game, ImmutableBundle.get())
        private set
    private var currentState: GameState? = null
    fun dispose() {
        currentScreen.dispose()
    }

    enum class GameState {
        MAIN_MENU, GAME, SCORE
    }

    init {
        setState(GameState.MAIN_MENU, ImmutableBundle.get())
    }

    constructor(game: KCTDGame, state: GameState) : this(game) {
        setState(state, ImmutableBundle.get())
    }

    fun renderCurrentScreen(deltaTime: Float) {
        currentScreen.render(deltaTime)
    }

    fun setState(gameState: GameState, bundle: Bundle) {
        currentState = gameState
        currentScreen.dispose()

        when (currentState) {
            GameStateManager.GameState.MAIN_MENU -> currentScreen = MainMenuScreen(game, bundle)
            GameStateManager.GameState.SCORE -> currentScreen = ScoreScreen(game, bundle)
            GameStateManager.GameState.GAME -> currentScreen = GameScreen(game, bundle)
        }
    }
}

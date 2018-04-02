package org.tetawex.kctd.app.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import ktx.actors.plus
import org.tetawex.kctd.app.GameStateManager
import org.tetawex.kctd.app.KCTDGame
import org.tetawex.kctd.app.actor.*
import org.tetawex.kctd.base.util.Bundle
import org.tetawex.kctd.base.util.ImmutableBundle
import java.util.*

class GameScreen(game: KCTDGame, bundle: Bundle) : BaseScreen(game, bundle) {
    lateinit private var player: Player
    lateinit private var world: World

    var score = 0

    var unusedBodiesList: Queue<Body> = LinkedList<Body>()
    lateinit var commandList: Queue<(World) -> Unit>

    lateinit var scoreBoard: Label

    private val debugRenderer: Box2DDebugRenderer = Box2DDebugRenderer()
    override fun initUi() {
        initWorld()
        for (i in 1..150)
            stage + Cloud(game)

        player = Player(game, world)
        player.setPosition(0f, 0f)
        stage + player
        stage + Ground(game, world)

        val table = Table()
        table.setFillParent(true)

        table.add(TextButton("", StyleFactory.generatePauseButtonStyle(assetManager))
                .also {
                    it.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            game.gameStateManager.setState(GameStateManager.GameState.MAIN_MENU, ImmutableBundle.get())
                        }

                    })
                })
                .left().top().size(16f)
        scoreBoard = Label("0", StyleFactory.generateScoreBoardStyle(assetManager))
        table.add().growX()
        table.add(scoreBoard).row()
        table.add().expand()
        table.debug = true

        stage + table

        stage.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (x > stage.viewport.worldWidth / 2)
                    player.moveRight()
                else
                    player.moveLeft()
                return true
            }

            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                player.stop()
            }

            override fun keyUp(event: InputEvent, keycode: Int): Boolean {
                if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT))
                    player.stop()
                return true
            }

            override fun keyDown(event: InputEvent, keycode: Int): Boolean {
                if (keycode == Input.Keys.LEFT)
                    player.moveLeft()
                if (keycode == Input.Keys.RIGHT)
                    player.moveRight()
                return true
            }
        })
        commandList = LinkedList<(World) -> Unit>()
        //stage + Raindrop(Vector2(0f, 1f), 3, Vector2(180f, 150f), game, world, this)
        stage + DropSpawner(game, world, this)
    }

    override fun render(delta: Float) {
        //Gdx.app.log(""+commandList.size,"oh")
        commandList.forEach({
            it.invoke(world)
        })
        commandList.clear()
        world.step(delta, 5, 5)
        super.render(delta)
        //debugRenderer.render(world, camera.combined.scl(Box2dScalingUtil.getScaled(1f)))
    }

    private fun initWorld() {
        world = World(Vector2(0f, -1f), false)
        world.setContactListener(object : ContactListener {
            override fun endContact(contact: Contact) {
            }

            override fun beginContact(contact: Contact) {
            }

            override fun preSolve(contact: Contact, oldManifold: Manifold) {
            }

            override fun postSolve(contact: Contact, impulse: ContactImpulse) {
                if (contact.fixtureA.body.userData == null || contact.fixtureB.body.userData == null)
                    return
                val c1 = contact.fixtureA.body.userData as ContactReceiver
                val c2 = contact.fixtureB.body.userData as ContactReceiver
                c1.onReceived(contact.fixtureB.body)
                c2.onReceived(contact.fixtureA.body)

            }

        })
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.camera.update()
        stage.viewport.update(width, height, true)
    }

    fun submitBox2dCommand(command: (World) -> (Unit)) {
        commandList.add(command)
    }

    fun triggerLoss() {
        game.gameStateManager.setState(GameStateManager.GameState.SCORE,
                Bundle.fromSingleValue("score", score.toString()))
    }

    fun incrementScore() {
        score++
        scoreBoard.setText(score.toString())
    }
}
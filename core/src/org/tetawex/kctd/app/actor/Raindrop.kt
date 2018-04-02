package org.tetawex.kctd.app.actor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.tetawex.kctd.app.KCTDGame
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.actors.plus
import java.util.*
import org.tetawex.kctd.app.view.GameScreen


class Raindrop(val initialImpulse: Vector2, val size: Int, val spawnPos: Vector2,
               game: KCTDGame, val world: World, val screen: GameScreen) : GameObject(game = game), ContactReceiver {
    val group: Short = 0
    var ready = false

    override val tag = Tag.DROP
    override fun onReceived(other: Body) {
        if (null != other.userData && (other.userData as ContactReceiver).tag == Tag.PLAYER) {
            if (size > 1) {
                val count = random.nextInt(2)
                for (i in 1..1) {
                    stage + Raindrop(Vector2(random.nextFloat()  - 0.5f, random.nextFloat() * 1 + 2f),
                            size - 1, Vector2(x, y + 10), game, world, screen)
                }
                screen.incrementScore()
            }
        } else if (null != other.userData && (other.userData as ContactReceiver).tag == Tag.GROUND) {
            screen.triggerLoss()
        } else return
        val burst = Burst(game)
        burst.setPosition(x, y)
        stage.addActor(burst)
        ready = false
        body.userData = null
        screen.submitBox2dCommand({
            it.destroyBody(body)
            remove()
        })
        sound.play()
    }

    lateinit var random: Random
    val texture: TextureRegion
    lateinit var body: Body
    lateinit var sound: Sound

    init {
        val sizeString = when (size) {
            1 -> "small"
            2 -> "medium"
            else -> "large"
        }
        random = game.context.inject()
        texture = assetManager.atlas.findRegion("drop_" + sizeString)
        sound = assetManager.get("bounce.wav", Sound::class.java)
        //for (i in 1..10)
        screen.submitBox2dCommand({
            ready = true
            setupPhysics(it)
            body.isActive = false
            body.setTransform(Vector2(
                    Box2dScalingUtil.getDescaled(spawnPos.x),
                    Box2dScalingUtil.getDescaled(spawnPos.y)), 0f)
            body.applyLinearImpulse(initialImpulse, Vector2(0f, 0f), true)
            body.isActive = true
            Gdx.app.log("" + body.position.x, "" + body.position.y)
        })
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (ready) {
            if (body.position.y <= -50f) {
                body.userData = null
                ready = false
                screen.submitBox2dCommand({
                    body.isActive = false
                    it.destroyBody(body)
                    remove()
                })
                return
            }
            x = Box2dScalingUtil.getScaled(body.position.x)
            y = Box2dScalingUtil.getScaled(body.position.y)
        }

    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (ready) {
            batch.draw(texture, x - texture.regionWidth / 2, y - texture.regionHeight / 2)
        }
    }

    private fun setupPhysics(world: World) {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(0f, 0f)

        // add it to the world
        body = world.createBody(bodyDef)
        body.userData = this

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        val shape = CircleShape()
        shape.radius = 0.45f
        if (size == 1)
            shape.radius *= 0.25f
        else if (size == 2)
            shape.radius *= 0.5f

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        val fixtureDef = FixtureDef()
        fixtureDef.filter.groupIndex = group
        fixtureDef.shape = shape
        fixtureDef.density = 0.5f
        fixtureDef.friction = 1f
        fixtureDef.restitution = 10f

        // create the physical object in our body)
        // without this our body would just be data in the world
        body.createFixture(shape, 0.0f)
        body.isFixedRotation = true

        // we no longer use the shape object here so dispose of it.
        shape.dispose()
    }
}
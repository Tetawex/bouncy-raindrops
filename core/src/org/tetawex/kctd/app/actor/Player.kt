package org.tetawex.kctd.app.actor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.tetawex.kctd.app.KCTDGame
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*

class Player(game: KCTDGame, val world: World) : GameObject(game = game), ContactReceiver {
    override val tag = Tag.PLAYER
    private val moveSpeed = 14f
    private var suggestedVelocity = Vector2(0f, 0f)
    private var flipped = false

    fun moveRight() {
        suggestedVelocity = Vector2(moveSpeed, 0f)
        if (!flipped)
            animation.keyFrames.forEach { it.flip(true, false) }
        flipped = true
    }

    fun stop() {
        suggestedVelocity = Vector2(0f, 0f)
    }

    fun moveLeft() {
        suggestedVelocity = Vector2(-moveSpeed, 0f)
        if (flipped)
            animation.keyFrames.forEach { it.flip(true, false) }
        flipped = false
    }

    override fun onReceived(other: Body) {
        //if ((other.userData as ContactReceiver).tag == Tag.DROP)
    }

    val animation: Animation<TextureRegion>
    lateinit var body: Body

    private var elapsedTime: Float = 0f

    init {
        animation = Animation(0.3f,
                assetManager.atlas.findRegions("player_walk"),
                Animation.PlayMode.LOOP)
        setupPhysics()
        body.setTransform(Vector2(6f, 0f), 0f)
    }

    override fun act(delta: Float) {
        super.act(delta)
        elapsedTime += delta
        body.linearVelocity = suggestedVelocity.also { y = body.linearVelocity.y }
        x = Box2dScalingUtil.getScaled(body.position.x)
        y = Box2dScalingUtil.getScaled(body.position.y)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        val currentFrame = animation.getKeyFrame(elapsedTime, true)
        batch.draw(currentFrame, x - 16, y - 14)
    }

    private fun setupPhysics() {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(0f, 0f)


        // add it to the world
        body = world.createBody(bodyDef)
        body.userData = this

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        val shape = CircleShape()
        shape.radius = 0.55f

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f
        fixtureDef.restitution = 1f

        // create the physical object in our body)
        // without this our body would just be data in the world
        body.createFixture(shape, 0.0f)
        body.isFixedRotation = true

        // we no longer use the shape object here so dispose of it.
        shape.dispose()
    }
}
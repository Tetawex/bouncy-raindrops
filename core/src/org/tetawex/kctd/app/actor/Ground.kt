package org.tetawex.kctd.app.actor

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import org.tetawex.kctd.app.KCTDGame

class Ground(game: KCTDGame, val world: World) : GameObject(game = game), ContactReceiver {
    override val tag = Tag.GROUND
    override fun onReceived(other: Body) {
        if ((other.userData as ContactReceiver).tag == Tag.GROUND)
        ;//impl asap!
    }

    lateinit var body: Body

    init {
        setupPhysics()
        createWall(-1.0f)
        createWall(11f)
        body.setTransform(Vector2(5f, -1f), 0f)
    }

    override fun act(delta: Float) {
        super.act(delta)
        x = Box2dScalingUtil.getScaled(body.position.x)
        y = Box2dScalingUtil.getScaled(body.position.y)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        //batch.draw(currentFrame, x, y)
    }

    private fun setupPhysics() {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position.set(-5f, 0f)


        // add it to the world
        body = world.createBody(bodyDef)
        body.userData = this

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        val shape = PolygonShape()
        shape.setAsBox(20f, 1f)

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f


        // create the physical object in our body)
        // without this our body would just be data in the world
        body.createFixture(shape, 0.0f)
        body.isFixedRotation = true

        // we no longer use the shape object here so dispose of it.
        shape.dispose()
    }

    private fun createWall(offset: Float) {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position.set(offset, 0f)


        // add it to the world
        val body = world.createBody(bodyDef)
        body.userData = object : ContactReceiver {
            override val tag: Tag
                get() = Tag.DROP

            override fun onReceived(other: Body) {

            }

        }

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        val shape = PolygonShape()
        shape.setAsBox(1f, 10f)

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f
        fixtureDef.restitution = 1f


        // create the physical object in our body)
        // without this our body would just be data in the world
        body.createFixture(shape, 0.0f)
        body.isFixedRotation = true
        body.transform.position.set(0f, 0f)

        // we no longer use the shape object here so dispose of it.
        shape.dispose()
    }
}
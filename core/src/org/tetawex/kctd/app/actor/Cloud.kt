package org.tetawex.kctd.app.actor

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.tetawex.kctd.app.KCTDGame
import java.util.*

class Cloud(game: KCTDGame) : GameObject(game = game) {
    val texture: TextureRegion
    var moveSpeed = 12f
    val random: Random = game.context.inject()
    var flipDelay = 0f

    private var elapsedTime: Float = 0f
    private var lifespan = 0f

    init {
        texture = assetManager.atlas.findRegion("cloud")
        x = random.nextInt(300) + 20f
        randomizeMoveSpeed()
        if (random.nextBoolean())
            moveSpeed *= -1
    }

    override fun act(delta: Float) {
        super.act(delta)
        x += delta * moveSpeed
        flipDelay -= delta
        if (flipDelay < 0 && (x > 320 + 128 || x < -128)) {
            randomizeMoveSpeed()
            moveSpeed *= -1
            flipDelay=3f
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(texture, x - 32, y - 64)
    }

    private fun randomizeMoveSpeed() {
        val value = random.nextFloat() * 12f
        moveSpeed = 12 + value
        y = random.nextInt(340) + 0f
    }

}
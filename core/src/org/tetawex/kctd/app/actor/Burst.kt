package org.tetawex.kctd.app.actor

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.tetawex.kctd.app.KCTDGame
import com.badlogic.gdx.graphics.g2d.Batch

class Burst(game: KCTDGame) : GameObject(game = game) {
    val animation: Animation<TextureRegion>

    private var elapsedTime: Float = 0f
    private var lifespan = 0f

    init {
        animation = Animation(0.1f,
                assetManager.atlas.findRegions("burst"),
                Animation.PlayMode.NORMAL)
        lifespan = animation.animationDuration;
    }

    override fun act(delta: Float) {
        super.act(delta)
        elapsedTime += delta
        if (elapsedTime >= lifespan)
            remove()
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        val currentFrame = animation.getKeyFrame(elapsedTime, true)
        batch.draw(currentFrame, x - 16, y - 14)
    }

}
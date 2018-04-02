package org.tetawex.kctd.app.actor

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Actor
import ktx.actors.plus
import org.tetawex.kctd.app.KCTDGame
import org.tetawex.kctd.app.view.GameScreen
import java.util.*

class DropSpawner(val game: KCTDGame, val world: World, val screen: GameScreen) : Actor() {
    val random: Random = game.context.inject()

    var spawnInterval = 3f
    var elapsedTime = 2f
    override fun act(delta: Float) {
        super.act(delta)
        elapsedTime += delta
        if (elapsedTime >= spawnInterval) {
            stage + Raindrop(Vector2(0f, 0f), random.nextInt(2)+2, Vector2(320f * random.nextFloat() * 0.9f, 200f+50* random.nextFloat()),
                    game, world, screen)
            elapsedTime = random.nextFloat()*0.1f
            spawnInterval *= 0.98f
        }
    }
}
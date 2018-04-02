package org.tetawex.kctd.app.actor

import com.badlogic.gdx.physics.box2d.Body

interface ContactReceiver {
    fun onReceived(other: Body)
    val tag: Tag
}
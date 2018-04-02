package org.tetawex.kctd.app.actor

object Box2dScalingUtil {
    //1 meter = 32px
    private val factor = 32f

    fun getScaled(value: Float) = value * factor
    fun getDescaled(value: Float) = value / factor
}
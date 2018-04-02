package org.tetawex.kctd.app.actor

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import org.tetawex.kctd.app.view.KCTDAssetManager

object StyleFactory {
    fun generatePauseButtonStyle(assetManager: KCTDAssetManager): TextButton.TextButtonStyle {
        val skin = Skin()
        skin.addRegions(assetManager.get("atlas.atlas", TextureAtlas::class.java))
        val textButtonStyle = TextButton.TextButtonStyle()
        textButtonStyle.font = BitmapFont()
        textButtonStyle.up = skin.getDrawable("button_pause")
        textButtonStyle.down = skin.getDrawable("button_pause")
        return textButtonStyle
    }
    fun generateMenuButtonStyle(assetManager: KCTDAssetManager): TextButton.TextButtonStyle {
        val skin = Skin()
        skin.addRegions(assetManager.get("atlas.atlas", TextureAtlas::class.java))
        val textButtonStyle = TextButton.TextButtonStyle()
        textButtonStyle.font = assetManager.get("font.fnt", BitmapFont::class.java)
        textButtonStyle.fontColor = Color(0.6f, 0.8f, 1f, 0.87f)
        textButtonStyle.up = skin.getDrawable("cloud")
        textButtonStyle.down = skin.getDrawable("cloud")
        return textButtonStyle
    }

    fun generateScoreBoardStyle(assetManager: KCTDAssetManager): Label.LabelStyle {
        val skin = Skin()
        skin.addRegions(assetManager.get("atlas.atlas", TextureAtlas::class.java))
        val labelStyle = Label.LabelStyle()
        labelStyle.font = assetManager.get("font.fnt", BitmapFont::class.java)
        labelStyle.fontColor = Color(0.6f, 0.8f, 1f, 0.87f)
        return labelStyle
    }
}
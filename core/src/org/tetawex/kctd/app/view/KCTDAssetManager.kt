package org.tetawex.kctd.app.view

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.I18NBundle

class KCTDAssetManager() : AssetManager() {
    init {
        load("i18n/bundle", I18NBundle::class.java)
        load("atlas.atlas", TextureAtlas::class.java)
        load("bounce.wav", Sound::class.java)
        load("font.fnt", BitmapFont::class.java)
    }

    val atlas: TextureAtlas
        get() {
            return get("atlas.atlas", TextureAtlas::class.java)
        }
}
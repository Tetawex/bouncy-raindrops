package org.tetawex.kctd.app.view

import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.czyzby.lml.parser.impl.AbstractLmlView

/**
 * Created by tetawex on 27.12.2017.
 */
class MainMenuLml(stage: Stage) : AbstractLmlView(stage), MainMenuView {
    override fun getViewId(): String {
        return "main"
    }
}
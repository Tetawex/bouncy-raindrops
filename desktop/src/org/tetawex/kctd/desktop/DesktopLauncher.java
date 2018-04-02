package org.tetawex.kctd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.tetawex.kctd.app.KCTDGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 0;
        config.width = 800;
        config.height = 400;
        new LwjglApplication(new KCTDGame(), config);
    }
}

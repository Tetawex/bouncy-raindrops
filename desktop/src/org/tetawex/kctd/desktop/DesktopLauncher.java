package org.tetawex.kctd.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.tetawex.kctd.app.KCTDGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 0;
        config.width = 1280;
        config.height = 700;
        config.addIcon("ctd.png", Files.FileType.Internal);
        config.addIcon("ctd-32.png", Files.FileType.Internal);
        config.addIcon("ctd-128.png", Files.FileType.Internal);
        new LwjglApplication(new KCTDGame(), config);
    }
}

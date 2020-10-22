package desktopLauncher;

import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.*;

import application.*;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        
        config.title = "Diamonix";
        config.addIcon("icons/icon_128.png", Files.FileType.Internal);
        config.addIcon("icons/icon_64.png", Files.FileType.Internal);
        config.addIcon("icons/icon_32.png", Files.FileType.Internal);
        config.addIcon("icons/icon_16.png", Files.FileType.Internal);
        config.width = 450;
        config.height = 800;
        config.foregroundFPS = 0;
        config.backgroundFPS = 0;
        
        config.preferencesFileType = Files.FileType.Local;
        config.preferencesDirectory = "files/";
        
        new LwjglApplication(new LibGDXBridge(new DesktopOnlineButtonHandler()), config);
    }
}

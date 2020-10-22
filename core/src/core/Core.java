package core;

import core.objects.*;
import core.objects.assets.*;
import core.objects.audio.*;
import core.objects.background.*;
import core.objects.designs.*;
import core.objects.files.*;
import core.objects.progress.*;
import core.objects.screen.*;
import core.objects.snapshot.*;
import core.objects.worldButton.*;

public class Core {
    public final OnlineButtonHandler onlineButtonHandler;
    
    public final Screen screen = new Screen();
    public final Assets assets = new Assets(screen.sizes().getWorldSize().toString());
    public final Files files = new Files();
    public final FPS fps = new FPS(screen.sizes(), assets.getFont("small"));
    public final Audio audio;
    public final Designs designs;
    public final Title title;
    public final Background background;
    public final Progress progress = new Progress(files);
    public final Snapshot snapshot = new Snapshot(files);
    
    public Core(OnlineButtonHandler onlineButtonHandler) {
        this.onlineButtonHandler = onlineButtonHandler;
        TextureOrigins.assets = assets;
        Fonts.assets = assets;
        audio = new Audio(assets);
        Sounds.soundPlayer = audio.soundPlayer;
        designs = new Designs();
        title = new Title();
        background = new Background(progress);
        
        files.loadSettings(audio.volume, fps);
    }
}

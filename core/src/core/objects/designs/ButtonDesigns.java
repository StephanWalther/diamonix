package core.objects.designs;

import core.*;
import gui.button.*;

public class ButtonDesigns {
    public final ButtonDesign main;
    public final ButtonDesign mainSmall;
    
    ButtonDesigns() {
        main = new ButtonDesign(TextureOrigins.get("button"),
          TextureOrigins.get("buttonHighlight"),
          Sounds.get("beep"), Sounds.get("error"));
        mainSmall = new ButtonDesign(TextureOrigins.get("buttonSmall"),
          TextureOrigins.get("buttonHighlightSmall"),
          Sounds.get("beep"), Sounds.get("error"));
    }
}

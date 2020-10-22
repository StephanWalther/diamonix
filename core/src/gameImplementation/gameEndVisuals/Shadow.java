package gameImplementation.gameEndVisuals;

import com.badlogic.gdx.graphics.*;

import core.objects.screen.*;
import tools.common.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.Screen;

class Shadow extends PrototypeProgressLineListener implements Drawable {
    private final Color color = new Color();
    
    Shadow() {
        color.set(0, 0, 0, 0);
    }
    
    @Override
    public void progressChanged(float progress) {
        color.a = 0.5f*PositiveSinWave.halfPeriod.calculate(progress);
    }
    
    @Override
    public void draw(Screen screen) {
        float worldWidth = SizeGetter.getWorldWidth();
        float worldHeight = SizeGetter.getWorldHeight();
        screen.drawer().draw(-worldWidth*0.5f, -worldHeight*0.5f, worldWidth, worldHeight,
          color, color, color, color);
    }
}

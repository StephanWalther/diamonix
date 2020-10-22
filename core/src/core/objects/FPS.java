package core.objects;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;

import java.util.*;

import tools.common.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.screen.*;

public class FPS implements Drawable {
    private final Sizes sizes;
    private boolean active;
    private final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    private final TextComponent textComponent;
    private final List<Long> lastRenderTimes = new ArrayList<Long>();
    private Long lastDisplayUpdate = 0L;
    
    public FPS(Sizes sizes, BitmapFont font) {
        this.sizes = sizes;
        textComponent = new TextComponent(font).setPhysicComponent(physicComponent)
                          .setOrigin(Origin.rightCenter);
    }
    
    public boolean isDisplayActive() {
        return active;
    }
    
    public void setDisplayActive() {
        active = true;
    }
    
    public void setDisplayInactive() {
        active = false;
    }
    
    @Override
    public void draw(Screen screen) {
        Long currentTime = TimeUtils.nanoTime();
        updateLastRenderTimes(currentTime);
        if (isDisplayActive()) {
            updateDisplay(currentTime);
            screen.draw(textComponent);
        }
    }
    
    private void updateLastRenderTimes(Long currentTime) {
        lastRenderTimes.add(TimeUtils.nanoTime());
        while (currentTime - lastRenderTimes.get(0) >= 1000000000L) {
            lastRenderTimes.remove(0);
        }
    }
    
    private void updateDisplay(Long currentTime) {
        Long timeBetweenDisplayUpdates = 100000000L;
        if (currentTime - lastDisplayUpdate >= timeBetweenDisplayUpdates) {
            lastDisplayUpdate = currentTime;
            textComponent.setString(Integer.toString(lastRenderTimes.size()) + " FPS");
            physicComponent.setCenter(sizes.getWorldWidth()*0.49f, -sizes.getWorldHeight()
                                                                     *0.485f);
        }
    }
}

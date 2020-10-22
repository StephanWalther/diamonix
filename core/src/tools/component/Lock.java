package tools.component;

import tools.common.*;
import tools.component.graphic.*;
import tools.input.input.*;
import tools.screen.*;

public class Lock implements Common {
    private final Lockable lockable;
    private final TextureComponent textureComponent;
    private boolean locked = true;
    
    public Lock(Lockable lockable, TextureOrigin textureOrigin) {
        this.lockable = lockable;
        this.textureComponent = new TextureComponent(lockable.getPhysicComponent(),
          lockable.getColorComponent(), textureOrigin);
    }
    
    @Override
    public boolean processInput(Input input) {
        return !locked && lockable.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        if (!locked) lockable.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        if (locked) screen.draw(textureComponent);
        else screen.draw(lockable);
    }
    
    public void lock() {
        locked = true;
        lockable.processInput(new Reset());
    }
    
    public void unlock() {
        locked = false;
    }
    
    public boolean isLocked() {
        return locked;
    }
}

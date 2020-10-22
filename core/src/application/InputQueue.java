package application;

import com.badlogic.gdx.math.*;

import java.util.*;

import core.objects.screen.*;
import tools.input.input.*;

public final class InputQueue implements com.badlogic.gdx.InputProcessor {
    private final Queue<Input> queue = new LinkedList<Input>();
    private final Camera camera;
    
    InputQueue(Camera camera) {
        this.camera = camera;
    }
    
    @Override
    public boolean keyDown(int keycode) {
        queue.add(new KeyDown(keycode));
        return false;
    }
    
    @Override
    public boolean keyUp(int keycode) {
        queue.add(new KeyUp(keycode));
        return false;
    }
    
    @Override
    public boolean keyTyped(char character) {
        queue.add(new KeyTyped(character));
        return false;
    }
    
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        Vector2 worldPos = camera.unproject(new Vector2(x, y));
        queue.add(new TouchDown(worldPos.x, worldPos.y, pointer, button));
        return false;
    }
    
    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        Vector2 worldPos = camera.unproject(new Vector2(x, y));
        queue.add(new TouchDragged(worldPos.x, worldPos.y, pointer));
        return false;
    }
    
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        Vector2 worldPos = camera.unproject(new Vector2(x, y));
        queue.add(new TouchUp(worldPos.x, worldPos.y, pointer, button));
        return false;
    }
    
    @Override
    public boolean mouseMoved(int x, int y) {
        Vector2 worldPos = camera.unproject(new Vector2(x, y));
        queue.add(new MouseMoved(worldPos.x, worldPos.y));
        return false;
    }
    
    @Override
    public boolean scrolled(int amount) {
        queue.add(new Scrolled(amount));
        return false;
    }
    
    public void addResize(final int width, final int height) {
        queue.add(new Resize(width, height));
    }
    
    public Input poll() {
        return queue.poll();
    }
}

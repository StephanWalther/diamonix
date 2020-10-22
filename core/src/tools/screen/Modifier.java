package tools.screen;

import com.badlogic.gdx.graphics.glutils.*;

import core.objects.screen.Screen;

public interface Modifier {
    void apply(final Screen screen, final ShaderProgram shader);
}

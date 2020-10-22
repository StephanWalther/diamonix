package tools.screen;

import com.badlogic.gdx.graphics.glutils.*;

import tools.common.*;

public interface Screen {
    Sizes sizes();
    
    Camera camera();
    
    Drawer drawer();
    
    void draw(Drawable drawable);
    
    void beginModification(ShaderProgram modificationShader);
    
    void modify(Modifier modifier);
    
    void endModification();
}

package core.objects.screen;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;

public final class Drawer implements tools.screen.Drawer {
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final ShaderProgram defaultShader = SpriteBatch.createDefaultShader();
    private final Texture whiteOneOne = buildWhiteOneOne();
    
    private Texture buildWhiteOneOne() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.drawPixel(0, 0);
        return new Texture(pixmap);
    }
    
    void setProjectionMatrix(Matrix4 matrix4) {
        spriteBatch.setProjectionMatrix(matrix4);
    }
    
    void begin() {
        spriteBatch.begin();
    }
    
    public void draw(TextureRegion region, float x, float y, float width, float height) {
        draw(region, x, y, width, height, Color.WHITE);
    }
    
    public void draw(TextureRegion region, float x, float y, float width, float height,
                     Color color) {
        setSpriteBatchColor(color);
        spriteBatch.draw(region, x, y, width, height);
    }
    
    public void draw(float x, float y, float width, float height,
                     Color color1, Color color2,
                     Color color3, Color color4) {    
        float[] vertices = new float[]{
          x, y, color1.toFloatBits(), 0, 1,
          x, y + height, color3.toFloatBits(), 0, 0,
          x + width, y + height, color4.toFloatBits(), 1, 0,
          x + width, y, color2.toFloatBits(), 1, 1
        };
        
        spriteBatch.draw(whiteOneOne, vertices, 0, vertices.length);
    }
    
    public void draw(Texture texture, float x, float y, float originX, float originY,
                     float width, float height, float scaleX, float scaleY, float rotation,
                     int srcX, int srcY, int srcWidth, int srcHeight,
                     boolean flipX, boolean flipY) {
        draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation,
          srcX, srcY, srcWidth, srcHeight, flipX, flipY, Color.WHITE);
    }
    
    public void draw(Texture texture, float x, float y, float originX, float originY,
                     float width, float height, float scaleX, float scaleY, float rotation,
                     int srcX, int srcY, int srcWidth, int srcHeight,
                     boolean flipX, boolean flipY, Color color) {
        setSpriteBatchColor(color);
        spriteBatch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation,
          srcX, srcY, srcWidth, srcHeight, flipX, flipY);
    }
    
    public void draw(TextureRegion region, float x, float y, float originX, float originY,
                     float width, float height, float scaleX, float scaleY, float rotation) {
        draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation, Color.WHITE);
    }
    
    public void draw(TextureRegion region, float x, float y, float originX, float originY,
                     float width, float height, float scaleX, float scaleY, float rotation,
                     Color color) {
        setSpriteBatchColor(color);
        spriteBatch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }
    
    public GlyphLayout draw(BitmapFont font, CharSequence str, float x, float y) {
        return draw(font, str, x, y, Color.WHITE);
    }
    
    public GlyphLayout draw(BitmapFont font, CharSequence str, float x, float y,
                            Color color) {
        setFontColor(font, color);
        return font.draw(spriteBatch, str, x, y);
    }
    
    void beginModification(ShaderProgram modificationShader) {
        spriteBatch.end();
        spriteBatch.setShader(modificationShader);
    }
    
    ShaderProgram getShader() {
        return spriteBatch.getShader();
    }
    
    void endModification() {
        spriteBatch.setShader(defaultShader);
        spriteBatch.begin();
    }
    
    void end() {
        spriteBatch.end();
    }
    
    private void setSpriteBatchColor(Color color) {
        spriteBatch.setColor(color.r, color.g, color.b, color.a);
    }
    
    private void setFontColor(BitmapFont font, Color color) {
        font.setColor(color.r, color.g, color.b, color.a);
    }
    
    void dispose() {
        spriteBatch.dispose();
        defaultShader.dispose();
    }
}

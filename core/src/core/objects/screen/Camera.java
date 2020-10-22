package core.objects.screen;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

public class Camera implements tools.screen.Camera {
    private final OrthographicCamera worldCamera = new OrthographicCamera();
    private final OrthographicCamera screenCamera = new OrthographicCamera();
    private final Sizes sizes;
    private final Drawer drawer;
    
    Camera(Sizes sizes, Drawer drawer) {
        this.sizes = sizes;
        this.drawer = drawer;
    }
    
    void resize(Sizes sizes) {
        resizeWorldCamera(sizes);
        resizeScreenCamera(sizes);
    }
    
    private void resizeWorldCamera(Sizes sizes) {
        worldCamera.viewportWidth = sizes.getWorldWidth();
        worldCamera.viewportHeight = sizes.getWorldHeight();
        worldCamera.position.set(0, 0, 0);
    }
    
    private void resizeScreenCamera(Sizes sizes) {
        screenCamera.viewportWidth = sizes.getScreenWidth();
        screenCamera.viewportHeight = sizes.getScreenHeight();
        screenCamera.zoom = sizes.getInversWorldToScreenScaleFactor();
        screenCamera.position.set(0, 0, 0);
        screenCamera.update();
    }
    
    public Vector2 project(Vector2 vector2) {
        Vector3 vector3 = worldCamera.project(new Vector3(vector2, 0));
        return new Vector2(vector3.x, vector3.y);
    }
    
    public Vector2 unproject(Vector2 vector2) {
        Vector3 vector3 = screenCamera.unproject(new Vector3(vector2, 0));
        return new Vector2(vector3.x, vector3.y);
    }
    
    @Override
    public void setPosition(Vector2 vector2) {
        worldCamera.position.set(vector2, 0);
    }
    
    @Override
    public Vector2 getPosition() {
        return new Vector2(worldCamera.position.x, worldCamera.position.y);
    }
    
    @Override
    public void setZoom(float zoom) {
        worldCamera.zoom = zoom;
    }
    
    @Override
    public float getZoom() {
        return worldCamera.zoom;
    }
    
    @Override
    public void update() {
        worldCamera.update();
        drawer.setProjectionMatrix(worldCamera.combined);
    }
    
    @Override
    public Rectangle getVisibleRectangle() {
        float centerX = worldCamera.position.x;
        float centerY = worldCamera.position.y;
        float width = sizes.getWorldWidth()*worldCamera.zoom;
        float height = sizes.getWorldHeight()*worldCamera.zoom;
        return new Rectangle(centerX - 0.5f*width, centerY - 0.5f*height, width, height);
    }
    
    Matrix4 getWorldCombined() {
        return worldCamera.combined;
    }
    
    Matrix4 getScreenCombined() {
        return screenCamera.combined;
    }
}

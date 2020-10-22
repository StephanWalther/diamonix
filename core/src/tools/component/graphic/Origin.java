package tools.component.graphic;

public interface Origin {
    float toX(float width);
    
    float toY(float height);
    
    Origin center = new DefaultOrigin(0.5f, 0.5f);
    
    Origin leftCenter = new DefaultOrigin(0, 0.5f);
    
    Origin rightCenter = new DefaultOrigin(1.f, 0.5f);
    
    Origin lowerTriangleCenter = new DefaultOrigin(0.5f, 1.f/3.f);
    
    Origin upperTriangleCenter = new DefaultOrigin(0.5f, 2.f/3.f);
    
    Origin leftTriangleCenter = new DefaultOrigin(1.f/3.f, 0.5f);
}

package tools.mapping;

public class MirrorY implements Mapping {
    private final Mapping mapping;
    private final float y;
    
    public MirrorY(Mapping mapping, float y) {
        this.mapping = mapping;
        this.y = y;
    }
    
    @Override
    public float calculate(float x) {
        return mapping.calculate(2*y - x);
    }
}

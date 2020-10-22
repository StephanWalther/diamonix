package tools.mapping;

public class MirrorX implements Mapping {
    private final Mapping mapping;
    private final float x;
    
    public MirrorX(Mapping mapping, float x) {
        this.mapping = mapping;
        this.x = x;
    }
    
    @Override
    public float calculate(float x) {
        return 2*this.x - mapping.calculate(x);
    }
}

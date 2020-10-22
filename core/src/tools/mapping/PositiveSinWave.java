package tools.mapping;

public class PositiveSinWave implements Mapping {
    public static final PositiveSinWave halfPeriod = new PositiveSinWave(0.5f);
    public static final PositiveSinWave fullPeriod = new PositiveSinWave(1.f);
    
    private final float periods;
    
    public PositiveSinWave(final float periods) {
        this.periods = periods;
    }
    
    @Override
    public float calculate(final float progress) {
        return 0.5f*((float) Math.sin((progress*periods*2.f - 0.5f)*Math.PI) + 1.f);
    }
}

package tools.mapping;

public class PositiveCosWave implements Mapping {
    public static final PositiveCosWave halfPeriod = new PositiveCosWave(0.5f);
    public static final PositiveCosWave fullPeriod = new PositiveCosWave(1.f);
    
    private final float periods;
    
    public PositiveCosWave(final float periods) {
        this.periods = periods;
    }
    
    @Override
    public float calculate(final float progress) {
        return 0.5f*((float) Math.cos(progress*2.f*Math.PI*periods) + 1.f);
    }
}

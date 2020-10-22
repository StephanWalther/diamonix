package tools.misc;

import com.badlogic.gdx.math.*;

public class Interval {
    public static final Interval ZERO_ONE = new Interval(0, 1);
    
    private final float a, b;
    
    public Interval(final Interval interval) {
        this(interval.getA(), interval.getB());
    }
    
    public Interval(final float a, final float b) {
        this.a = a;
        this.b = b;
    }
    
    public float getA() {
        return a;
    }
    
    public float getB() {
        return b;
    }
    
    public float length() {
        return b - a;
    }
    
    public boolean contains(final float x) {
        return a <= x && x <= b;
    }
    
    public float clamp(final float x) {
        return MathUtils.clamp(x, a, b);
    }
}

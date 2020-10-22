package tools.mapping;

import java.util.*;

import tools.misc.*;

public class Polynomial implements Mapping {
    public static final Polynomial zero = new Polynomial(0.f);
    public static final Polynomial one = new Polynomial(1.f);
    
    public static final Polynomial quadratic = new Polynomial(1.f, 0.f, 0.f);
    public static final Polynomial negativeQuadratic = new Polynomial(-1.f, 2.f, 0.f);
    public static final Polynomial reverseQuadratic = new Polynomial(1.f, -2.f, 1.f);
    public static final Polynomial reverseNegativeQuadratic = new Polynomial(-1.f, 0.f, 1.f);
    public static final Polynomial negativeQuadraticNull = new Polynomial(-4.f, 4.f, 0.f);
    
    public static Polynomial linearIntervalToInterval(Interval i1, Interval i2) {
        float a = (i2.getA() - i2.getB())/(i1.getA() - i1.getB());
        float b = i2.getA() - a*i1.getA();
        return new Polynomial(a, b);
    }
    
    public static Polynomial quadraticPeak(float peakHeight) {
        if (peakHeight < 1) {
            throw new java.lang.IllegalArgumentException("peakHeight must not be lower than one");
        }
        float alpha = peakHeight - (float) Math.sqrt(peakHeight*peakHeight - peakHeight);
        float a = 1/(1 - 2*alpha);
        float b = 1 - a;
        return new Polynomial(a, b, 0.f);
    }
    
    private final List<Float> coefficients;
    
    public Polynomial(Float an, Float... a) {
        coefficients = new ArrayList<Float>(1 + a.length);
        coefficients.add(an);
        coefficients.addAll(Arrays.asList(a));
    }
    
    @Override
    public float calculate(float x) {
        float y = 0;
        for (Float coefficient : coefficients) y = y*x + coefficient;
        return y;
    }
}

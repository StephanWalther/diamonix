package tools.misc;

public class Accumulator {
    private float accumulation = 0;
    private float accumulationLimit = Float.MAX_VALUE;
    private float subtractionAmount = Float.MAX_VALUE;
    
    public Accumulator() {}
    
    public Accumulator(Accumulator accumulator) {
        set(accumulator);
    }
    
    public Accumulator set(Accumulator accumulator) {
        accumulation = accumulator.accumulation;
        accumulationLimit = accumulator.accumulationLimit;
        subtractionAmount = accumulator.subtractionAmount;
        return this;
    }
    
    public Accumulator setAccumulation(float accumulation) {
        this.accumulation = accumulation;
        return this;
    }
    
    public float getAccumulation() {
        return accumulation;
    }
    
    public Accumulator setAccumulationLimit(final float accumulationLimit) {
        this.accumulationLimit = accumulationLimit;
        return this;
    }
    
    public float getAccumulationLimit() {
        return accumulationLimit;
    }
    
    public Accumulator setSubtractionAmount(float subtractionAmount) {
        if (subtractionAmount < 0) {
            throw new IllegalArgumentException("SubtractionAmount must be positive.");
        }
        this.subtractionAmount = subtractionAmount;
        return this;
    }
    
    public float getSubtractionAmount() {
        return subtractionAmount;
    }
    
    public Accumulator add(float amount) {
        accumulation += amount;
        if (accumulationLimit < accumulation) accumulation = accumulationLimit;
        return this;
    }
    
    public int subtract() {
        if (subtractionAmount == 0) return Integer.MAX_VALUE;
        int count = (int) (accumulation/subtractionAmount);
        accumulation -= count*subtractionAmount;
        return count;
    }
}

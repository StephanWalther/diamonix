package tools.mapping;

import java.util.*;

public class MultiMapping implements Mapping {
    private final IntervalPartition intervalPartition;
    private final List<Mapping> mappings;
    
    public MultiMapping(IntervalPartition intervalPartition, List<Mapping> mappings) {
        this.intervalPartition = intervalPartition;
        this.mappings = mappings;
        check(intervalPartition, mappings);
    }
    
    private void check(IntervalPartition intervalPartition, List<Mapping> mappings) {
        if (intervalPartition.size() != mappings.size()) {
            throw new IllegalArgumentException("The sizes does not match.");
        }
    }
    
    @Override
    public float calculate(float x) {
        return mappings.get(intervalPartition.getLocalIntervalNumber(x)).calculate(x);
    }
}

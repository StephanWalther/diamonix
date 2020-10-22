package tools.mapping;

import java.util.*;

import tools.mapping.*;
import tools.misc.*;

public class IntervalPartition {
    private final List<Float> partition;
    
    public IntervalPartition(Float p0, Float p1, Float p2, Float... p) {
        partition = new ArrayList<Float>(3 + p.length);
        partition.add(p0);
        partition.add(p1);
        partition.add(p2);
        partition.addAll(Arrays.asList(p));
        check();
    }
    
    private void check() {
        float x = partition.get(0) - 1.f;
        for (Float f : partition) {
            if (f <= x) throwException();
            x = f;
        }
    }
    
    private void throwException() {
        throw new java.lang.IllegalArgumentException("Numbers must be increasing.");
    }
    
    public int getLocalIntervalNumber(float x) {
        if (x < partition.get(0) || partition.get(partition.size() - 1) < x) {
            throw new java.lang.IllegalArgumentException("Interval does not contain the argument.");
        }
        int localIntervalNumber = -1;
        for (Float f : partition) {
            if (x < f) return localIntervalNumber;
            localIntervalNumber++;
        }
        return localIntervalNumber - 1;
    }
    
    public List<Mapping> makeLinearLocalToGlobalIntervalMappings() {
        List<Mapping> mappings = new ArrayList<Mapping>(partition.size() - 1);
        Interval globalInterval = new Interval(
          partition.get(0), partition.get(partition.size() - 1));
        for (int i = 0; i < partition.size() - 1; i++) {
            Interval localInterval = new Interval(partition.get(i), partition.get(i + 1));
            mappings.add(Polynomial.linearIntervalToInterval(localInterval, globalInterval));
        }
        return mappings;
    }
    
    public int size() {
        return partition.size() - 1;
    }
}

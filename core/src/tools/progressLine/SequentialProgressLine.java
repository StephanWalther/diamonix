package tools.progressLine;

import com.badlogic.gdx.math.*;

import tools.misc.*;

public class SequentialProgressLine extends PrototypeManifoldProgressLine {
    private final Accumulator nextProgressLineAccumulator
      = new Accumulator().setSubtractionAmount(1.f);
    private float timeBetweenIncreases = nextProgressLineAccumulator.getSubtractionAmount();
    private float timeBetweenDecreases = nextProgressLineAccumulator.getSubtractionAmount();
    private boolean increasing = false;
    private int lastIncreasingProgressLine = -1;
    
    public SequentialProgressLine setTimeBetweenIncreasesAndDecreases(float time) {
        timeBetweenIncreases = time;
        timeBetweenDecreases = time;
        nextProgressLineAccumulator.setSubtractionAmount(time);
        return this;
    }
    
    public SequentialProgressLine setTimeBetweenIncreases(float timeBetweenIncreases) {
        this.timeBetweenIncreases = timeBetweenIncreases;
        if (isIncreasing()) nextProgressLineAccumulator.setSubtractionAmount(timeBetweenIncreases);
        return this;
    }
    
    public float getTimeBetweenIncreases() {
        return timeBetweenIncreases;
    }
    
    public SequentialProgressLine setTimeBetweenDecreases(float timeBetweenDecreases) {
        this.timeBetweenDecreases = timeBetweenDecreases;
        if (!isIncreasing()) nextProgressLineAccumulator.setSubtractionAmount(timeBetweenDecreases);
        return this;
    }
    
    public float getTimeBetweenDecreases() {
        return timeBetweenDecreases;
    }
    
    @Override
    public void update(float dt) {
        for (ProgressLine progressLine : progressLines) progressLine.update(dt);
        updateNextProgressLineTimer(dt);
    }
    
    private void updateNextProgressLineTimer(float dt) {
        if (0 <= lastIncreasingProgressLine &&
              lastIncreasingProgressLine < progressLines.size() - 1) {
            nextProgressLineAccumulator.add(dt);
            int nextProgressLineCount = nextProgressLineAccumulator.subtract();
            if (isIncreasing()) handleIncreases(nextProgressLineCount);
            else handleDecreases(nextProgressLineCount);
        }
    }
    
    private void handleIncreases(int count) {
        int newLastIncreasingProgressLine
          = Math.min(lastIncreasingProgressLine + count, progressLines.size() - 1);
        for (int i = lastIncreasingProgressLine + 1; i <= newLastIncreasingProgressLine; i++) {
            progressLines.get(i).increase();
        }
        lastIncreasingProgressLine = newLastIncreasingProgressLine;
    }
    
    private void handleDecreases(int count) {
        int newLastIncreasingProgressLine
          = Math.max(-1, lastIncreasingProgressLine - count);
        for (int i = newLastIncreasingProgressLine + 1; i <= lastIncreasingProgressLine; i++) {
            progressLines.get(i).decrease();
        }
        lastIncreasingProgressLine = newLastIncreasingProgressLine;
    }
    
    @Override
    public SequentialProgressLine setProgress(float progress) {
        if (progressLines.isEmpty()) return this;
        progress = MathUtils.clamp(progress, 0, 1);
        int fullProgressedLineCount = (int) (progress*progressLines.size());
        for (int i = 0; i < fullProgressedLineCount; i++) {
            progressLines.get(i).setProgress(1);
            progressLines.get(i).increase();
        }
        if (fullProgressedLineCount < progressLines.size()) {
            ProgressLine partialProgressedLine = progressLines.get(fullProgressedLineCount);
            partialProgressedLine
              .setProgress(progress*progressLines.size() - fullProgressedLineCount);
            if (isIncreasing()) {
                partialProgressedLine.increase();
                lastIncreasingProgressLine = fullProgressedLineCount;
            } else {
                partialProgressedLine.decrease();
                lastIncreasingProgressLine = fullProgressedLineCount - 1;
            }
            for (int i = fullProgressedLineCount + 1; i < progressLines.size(); i++) {
                progressLines.get(i).setProgress(0);
                progressLines.get(i).decrease();
            }
        }
        return this;
    }
    
    @Override
    public SequentialProgressLine increase() {
        if (!isIncreasing()) {
            increasing = true;
            nextProgressLineAccumulator.setSubtractionAmount(timeBetweenIncreases);
            if (!progressLines.isEmpty()) {
                nextProgressLineAccumulator.setAccumulation(0);
                handleIncreases(1);
            }
        }
        return this;
    }
    
    @Override
    public SequentialProgressLine decrease() {
        if (isIncreasing()) {
            increasing = false;
            nextProgressLineAccumulator.setSubtractionAmount(timeBetweenDecreases);
            if (!progressLines.isEmpty()) {
                nextProgressLineAccumulator.setAccumulation(0);
                handleDecreases(1);
            }
        }
        return this;
    }
    
    @Override
    public boolean isIncreasing() {
        return increasing;
    }
    
    @Override
    public void informPendingProgressLineAdd(ProgressLine timeLine) {
        if (progressLines.isEmpty() && isIncreasing()) {
            timeLine.increase();
            lastIncreasingProgressLine = 0;
        } else timeLine.decrease();
    }
    
    @Override
    public SequentialProgressLine clear() {
        super.clear();
        lastIncreasingProgressLine = -1;
        nextProgressLineAccumulator.setAccumulation(0);
        return this;
    }
    
    @Override
    public SequentialProgressLine extract() {
        SequentialProgressLine sequentialTimeLine = new SequentialProgressLine();
        sequentialTimeLine.progressLines.addAll(progressLines);
        progressLines.clear();
        sequentialTimeLine.nextProgressLineAccumulator.set(nextProgressLineAccumulator);
        sequentialTimeLine.timeBetweenIncreases = timeBetweenIncreases;
        sequentialTimeLine.timeBetweenDecreases = timeBetweenDecreases;
        sequentialTimeLine.increasing = increasing;
        sequentialTimeLine.lastIncreasingProgressLine = lastIncreasingProgressLine;
        return sequentialTimeLine;
    }
}

package tools.progressLine;

public class ProgressLineObserver implements ProgressLine {
    private ProgressLine observedProgressLine;
    private final ProgressLineListener progressLineListener;
    
    public ProgressLineObserver(ProgressLine observedProgressLine,
                                ProgressLineListener progressLineListener) {
        this.observedProgressLine = observedProgressLine.extract();
        this.progressLineListener = progressLineListener;
    }
    
    @Override
    public void update(final float dt) {
        observedProgressLine.update(dt);
        progressLineListener.updateCalled(dt, observedProgressLine);
    }
    
    @Override
    public ProgressLineObserver setProgress(float progress) {
        observedProgressLine.setProgress(progress);
        progressLineListener.setProgressCalled(progress, observedProgressLine);
        return this;
    }
    
    @Override
    public float getProgress() {
        return observedProgressLine.getProgress();
    }
    
    @Override
    public ProgressLineObserver increase() {
        boolean observedProgressLineWasDecreasing = !observedProgressLine.isIncreasing();
        observedProgressLine.increase();
        if (observedProgressLineWasDecreasing) {
            progressLineListener.switchedToIncrease(observedProgressLine);
        }
        return this;
    }
    
    @Override
    public ProgressLineObserver decrease() {
        boolean observedProgressLineWasIncreasing = observedProgressLine.isIncreasing();
        observedProgressLine.decrease();
        if (observedProgressLineWasIncreasing) {
            progressLineListener.switchedToIncrease(observedProgressLine);
        }
        return this;
    }
    
    @Override
    public boolean isIncreasing() {
        return observedProgressLine.isIncreasing();
    }
    
    @Override
    public ProgressLineObserver extract() {
        ProgressLine temp = observedProgressLine;
        observedProgressLine = null;
        return new ProgressLineObserver(temp, progressLineListener);
    }
}

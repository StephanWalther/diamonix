package tools.progressLine;

public class ReversingProgressLine implements ProgressLine {
    private ProgressLine progressLine;
    private boolean reverse = false;
    private ReversingListener reversingListener;
    
    public void setReversingListener(ReversingListener reversingListener) {
        this.reversingListener = reversingListener;
    }
    
    @Override
    public void update(final float dt) {
        progressLine.update(dt);
        if (reverse) {
            if (isIncreasing()) {
                if (getProgress() == 1.f) {
                    decrease();
                    reverse = false;
                    reversingListener.reversed();
                }
            } else if (getProgress() == 0.f) {
                increase();
                reverse = false;
                reversingListener.reversed();
            }
        }
    }
    
    @Override
    public ReversingProgressLine setProgress(float progress) {
        progressLine.setProgress(progress);
        return this;
    }
    
    @Override
    public float getProgress() {
        return progressLine.getProgress();
    }
    
    @Override
    public ReversingProgressLine increase() {
        progressLine.increase();
        return this;
    }
    
    @Override
    public ReversingProgressLine decrease() {
        progressLine.decrease();
        return this;
    }
    
    @Override
    public boolean isIncreasing() {
        return progressLine.isIncreasing();
    }
    
    @Override
    public ReversingProgressLine extract() {
        ReversingProgressLine reversingProgressLine = new ReversingProgressLine();
        reversingProgressLine.progressLine = progressLine;
        progressLine = null;
        return reversingProgressLine;
    }
    
    public ProgressLineInfo absorb(ProgressLine progressLine) {
        this.progressLine = progressLine.extract();
        return progressLine;
    }
    
    public void reverseOnce() {
        reverse = true;
    }
    
    public void doNotReverse() {
        reverse = false;
    }
}

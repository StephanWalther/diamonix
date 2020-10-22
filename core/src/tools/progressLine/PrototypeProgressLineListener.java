package tools.progressLine;

public abstract class PrototypeProgressLineListener implements ProgressLineListener {
    @Override
    public void updateCalled(float dt, ProgressLineInfo progressLineInfo) {
        progressChanged(progressLineInfo.getProgress());
    }
    
    @Override
    public void setProgressCalled(float progress, ProgressLineInfo progressLineInfo) {
        progressChanged(progressLineInfo.getProgress());
    }
    
    protected abstract void progressChanged(float progress);
    
    @Override
    public void switchedToDecrease(ProgressLineInfo progressLineInfo) {}
    
    @Override
    public void switchedToIncrease(ProgressLineInfo progressLineInfo) {}
}

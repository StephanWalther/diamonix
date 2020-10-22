package tools.progressLine;

public interface ProgressLineListener {
    void updateCalled(float dt, ProgressLineInfo progressLineInfo);
    
    void setProgressCalled(float progress, ProgressLineInfo progressLineInfo);
    
    void switchedToDecrease(ProgressLineInfo progressLineInfo);
    
    void switchedToIncrease(ProgressLineInfo progressLineInfo);
}

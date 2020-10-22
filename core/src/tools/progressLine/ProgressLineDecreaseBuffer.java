package tools.progressLine;

public class ProgressLineDecreaseBuffer implements ProgressLine {
    private ProgressLineInfo buffer;
    private ProgressLine realProgressLine;
    private float bufferPercentage = 0.f;
    
    public void setBuffer(ProgressLineInfo buffer) {
        this.buffer = buffer;
    }
    
    public void absorbRealProgressLine(ProgressLine realProgressLine) {
        this.realProgressLine = realProgressLine.extract();
    }
    
    public ProgressLineDecreaseBuffer setBufferPercentage(float bufferPercentage) {
        this.bufferPercentage = bufferPercentage;
        return this;
    }
    
    @Override
    public void update(float dt) {
        if (realProgressLine.isIncreasing() || bufferPercentageReached()) {
            realProgressLine.update(dt);
        }
    }
    
    @Override
    public ProgressLineDecreaseBuffer setProgress(float progress) {
        realProgressLine.setProgress(progress);
        return this;
    }
    
    @Override
    public float getProgress() {
        return realProgressLine.getProgress();
    }
    
    @Override
    public ProgressLineDecreaseBuffer increase() {
        realProgressLine.increase();
        return this;
    }
    
    @Override
    public ProgressLineDecreaseBuffer decrease() {
        realProgressLine.decrease();
        return this;
    }
    
    private boolean bufferPercentageReached() {
        return buffer.getProgress() <= bufferPercentage;
    }
    
    @Override
    public ProgressLineDecreaseBuffer extract() {
        ProgressLineDecreaseBuffer progressLineDecreaseBuffer = new ProgressLineDecreaseBuffer();
        if (buffer != null) progressLineDecreaseBuffer.setBuffer(buffer);
        if (realProgressLine != null) {
            progressLineDecreaseBuffer.absorbRealProgressLine(realProgressLine);
        }
        return progressLineDecreaseBuffer;
    }
    
    @Override
    public boolean isIncreasing() {
        return realProgressLine.isIncreasing();
    }
}

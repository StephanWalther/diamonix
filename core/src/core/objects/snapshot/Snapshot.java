package core.objects.snapshot;

import core.objects.files.*;

public class Snapshot {
    private final Files files;
    private SnapshotData snapshotData;
    
    public Snapshot(Files files) {
        this.files = files;
        snapshotData = files.loadSnapshotData();
    }
    
    public void setSnapshotData(SnapshotData snapshotData) {
        this.snapshotData = snapshotData;
        files.saveSnapshotData(snapshotData);
    }
    
    public SnapshotData getSnapshotData() throws HasNoSnapshotDataException {
        if (snapshotData != null) return snapshotData;
        throw new HasNoSnapshotDataException();
    }
}

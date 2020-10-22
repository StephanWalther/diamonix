package misc;

import java.io.*;

public class Version implements Serializable {
    public static final Version CURRENT = new Version(2, 1);
    
    public final int major;
    public final int minor;
    
    public Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }
    
    @Override
    public String toString() {
        return "Version " + Integer.toString(major) + "." + Integer.toString(minor);
    }
}

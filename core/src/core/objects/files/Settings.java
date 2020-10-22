package core.objects.files;

import java.io.*;

import core.objects.audio.*;

class Settings implements Serializable {
    float globalVolume;
    float musicVolume;
    float soundVolume;
    boolean isFPSActive;
}

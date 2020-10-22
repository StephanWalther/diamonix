package core.objects.files;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;

import java.io.*;
import java.util.*;

import core.objects.*;
import core.objects.audio.*;
import core.objects.progress.*;
import core.objects.progress.highScores.*;
import core.objects.snapshot.*;

public class Files {
    public List<Integer> loadHighScores() {
        Object object = load(Path.highScores);
        List<Integer> highScores = new ArrayList<Integer>(HighScores.MAX_HIGH_SCORES);
        if (object instanceof int[]) {
            int[] loadedHighScores = (int[]) object;
            for (int i = 0; i < Math.min(loadedHighScores.length, HighScores.MAX_HIGH_SCORES);
                 i++) {
                highScores.add(loadedHighScores[i]);
            }
        } else {
            for (int i = 0; i < HighScores.MAX_HIGH_SCORES; i++) highScores.add(0);
        }
        return highScores;
    }
    
    public void saveHighScores(List<Integer> highScores) {
        int[] hss = new int[highScores.size()];
        for (int i = 0; i < hss.length; i++) hss[i] = highScores.get(i);
        save(hss, Path.highScores);
    }
    
    public AchievementsData loadAchievementsData() {
        Object object = load(Path.achievementsSaveData);
        AchievementsData achievementsData = new AchievementsData();
        if (object instanceof AchievementsSaveData) {
            AchievementsSaveData achievementsSaveData = (AchievementsSaveData) object;
            achievementsData.maxDestroyedDiamonds = achievementsSaveData.maxDestroyedDiamonds;
            achievementsData.maxDestroyedDiamondsWithSameColour
              = achievementsSaveData.maxDestroyedDiamondsWithSameColour;
            achievementsData.maxDestroyedDiamondsWithDifferentColour
              = achievementsSaveData.maxDestroyedDiamondsWithDifferentColour;
            achievementsData.everDestroyedDiamonds = achievementsSaveData.everDestroyedDiamonds;
            achievementsData.everDestroyedDiamondsLeftFor5000Achievement
              = achievementsSaveData.everDestroyedDiamondsLeftFor5000Achievement;
            achievementsData.everDestroyedDiamondsLeftFor50000Achievement
              = achievementsSaveData.everDestroyedDiamondsLeftFor50000Achievement;
        }
        return achievementsData;
    }
    
    public void saveAchievementsData(AchievementsData achievementsData) {
        AchievementsSaveData achievementsSaveData = new AchievementsSaveData();
        achievementsSaveData.maxDestroyedDiamonds = achievementsData.maxDestroyedDiamonds;
        achievementsSaveData.maxDestroyedDiamondsWithSameColour
          = achievementsData.maxDestroyedDiamondsWithSameColour;
        achievementsSaveData.maxDestroyedDiamondsWithDifferentColour
          = achievementsData.maxDestroyedDiamondsWithDifferentColour;
        achievementsSaveData.everDestroyedDiamonds = achievementsData.everDestroyedDiamonds;
        achievementsSaveData.everDestroyedDiamondsLeftFor5000Achievement
          = achievementsData.everDestroyedDiamondsLeftFor5000Achievement;
        achievementsSaveData.everDestroyedDiamondsLeftFor50000Achievement
          = achievementsData.everDestroyedDiamondsLeftFor50000Achievement;
        save(achievementsSaveData, Path.achievementsSaveData);
    }
    
    public boolean loadTutorialPlayed() {
        Object object = load(Path.tutorialPlayed);
        return object instanceof TutorialPlayed;
    }
    
    public void saveTutorialPlayed() {
        save(new TutorialPlayed(), Path.tutorialPlayed);
    }
    
    public SnapshotData loadSnapshotData() {
        Object object = load(Path.snapshotData);
        if (!(object instanceof SnapshotData)) return null;
        return (SnapshotData) object;
    }
    
    public void saveSnapshotData(SnapshotData snapshotData) {
        save(snapshotData, Path.snapshotData);
    }
    
    public void loadSettings(Volume volume, FPS fps) {
        Object object = load(Path.settings);
        if (!(object instanceof Settings)) return;
        Settings settings = (Settings) object;
        volume.setGlobalVolume(settings.globalVolume);
        volume.setMusicVolume(settings.musicVolume);
        volume.setSoundVolume(settings.soundVolume);
        if (settings.isFPSActive) fps.setDisplayActive();
        else fps.setDisplayInactive();
    }
    
    public void saveSettings(Volume volume, FPS fps) {
        Settings settings = new Settings();
        settings.globalVolume = volume.getGlobalVolume();
        settings.musicVolume = volume.getMusicVolume();
        settings.soundVolume = volume.getSoundVolume();
        settings.isFPSActive = fps.isDisplayActive();
        save(settings, Files.Path.settings);
    }
    
    private Object load(Path path) {
        try {
            byte[] bytes = Gdx.files.local(path.string).readBytes();
            return fromBytes(bytes);
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (GdxRuntimeException e) {
            return null;
        }
    }
    
    private Object fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object data = ois.readObject();
        ois.close();
        bais.close();
        return data;
    }
    
    private void save(Object object, Path path) {
        if (object != null) {
            try {
                Gdx.files.local(path.string).writeBytes(toBytes(object), false);
            } catch (IOException e) {} catch (GdxRuntimeException e) {}
        } else Gdx.files.local(path.string).delete();
    }
    
    private byte[] toBytes(Object data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(data);
        oos.flush();
        byte[] bytes = baos.toByteArray();
        oos.close();
        baos.close();
        return bytes;
    }
    
    enum Path {
        highScores("highScores"),
        achievementsSaveData("achievementsSaveData"),
        tutorialPlayed("tutorialPlayed"),
        snapshotData("snapshotData"),
        settings("settings");
        
        final String string;
        
        Path(String string) {
            this.string = "files/" + string;
        }
    }
}

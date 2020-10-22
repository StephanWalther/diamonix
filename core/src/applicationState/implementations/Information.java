package applicationState.implementations;

import applicationState.components.*;
import applicationState.keys.*;
import applicationState.state.*;
import core.*;
import gui.text.*;
import misc.*;
import tools.*;
import tools.component.graphic.*;
import tools.progressLine.*;
import tools.spawn.*;

public class Information extends ComponentApplicationState<SequentialProgressLine> {
    private final DrawComponent drawComponent = new DrawComponent();
    private final CenterPhysicBuilder centerPhysicBuilder = new CenterPhysicBuilder();
    private final SpawnTextBuilder spawnTextBuilder = new SpawnTextBuilder();
    private float textCenterY = core.screen.sizes().getIdealWorldHeight()*0.3f;
    private int textCount = 0;
    private final float distBetweenParagraphs = core.screen.sizes().getIdealWorldHeight()*0.04f;
    
    public Information(Core core) {
        super(core, new SequentialProgressLine());
        buildBackKeyComponent();
        buildTexts();
        stateSpawnProgressLine.setTimeBetweenIncreasesAndDecreases(
          Constants.SPAWN_TIME/(float) (textCount - 1));
    }
    
    private void buildTexts() {
        addComponent(drawComponent);
        spawnTextBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        spawnTextBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        spawnTextBuilder.font = Fonts.get("small");
        String OSLanguage = java.util.Locale.getDefault().getDisplayLanguage();
        if (OSLanguage.equals("Deutsch")) buildGermanTexts();
        else buildEnglishTexts();
    }
    
    private void buildGermanTexts() {
        buildText("Entwickelt von");
        buildText("Stephan Walther");
        textCenterY -= distBetweenParagraphs;
        buildText("Quelle der Grafiken:");
        buildText("https://craftpix.net/");
        textCenterY -= distBetweenParagraphs;
        buildText("Quelle des Fonts:");
        buildText("https://www.1001fonts.com/");
        buildText("ds-accent-font.html");
        textCenterY -= distBetweenParagraphs;
        buildText("Musik und Soundeffekte von");
        buildText("Eric Matyas");
        buildText("www.soundimage.org");
        textCenterY -= distBetweenParagraphs;
        buildText("Die Datenschutzerklärung");
        buildText("kann unter");
        buildText("https://www.dropbox.com/s");
        buildText("/nfq4srx82r2wzcw/Privacy");
        buildText("%20Policy_Datenschutzerkl");
        buildText("%C3%A4rung.pdf?dl=0");
        buildText("gefunden werden");
        textCenterY -= distBetweenParagraphs;
        buildText(Version.CURRENT.toString());
    }
    
    private void buildEnglishTexts() {
        buildText("Developed by");
        buildText("Stephan Walther");
        textCenterY -= distBetweenParagraphs;
        buildText("Source of Graphics:");
        buildText("https://craftpix.net/");
        textCenterY -= distBetweenParagraphs;
        buildText("Source of the Font:");
        buildText("https://www.1001fonts.com/");
        buildText("ds-accent-font.html");
        textCenterY -= distBetweenParagraphs;
        buildText("Music and Sound Effects by");
        buildText("Eric Matyas");
        buildText("www.soundimage.org");
        textCenterY -= distBetweenParagraphs;
        buildText("The Privacy Policy");
        buildText("can be found under");
        buildText("https://www.dropbox.com/s");
        buildText("/nfq4srx82r2wzcw/Privacy");
        buildText("%20Policy_Datenschutzerkl");
        buildText("%C3%A4rung.pdf?dl=0");
        textCenterY -= distBetweenParagraphs;
        buildText(Version.CURRENT.toString());
    }
    
    private void buildText(String string) {
        centerPhysicBuilder.centerY = textCenterY;
        textCenterY -= core.screen.sizes().getIdealWorldHeight()*0.03f;
        Pair<TextComponent, ProgressLine> pair = spawnTextBuilder.buildSpawnText();
        pair.one.setString(string);
        drawComponent.add(pair.one);
        stateSpawnProgressLine.absorb(pair.two);
        textCount++;
    }
    
    @Override
    public boolean handleExternalStateChangeRequest(ApplicationStateKey applicationStateKey) {
        scheduleStatePush(applicationStateKey);
        return true;
    }
    
    @Override
    public Class<InformationKey> getApplicationStateKeyClass() {
        return InformationKey.class;
    }
}

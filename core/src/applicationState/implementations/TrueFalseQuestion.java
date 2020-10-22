package applicationState.implementations;

import java.util.*;

import applicationState.components.*;
import applicationState.keys.*;
import applicationState.keys.data.trueFalseQuestionData.*;
import applicationState.state.*;
import core.*;
import core.objects.screen.*;
import gui.button.*;
import misc.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.progressLine.Timer;

public class TrueFalseQuestion extends ComponentApplicationState<Timer> {
    private final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    private final DefaultColorComponent colorComponent = new DefaultColorComponent();
    private final DefaultColorComponent newGameTextColorComponent = new DefaultColorComponent();
    private final List<DefaultColorComponent> revokeAccessTextColorComponents
      = new ArrayList<DefaultColorComponent>();
    private TrueFalseQuestionListener trueFalseQuestionListener;
    private boolean answer;
    private boolean sendAnswer;
    
    public TrueFalseQuestion(Core core) {
        super(core, new Timer(Constants.SPAWN_TIME));
        colorComponent.setColorA(0);
        buildBackKeyComponent();
        buildBackTextureAndText();
        buildButtons();
    }
    
    @Override
    protected void buildBackKeyComponent() {
        buildBackKeyComponent(new Action() {
            @Override
            public void perform() {
                stateSpawnProgressLine.decrease();
            }
        });
    }
    
    private void buildBackTextureAndText() {
        DrawComponent drawComponent = new DrawComponent();
        addComponent(drawComponent);
        
        drawComponent.add(new TextureComponent(physicComponent, colorComponent,
          TextureOrigins.get("popUpBack")));
        
        String OSLanguage = java.util.Locale.getDefault().getDisplayLanguage();
        
        TextComponent newGameTextComponent = new TextComponent(Fonts.get("small_medium"));
        newGameTextComponent.setPhysicComponent(PhysicComponentBagBuilder.build(
          0, 0.085f*SizeGetter.getIdealWorldHeight(), physicComponent,
          new NodePhysicComponentBag()));
        ColorComponentBag colorComponentBag = new ColorComponentBag();
        colorComponentBag.addColorComponent(colorComponent);
        colorComponentBag.addColorComponent(newGameTextColorComponent);
        newGameTextComponent.setColorComponent(colorComponentBag);
        newGameTextComponent.setString(OSLanguage.equals("Deutsch") ?
                                         "Neues Spiel?" : "New Game?");
        
        drawComponent.add(newGameTextComponent);
        
        for (int i = 0; i < 3; i++) {
            TextComponent textComponent = new TextComponent(Fonts.get("small"));
            textComponent.setPhysicComponent(PhysicComponentBagBuilder.build(
              0, (0.11f - i*0.0325f)*SizeGetter.getIdealWorldHeight(), physicComponent,
              new NodePhysicComponentBag()));
            DefaultColorComponent dcc = new DefaultColorComponent();
            colorComponentBag = new ColorComponentBag();
            colorComponentBag.addColorComponent(colorComponent);
            colorComponentBag.addColorComponent(dcc);
            textComponent.setColorComponent(colorComponentBag);
            if (OSLanguage.equals("Deutsch")) {
                switch (i) {
                    case 0: textComponent.setString("Verbindung zu"); break;
                    case 1: textComponent.setString("Google Play Spiele"); break;
                    case 2: textComponent.setString("trennen?"); break;
                }
            } else {
                switch (i) {
                    case 0: textComponent.setString("Disconnect from"); break;
                    case 1: textComponent.setString("Google Play Games?"); break;
                    case 2: textComponent.setString(""); break;
                }
            }
            revokeAccessTextColorComponents.add(dcc);
            drawComponent.add(textComponent);
        }
    }
    
    private void buildButtons() {
        CommonComponent commonComponent = new CommonComponent();
        addComponent(commonComponent);
        
        float centerX = -0.175f*SizeGetter.getIdealWorldWidth();
        float centerY = -0.05f*SizeGetter.getIdealWorldHeight();
        ButtonConfiguration buttonConfiguration = new ButtonConfiguration(
          PhysicComponentBagBuilder.build(centerX, centerY, physicComponent,
            new NodePhysicComponentBag()),
          colorComponent,
          TextureOrigins.get("symbolCheckMarkWhite"),
          new Action() {
              @Override
              public void perform() {
                  stateSpawnProgressLine.decrease();
                  answer = true;
                  sendAnswer = true;
              }
          }
        );
        commonComponent.add(new Button(buttonConfiguration, core.designs.buttonDesigns.main));
        
        centerX = -centerX;
        buttonConfiguration = new ButtonConfiguration(
          PhysicComponentBagBuilder.build(centerX, centerY, physicComponent,
            new NodePhysicComponentBag()),
          colorComponent,
          TextureOrigins.get("symbolCross"),
          new Action() {
              @Override
              public void perform() {
                  stateSpawnProgressLine.decrease();
                  trueFalseQuestionListener.receiveTrueFalseQuestionAnswer(false);
                  answer = false;
                  sendAnswer = true;
              }
          }
        );
        commonComponent.add(new Button(buttonConfiguration, core.designs.buttonDesigns.main));
    }
    
    @Override
    public void update(float dt) {
        super.update(dt);
        float progress = stateSpawnProgressLine.getProgress();
        physicComponent.setScale(Mapping.scaleUp.calculate(progress));
        colorComponent.setColorA(PositiveSinWave.halfPeriod.calculate(progress));
        if (sendAnswer && progress < 0.5f && !stateSpawnProgressLine.isIncreasing()) {
            sendAnswer = false;
            trueFalseQuestionListener.receiveTrueFalseQuestionAnswer(answer);
        }
    }
    
    @Override
    public void pushed(ApplicationStateKey applicationStateKey) {
        super.pushed(applicationStateKey);
        TrueFalseQuestionData tfqd = applicationStateKey.getTrueFalseQuestionData();
        trueFalseQuestionListener = tfqd.trueFalseQuestionListener;
        if (tfqd.question == Question.newGame) showNewGameQuestion();
        else if (tfqd.question == Question.revokeAccess) showRevokeAccessQuestion();
    }
    
    private void showNewGameQuestion() {
        newGameTextColorComponent.setColorA(1);
        for (DefaultColorComponent dcc : revokeAccessTextColorComponents) dcc.setColorA(0);
    }
    
    private void showRevokeAccessQuestion() {
        newGameTextColorComponent.setColorA(0);
        for (DefaultColorComponent dcc : revokeAccessTextColorComponents) dcc.setColorA(1);
    }
    
    @Override
    public Class<TrueFalseQuestionKey> getApplicationStateKeyClass() {
        return TrueFalseQuestionKey.class;
    }
    
    public enum Question {
        newGame, revokeAccess
    }
}

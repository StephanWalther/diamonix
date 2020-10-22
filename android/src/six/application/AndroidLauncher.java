package six.application;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;

import com.badlogic.gdx.backends.android.*;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.*;
import com.google.android.gms.games.*;
import com.google.android.gms.tasks.*;

import application.*;
import core.objects.progress.*;
import core.objects.worldButton.*;
import walther.project.game.six.one.R;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class AndroidLauncher extends AndroidApplication implements OnlineButtonHandler {
    private final int signInRequestCode = 8765;
    private final int unusedRequestCode = 5678;
    private final Strings strings = new Strings();
    private final OnSuccessListener<Intent> onLeaderboardsOrAchievementsSuccessListener
      = new OnSuccessListener<Intent>() {
        @Override
        public void onSuccess(Intent intent) {
            startActivityForResult(intent, unusedRequestCode);
        }
    };
    private final OnFailureListener onLeaderboardsOrAchievementsFailureListener
      = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            showAlertDialog(strings.leaderboardsOrAchievementsRequestActivityFailure
                              + strings.additionalInformationText + e.toString());
        }
    };
    private OnlineButtonData onlineButtonData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        
        config.useAccelerometer = false;
        config.useCompass = false;
        
        initialize(new LibGDXBridge(this), config);
    }
    
    @Override
    public void handleOnlineButtonPressed(OnlineButtonData onlineButtonData) {
        this.onlineButtonData = onlineButtonData;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) signIn();
        else sendProgressAndStartActivity(account);
    }
    
    @Override
    public void handleRevokeAccess() {
        getSignInClient().revokeAccess();
    }
    
    private void signIn() {
        GoogleSignInClient googleSignInClient = getSignInClient();
        startActivityForResult(googleSignInClient.getSignInIntent(), signInRequestCode);
    }
    
    private GoogleSignInClient getSignInClient() {
        return GoogleSignIn.getClient(this,
          new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == signInRequestCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                sendProgressAndStartActivity(account);
            } catch (ApiException apiException) {
                showAlertDialog(strings.signInFailure + strings.additionalInformationText
                                  + apiException.toString());
            }
        }
    }
    
    private void sendProgressAndStartActivity(GoogleSignInAccount googleSignInAccount) {
        LeaderboardsClient leaderboardsClient
          = Games.getLeaderboardsClient(this, googleSignInAccount);
        AchievementsClient achievementsClient
          = Games.getAchievementsClient(this, googleSignInAccount);
        sendProgress(leaderboardsClient, achievementsClient);
        if (onlineButtonData.leaderboardsButtonPressed) {
            requestAllLeaderboardsActivity(leaderboardsClient);
        } else requestAchievementsActivity(achievementsClient);
    }
    
    private void sendProgress(LeaderboardsClient leaderboardsClient,
                              AchievementsClient achievementsClient) {
        int highScore = onlineButtonData.progress.highScores.getHighScore();
        leaderboardsClient.submitScore(getString(R.string.leaderboard_high_scores), highScore);
        leaderboardsClient.submitScore(getString(R.string.leaderboard_average_high_scores),
          onlineButtonData.progress.highScores.getAverageHighScore());
        
        if (highScore >= 800) {
            achievementsClient.unlock(getString(R.string.achievement_novice));
        } if (highScore >= 1500) {
            achievementsClient.unlock(getString(R.string.achievement_advanced));
        } if (highScore >= 2000) {
            achievementsClient.unlock(getString(R.string.achievement_master));
        }
        
        AchievementsData achievementsData
          = onlineButtonData.progress.achievements.getAchievementsData();
        if (achievementsData.maxDestroyedDiamonds >= 14) {
            achievementsClient.unlock(getString(R.string.achievement_at_a_blow));
        } if (achievementsData.maxDestroyedDiamonds >= 18) {
            achievementsClient.unlock(getString(R.string.achievement_at_a_blow_extreme));
        } if (achievementsData.maxDestroyedDiamondsWithSameColour >= 8) {
            achievementsClient.unlock(getString(R.string.achievement_one_colour));
        } if (achievementsData.maxDestroyedDiamondsWithSameColour >= 10) {
            achievementsClient.unlock(getString(R.string.achievement_one_colour_extreme));
        } if (achievementsData.maxDestroyedDiamondsWithDifferentColour >= 4) {
            achievementsClient.unlock(getString(R.string.achievement_all_colours));
        }
        
        if (achievementsData.everDestroyedDiamondsLeftFor5000Achievement > 0) {
            int increment5000 = achievementsData.everDestroyedDiamondsLeftFor5000Achievement;
            achievementsData.everDestroyedDiamondsLeftFor5000Achievement = 0;
            int increment50000 = achievementsData.everDestroyedDiamondsLeftFor50000Achievement;
            increment50000 -= increment50000%10;
            achievementsData.everDestroyedDiamondsLeftFor50000Achievement -= increment50000;
            increment50000 /= 10;
            onlineButtonData.progress.achievements.setAchievementsData(achievementsData);
            
            achievementsClient.increment(getString(R.string.achievement_marathon), increment5000);
            achievementsClient.increment(getString(R.string.achievement_marathon_extreme),
              increment50000);
        }
    }
    
    private void requestAllLeaderboardsActivity(LeaderboardsClient leaderboardsClient) {
        leaderboardsClient.getAllLeaderboardsIntent()
          .addOnSuccessListener(onLeaderboardsOrAchievementsSuccessListener)
          .addOnFailureListener(onLeaderboardsOrAchievementsFailureListener);
    }
    
    private void requestAchievementsActivity(AchievementsClient achievementsClient) {
        achievementsClient.getAchievementsIntent()
          .addOnSuccessListener(onLeaderboardsOrAchievementsSuccessListener)
          .addOnFailureListener(onLeaderboardsOrAchievementsFailureListener);
    }
    
    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this, THEME_HOLO_LIGHT)
          .setTitle(strings.alertDialogTitle)
          .setIconAttribute(android.R.attr.alertDialogIcon)
          .setMessage(message)
          .setNeutralButton(strings.alertDialogButtonText, null)
          .show();
    }
}

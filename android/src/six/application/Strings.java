package six.application;

class Strings {
    final String alertDialogTitle;
    final String signInFailure;
    final String leaderboardsOrAchievementsRequestActivityFailure;
    final String additionalInformationText;
    final String alertDialogButtonText;
    
    Strings() {
        String operatingSystemLanguage = java.util.Locale.getDefault().getDisplayLanguage();
        if (operatingSystemLanguage.equals("Deutsch")) {
            alertDialogTitle = "Fehler";
            signInFailure = "Es konnte keine Verbindung zu Google Play Spiele hergestellt werden." +
                              " Besteht eine Verbindung zum Internet?";
            leaderboardsOrAchievementsRequestActivityFailure
              = "Die Anfrage konnte nicht bearbeitet werden. Versuche durch Drücken der Taste" +
                  " unten links im Rekord Bildschirm die Verbindung zu " +
                  "Google Play Spiele zu trennen und versuche es dann erneut.";
            additionalInformationText = "\nWeitere Informationen:\n";
            alertDialogButtonText = "Zurück";
        } else {
            alertDialogTitle = "Error";
            signInFailure = "Could not connect to Google Play Games." +
                              " Does a connection to the internet exists?";
            leaderboardsOrAchievementsRequestActivityFailure
              = "The request could not be processed. Try to disconnect from " +
                  "Google Play Games by pressing the button in the lower left corner " +
                  "in the record screen and then try it again.";
            additionalInformationText = "\nAdditional information:\n";
            alertDialogButtonText = "Back";
        }
    }
}

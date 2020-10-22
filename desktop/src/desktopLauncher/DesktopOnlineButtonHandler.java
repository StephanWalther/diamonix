package desktopLauncher;

import core.objects.worldButton.*;

class DesktopOnlineButtonHandler implements OnlineButtonHandler {
    @Override
    public void handleOnlineButtonPressed(OnlineButtonData onlineButtonData) {
        printNotImplemented();
    }
    
    @Override
    public void handleRevokeAccess() {
        printNotImplemented();
    }
    
    private void printNotImplemented() {
        System.out.println("This feature is only on Android implemented.");
    }
}

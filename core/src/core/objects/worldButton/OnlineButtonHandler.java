package core.objects.worldButton;

public interface OnlineButtonHandler {
    void handleOnlineButtonPressed(OnlineButtonData onlineButtonData);
    
    void handleRevokeAccess();
}

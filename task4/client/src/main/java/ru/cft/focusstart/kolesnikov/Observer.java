package ru.cft.focusstart.kolesnikov;

public interface Observer {

    public void onUserConnected();
    public void onUserDisconnected(Observed observed);
    public void onMessageReceived(Observed observed);

}

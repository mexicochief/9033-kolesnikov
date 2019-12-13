package ru.cft.focusstart.kolesnikov;

public enum MessageType {
    TRYING_CONNECT("Trying to connect"),
    USER_CONNECTED("Connected"),
    USER_NAME_INVALID("User name is invalid"),
    MESSAGE("Message"),
    USER_NAME_VALID("User name is valid"),
    DISCONNECTED("Disconnected");
    private String info;

    MessageType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}

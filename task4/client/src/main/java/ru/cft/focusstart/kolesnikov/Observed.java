package ru.cft.focusstart.kolesnikov;

import java.util.ArrayList;

public interface Observed {

    public void addObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyUserList(Message msg);
}

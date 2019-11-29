package ru.cft.focusstart.kolesnikov.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

public class Transmitter {
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public boolean connect(String serverAddress, int port){
        try {
            socket = new Socket(serverAddress, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean sendNewName(String name){
        return true;
    }

    public void sendMessageToServer(String msg) {
    }

    public String takeMessageFromServer() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashSet<String> takeNameList(){
        HashSet<String> nameList = new HashSet<>();
        return nameList;
    }

}

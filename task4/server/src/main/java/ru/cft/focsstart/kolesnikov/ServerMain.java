package ru.cft.focsstart.kolesnikov;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.kolesnikov.Message;
import ru.cft.focusstart.kolesnikov.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.runServer();
    }
}

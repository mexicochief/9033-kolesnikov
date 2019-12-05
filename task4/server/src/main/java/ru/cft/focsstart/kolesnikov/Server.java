package ru.cft.focsstart.kolesnikov;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.kolesnikov.Message;
import ru.cft.focusstart.kolesnikov.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    ClientManager clientManager;
    static ArrayList<String> userList = new ArrayList<>(); // тут поправить мб убрать static
    ArrayList<ClientManager> clients = new ArrayList<>();
    ArrayList<ClientManager> inactiveClients = new ArrayList<>();
    ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(4004);
    }

    public void runServer() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            clientManager = new ClientManager(socket,this);
            inactiveClients.add(clientManager);
            //clients.add(clientManager);
            new Thread(clientManager).start();
        }
    }

    public void sendNewUserInfoToAll(Message msg) {
        for (ClientManager client : clients) {
            client.notifyUserConnected(msg);
        }
    }

    public void sendMsgToAll(Message msg){
        for (ClientManager client: clients){
            client.sendMessage(msg);
        }
    }

//    public void sendUserDisconnectedInfo(){
//        for (ClientManager client:clients){
//            client.notifyUserDisconnected();
//        }
//    }

    public void delSocket(ClientManager client){
        clients.remove(client);
    }





}

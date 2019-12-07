package ru.cft.focsstart.kolesnikov;

import ru.cft.focusstart.kolesnikov.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class Server {
    private ClientManager clientManager;
    private ArrayList<String> userList = new ArrayList<>(); // тут поправить мб убрать static
    private ArrayList<ClientManager> clients = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private ServerSocket serverSocket;
    private boolean serverRunning;

    public Server()  {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            for (Thread thread:threads){
                thread.interrupt();
            }
            serverRunning = false;
        }));
    }

    public void runServer() {
        Properties properties = new Properties();
        try(InputStream propertiesStream = Server.class.getResourceAsStream("/server.properties")) {
            properties.load(propertiesStream);
            serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("server.port")));
            serverRunning = true;
            while (serverRunning) {
                Socket socket = serverSocket.accept();
                clientManager = new ClientManager(socket, this,userList);
            }
        } catch (IOException e) {
            System.out.println("IOEx in Server"); // исправить
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendNewUserInfoToAll(Message msg) {
        for (ClientManager client : clients) {
            client.notifyUserConnected(msg);
        }
    }

    public void sendMsgToAll(Message msg) {
        for (ClientManager client : clients) {
            client.sendMessage(msg);
        }
    }

    public void addClient(ClientManager clientManager){
        clients.add(clientManager);
    }


    public void delSocket(ClientManager client) {
        clients.remove(client);
    }


}

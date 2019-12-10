package ru.cft.focsstart.kolesnikov;

import org.apache.log4j.Logger;
import ru.cft.focusstart.kolesnikov.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class Server {
    private ArrayList<String> userList = new ArrayList<>();
    private ArrayList<ClientManager> clients = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private ServerSocket serverSocket;
    private boolean serverRunning;
    private Logger log = Logger.getLogger("Server: ");

    public Server()  {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->serverRunning = false));
    }

    public void runServer() {
        Properties properties = new Properties();
        try(InputStream propertiesStream = Server.class.getResourceAsStream("/server.properties")) {
            properties.load(propertiesStream);
            serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("server.port")));
            serverRunning = true;
            while (serverRunning) {
                Socket socket = serverSocket.accept();
                ClientManager clientManager = new ClientManager(socket, this);
            }
        } catch (IOException e) {
            log.error("IOException in server");
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
               log.error("IOException in server when try close server socket");
            }
        }
    }

    public void sendMsgToAll(Message msg) throws IOException {
        for (ClientManager client : clients) {
            client.send(msg);
        }
    }

    public void addClient(ClientManager clientManager){
        clients.add(clientManager);
    }

    public void delSocket(ClientManager client) {
        removeUserFromUserList(client.getUserName());
        clients.remove(client);
    }

    public ArrayList<String> getUserList() {
        return userList;
    }

    public void addUserToUserList(String name){
        userList.add(name);
    }

    public void removeUserFromUserList(String name){
        userList.remove(name);
    }
}

package ru.cft.focsstart.kolesnikov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.cft.focusstart.kolesnikov.Message;
import ru.cft.focusstart.kolesnikov.MessageType;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientManager implements Runnable {
    Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ObjectMapper mapper = new ObjectMapper();
    private Server server;
    private String name; // перемеиновать
    private Thread thread;
    private ArrayList<String> userList;

    ClientManager(Socket clientSocket, Server server, ArrayList<String> userList) {
        mapper.registerModule(new JavaTimeModule());
        this.server = server;
        this.clientSocket = clientSocket;
        thread = new Thread(this);
        thread.start();
        this.userList = userList;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String newUser = reader.readLine();
            Message newMsg = mapper.readValue(newUser, Message.class);
            while (!thread.isInterrupted()) {
                switch (newMsg.getMessageType()) {
                    case USER_NAME_VALID:
                        server.addClient(this); // добавляем в активные
                        Message message = new Message(MessageType.USER_CONNECTED);
                        message.setUserList(userList);
                        message.setUserName(name);
                        message.setMessageVal(MessageType.USER_CONNECTED.getInfo());
                        server.sendNewUserInfoToAll(message);
                        newMsg = mapper.readValue(reader.readLine(), Message.class);
                        break;
                    case MESSAGE:
                        newMsg.setUserName(name);
                        server.sendMsgToAll(newMsg);
                        newMsg = mapper.readValue(reader.readLine(), Message.class);
                        break;
                    case DISCONNECTED:
                        Message userGone = new Message(MessageType.DISCONNECTED);
                        userGone.setMessageVal(MessageType.DISCONNECTED.getInfo());
                        userGone.setUserList(userList);
                        userGone.setUserName(name);
                        server.sendNewUserInfoToAll(userGone);
                        thread.interrupt();
                        break;
                    case TRYING_CONNECT:
                        if (userList.contains(newMsg.getUserName())) {
                            newMsg.setMessageType(MessageType.USER_NAME_INVALID);
                            send(newMsg);
                            newMsg = mapper.readValue(reader.readLine(), Message.class);
                        } else {
                            name = newMsg.getUserName(); // дублируется
                            userList.add(newMsg.getUserName());
                            newMsg = new Message(MessageType.USER_NAME_VALID);
                            newMsg.setUserName(name); // тоже дублируется
                            send(newMsg);
                            newMsg.setUserList(userList);
                        }
                        break;
                }
            }
        } catch (SocketException e) {
            userList.remove(name);
            server.delSocket(this);
            Message message = new Message(MessageType.DISCONNECTED);
            message.setMessageVal(MessageType.DISCONNECTED.getInfo());
            message.setUserList(userList);
            message.setUserName(name);
            server.sendNewUserInfoToAll(message);
        } catch (IOException e) {
            e.printStackTrace(); // исправить
        } finally {
            try {
                writer.close();
                reader.close();
                clientSocket.close();
                server.delSocket(this);
            } catch (IOException e) {
                System.out.println("IOEx in Client Manager"); // тоже исправить
            }
        }
    }

    private void send(Message msg) throws IOException {
        writer.write(mapper.writeValueAsString(msg));
        writer.write(System.lineSeparator());
        writer.flush();
    }

    public void notifyUserConnected(Message msg) {
        try {
//            Message message = new Message(MessageType.USER_CONNECTED);
//            message.setUserList(Server.userList);
//            message.setUserName(name);
            writer.write(mapper.writeValueAsString(msg));
            writer.write(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message msg) {
//        Message message = new Message(MessageType.MESSAGE);
//        message.setUserName(name);
//        message.setMessageVal(msg.getMessageVal());
        try {
            send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

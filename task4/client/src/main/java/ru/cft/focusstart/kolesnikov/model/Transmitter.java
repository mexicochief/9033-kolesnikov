package ru.cft.focusstart.kolesnikov.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.cft.focusstart.kolesnikov.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Transmitter implements Observed {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<String> allUsers = new ArrayList<>();
    private ObjectMapper mapper;
    private Runnable refreshingUserList = () -> {
        ChatFrame c; // исправить
        while (true) {
            Message msg = getMessageFromServer();
            if (msg != null) {
                switch (msg.getMessageType()) {
                    case USER_NAME_VALID:
                        c = (ChatFrame) observers.get(0);
                        System.out.println("tut");
                        c.runMainWindow();
                        break;
                    case USER_NAME_INVALID:
                        c = (ChatFrame) observers.get(0);
                        c.makeUserInvalidWindow();
                        break;
                    case USER_CONNECTED:
                        notifyUserList(msg);
                        break;
                    case MESSAGE:
                        notifyMsgField(msg);
                        break;
                    case DISCONNECTED:
                        notifyUserList(msg);
                        break;
                }

            }
        }
    };
    public Thread refreshingUserListThread = new Thread(refreshingUserList);

    public Transmitter() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public boolean connect(String serverAddress, int port, String name) { // поменять, не только сокет дает IOException
        try {
            if (socket == null) {
                socket = new Socket(serverAddress, port);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                userTryingConnect(name);
                refreshingUserListThread.start();
            } else {
                userTryingConnect(name);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void userTryingConnect(String userName) {
        Message message = new Message(MessageType.TRYING_CONNECT);
        message.setUserName(userName);
        String s;
        try {
            s = mapper.writeValueAsString(message);
            System.out.println(s);
            bufferedWriter.write(s);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (JsonProcessingException e) {
            System.out.println("JsonProcessingException");
            // тут как-то по другому обработать
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message getMessageFromServer() {
        Message message = null;
        try {
            String serMes;
            if (bufferedReader != null) {
                serMes = bufferedReader.readLine();
                System.out.println(serMes);

                message = mapper.readValue(serMes, Message.class);
            }
        } catch (IOException e) {
            e.printStackTrace();// поменять
        }
        return message;
    }

    public void sendMsgToServer(String msg) {
        Message message = new Message(MessageType.MESSAGE);
        message.setMessageVal(msg);
        try {
            bufferedWriter.write(mapper.writeValueAsString(message));
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyUserList(Message msg) {
        ChatFrame c = (ChatFrame) observers.get(0);
        c.refreshUserList(msg.getUserList());
        c.writeMessage(msg.getMessageType().getInfo() + ": " + msg.getUserName());
    }

    public void notifyMsgField(Message msg) {
        ChatFrame c = (ChatFrame) observers.get(0);
        c.refreshMsgField(msg.getUserName() + ": " + msg.getMessageVal());
    }

    public void start() {
        refreshingUserListThread.start();
    }
}

package ru.cft.focsstart.kolesnikov;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.kolesnikov.Message;
import ru.cft.focusstart.kolesnikov.MessageType;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientManager implements Runnable {
    Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ObjectMapper mapper = new ObjectMapper();
    private Server server;
    private String name; // перемеиновать

    ClientManager(Socket clientSocket, Server server) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // тут цикл должен быть
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String s = reader.readLine();
            System.out.println(s);
            Message newMsg = mapper.readValue(s, Message.class);
            while (true) {
                switch (newMsg.getMessageType()) {
                    case USER_NAME_VALID:
                        server.clients.add(this); // добавляем в активные
                        Message message = new Message(MessageType.USER_CONNECTED);
                        message.setUserList(Server.userList);
                        message.setUserName(name);
                        server.sendNewUserInfoToAll(message);
                        newMsg = mapper.readValue(reader.readLine(), Message.class);
                        break;
                    case MESSAGE:
                        newMsg.setUserName(name);
                        server.sendMsgToAll(newMsg);
                        newMsg = mapper.readValue(reader.readLine(), Message.class);
                        break;
                    case DISCONNECTED:
                        // тут наверное написать лоику если пользователь коректно закрыл приложение
                        notifyUserDisconnected();
                        break;
                    case TRYING_CONNECT:
                        if (Server.userList.contains(newMsg.getUserName())) {
                            send(newMsg);
                            newMsg = mapper.readValue(reader.readLine(), Message.class);
//                                server.delSocket(this);
//                                clientSocket.close();
                            // закрывать поток если не получилось
                        } else {
                            name = newMsg.getUserName(); // дублируется
                            System.out.println(name);
                            Server.userList.add(newMsg.getUserName());
                            newMsg = new Message(MessageType.USER_NAME_VALID);
                            newMsg.setUserName(name); // тоже дублируется
                            send(newMsg);
                            newMsg.setUserList(Server.userList);
                            System.out.println(newMsg.getUserList());
                        }
                        break;
                }
            }
        } catch (SocketException e) {
            Server.userList.remove(name);
            server.delSocket(this);
            Message message = new Message(MessageType.DISCONNECTED);
            message.setUserList(Server.userList);
            message.setUserName(name);
            server.sendNewUserInfoToAll(message);
        } catch (IOException e) {
            e.printStackTrace(); // исправить
        } finally {

            // тут закрыть все что связанно с потоком
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

    public void notifyUserDisconnected() {

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

    // cделать метод для рассылки всем клиентам
    // и поменять на try ...finnaly,наверное
}

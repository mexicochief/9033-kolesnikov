package ru.cft.focsstart.kolesnikov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.log4j.Logger;
import ru.cft.focusstart.kolesnikov.Message;
import ru.cft.focusstart.kolesnikov.MessageType;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientManager implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ObjectMapper mapper = new ObjectMapper();
    private Server server;
    private String userName;
    private Thread thread;
    private Logger log = Logger.getLogger("ClientManager: ");

    ClientManager(Socket clientSocket, Server server) {
        mapper.registerModule(new JavaTimeModule());
        this.server = server;
        this.clientSocket = clientSocket;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            while (!thread.isInterrupted()) {
                Message newMsg = mapper.readValue(reader.readLine(), Message.class);
                switch (newMsg.getMessageType()) {
                    case MESSAGE:
                        newMsg.setUserName(userName);
                        server.sendMsgToAll(makeMsgFromUser(newMsg.getMessageVal()));
                        break;
                    case DISCONNECTED:
                        server.removeUserFromUserList(userName);
                        server.sendMsgToAll(makeUserDisconnectedMsg());
                        thread.interrupt();
                        break;
                    case TRYING_CONNECT:
                        if (server.getUserList().contains(newMsg.getUserName())) {
                            send(new Message(MessageType.USER_NAME_INVALID));
                        } else {
                            userName = newMsg.getUserName();
                            server.addUserToUserList(newMsg.getUserName());
                            send(makeUserValidMsg());
                            server.addClient(this);
                            server.sendMsgToAll(makeUserConnectedMsg());
                        }
                        break;
                }
            }
        } catch (SocketException e) {
            log.error("SocketEx in ClientManager");
            destroyCurrentClient();
        } catch (IOException e) {
            log.error(e.getMessage());
           destroyCurrentClient();
        } finally {
            try {
                writer.close();
                reader.close();
                clientSocket.close();
                server.delSocket(this);
            } catch (IOException e) {
                log.error("IOException in ClientManager when try close"); // записать в лог
            }
        }
    }

    public void send(Message msg) throws IOException {
        writer.write(mapper.writeValueAsString(msg));
        writer.write(System.lineSeparator());
        writer.flush();
    }
    
    public String getUserName(){
        return userName;
    }

    private Message makeUserValidMsg() {
        Message message = new Message(MessageType.USER_NAME_VALID);
        message.setUserName(userName);
        return message;
    }

    private Message makeUserConnectedMsg() {
        Message message = new Message(MessageType.USER_CONNECTED);
        message.setUserList(server.getUserList());
        message.setUserName(userName);
        message.setMessageVal(MessageType.USER_CONNECTED.getInfo());
        return message;
    }

    private Message makeMsgFromUser(String msgVal) {
        Message message = new Message(MessageType.MESSAGE);
        message.setUserName(userName);
        message.setMessageVal(msgVal);
        return message;
    }

    private Message makeUserDisconnectedMsg(){
        Message message = new Message(MessageType.DISCONNECTED);
        message.setMessageVal(MessageType.DISCONNECTED.getInfo());
        message.setUserList(server.getUserList());
        message.setUserName(userName);
        return message;
    }

    private void destroyCurrentClient(){
        server.getUserList().remove(userName);
        server.delSocket(this);
        Message message = makeUserDisconnectedMsg();
        try { // тут как-то поправить
            server.sendMsgToAll(message);
        } catch (IOException ex) {
            log.error("IOException in ClientManager in method destroyCurrentClient()");
        }
    }
}

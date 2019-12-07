package ru.cft.focusstart.kolesnikov;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.Date;

public class Message {
    private String messageVal;
    private String userName;
    private Date date;
    private MessageType messageType;
    private ArrayList<String> userList;

    public Message(){
        date = new Date();
    }

    public Message(MessageType messageType){
        date = new Date();
        this.messageType = messageType;
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageVal() {
        return  messageVal;
    }

    public void setMessageVal(String messageVal) {
        this.messageVal = messageVal;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<String> userList) {
        this.userList = userList;
    }


}

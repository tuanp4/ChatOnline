/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import model.Message;
import view.ChatBox;
import view.GroupChatBox;

/**
 *
 * @author Valdez
 */
public class MessageController {

    private ChatBox chatBox;
    private GroupChatBox groupChatBox;
    private String serverHost = "localhost";
    private int serverPort = 8888;

    public MessageController(ChatBox chatBox) {
        this.chatBox = chatBox;
    }

    public MessageController(GroupChatBox groupChatBox) {
        this.groupChatBox = groupChatBox;
    }

    public void sendMessage() {
        try {
            Message message = chatBox.getSendingMessage();
            message.setAction("sendMessage");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            Message result = (Message) o;
            if (result == null) {
                chatBox.showMessage("Some error occurred. Please try again!");
            } else {
                chatBox.returnMessage(result);
            }
            mySocket.close();
        } catch (Exception ex) {
            chatBox.showMessage(ex.getStackTrace().toString());
        }
    }

    public void getHistoryMessages() {
        try {
            Message message = chatBox.getConversationID();
            message.setAction("getHistoryMessages");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            ArrayList<Message> result = (ArrayList<Message>) o;
            chatBox.returnHistoryMessages(result);
            mySocket.close();
        } catch (Exception ex) {
            chatBox.showMessage(ex.getStackTrace().toString());
        }
    }

    public void sendGroupMessage() {
        try {
            Message message = groupChatBox.getSendingMessage();
            message.setAction("sendGroupMessage");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            Message result = (Message) o;
            if (result == null) {
                groupChatBox.showMessage("Some error occurred. Please try again!");
            } else {
                groupChatBox.returnMessage(result);

            }
            mySocket.close();
        } catch (Exception ex) {
            groupChatBox.showMessage(ex.getStackTrace().toString());
        }
    }

    public void getGroupHistoryMessages() {
        try {
            Message message = groupChatBox.getConversationID();
            message.setAction("getGroupHistoryMessages");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            ArrayList<Message> result = (ArrayList<Message>) o;
            groupChatBox.returnHistoryMessages(result);
            mySocket.close();
        } catch (Exception ex) {
            groupChatBox.showMessage(ex.getStackTrace().toString());
        }
    }

}

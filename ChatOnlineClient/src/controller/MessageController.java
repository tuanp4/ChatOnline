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

/**
 *
 * @author Valdez
 */
public class MessageController {

    private ChatBox chatBox;
    private String serverHost = "localhost";
    private int serverPort = 8888;

    public MessageController(ChatBox chatBox) {
        this.chatBox = chatBox;
    }

    public void sendMessage() {
        try {
            Message message = chatBox.getSendingMessage();
            message.setAction("sendMessage");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(message);
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
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            ArrayList<Message> result = (ArrayList<Message>) o;
            chatBox.returnHistoryMessages(result);
            mySocket.close();
        } catch (Exception ex) {
            chatBox.showMessage(ex.getStackTrace().toString());
        }
    }

}

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
import model.Conversation;
import model.User;
import view.AccountInfoView;
import view.ChangePasswordView;
import view.ClientMainView;
import view.ClientStartView;

/**
 *
 * @author Valdez
 */
public class ConversationController {

    private ClientMainView clientMainView;
    private String serverHost = "localhost";
    private int serverPort = 8888;

    public ConversationController(ClientMainView clientMainView) {
        this.clientMainView = clientMainView;
    }

    public void getGroupList() {
        try {
            Conversation conversation = new Conversation();
            conversation.setMainUserId(clientMainView.getUserID().getId());
            conversation.setAction("getGroupList");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(conversation);
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            ArrayList<Conversation> result = (ArrayList<Conversation>) o;
            clientMainView.returnGroupList(result);
            mySocket.close();
        } catch (Exception ex) {
            clientMainView.showMessage(ex.getStackTrace().toString());
        }
    }

}

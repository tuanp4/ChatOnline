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
import view.CreateGroupView;

/**
 *
 * @author Valdez
 */
public class ConversationController {

    private ClientMainView clientMainView;
    private CreateGroupView createGroupView;
    private String serverHost = "localhost";
    private int serverPort = 8888;
    
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    
    public ConversationController(ClientMainView clientMainView, ObjectInputStream ois, ObjectOutputStream oos) {
        this.oos = oos;
        this.ois = ois;
        this.clientMainView = clientMainView;
    }

    public ConversationController(CreateGroupView createGroupView, ObjectInputStream ois, ObjectOutputStream oos) {
        this.createGroupView = createGroupView;
        this.oos = oos;
        this.ois = ois;
    }

    public void getGroupList() {
        try {
            Conversation conversation = new Conversation();
            conversation.setMainUserId(clientMainView.getUserID().getId());
            conversation.setAction("getGroupList");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(conversation);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            ArrayList<Conversation> result = (ArrayList<Conversation>) o;
            clientMainView.returnGroupList(result);
            mySocket.close();
        } catch (Exception ex) {
            clientMainView.showMessage(ex.getStackTrace().toString());
        }
    }

    public void openFriendConversation(int friendID) {
        try {
            Conversation conversation = new Conversation();
            conversation.setMainUserId(clientMainView.getUserID().getId());
            conversation.setFriendId(friendID);
            conversation.setAction("openFriendConversation");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(conversation);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            Conversation result = (Conversation) o;
            if (result == null) {
                clientMainView.showMessage("Some error occurred. Please try again!");
            } else {
                clientMainView.openFriendChatBox(result);
            }
            mySocket.close();
        } catch (Exception ex) {
            clientMainView.showMessage(ex.getStackTrace().toString());
        }
    }

    public void createGroupConversation() {
        try {
            System.out.println("start create conversation");
            System.out.println(this.oos);
            Conversation conversation = createGroupView.getConversationParticipants();
            conversation.setAction("createGroupConversation");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(conversation);
            oos.flush();
            System.out.println("send from server creategroup");
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            Conversation result = (Conversation) o;
            if (result == null) {
                createGroupView.showMessage("Some error occurred. Please try again!");
            } else {
                createGroupView.openCreatedGroupChat(result);
            }
            //mySocket.close();
        } catch (Exception ex) {
            createGroupView.showMessage(ex.getStackTrace().toString());
        }
    }

    public void openGroupConversation(int conversationID) {
        try {
            Conversation conversation = new Conversation();
            conversation.setId(conversationID);
            conversation.setMainUserId(clientMainView.getUserID().getId());
            conversation.setAction("openGroupConversation");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(conversation);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            Conversation result = (Conversation) o;
            if (result == null) {
                clientMainView.showMessage("Some error occurred. Please try again!");
            } else {
                clientMainView.openGroupChatBox(result);
            }
            mySocket.close();
        } catch (Exception ex) {
            clientMainView.showMessage(ex.getStackTrace().toString());
        }
    }

}

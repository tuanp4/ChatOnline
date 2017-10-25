/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.Friendship;
import view.OtherUserInfoView;

/**
 *
 * @author Valdez
 */
public class FriendshipController {

    private OtherUserInfoView otherUserInfoView;
    private String serverHost = "localhost";
    private int serverPort = 8888;

    public FriendshipController(OtherUserInfoView otherUserInfoView) {
        this.otherUserInfoView = otherUserInfoView;
    }

    public void getFriendshipState() {
        try {
            Friendship friendship = otherUserInfoView.getUserIDs();
            friendship.setAction("getFriendshipState");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(friendship);
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            oos.flush();
            String result = (String) o;
            otherUserInfoView.returnFriendshipState(result);
            mySocket.close();
        } catch (Exception ex) {
            otherUserInfoView.showMessage(ex.getStackTrace().toString());
            ex.printStackTrace();
        }
    }

    public void addFriend() {
        try {
            Friendship friendship = otherUserInfoView.getUserIDs();
            friendship.setAction("addFriend");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(friendship);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            Boolean result = (Boolean) o;
            if (!result) {
                otherUserInfoView.showMessage("Some error occurred. Please try again!");
            } else {
                otherUserInfoView.changeFriendshipState("Friend Request Sent");
                otherUserInfoView.showMessage("Friend request sent!");
            }
            mySocket.close();
        } catch (Exception ex) {
            otherUserInfoView.showMessage(ex.getStackTrace().toString());
        }
    }

    public void acceptFriendRequest() {
        try {
            Friendship friendship = otherUserInfoView.getUserIDs();
            friendship.setAction("acceptFriendRequest");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(friendship);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            Boolean result = (Boolean) o;
            if (!result) {
                otherUserInfoView.showMessage("Some error occurred. Please try again!");
            } else {
                otherUserInfoView.changeFriendshipState("Friends");
                otherUserInfoView.showMessage("Congratulations, you just made a new friend!");
            }
            mySocket.close();
        } catch (Exception ex) {
            otherUserInfoView.showMessage(ex.getStackTrace().toString());
        }
    }

}

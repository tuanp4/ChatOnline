/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.dao.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import view.ServerView;
import model.*;

/**
 *
 * @author Valdez
 */
public class ServerController {

    private ServerView view;
    private Connection con;
    private ServerSocket myServer;
    private Socket clientSocket;
    private int serverPort = 8888;

    public ServerController(ServerView view) {
        this.view = view;
        getDBConnection("chat_online", "root", "");
        openServer(serverPort);
        view.showMessage("TCP server is running...");
        while (true) {
            listenning();
        }
    }

    private void getDBConnection(String dbName, String username, String password) {
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&characterEncoding=utf8";
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e) {
            view.showMessage(e.getStackTrace().toString());
        }
    }

    private void openServer(int portNumber) {
        try {
            myServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            view.showMessage(e.toString());
        }
    }

    private void listenning() {
        try {
            clientSocket = new Socket();
            clientSocket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            Object o = ois.readObject();
            if (o instanceof Conversation) {
                Conversation conversation = (Conversation) o;
                DAOConversation dao = new DAOConversation(con);
                ConversationController conversationController = new ConversationController(dao);
                if (conversation.getAction().equals("getGroupList")) {
                    oos.writeObject(conversationController.returnAvailableGroupList(conversation));
                    oos.flush();
                }
                if (conversation.getAction().equals("createFriendConversation")) {
                    oos.writeObject(conversationController.returnFriendConversation(conversation));
                    oos.flush();
                }
            } else if (o instanceof Friendship) {
                Friendship friendship = (Friendship) o;
                DAOFriendship dao = new DAOFriendship(con);
                FriendshipController friendshipController = new FriendshipController(dao);
                if (friendship.getAction().equals("getFriendshipState")) {
                    oos.writeObject(friendshipController.returnFriendshipState(friendship));
                    oos.flush();
                }
                if (friendship.getAction().equals("addFriend")) {
                    oos.writeObject(friendshipController.returnAddFriendRespond(friendship));
                    oos.flush();
                }
                if (friendship.getAction().equals("acceptFriendRequest")) {
                    oos.writeObject(friendshipController.returnAcceptFriendRequestRespond(friendship));
                    oos.flush();
                }
            } else if (o instanceof Group) {
                Group group = (Group) o;

            } else if (o instanceof Message) {
                Message message = (Message) o;
                DAOMessage dao = new DAOMessage(con);
                MessageController messageController = new MessageController(dao);
                if (message.getAction().equals("sendMessage")) {
                    oos.writeObject(messageController.returnSentMessage(message));
                    oos.flush();
                }
                if (message.getAction().equals("getHistoryMessages")) {
                    oos.writeObject(messageController.returnHistoryMessages(message));
                    oos.flush();
                }
            } else if (o instanceof User) {
                User user = (User) o;
                DAOUser dao = new DAOUser(con);
                UserController userController = new UserController(dao);
                if (user.getAction().equals("login")) {
                    oos.writeObject(userController.checkUserLogin(user));
                    oos.flush();
                }
                if (user.getAction().equals("signUp")) {
                    oos.writeObject(userController.signUpUser(user));
                    oos.flush();
                }
                if (user.getAction().equals("changeInfo")) {
                    oos.writeObject(userController.changeUserInfo(user));
                    oos.flush();
                }
                if (user.getAction().equals("changeAvatar")) {
                    oos.writeObject(userController.changeUserAvatar(user));
                    oos.flush();
                }
                if (user.getAction().equals("changeStatus")) {
                    oos.writeObject(userController.changeUserStatus(user));
                    oos.flush();
                }
                if (user.getAction().equals("changePassword")) {
                    oos.writeObject(userController.changeUserPassword(user));
                    oos.flush();
                }
                if (user.getAction().equals("changeDescription")) {
                    oos.writeObject(userController.changeUserDescription(user));
                    oos.flush();
                }
                if (user.getAction().equals("getFriendList")) {
                    oos.writeObject(userController.returnAvailableFriendList(user));
                    oos.flush();
                }
                if (user.getAction().equals("getSuggestedNewFriends")) {
                    oos.writeObject(userController.returnSuggestedUsers(user));
                    oos.flush();
                }
                if (user.getAction().equals("getSuggestedParticipantFriends")) {
                    oos.writeObject(userController.returnSuggestedParticipants(user));
                    oos.flush();
                }
            }
        } catch (Exception e) {
            view.showMessage(e.toString());
            e.printStackTrace();
        }
    }

}

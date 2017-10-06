/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import controller.dao.DAOUser;
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
                
            } else if (o instanceof Friendship) {
                Friendship friendship = (Friendship) o;
                
            } else if (o instanceof Group) {
                Group group = (Group) o;
                
            } else if (o instanceof Message) {
                Message message = (Message) o;
                
            } else if (o instanceof User) {
                User user = (User) o;
                DAOUser dao = new DAOUser(con);
                UserController userController = new UserController(dao);
                if (user.getAction().equals("login")) {
                    oos.writeObject(userController.checkUserLogin(user));
                }
                if (user.getAction().equals("signUp")) {
                    oos.writeObject(userController.signUpUser(user));
                }
                if (user.getAction().equals("changeInfo")) {
                    oos.writeObject(userController.changeUserInfo(user));
                }
                if (user.getAction().equals("changeAvatar")) {
                    oos.writeObject(userController.changeUserAvatar(user));
                }
                if (user.getAction().equals("changeStatus")) {
                    oos.writeObject(userController.changeUserStatus(user));
                }
                if (user.getAction().equals("changePassword")) {
                    oos.writeObject(userController.changeUserPassword(user));
                }
                if (user.getAction().equals("changeDescription")) {
                    oos.writeObject(userController.changeUserDescription(user));
                }
            }
        } catch (Exception e) {
            view.showMessage(e.toString());
            printStackTrace();
        }
    }

}

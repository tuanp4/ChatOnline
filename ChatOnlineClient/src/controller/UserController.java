/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.User;
import view.ClientStartView;

/**
 *
 * @author Valdez
 */
public class UserController {

    private ClientStartView view;
    private String serverHost = "localhost";
    private int serverPort = 8888;

    public UserController(ClientStartView view) {
        this.view = view;
    }

    public void login() {
        try {
            User user = view.getUserLogin();
            user.setAction("login");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(user);
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            User result = (User) o;
            if (result == null) {
                view.showMessage("Invalid Username and/or Password!");
            } else {
                view.toMainView(result);
            }
            mySocket.close();
        } catch (Exception ex) {
            view.showMessage(ex.getStackTrace().toString());
        }
    }

    public void signUp() {
        try {
            User user = view.getUserSignUp();
            user.setAction("signUp");
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(user);
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            Boolean result = (Boolean) o;
            if (result.equals(false)) {
                view.showMessage("Username and/or Email already exist!");
            } else {
                view.switchToLogin();
                view.showMessage("Register successfully. Try login now!");
            }
            mySocket.close();
        } catch (Exception ex) {
            view.showMessage(ex.getStackTrace().toString());
        }
    }

}

package run;

import controller.UserController;
import controller.UserController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import view.ClientStartView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Valdez
 */
public class ClientRun {
    private static Socket clientSocket = null;
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    
    public static void main(String[] args) throws IOException {
        clientSocket = new Socket("localhost",8888);
        System.out.println(clientSocket);
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        System.out.println(oos);
        ois = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println(ois);
        ClientStartView clientStartView = new ClientStartView(ois,oos);
        UserController userController = new UserController(clientStartView);
        clientStartView.setVisible(true);
    }
    
}

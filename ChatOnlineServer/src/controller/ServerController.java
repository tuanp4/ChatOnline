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
import java.io.PrintStream;
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
public class ServerController extends Thread{

    private ServerView view;
    private Connection con;
    private ServerSocket myServer;
    private Socket clientSocket;
    private int serverPort = 8888;
    
    private static final int maxClientsCount = 500;
    private static final clientThread[] threads = new clientThread[maxClientsCount];
    
    public ServerController(ServerView view) {
        this.view = view;
        this.clientSocket = new Socket();
        getDBConnection("chat_online", "root", "");
        openServer(serverPort);
        view.showMessage("TCP server is running...");
        //while (true) {
        //    listenning();
        //}
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
            //clientSocket = new Socket();
            this.clientSocket = myServer.accept();
            
            
            int i= 0;
                for(i=0; i<maxClientsCount; i++){
                    if (threads[i] == null){
                        System.out.println(i);
                        (threads[i] = new clientThread(clientSocket, threads, con, view)).start();
                        break;
                    }
                }
                
                if(i == maxClientsCount){
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy");
                    os.close();
                    clientSocket.close();
                }
                
        } catch (Exception e) {
            view.showMessage(e.toString());
            e.printStackTrace();
        }
    }
    
    public void run(){
        while(true){
                try {
                //clientSocket = new Socket();
                this.clientSocket = myServer.accept();


                int i= 0;
                    for(i=0; i<maxClientsCount; i++){
                        if (threads[i] == null){
                            System.out.println(i);
                            (threads[i] = new clientThread(clientSocket, threads, con, view)).start();
                            break;
                        }
                    }

                    if(i == maxClientsCount){
                        PrintStream os = new PrintStream(clientSocket.getOutputStream());
                        os.println("Server too busy");
                        os.close();
                        clientSocket.close();
                    }

            } catch (Exception e) {
                view.showMessage(e.toString());
                e.printStackTrace();
            }
        }
    }
}

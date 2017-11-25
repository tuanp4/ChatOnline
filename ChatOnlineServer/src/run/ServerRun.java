/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import controller.ServerController;
import view.ServerView;

/**
 *
 * @author Valdez
 */
public class ServerRun {

    public static void main(String[] args) {
        ServerView serverView = new ServerView();
        ServerController serverController = new ServerController(serverView);
        serverController.start();
    }
    
}

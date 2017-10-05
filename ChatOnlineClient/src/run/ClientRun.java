package run;

import controller.UserController;
import controller.UserController;
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

    public static void main(String[] args) {
        ClientStartView clientStartView = new ClientStartView();
        UserController userController = new UserController(clientStartView);
        clientStartView.setVisible(true);
    }
    
}

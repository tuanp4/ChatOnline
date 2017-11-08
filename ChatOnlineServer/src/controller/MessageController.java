/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.dao.DAOMessage;
import java.util.ArrayList;
import java.util.Arrays;
import model.Message;

/**
 *
 * @author Valdez
 */
public class MessageController {

    DAOMessage dao;

    public MessageController(DAOMessage dao) {
        this.dao = dao;
    }

    public Message returnSentMessage(Message message) {
        return dao.sendMessage(message);
    }

    public ArrayList<Message> returnHistoryMessages(Message message) {
        ArrayList<Message> allHistoryMessages = new ArrayList<>(Arrays.asList(dao.getHistoryMessages(message)));
        return allHistoryMessages;
    }

}

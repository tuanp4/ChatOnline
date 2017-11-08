/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.dao.DAOConversation;
import java.util.ArrayList;
import java.util.Arrays;
import model.Conversation;
import model.User;

/**
 *
 * @author Valdez
 */
public class ConversationController {

    DAOConversation dao;

    public ConversationController(DAOConversation dao) {
        this.dao = dao;
    }

    public ArrayList<Conversation> returnAvailableGroupList(Conversation conversation) {
        ArrayList<Conversation> availableGroupList = new ArrayList<>(Arrays.asList(dao.getAvailableGroupList(conversation)));
        return availableGroupList;
    }

    public Conversation openFriendConversation(Conversation conversation) {
        return dao.openFriendConversation(conversation);
    }

    public Conversation returnGroupConversation(Conversation conversation) {
        return dao.getGroupConversation(conversation);
    }

    public Conversation openGroupConversation(Conversation conversation) {
        return dao.openGroupConversation(conversation);
    }

}

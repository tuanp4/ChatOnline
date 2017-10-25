/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.dao.DAOFriendship;
import model.Friendship;

/**
 *
 * @author Valdez
 */
public class FriendshipController {

    DAOFriendship dao;

    public FriendshipController(DAOFriendship dao) {
        this.dao = dao;
    }

    public String returnFriendshipState(Friendship friendship) {
        return dao.getFriendshipState(friendship);
    }

    public Boolean returnAddFriendRespond(Friendship friendship) {
        return dao.addFriend(friendship);
    }

    public Boolean returnAcceptFriendRequestRespond(Friendship friendship) {
        return dao.acceptFriendRequest(friendship);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.dao.DAOUser;
import model.User;

/**
 *
 * @author Valdez
 */
public class UserController {
    
    DAOUser dao;
    
    public UserController(DAOUser dao) {
        this.dao = dao;
    }
    
    public User checkUserLogin(User user) {
        User userDAO = dao.getUserByUsername(user);
        if (userDAO == null) {
            return null;
        }
        if (user.getUsername().equals(userDAO.getUsername())
                && user.getPassword_hash().equals(userDAO.getPassword_hash())) {
            return userDAO;
        }
        return null;
    }
    
    public boolean signUpUser(User user) {
        User userDAO = dao.getUserByUsernameOrEmail(user);
        if (userDAO != null) {
            return false;
        }
        if (dao.signUpUser(user)) {
            return true;
        }
        return false;
    }
    
    public User changeUserInfo(User user) {
        if (dao.changeUserInfo(user)) {
            return dao.getUserById(user);
        }
        return null;
    }
    
}

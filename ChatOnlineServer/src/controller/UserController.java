/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.dao.DAOUser;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private String MD5(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
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

    public User changeUserAvatar(User user) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH/");
            String filePath = "/avatar/" + dateFormat.format(new Date());
            String fileName = (MD5(Math.random() + "")).substring(0, 16) + "." + user.getAvatarImageExtenstion();
            File fullPathFile = new File("upload" + filePath + fileName);
            if (fullPathFile.exists()) {
                fileName = System.currentTimeMillis() + "_" + fileName;
                fullPathFile = new File("upload" + filePath + fileName);
            }
            fullPathFile.getParentFile().mkdirs();
            byte[] byteReceive = user.getAvatarImageByte();
            FileOutputStream fos = new FileOutputStream(fullPathFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(byteReceive, 0, byteReceive.length);
            fos.close();
            bos.close();
            if (dao.changeUserAvatar(user, filePath + fileName)) {
                return dao.getUserById(user);
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

}

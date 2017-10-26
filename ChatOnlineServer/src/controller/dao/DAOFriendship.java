/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import model.Friendship;

/**
 *
 * @author Valdez
 */
public class DAOFriendship extends IDAO {

    public DAOFriendship(Connection conn) {
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFriendshipState(Friendship friendship) {
        String result = "";
        try {
            String sql = "SELECT `confirm` FROM `friendship` WHERE `sender_id` = ? AND `receiver_id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, friendship.getSender_id());
            this.preStatement.setInt(2, friendship.getReceiver_id());
            rs = this.preStatement.executeQuery();
            if (rs.first()) {
                if (rs.getInt(1) == 0) {
                    result = "Friend Request Sent";
                } else if (rs.getInt(1) == 1) {
                    result = "Friends";
                }
            } else {
                sql = "SELECT `confirm` FROM `friendship` WHERE `sender_id` = ? AND `receiver_id` = ?";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, friendship.getReceiver_id());
                this.preStatement.setInt(2, friendship.getSender_id());
                rs = this.preStatement.executeQuery();
                if (rs.first()) {
                    if (rs.getInt(1) == 0) {
                        result = "Respond to Friend Request";
                    } else if (rs.getInt(1) == 1) {
                        result = "Friends";
                    }
                } else {
                    result = "Add Friend";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public Boolean addFriend(Friendship friendship) {
        try {
            String sql = "INSERT INTO `friendship`(`sender_id`, `receiver_id`) VALUES (?, ?)";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, friendship.getSender_id());
            this.preStatement.setInt(2, friendship.getReceiver_id());
            int check = this.preStatement.executeUpdate();
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean acceptFriendRequest(Friendship friendship) {
        try {
            String sql = "UPDATE `friendship` SET `confirm` = 1 WHERE `sender_id` = ? AND `receiver_id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, friendship.getReceiver_id());
            this.preStatement.setInt(2, friendship.getSender_id());
            int check = this.preStatement.executeUpdate();
            if (check != 0) {
                sql = "INSERT INTO `conversation`(`description`) VALUES (?)";
                this.preStatement = this.conn.prepareStatement(sql);
                if (friendship.getSender_id() < friendship.getReceiver_id()) {
                    this.preStatement.setString(1, friendship.getSender_id() + " " + friendship.getReceiver_id());
                } else {
                    this.preStatement.setString(1, friendship.getReceiver_id() + " " + friendship.getSender_id());
                }
                this.preStatement.executeUpdate();
                //--------------------------------------------------------------
                int conversationID = -1;
                sql = "SELECT `id` FROM `conversation` WHERE `description` = ?";
                this.preStatement = this.conn.prepareStatement(sql);
                if (friendship.getSender_id() < friendship.getReceiver_id()) {
                    this.preStatement.setString(1, friendship.getSender_id() + " " + friendship.getReceiver_id());
                } else {
                    this.preStatement.setString(1, friendship.getReceiver_id() + " " + friendship.getSender_id());
                }
                rs = this.preStatement.executeQuery();
                while (rs.next()) {
                    conversationID = rs.getInt(1);
                    break;
                }
                //--------------------------------------------------------------
                String senderUsername = "";
                sql = "SELECT `username`, `display_name` FROM `user` WHERE `id` = ?";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, friendship.getSender_id());
                rs = this.preStatement.executeQuery();
                while (rs.next()) {
                    if (rs.getString(2) == null || rs.getString(2).equals("")) {
                        senderUsername = rs.getString(1);
                    } else {
                        senderUsername = rs.getString(2);
                    }
                    break;
                }
                //--------------------------------------------------------------
                String receiverUsername = "";
                sql = "SELECT `username`, `display_name` FROM `user` WHERE `id` = ?";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, friendship.getReceiver_id());
                rs = this.preStatement.executeQuery();
                while (rs.next()) {
                    if (rs.getString(2) == null || rs.getString(2).equals("")) {
                        receiverUsername = rs.getString(1);
                    } else {
                        receiverUsername = rs.getString(2);
                    }
                    break;
                }
                //--------------------------------------------------------------
                sql = "INSERT INTO `group`(`conversation_id`, `user_id`, `nick_name`) VALUES (?, ?, ?)";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, conversationID);
                this.preStatement.setInt(2, friendship.getSender_id());
                this.preStatement.setString(3, senderUsername);
                this.preStatement.executeUpdate();
                //--------------------------------------------------------------
                sql = "INSERT INTO `group`(`conversation_id`, `user_id`, `nick_name`) VALUES (?, ?, ?)";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, conversationID);
                this.preStatement.setInt(2, friendship.getReceiver_id());
                this.preStatement.setString(3, receiverUsername);
                this.preStatement.executeUpdate();
            }
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

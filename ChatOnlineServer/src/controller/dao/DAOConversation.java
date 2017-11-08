/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import model.Conversation;

/**
 *
 * @author Valdez
 */
public class DAOConversation extends IDAO {

    public DAOConversation(Connection conn) {
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

    public Conversation[] getAvailableGroupList(Conversation conversation) {
        Vector<Conversation> vector = new Vector<Conversation>();
        Conversation[] result;
        try {
            String sql = "SELECT `id`, `name`, `avatar_path`, `description` FROM `conversation` INNER JOIN `group` ON `conversation`.`id` = `group`.`conversation_id` WHERE `name` IS NOT NULL AND `user_id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, conversation.getMainUserId());
            rs = this.preStatement.executeQuery();
            int i = 0;
            while (rs.next()) {
                Conversation temp = new Conversation();
                temp.setId(rs.getInt(1));
                temp.setName(rs.getString(2));
                temp.setAvatar_path(rs.getString(3));
                temp.setDescription(rs.getString(4));
                vector.add(temp);
                i++;
            }
            result = new Conversation[i];
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vector.toArray(result);
    }

    public Conversation openFriendConversation(Conversation conversation) {
        try {
            String sql = "SELECT `id` FROM `conversation` WHERE `conversation`.`name` IS NULL AND `conversation`.`description` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            if (conversation.getMainUserId() < conversation.getFriendId()) {
                this.preStatement.setString(1, conversation.getMainUserId() + " " + conversation.getFriendId());
            } else {
                this.preStatement.setString(1, conversation.getFriendId() + " " + conversation.getMainUserId());
            }
            rs = this.preStatement.executeQuery();
            Conversation temp = null;
            while (rs.next()) {
                temp = new Conversation();
                temp.setId(rs.getInt(1));
                break;
            }
            temp.setMainUserId(conversation.getMainUserId());
            temp.setFriendId(conversation.getFriendId());
            sql = "SELECT `avatar_path` FROM `user` WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, conversation.getMainUserId());
            rs = this.preStatement.executeQuery();
            while (rs.next()) {
                temp.setMainUserAvatarPath(rs.getString(1));
                break;
            }
            sql = "SELECT `username`, `display_name`, `avatar_path` FROM `user` WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, conversation.getFriendId());
            rs = this.preStatement.executeQuery();
            while (rs.next()) {
                if (rs.getString(2) != null && !rs.getString(2).equals("")) {
                    temp.setFriendDisplayName(rs.getString(2));
                } else {
                    temp.setFriendDisplayName(rs.getString(1));
                }
                temp.setFriendAvatarPath(rs.getString(3));
                break;
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Conversation getGroupConversation(Conversation conversation) {
        try {
            String sql = "INSERT INTO `conversation`(`name`) VALUES (?)";
            this.preStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.preStatement.setString(1, conversation.getName());
            this.preStatement.executeUpdate();
            ResultSet rs = this.preStatement.getGeneratedKeys();
            Conversation temp = null;
            if (rs.next()) {
                temp = new Conversation();
                temp.setId(rs.getInt(1));
                for (int i = 0; i < conversation.getParticipantId().size(); i++) {
                    sql = "INSERT INTO `group`(`conversation_id`, `user_id`, `nick_name`) VALUES (?, ?, ?)";
                    this.preStatement = this.conn.prepareStatement(sql);
                    this.preStatement.setInt(1, temp.getId());
                    this.preStatement.setInt(2, conversation.getParticipantId().get(i));
                    this.preStatement.setString(3, conversation.getParticipantDisplayName().get(i));
                    this.preStatement.executeUpdate();
                }
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Conversation openGroupConversation(Conversation conversation) {
        try {
            String sql = "SELECT `name`, `avatar_path` FROM `conversation` WHERE `conversation`.`id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, conversation.getId());
            rs = this.preStatement.executeQuery();
            Conversation temp = null;
            while (rs.next()) {
                temp = new Conversation();
                temp.setId(conversation.getId());
                temp.setName(rs.getString(1));
                temp.setAvatar_path(rs.getString(2));
                temp.setMainUserId(conversation.getMainUserId());
                break;
            }
            sql = "SELECT `avatar_path` FROM `user` WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, conversation.getMainUserId());
            rs = this.preStatement.executeQuery();
            while (rs.next()) {
                temp.setMainUserAvatarPath(rs.getString(1));
                break;
            }
            sql = "SELECT `username`, `display_name`, `status` FROM `user` INNER JOIN `group` ON `user`.`id` = `group`.`user_id` WHERE `group`.`conversation_id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, conversation.getId());
            rs = this.preStatement.executeQuery();
            ArrayList<String> otherParticipantDisplayName = new ArrayList<String>();
            ArrayList<Integer> otherParticipantStatus = new ArrayList<Integer>();
            while (rs.next()) {
                if (rs.getString(2) != null && !rs.getString(2).equals("")) {
                    otherParticipantDisplayName.add(rs.getString(2));
                } else {
                    otherParticipantDisplayName.add(rs.getString(1));
                }
                otherParticipantStatus.add(rs.getInt(3));
            }
            temp.setParticipantDisplayName(otherParticipantDisplayName);
            temp.setParticipantStatus(otherParticipantStatus);
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.Message;

/**
 *
 * @author Valdez
 */
public class DAOMessage extends IDAO {

    public DAOMessage(Connection conn) {
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

    public Message sendMessage(Message message) {
        try {
            String sql = "INSERT INTO `message` (`conversation_id`, `user_id`, `content`) VALUES (?, ?, ?)";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, message.getConversation_id());
            this.preStatement.setInt(2, message.getUser_id());
            this.preStatement.setString(3, message.getContent());
            int check = this.preStatement.executeUpdate();
            if (check != 0) {
                sql = "SELECT `nick_name` FROM `group` WHERE `conversation_id` = ? AND `user_id` = ?";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, message.getConversation_id());
                this.preStatement.setInt(2, message.getUser_id());
                rs = this.preStatement.executeQuery();
                while (rs.next()) {
                    message.setNick_name(rs.getString(1));
                    break;
                }
                return message;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Message[] getHistoryMessages(Message message) {
        Vector<Message> vector = new Vector<Message>();
        Message[] result;
        try {
            String sql = "SELECT `user_id`, `content` FROM `message` WHERE `conversation_id` = ? ORDER BY `created_at`";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, message.getConversation_id());
            rs = this.preStatement.executeQuery();
            int i = 0;
            while (rs.next()) {
                Message temp = new Message();
                temp.setUser_id(rs.getInt(1));
                temp.setContent(rs.getString(2));
                sql = "SELECT `nick_name` FROM `group` WHERE `conversation_id` = ? AND `user_id` = ?";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, message.getConversation_id());
                this.preStatement.setInt(2, temp.getUser_id());
                ResultSet nick_name = this.preStatement.executeQuery();
                while (nick_name.next()) {
                    temp.setNick_name(nick_name.getString(1));
                    break;
                }
                vector.add(temp);
                i++;
            }
            result = new Message[i];
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vector.toArray(result);
    }

}

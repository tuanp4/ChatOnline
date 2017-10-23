/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
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
                sql = "SELECT `nick_name` FROM `group` WHERE `conversation_id` = ?";
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setInt(1, message.getConversation_id());
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

}

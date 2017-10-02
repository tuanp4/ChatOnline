/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author Valdez
 */
public class DAOUser extends IDAO {

    public DAOUser(Connection conn) {
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

    public User getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM user WHERE username = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, username);
            rs = this.preStatement.executeQuery();
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword_hash(rs.getString(3));
                user.setDisplay_name(rs.getString(4));
                user.setAvatar_path(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setPhone_number(rs.getString(7));
                user.setDescription(rs.getString(8));
                user.setStatus(rs.getInt(9));
                break;
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByUsernameOrEmail(String username, String email) {
        try {
            String sql = "SELECT * FROM user WHERE username = ? OR email = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, username);
            this.preStatement.setString(2, email);
            rs = this.preStatement.executeQuery();
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                break;
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean signUpUser(User user) {
        try {
            String sql = "INSERT INTO user (username, password_hash, email) VALUES (?,?,?)";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getUsername());
            this.preStatement.setString(2, user.getPassword_hash());
            this.preStatement.setString(3, user.getEmail());
            int check = this.preStatement.executeUpdate();
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

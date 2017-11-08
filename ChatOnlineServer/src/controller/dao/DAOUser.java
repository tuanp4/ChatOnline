/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
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

    public User getUserByUsername(User user) {
        try {
            String sql = "SELECT * FROM `user` WHERE `username` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getUsername());
            rs = this.preStatement.executeQuery();
            User temp = null;
            while (rs.next()) {
                temp = new User();
                temp.setId(rs.getInt(1));
                temp.setUsername(rs.getString(2));
                temp.setPassword_hash(rs.getString(3));
                temp.setDisplay_name(rs.getString(4));
                temp.setGender(rs.getInt(5));
                temp.setAvatar_path(rs.getString(6));
                temp.setEmail(rs.getString(7));
                temp.setPhone_number(rs.getString(8));
                temp.setDescription(rs.getString(9));
                temp.setStatus(rs.getInt(10));
                break;
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByUsernameOrEmail(User user) {
        try {
            String sql = "SELECT * FROM `user` WHERE `username` = ? OR `email` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getUsername());
            this.preStatement.setString(2, user.getEmail());
            rs = this.preStatement.executeQuery();
            User temp = null;
            while (rs.next()) {
                temp = new User();
                temp.setId(rs.getInt(1));
                break;
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(User user) {
        try {
            String sql = "SELECT * FROM `user` WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, user.getId());
            rs = this.preStatement.executeQuery();
            User temp = null;
            while (rs.next()) {
                temp = new User();
                temp.setId(rs.getInt(1));
                temp.setUsername(rs.getString(2));
                temp.setPassword_hash(rs.getString(3));
                temp.setDisplay_name(rs.getString(4));
                temp.setGender(rs.getInt(5));
                temp.setAvatar_path(rs.getString(6));
                temp.setEmail(rs.getString(7));
                temp.setPhone_number(rs.getString(8));
                temp.setDescription(rs.getString(9));
                temp.setStatus(rs.getInt(10));
                break;
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean signUpUser(User user) {
        try {
            String sql = "INSERT INTO `user` (`username`, `password_hash`, `email`, `gender`) VALUES (?, ?, ?, ?)";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getUsername());
            this.preStatement.setString(2, user.getPassword_hash());
            this.preStatement.setString(3, user.getEmail());
            this.preStatement.setInt(4, user.getGender());
            int check = this.preStatement.executeUpdate();
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean changeUserInfo(User user) {
        try {
            String sql = "UPDATE `user` SET `display_name` = ?, `email` = ?, `phone_number` = ? WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getDisplay_name());
            this.preStatement.setString(2, user.getEmail());
            this.preStatement.setString(3, user.getPhone_number());
            this.preStatement.setInt(4, user.getId());
            int check = this.preStatement.executeUpdate();
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean changeUserAvatar(User user, String avatar_path) {
        try {
            String sql = "UPDATE `user` SET `avatar_path` = ? WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, avatar_path);
            this.preStatement.setInt(2, user.getId());
            int check = this.preStatement.executeUpdate();
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean changeUserStatus(User user) {
        try {
            String sql = "UPDATE `user` SET `status` = ? WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, user.getStatus());
            this.preStatement.setInt(2, user.getId());
            int check = this.preStatement.executeUpdate();
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean changeUserPassword(User user) {
        try {
            String sql = "UPDATE `user` SET `password_hash` = ? WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getPassword_hash());
            this.preStatement.setInt(2, user.getId());
            int check = this.preStatement.executeUpdate();
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean changeUserDescription(User user) {
        try {
            String sql = "UPDATE `user` SET `description` = ? WHERE `id` = ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getDescription());
            this.preStatement.setInt(2, user.getId());
            int check = this.preStatement.executeUpdate();
            return (check != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User[] getAvailableFriendList(User user) {
        Vector<User> vector = new Vector<User>();
        User[] result;
        try {
            String sql = "SELECT `id`, `username`, `display_name`, `avatar_path`, `description`, `status` "
                    + "FROM `user` INNER JOIN `friendship` ON `user`.`id` = `friendship`.`sender_id` "
                    + "WHERE `friendship`.`receiver_id` = ? AND `friendship`.`confirm` = 1 AND `user`.`status` != 3 "
                    + "UNION "
                    + "SELECT `id`, `username`, `display_name`, `avatar_path`, `description`, `status` "
                    + "FROM `user` INNER JOIN `friendship` ON `user`.`id` = `friendship`.`receiver_id` "
                    + "WHERE `friendship`.`sender_id` = ? AND `friendship`.`confirm` = 1 AND `user`.`status` != 3 "
                    + "ORDER BY `status`";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, user.getId());
            this.preStatement.setInt(2, user.getId());
            rs = this.preStatement.executeQuery();
            int i = 0;
            while (rs.next()) {
                User temp = new User();
                temp.setId(rs.getInt(1));
                temp.setUsername(rs.getString(2));
                temp.setDisplay_name(rs.getString(3));
                temp.setAvatar_path(rs.getString(4));
                temp.setDescription(rs.getString(5));
                temp.setStatus(rs.getInt(6));
                vector.add(temp);
                i++;
            }
            result = new User[i];
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vector.toArray(result);
    }

    public User[] getSuggestedUsers(User user) {
        Vector<User> vector = new Vector<User>();
        User[] result;
        try {
            String sql = "SELECT `id`, `username`, `display_name`, `gender`, `avatar_path`, `email`, `phone_number` FROM `user` WHERE `id` != ? AND `username` LIKE ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, user.getId());
            this.preStatement.setString(2, "%" + user.getUsername() + "%");
            rs = this.preStatement.executeQuery();
            int i = 0;
            while (rs.next()) {
                User temp = new User();
                temp.setId(rs.getInt(1));
                temp.setUsername(rs.getString(2));
                temp.setDisplay_name(rs.getString(3));
                temp.setGender(rs.getInt(4));
                temp.setAvatar_path(rs.getString(5));
                temp.setEmail(rs.getString(6));
                temp.setPhone_number(rs.getString(7));
                vector.add(temp);
                i++;
            }
            result = new User[i];
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vector.toArray(result);
    }

    public User[] getSuggestedParticipants(User user) {
        Vector<User> vector = new Vector<User>();
        User[] result;
        try {
            String sql = "SELECT `id`, `username`, `display_name` "
                    + "FROM `user` INNER JOIN `friendship` ON `user`.`id` = `friendship`.`sender_id` "
                    + "WHERE `friendship`.`receiver_id` = ? AND `friendship`.`confirm` = 1 AND `user`.`username` LIKE ? "
                    + "UNION "
                    + "SELECT `id`, `username`, `display_name` "
                    + "FROM `user` INNER JOIN `friendship` ON `user`.`id` = `friendship`.`receiver_id` "
                    + "WHERE `friendship`.`sender_id` = ? AND `friendship`.`confirm` = 1 AND `user`.`username` LIKE ?";
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setInt(1, user.getId());
            this.preStatement.setString(2, "%" + user.getUsername() + "%");
            this.preStatement.setInt(3, user.getId());
            this.preStatement.setString(4, "%" + user.getUsername() + "%");
            rs = this.preStatement.executeQuery();
            int i = 0;
            while (rs.next()) {
                User temp = new User();
                temp.setId(rs.getInt(1));
                temp.setUsername(rs.getString(2));
                temp.setDisplay_name(rs.getString(3));
                vector.add(temp);
                i++;
            }
            result = new User[i];
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vector.toArray(result);
    }

}

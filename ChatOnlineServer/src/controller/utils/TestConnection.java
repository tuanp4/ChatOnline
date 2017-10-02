/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Valdez
 */
public class TestConnection {

    public static Connection getMyConnection() throws SQLException,
            ClassNotFoundException {
        // Sử dụng Mysql.
        // Bạn có thể thay thế bởi Database nào đó.
        return MySQLConnUtils.getMySQLConnection();
    }

    //
    // Test Connection ...
    //
    public static void main(String[] args) throws SQLException,
            ClassNotFoundException {

        System.out.println("Get connection ... ");

        // Lấy ra đối tượng Connection kết nối vào database.
        Connection conn = TestConnection.getMyConnection();

        System.out.println("Get connection " + conn);

        System.out.println("Done!");
    }

}

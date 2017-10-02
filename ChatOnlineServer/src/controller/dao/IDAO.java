/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Valdez
 */
public abstract class IDAO {

    Statement statement;
    PreparedStatement preStatement;
    Connection conn;
    ResultSet rs;

    public abstract void closeConnection();

}

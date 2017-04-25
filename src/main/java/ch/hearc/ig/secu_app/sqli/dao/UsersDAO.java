/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.secu_app.sqli.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tim.sermier
 */
public class UsersDAO {
    private final String DBURL = "jdbc:oracle:thin:@db.ig.he-arc.ch:1521:ens2";
    private final String DBUSER = "MATHIAS_MEIER";
    private final String DBPWD = "MEIER_MATHIAS";
    private Connection cnn = null;
    
    public void openConnection() throws SQLException{
        cnn = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
        cnn.setAutoCommit(false);
    }
    
    public void closeConnection() throws SQLException{
        cnn.close();
    }
    
    public void commitTransaction() throws SQLException{
        cnn.commit();
    }
    
    public void RollbackTransaction() throws SQLException{
        cnn.rollback();
    }
    
    public List<String> getUsersWrongMethod(String username, String password) throws SQLException{
        List<String> users = new ArrayList<>();
        PreparedStatement pstmt = cnn.prepareStatement("SELECT username FROM USERS WHERE username = '" + username + "' and password = '" + password + "'");
    }
    
}

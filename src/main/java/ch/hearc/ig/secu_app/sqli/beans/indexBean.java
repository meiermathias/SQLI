/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.secu_app.sqli.beans;

import ch.hearc.ig.secu_app.sqli.dao.UsersDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author tim.sermier
 */
@Named(value = "indexBean")
@SessionScoped
public class indexBean implements Serializable {

    private String username;
    private String password;
    private List<String> users;
    private UsersDAO usersDAO;
    private boolean escapeWrongchar;
    private boolean errorMsg;

    public boolean isErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(boolean errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    

    public boolean isEscapeWrongchar() {
        return escapeWrongchar;
    }

    public void setEscapeWrongchar(boolean escapeWrongchar) {
        this.escapeWrongchar = escapeWrongchar;
    }

    public void changeState() {
        this.escapeWrongchar = !this.escapeWrongchar;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Creates a new instance of indexBean
     */
    public indexBean() {
    }

    @PostConstruct
    public void init() {
        this.users = new ArrayList<>();
    }

    public String authenticateWrong() {
        try {
            if(escapeWrongchar){
                username = username.replace("'", "").replace("\"", "");
                password = password.replace("'", "").replace("\"", "");
            }
            usersDAO = new UsersDAO();
            usersDAO.openConnection();
            for (String s : usersDAO.getUsersWrongMethod(username, password)) {
                users.add(s);
            }

            usersDAO.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(indexBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (users.size() > 0) {
            errorMsg = false;
            return "result.xhtml";
        } else {
            errorMsg = true;
            return "index.xhtml";
        }
    }

    public String authenticateRight() {
        try {
            usersDAO = new UsersDAO();
            usersDAO.openConnection();
            for (String s : usersDAO.getUsersRightMethod(username, password)) {
                users.add(s);
            }
            usersDAO.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(indexBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (users.size() > 0) {
            errorMsg = false;
            return "result.xhtml";
        } else {
            errorMsg = true;
            return "index.xhtml";
        }
    }

}

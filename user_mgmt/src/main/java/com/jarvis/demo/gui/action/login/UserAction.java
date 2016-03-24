package com.jarvis.demo.gui.action.login;

import com.jarvis.demo.gui.ForwardURL;

/**
 * Created by Jarvis on 3/24/16.
 */
public class UserAction {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String execute() {
        return ForwardURL.SUCCESS;
    }
}

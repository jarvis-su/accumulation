package com.jarvis.users;

import java.util.List;

/**
 * Created by Jarvis on 4/16/16.
 */
public class Role {

    private UserType userType;
    private List<Privilege> rolePrivileges;

    public List<Privilege> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(List<Privilege> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }

    public UserType getUserType() {

        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

package com.jarvis.users;

import java.util.Date;
import java.util.List;

/**
 * Created by Jarvis on 4/16/16.
 */
public class Role {
    private Integer roleId;
    private String roleDesc;
    private Date createdDate;

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

package com.jarvis.users;

import java.util.Date;
import java.util.List;

/**
 * Created by Jarvis on 4/11/16.
 */
public class ActiveUserImpl implements IActiveUser {

    private Integer userId;
    private String loginName;
    private String password;
    private Integer badPasswordCnt;
    private Date createdDate;
    private Integer userTypeId;

    private Role userRole;

    private List<Privilege> userPrivileges;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBadPasswordCnt() {
        return badPasswordCnt;
    }

    public void setBadPasswordCnt(Integer badPasswordCnt) {
        this.badPasswordCnt = badPasswordCnt;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public List<Privilege> getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(List<Privilege> userPrivileges) {
        this.userPrivileges = userPrivileges;
    }
}

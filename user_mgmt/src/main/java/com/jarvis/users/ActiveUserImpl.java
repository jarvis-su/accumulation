package com.jarvis.users;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jarvis on 4/11/16.
 */
public class ActiveUserImpl implements IActiveUser {
    @Override
    public Integer getIdentifier() {
        return null;
    }

    @Override
    public String getSessionIdentifier() {
        return null;
    }

    @Override
    public void setLastActive(Calendar lastActive) {

    }

    @Override
    public Calendar getLastActive() {
        return null;
    }

    @Override
    public void setSessionStartTime(Calendar startTime) {

    }

    @Override
    public Calendar getSessionStartTime() {
        return null;
    }

    @Override
    public int getUserType() {
        return 0;
    }

    @Override
    public boolean[] getPrivilegeBitmap() {
        return new boolean[0];
    }

    @Override
    public Calendar getPwdExpireDate() {
        return null;
    }

    @Override
    public boolean hasPwdExpired() {
        return false;
    }

    @Override
    public int[] getOfficeIds() {
        return new int[0];
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public int getAgencyId() {
        return 0;
    }

    @Override
    public void setUserEndDate(Date date) {

    }

    @Override
    public Date getUserEndDate() {
        return null;
    }
}

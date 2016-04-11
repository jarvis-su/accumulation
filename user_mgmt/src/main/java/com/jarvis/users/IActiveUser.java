package com.jarvis.users;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jarvis on 4/11/16.
 */
public interface IActiveUser {
    public Integer getIdentifier();

    public String getSessionIdentifier();

    public void setLastActive(Calendar lastActive);

    public Calendar getLastActive();

    public void setSessionStartTime(Calendar startTime);

    public Calendar getSessionStartTime();

    public int getUserType();

    public boolean[] getPrivilegeBitmap();

    public Calendar getPwdExpireDate();

    public boolean hasPwdExpired();

    public int[] getOfficeIds();

    public String getName();

    public int getUserId();

    public int getAgencyId();

    public void setUserEndDate(Date date);

    public Date getUserEndDate();


}

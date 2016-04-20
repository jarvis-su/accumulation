package com.jarvis.supporter;

/**
 * Created by Jarvis on 4/20/16.
 */
public class UserMgmtBusinessServerManager extends BusinessServerManager {
    @Override
    protected void initLookupValues() {
        if(lookupValues == null){
            lookupValues = new LookupValues();
        }
    }
}

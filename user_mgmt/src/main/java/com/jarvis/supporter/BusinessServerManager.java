package com.jarvis.supporter;

import java.util.ResourceBundle;

/**
 * Created by Jarvis on 4/20/16.
 */
public abstract class BusinessServerManager {

    private static final ResourceBundle RUNTIME_BUNDLE = ResourceBundle.getBundle("jarvis.demo.runtime");
    private static BusinessServerManager soleInstance = null;

    public static BusinessServerManager getSoleInstance() {
        if (soleInstance == null) {
            synchronized (BusinessServerManager.class) {
                if (soleInstance == null) {
                    // This should terminate the process since without this value, system should not start
                    String name = RUNTIME_BUNDLE.getString("bs.business.server.manager.class");
                    try {
                        BusinessServerManager tmpInstance = (BusinessServerManager) Class.forName(name).newInstance();
                        soleInstance = tmpInstance;
                    } catch (Exception e) {
                        throw new IllegalArgumentException(
                                "Error while trying to instantiate BusinessServerManager class " +
                                        name);
                    }
                }
            }
        }
        return soleInstance;
    }

    protected LookupValues lookupValues;

    protected abstract void initLookupValues();


    public synchronized LookupValues getLookupValues() {
        if (lookupValues == null) {
            initLookupValues();
        }
        return lookupValues;
    }

    public static void  test(){

    }


}

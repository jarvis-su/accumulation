package com.jarvis.service.ws.server;

import com.jarvis.users.ActiveUser;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
import org.apache.log4j.Logger;

/**
 * Created by Jarvis on 4/11/16.
 */
public class PlainTextPasswordValidator implements PasswordValidationCallback.PasswordValidator {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public boolean validate(PasswordValidationCallback.Request request)
            throws PasswordValidationCallback.PasswordValidationException {

        logger.info("Using configured PlainTextPasswordValidator................");
        PasswordValidationCallback.PlainTextPasswordRequest plainTextRequest =
                (PasswordValidationCallback.PlainTextPasswordRequest) request;
        ActiveUser user = null;
        //Check DB for user
        try {
            logger.info("Accessing DB for Authentication................");
//            user = WSManager.loginUser(plainTextRequest.getUsername(),
//                    plainTextRequest.getPassword(),
//                    String.valueOf(LookupValues.MODULE_WEBSERVICE));
        } catch (Exception e) {
            //Don't throw EccWSException. Let XWSSecurityException handle it in the security handler
            logger.error("Authentication failed for user " + plainTextRequest.getUsername() + ": " + e.getLocalizedMessage());
            return false;
        }

        if (user != null) {
            logger.info("User " + user.getName() + " Authenticated");
        }
        return (user != null) ? true : false;
    }


}

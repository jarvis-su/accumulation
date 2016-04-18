package com.jarvis.service.ws.server;

import org.apache.log4j.Logger;

import com.jarvis.users.ActiveUserImpl;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback;

/**
 * Created by Jarvis on 4/11/16.
 */
public class PlainTextPasswordValidator implements PasswordValidationCallback.PasswordValidator {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @SuppressWarnings({ "unused", "null" })
	@Override
	public boolean validate(PasswordValidationCallback.Request request)
            throws PasswordValidationCallback.PasswordValidationException {

        logger.info("Using configured PlainTextPasswordValidator................");
        PasswordValidationCallback.PlainTextPasswordRequest plainTextRequest =
                (PasswordValidationCallback.PlainTextPasswordRequest) request;
        ActiveUserImpl user = null;
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
            logger.info("User " + user.getLoginName() + " Authenticated");
        }
        return (user != null) ? true : false;
    }


}

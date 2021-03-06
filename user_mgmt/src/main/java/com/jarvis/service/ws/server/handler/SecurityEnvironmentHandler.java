package com.jarvis.service.ws.server.handler;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.jarvis.service.ws.server.MyTimestampValidator;
import com.jarvis.service.ws.server.PlainTextPasswordValidator;
import com.jarvis.supporter.logger.Log4jAdapter;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
import com.sun.xml.wss.impl.callback.TimestampValidationCallback;

/**
 * Created by Jarvis on 4/11/16.
 */
public class SecurityEnvironmentHandler implements CallbackHandler {

    private Log4jAdapter logger = Log4jAdapter.getLog4jAdapter(this.getClass().getName());
    private static final UnsupportedCallbackException unsupported =
            new UnsupportedCallbackException(null, "Unsupported Callback Type Encountered");

    /**
     * Creates a new instance of SecurityEnvironmentHandler
     */
    public SecurityEnvironmentHandler() {

    }

    @Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof PasswordValidationCallback) {
                PasswordValidationCallback cb = (PasswordValidationCallback) callbacks[i];
                if (cb.getRequest() instanceof PasswordValidationCallback.PlainTextPasswordRequest) {
                    logger.info("PasswordValidationCallback.PlainTextPasswordRequest is being used for Authentication");
                    cb.setValidator(new PlainTextPasswordValidator());

                }
            } else if (callbacks[i] instanceof TimestampValidationCallback) {
                TimestampValidationCallback cb = (TimestampValidationCallback) callbacks[i];
                cb.setValidator(new MyTimestampValidator());

            } else {
                logger.info("Callback :  " + callbacks[i].toString());
                throw unsupported;
            }
        }
    }


}

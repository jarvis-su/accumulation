package com.jarvis.service.ws.server.handler;

import com.sun.xml.wss.*;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jarvis on 4/11/16.
 */
public class SecurityHandler extends WebserviceHandler {

    protected final static String SERVER_XML = "user-pass-authenticate-server.xml";

    protected XWSSProcessor sprocessor;

    @Override
    protected void initialize(SOAPMessageContext messageContext) {
        logger = Logger.getLogger(SecurityHandler.class.getName());

        QName qName = (QName) messageContext.get(MessageContext.WSDL_INTERFACE);
        logger.info("Initializing secured handler for [" + qName.getLocalPart() + "] endpoint\n");

        //Get the location of the server's authentication file
        //TODO get the file name by some method.....
        String serverAuthXML = SERVER_XML;
        logger.info("   using server auth file at:  " + serverAuthXML);

        FileInputStream serverConfig = null;
        try {
            serverConfig = new java.io.FileInputStream(new java.io.File(serverAuthXML));
            //Create a XWSSProcessFactory.
            XWSSProcessorFactory factory = XWSSProcessorFactory.newInstance();
            sprocessor = factory
                    .createProcessorForSecurityConfiguration(serverConfig, new SecurityEnvironmentHandler());
            serverConfig.close();
        } catch (Exception e) {
            logger.error("Ecc Web Service Security Handler was not Intitialized because : " + e.getMessage() +
                    "\n Going to throw RuntimeException so that system could be configured properly");
            throw new RuntimeException(e);
        }
        initialized = true;
    }

    /**
     * Method to the the SOAP header
     */
    @Override
    public Set<QName> getHeaders() {
        QName securityHeader = new QName(
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "wsse");
        HashSet<QName> headers = new HashSet<>();
        headers.add(securityHeader);
        return headers;
    }

    @Override
    protected void processSoapMessage(boolean inBound, SOAPMessageContext messageContext, SOAPMessage message)
            throws XWSSecurityException {

        super.processSoapMessage(inBound, messageContext, message);

        if (inBound) {
            ProcessingContext context = sprocessor.createProcessingContext(message);
            addExtraPropToContext(messageContext, context);
            context.setSOAPMessage(message);
            SOAPMessage verifiedMsg = sprocessor.verifyInboundMessage(context);
            logger.info("Requester " + SubjectAccessor.getRequesterSubject(context));

            messageContext.setMessage(verifiedMsg);
        }
    }

    @SuppressWarnings("unchecked")
    protected void addExtraPropToContext(SOAPMessageContext messageContext, ProcessingContext context) {
        context.getExtraneousProperties().putAll(messageContext);
    }


}

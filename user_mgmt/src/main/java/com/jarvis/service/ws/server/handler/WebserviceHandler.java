package com.jarvis.service.ws.server.handler;

import com.sun.xml.wss.XWSSecurityException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Jarvis on 4/11/16.
 */
public class WebserviceHandler implements SOAPHandler<SOAPMessageContext> {


    protected static Logger logger = Logger.getLogger(WebserviceHandler.class.getName());
    protected static Transformer tf = null;

    protected boolean initialized = false;

    public WebserviceHandler() {
        TransformerFactory tff = TransformerFactory.newInstance();
        try {
            tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
        } catch (TransformerConfigurationException e) {
            logger.error("TransformerConfigurationException Error: " + e.getMessage());
        }
    }

    protected void initialize(SOAPMessageContext messageContext) {
        QName qName = (QName) messageContext.get(MessageContext.WSDL_INTERFACE);
        logger.info("Initializing handler for [" + qName.getLocalPart() + "] endpoint\n");
        initialized = true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    @Override
    public void close(MessageContext arg0) {
    }

    @Override
    public boolean handleFault(SOAPMessageContext arg0) {
        return true;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {

        if (!initialized) {
            initialize(messageContext);
        }

        Boolean outMessageIndicator = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        SOAPMessage message = messageContext.getMessage();
        try {
            processSoapMessage(!outMessageIndicator.booleanValue(), messageContext, message);
        } catch (XWSSecurityException ex) {
            logger.error(" XWSSecurityException - Error occured in processing header: " +
                    ex.getCause().getMessage());
            throw new WebServiceException(ex.getCause().getMessage(), ex.getCause());
        } catch (Exception ex) {
            logger.error(" Exception - Error occured in processing header: " + ex.getCause().getMessage());
            throw new WebServiceException(ex.getCause().getMessage(), ex.getCause());
        }

        return true;
    }

    protected void processSoapMessage(boolean inBound, SOAPMessageContext messageContext, @SuppressWarnings("unused") SOAPMessage message)
            throws XWSSecurityException {
        HttpServletRequest req = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
        String ipAddress = "?.?.?.?";
        if (req != null) {
            ipAddress = req.getRemoteAddr();
        }
        log(messageContext, "Processing " + (inBound ? "Inbound" : "Outbound") + " Message [" + ipAddress + "]");
    }

    protected void log(SOAPMessageContext messageContext, String label) {

        try {
            SOAPMessage msg = messageContext.getMessage();

            Source sc = msg.getSOAPPart().getContent();

            ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(streamOut);
            tf.transform(sc, result);

            logger.info(label + ": \n" + streamOut.toString());

        } catch (SOAPException e) {
            logger.error("SOAPException Error: " + e.getMessage());
        } catch (TransformerException e) {
            logger.error("TransformerException Error: " + e.getMessage());
        }
    }


}

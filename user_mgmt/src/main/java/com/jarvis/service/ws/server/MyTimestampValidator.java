package com.jarvis.service.ws.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.jarvis.supporter.logger.Log4jAdapter;
import com.sun.xml.wss.impl.callback.TimestampValidationCallback;
import com.sun.xml.wss.impl.callback.TimestampValidationCallback.Request;
import com.sun.xml.wss.impl.callback.TimestampValidationCallback.TimestampValidationException;

/**
 * Created by Jarvis on 4/11/16.
 */
public class MyTimestampValidator implements TimestampValidationCallback.TimestampValidator {
    private static Log4jAdapter logger = (Log4jAdapter) Log4jAdapter.getLogger(MyTimestampValidator.class);
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    @Override
	public void validate(Request request) throws TimestampValidationException {
        if (request instanceof TimestampValidationCallback.UTCTimestampRequest) {
            TimestampValidationCallback.UTCTimestampRequest utcRequest = (TimestampValidationCallback.UTCTimestampRequest) request;

            Date created = parseDate(utcRequest.getCreated());
            validateCreationTime(created, utcRequest.getMaxClockSkew(), utcRequest.getTimestampFreshnessLimit());

            if (utcRequest.getExpired() != null) {
                Date expired = parseDate(utcRequest.getExpired());
                validateExpirationTime(expired, utcRequest.getMaxClockSkew());
            }
        } else {
            throw new TimestampValidationCallback.TimestampValidationException("Unsupport request: [" + request + "]");
        }
    }

    private Date getFreshnessAndSkewAdjustedDate(long maxClockSkew,
                                                 long timestampFreshnessLimit) {
        Calendar c = new GregorianCalendar();
        long offset = c.get(Calendar.ZONE_OFFSET);
        if (c.getTimeZone().inDaylightTime(c.getTime())) {
            offset += c.getTimeZone().getDSTSavings();
        }
        long beforeTime = c.getTimeInMillis();
        long currentTime = beforeTime - offset;

        long adjustedTime = currentTime - maxClockSkew
                - timestampFreshnessLimit;
        c.setTimeInMillis(adjustedTime);

        return c.getTime();
    }

    private Date getGMTDateWithSkewAdjusted(Calendar calendar,
                                            long maxClockSkew, boolean addSkew) {
        long offset = calendar.get(Calendar.ZONE_OFFSET);
        if (calendar.getTimeZone().inDaylightTime(calendar.getTime())) {
            offset += calendar.getTimeZone().getDSTSavings();
        }
        long beforeTime = calendar.getTimeInMillis();
        long currentTime = beforeTime - offset;

        if (addSkew) {
            currentTime = currentTime + maxClockSkew;
        } else {
            currentTime = currentTime - maxClockSkew;
        }

        calendar.setTimeInMillis(currentTime);
        return calendar.getTime();
    }

    private Date parseDate(String date) throws TimestampValidationCallback.TimestampValidationException {
        SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        calendarFormatter1.setTimeZone(UTC);
        SimpleDateFormat calendarFormatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
        calendarFormatter2.setTimeZone(UTC);

        try {
            try {
                return calendarFormatter1.parse(date);
            } catch (ParseException ignored) {
                logger.warn("a formatting error was caught", ignored);
                return calendarFormatter2.parse(date);
            }
        } catch (ParseException ex) {
            throw new TimestampValidationCallback.TimestampValidationException(
                    "Could not parse request date: " + date, ex);
        }
    }

    private void validateCreationTime(Date created, long maxClockSkew, long timestampFreshnessLimit)
            throws TimestampValidationCallback.TimestampValidationException {
        Date current = getFreshnessAndSkewAdjustedDate(maxClockSkew, timestampFreshnessLimit);

        if (created.before(current)) {
            // Log the error. Do not throw the error
            // Allows client's creation time to be in the future since
            // there is no way of guaranteeing that we would be on the same clock skew
            logger.error("The creation time is older than  currenttime - timestamp-freshness-limit - max-clock-skew");
        } else {
            Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkew, true);
            if (currentTime.before(created)) {
                logger.error("The creation time is ahead of the current time.");
//            	throw new TimestampValidationCallback.TimestampValidationException(
//                    "The creation time is ahead of the current time.");
            }
        }
    }

    private void validateExpirationTime(Date expires, long maxClockSkew)
            throws TimestampValidationCallback.TimestampValidationException {
        Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(),
                maxClockSkew, false);
        if (expires.before(currentTime)) {
            logger.error("The current time is ahead of the expiration time in Timestamp");
//			throw new TimestampValidationCallback.TimestampValidationException(
//					"The current time is ahead of the expiration time in Timestamp");
        }
    }

}

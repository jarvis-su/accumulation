package logging.handler;

import java.io.OutputStream;
import java.util.logging.StreamHandler;

/**
 * DefaultStreamHandler
 *
 * @author Jarvis Su
 * @date 2/17/2016
 */

public class DefaultStreamHandler extends StreamHandler {
    @Override
    public synchronized void setOutputStream(OutputStream out)
            throws SecurityException {
        super.setOutputStream(out);
    }
}
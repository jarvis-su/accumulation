package jarvis.test;

import java.io.File;

import jarvis.utils.FilesUtilsEnhance;
import junit.framework.TestCase;

/**
 * Test
 *
 * @author Jarvis Su
 * @date 3/21/2016
 */

public class Test extends TestCase {
    String pa = "^[1-9]\\d*|0$";

    public void test1() {
        String sFileName = "E:\\VM\\nexus\\nexus-2.12.0-01-bundle.tar.gz";
        String dFileName = "E:\\VM\\nexus\\nexus-2.12.0-01-bundle.tar.gz_bak";
        File sFile = new File(sFileName);
        File dFile = new File(dFileName);
        FilesUtilsEnhance.fileChannelCopy(sFile, dFile);
    }
}

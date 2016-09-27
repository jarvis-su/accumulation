/**
 * 
 */
package com.smj.dbvariable;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author c5023771
 *
 */
public class VarCacheTest {

    @Test
    public void test() {
        VarCache.getInstance().load();
        try {
            System.out.println(VarCache.resolve("{-14days}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

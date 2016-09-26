package com.jarvis.com.jarvis.java8;

/**
 * Created by Jarvis on 9/25/16.
 */
public class InterfaceTest {

    private interface Defaulable {

        default String notRequired() {
            return "Default implementation";
        }
    }

    private static class DefaultableImpl implements Defaulable {
    }

    private static class OverridableImpl implements Defaulable {
        @Override
        public String notRequired() {
            return "Overridden implementation";
        }
    }
}

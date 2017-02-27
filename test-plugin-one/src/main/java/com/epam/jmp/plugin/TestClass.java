package com.epam.jmp.plugin;

import com.epam.jmp.plugin.test.Testable;
import org.apache.log4j.Logger;

/**
 * @author Eugene_Kortelyov
 */
public class TestClass implements Testable {

    private static final Logger logger = Logger.getLogger(TestClass.class);
    private static final String PLUGIN_VERSION = "ONE";

    public void test() {
        logger.info("Plugin version: " + PLUGIN_VERSION);
    }

}

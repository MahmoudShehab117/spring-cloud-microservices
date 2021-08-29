/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Mahmoud Shehab
 */
public class CCATLogger {
//    private static final Logger INFO_LOGGER = LogManager.getLogger("infoLogger");

    private static final Logger DEBUG_LOGGER = LogManager.getLogger("debugLogger");
    private static final Logger FOOTPRINT_LOGGER = LogManager.getLogger("footprintLogger");
    private static final Logger ERROR_LOGGER = LogManager.getLogger("errorLogger");

    public static void error(String msg, Throwable e) {
        msg = format(msg);
        DEBUG_LOGGER.error(msg);
        ERROR_LOGGER.error(msg, e);
    }

    public static void debugError(String msg) {
        DEBUG_LOGGER.error(format(msg));
    }

    public static void info(String msg) {
        DEBUG_LOGGER.info(format(msg));
    }

    public static void debug(String msg) {
        DEBUG_LOGGER.debug(format(msg));
    }

    public static void footPrint(String msg) {
        FOOTPRINT_LOGGER.debug(format(msg));
    }

    /**
     * 0.13 milliseconds avg time
     *
     * @param msg
     * @return
     */
    private static String format(String msg) {
        StringBuilder formattedMsg = new StringBuilder();
        StackTraceElement ste = getCallerFromStack();
        formattedMsg.append("[")
                .append(ste.getClassName().substring(ste.getClassName().lastIndexOf(".") + 1))
                .append(".")
                .append(ste.getMethodName())
                .append("] ")
                .append(msg);
        return formattedMsg.toString();
    }

    private static StackTraceElement getCallerFromStack() {
        return Thread.currentThread().getStackTrace()[4];
    }
}

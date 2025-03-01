package com.token.checker.token.checker.logging;

import com.token.checker.token.checker.logging.enu.Level;

public interface LoggingService {
    /**
     * Logs an error message
     * @param message to be log
     */
    public void error(String message);
    /**
     * Logs a warn message
     * @param message to be logged
     */
    public void warn(String message);
    /**
     * Logs an info message
     * @param message to be logged
     */
    public void info(String message);
    /**
     * Logs an debug message
     * @param message to be logged
     */
    public void debug(String message);
    /**
     * Logs a message for any level
     * @param level of debug
     * @param message to be logged
     */
    public void log(Level level, String message);
}

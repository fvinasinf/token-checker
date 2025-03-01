package com.token.checker.token.checker.logging.enu;

public enum Level {
    ERROR,
    WARN,
    INFO,
    DEBUG;

    public static boolean checkLevel(Level level, Level maxLevel) {
        return level.ordinal() > maxLevel.ordinal(); 
    }
}

package com.barclays.bmg.logging;

public interface BMBLog {

    public static final String DEBUG = "DEBUG";
    public static final String ERROR = "ERROR";
    public static final String INFO = "INFO";
    public static final String FATAL = "FATAL";
    public static final String TRACE = "TRACE";
    public static final String WARN = "WARN";

    public void debug(String message);

    public void debug(String message, Object t);

    public void error(String message);

    public void error(String message, Object t);

    public void error(String message, String errroCode, Object t);

    public void error(String message, String errroCode);

    public void fatal(String message);

    public void fatal(String message, Object t);

    public void info(String message);

    public void info(String message, Object t);

    public void trace(String message);

    public void trace(String message, Object t);

    public void warn(String message);

    public void warn(String message, Object t);

    public void warn(String message, String errroCode);

    public void warn(String message, String errroCode, Object t);

}

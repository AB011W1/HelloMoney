package com.barclays.bmg.context;

public abstract class BMGContextHolder {
    private static final ThreadLocal<BMGGlobalContext> logContextHolder = new ThreadLocal<BMGGlobalContext>();
    public static final ThreadLocal<Boolean> debugMode = new ThreadLocal<Boolean>();
    public static ThreadLocal<String> endpoint_Suffix = new ThreadLocal<String>();

    static {
	debugMode.set(Boolean.FALSE);
    }

    public static void resetLogContext() {
	logContextHolder.set(null);
    }

    public static void setLogContext(BMGGlobalContext context) {
	logContextHolder.set(context);
    }

    public static BMGGlobalContext getLogContext() {
	return logContextHolder.get();
    }

    public static void remove() {
	logContextHolder.remove();
    }

}

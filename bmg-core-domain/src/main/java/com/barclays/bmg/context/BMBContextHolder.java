package com.barclays.bmg.context;

public abstract class BMBContextHolder {
    private static final ThreadLocal<Context> contextHolder = new ThreadLocal<Context>();

    public static void resetContext() {
	contextHolder.set(null);
    }

    public static void setContext(Context context) {
	contextHolder.set(context);
    }

    public static Context getContext() {
	return contextHolder.get();
    }

    public static void remove() {
	contextHolder.remove();
    }
}

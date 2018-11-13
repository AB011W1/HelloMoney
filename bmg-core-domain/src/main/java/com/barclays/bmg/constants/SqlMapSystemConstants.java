package com.barclays.bmg.constants;

public interface SqlMapSystemConstants {

    /******************************************************************
     * System Module.
     ******************************************************************/
    public static final String SYSTEM_PARAMETER_GET = "getSystemParameter";

    public static final String SYSTEM_PARAMETER_GET_FORFILTER = "getSystemParameterForFilter";

    public static final String SYSTEM_PARAMETER_SAVE = "insertSystemParameter";

    public static final String SYSTEM_PARAMETER_DELETE = "deleteSystemParameter";

    public static final String SYSTEM_PARAMETER_QUERY = "getSystemParameters";

    public static final String SYSTEM_PARAMETER_UPDATE = "updateSystemParameter";

    public static final String SYSTEM_PARAMETER_FIND_BY_CATEGORY = "findSystemParameterByCategory";

    /*************************************************************
     * Session Summary
     ************************************************************/
    public static final String SESSION_ACTIVITY_INSERT = "insertSessionActivity";

    public static final String SESSION_ACTIVITY_FIND_BY_USER = "findSessionActivityByUser";

    public static final String SESSION_ACTIVITY_FIND_BY_SESSION = "findSessionActivityBySession";

    public static final String SESSION_ACTIVITY_FIND_BY_USER_AND_TYPE = "listSessionActivityByUserAndType";

    /************************************************************
     * Mobile List
     ************************************************************/
    String RETRIEVE_MOBILE_LIST = "retrieveMobileList";

    /*********************************************************
     * Entitlement
     *********************************************************/
    String RETRIEVE_ENTITLEMENT = "retrieveEntitlement";

    /******************************************************************
     * Terms Of Use Module.
     ******************************************************************/
    public static final String TERMS_OF_USE_INSERT = "insertTremsOfUse";

    public static final String TERMS_OF_USE_BY_VERSION = "findTremsOfUseByVersion";

    /******************************************************************
     * Internet Rates Module.
     ******************************************************************/
    public static final String INTRATE_CACHE = "loadIntrateCache";
    
    public static final String INTRATE_CACHE_FOR_FCR_COUNTRY = "loadIntrateCacheForFCRCountry";

    /******************************************************************
     * Feature Blackout Module.
     ******************************************************************/
    public static final String FEATURE_BLACKOUT_FIND_BY_ACTIVITYID = "findFeatureBlackoutByActivityId";

}

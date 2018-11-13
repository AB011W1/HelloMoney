/**
 * IUSSDRequestFactory.java
 */
package com.barclays.ussd.bmg.factory.request;

/**
 * @author BTCI Abstract Factory interface
 * 
 */
public interface IUSSDRequestFactory {
    /**
     * @param bmgOpCode
     * @param tranDataId
     * @return BmgBaseRequestBuilder
     */
    public BmgBaseRequestBuilder getBmgRequestBuilderObject(String tranDataId);

}

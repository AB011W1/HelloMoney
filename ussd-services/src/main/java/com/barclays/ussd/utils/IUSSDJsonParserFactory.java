/**
 * IUSSDJsonParserFactory.java
 */
package com.barclays.ussd.utils;

import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;

/**
 * @author BTCI
 * 
 */
public interface IUSSDJsonParserFactory {
    BmgBaseJsonParser getParser(ResponseBuilderParamsDTO responseBuilderParamsDTO);

    BmgBaseJsonParser getParser(String tranDataId);
}

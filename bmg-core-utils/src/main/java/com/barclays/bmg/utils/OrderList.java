/* Copyright 2008 Barclays PLC */

/**************************** Revision History ****************************************************
 * Version        Author          Date                        Description
 * 0.1            Elicer Zheng        2009/02/08                  Initial version
 *
 *
 **************************************************************************************************/

package com.barclays.bmg.utils;

import java.io.Serializable;
import java.util.Comparator;

import com.barclays.bmg.dto.CreditCardActivityDTO;



/**
 * @author Elicer Zheng
 * @param <T>
 */
@SuppressWarnings("serial")
public class OrderList<T> implements Comparator<T>, Serializable {

//    private static final transient MCFELog logger = MCFELogUtility.getLogger(OrderList.class);
    /**
     * @param sourceObject Object
     * @param destiObject Object
     * @return int
     */
    public int compare(T sourceObject, T destiObject) {
        // TODO Auto-generated method stub
        /*if (sourceObject instanceof UnclearedFundDTO) {
            UnclearedFundDTO sourceDTO = (UnclearedFundDTO)sourceObject;
            UnclearedFundDTO destiDTO = (UnclearedFundDTO)destiObject;
            try {
                if (sourceDTO.getValueDate().after(destiDTO.getValueDate())) {
                    return -1;
                } else if (sourceDTO.getValueDate().before(destiDTO.getValueDate())) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                logger.info("Value Date is null in UnclearedFundDTO", e);
            }
        } else*/ if (sourceObject instanceof CreditCardActivityDTO) {

            CreditCardActivityDTO sourceDTO = (CreditCardActivityDTO)sourceObject;
            CreditCardActivityDTO destiDTO = (CreditCardActivityDTO)destiObject;
            try {
                if (sourceDTO.getTransactionDate().after(destiDTO.getTransactionDate())) {
                    return -1;
                } else if (sourceDTO.getTransactionDate().before(destiDTO.getTransactionDate())) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
               // logger.info("Txn Date is null in CreditCardActivityDTO", e);
            }
        }/* else if (sourceObject instanceof CategoryDTO) {
            CategoryDTO sourceDTO = (CategoryDTO)sourceObject;
            CategoryDTO destiDTO = (CategoryDTO)destiObject;
            if (sourceDTO.getDisplayOrder() > destiDTO.getDisplayOrder()) {
                return 1;
            } else if (sourceDTO.getDisplayOrder() < destiDTO.getDisplayOrder()) {
                return -1;
            } else {
                return 0;
            }
        } else if (sourceObject instanceof AccountActivityDTO) {
            AccountActivityDTO sourceDTO = (AccountActivityDTO)sourceObject;
            AccountActivityDTO destiDTO = (AccountActivityDTO)destiObject;
            try {
                if (sourceDTO.getTransactionDate().after(destiDTO.getTransactionDate())) {
                    return -1;
                } else if (sourceDTO.getTransactionDate().before(destiDTO.getTransactionDate())) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                logger.info("Txn Date is null in AccountActivityDTO", e);
            }
        } else if (sourceObject instanceof FXRateDTO) {
            FXRateDTO sourceDTO = (FXRateDTO)sourceObject;
            FXRateDTO destiDTO = (FXRateDTO)destiObject;
            //Modified by Tony, compare by all the string, not only the first char
            String sourceStr = sourceDTO.getCurrencyDescription().toUpperCase();
            String destiStr = destiDTO.getCurrencyDescription().toUpperCase();
            return sourceStr.compareTo(destiStr);
            //Modified End
        } else if (sourceObject instanceof IntrateDTO) {
            // Double.compare(((IntrateDTO) sourceObject).getFrom(),
            // ((IntrateDTO) destiObject).getFrom());
            if (Double.compare(((IntrateDTO)sourceObject).getFrom(), ((IntrateDTO)destiObject)
                    .getFrom()) > 0) {
                return 1;
            } else if (Double.compare(((IntrateDTO)sourceObject).getFrom(),
                    ((IntrateDTO)destiObject).getFrom()) < 0) {
                return -1;
            } else {
                return 0;
            }
        }*/

        return -3;

    }
}

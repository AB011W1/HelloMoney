/*
 * Copyright (c) 2010 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */

package com.barclays.bmg.dao.core.proxy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MaskingRuleImpl implements MaskingRule {

    /** TODO Comment for <code>CONFIG_MASKING_PROPERTIES</code>. */

    private static final String CONFIG_MASKING_PROPERTIES = "/config/masking.properties";

    private final Logger log = Logger.getLogger(MaskingRuleImpl.class);

    private static final String MASK_ASTERISK = "*";

    private static final String CARD_MASK_ASTERISK = "x";

    private static final int  CARD_UNMASK_PREFIX_NUMBER = 6;

    private static final int UNMASK_NUMBER = 4;

    private static final int CARD_NUMBER_LENGTH = 16;

	private static final int NIB_NUMBER_LENGTH = 21;



    private boolean maskingRrequired = false;
    private String maskingCharacter = MASK_ASTERISK;
    private int unmaskNumber = UNMASK_NUMBER;
    private String cardMaskingCharacter = CARD_MASK_ASTERISK;
    private int cardUnmaskNumberPrefix = CARD_UNMASK_PREFIX_NUMBER;
    private List<String> fullMaskElements = new ArrayList<String>();
    private List<String> partialMaskElements = new ArrayList<String>();
    private List<String> numberMaskElements = new ArrayList<String>();


    private static MaskingRule rule = null;
    public static MaskingRule getInstance(){
        if(rule == null){
            rule = new MaskingRuleImpl();
        }
        return rule;
    }

    private MaskingRuleImpl(){
        loadConfiguration();
    }
    /**
     * @param text
     * @param maskingMode
     * @return
     * @see com.barclays.mcfe.ccs.handler.MaskingRule#mask(java.lang.String, com.barclays.mcfe.ccs.handler.MaskingMode)
     */
    public String mask(String text, MaskingMode maskingMode) {
        if(maskingMode == MaskingMode.FULL){
            return "********";
        }else if(maskingMode == MaskingMode.PARTIAL){
            return partialMask(text);
        }else if(maskingMode == MaskingMode.NONE){
            return text;
        }else if(maskingMode == MaskingMode.NUMBERPARTIAL){
        	return numberPartialMask(text);
        }

        return text;
    }

    /**
     * TODO Method description, including pre/post conditions and invariants.
     */
    private void loadConfiguration() {
        InputStream is = MaskingRuleImpl.class.getResourceAsStream(CONFIG_MASKING_PROPERTIES);
        if(is != null){
            Properties prop = new Properties();
            try {
                prop.load(is);

                this.maskingRrequired = Boolean.valueOf((String)prop.get("masking.required"));
                this.maskingCharacter = (String)prop.getProperty("masking.character.displayed");
                this.unmaskNumber = Integer.parseInt(prop.getProperty("masking.unmasknumber"));
                this.cardMaskingCharacter = (String)prop.getProperty("masking.cardcharacter.displayed");
                this.cardUnmaskNumberPrefix = Integer.parseInt(prop.getProperty("masking.cardunmaskprefixnumber"));
                String partialMaskingStr = (String)prop.get("masking.partial");
                initMaksingList(partialMaskElements, partialMaskingStr);

                String fullMaskingStr = (String)prop.get("masking.full");
                initMaksingList(fullMaskElements, fullMaskingStr);

                String numberMaskingStr = (String)prop.get("masking.numberpartial");
                initMaksingList(numberMaskElements, numberMaskingStr);

            } catch (IOException e) {
            	log.error("Failed to load configuration file from /config/masking.properties from class path",  e);
//                log.error("Failed to load configuration file from /config/masking.properties from class path", e);
            } catch (Exception e){
                log.error("Failed to load configuration, check whether the value are configured correctly",e);
//                log.error("Failed to load configuration, check whether the value are configured correctly", e);
            }
        }

    }
    /**
     * TODO Method description, including pre/post conditions and invariants.
     * @param fullMaskingStr
     * @param partialMaskingStr
     */
    private void initMaksingList(List<String> maskElements, String maskingStr) {
        if(maskingStr != null){
            String[] targetElements = maskingStr.split(",");
            if(targetElements != null){
                for(String s : targetElements){
                    maskElements.add(s.trim().toLowerCase());
                }
            }
        }
    }

    /**
     * TODO Method description, including pre/post conditions and invariants.
     * @param text
     * @return
     */
    private String partialMask(String text) {
    	String updatedtext = text;
    	updatedtext = updatedtext.trim();
        if (updatedtext.length() <= unmaskNumber) {
            return updatedtext;
        }
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < updatedtext.length() - unmaskNumber; i++) {
            masked.append(maskingCharacter);
        }
        masked.append(updatedtext.substring(updatedtext.length() - unmaskNumber));
        return masked.toString();
    }

    private String numberPartialMask(String text){
    	if(isCardNumber(text)||isNIB(text)){
    		StringBuilder masked = new StringBuilder();
    		masked.append(text.substring(0, cardUnmaskNumberPrefix));
            for (int i = 0; i < text.length() - unmaskNumber - cardUnmaskNumberPrefix; i++) {
                masked.append(cardMaskingCharacter);
            }
            masked.append(text.substring(text.length() - unmaskNumber));
            return masked.toString();
    	}else{
    		return partialMask(text);
    	}
    }
    /**
     * @param element sample: &ltp123:AccountNumber&gt;
     * @return
     * @see com.barclays.mcfe.ccs.handler.MaskingRule#checkMaskingMode(java.lang.String)
     */
    public MaskingMode checkMaskingMode(String element) {

        MaskingMode maskingMode = MaskingMode.NONE;
        if(isMaskingRequired()){
            if(partialMaskElements.contains(getElementName(element)))
                maskingMode = MaskingMode.PARTIAL;
            else if(fullMaskElements.contains(getElementName(element)))
                maskingMode = MaskingMode.FULL;
            else if (numberMaskElements.contains(getElementName(element))){
            	maskingMode = MaskingMode.NUMBERPARTIAL;
            }
        }

        return maskingMode;
    }

    /**
     * if the param is &ltp123:AccountNumber&gt; then AccountNumber will be returned.
     * @param element
     * @return
     */
    private String getElementName(String element) {
    	String updatedelement = element;
        if(updatedelement != null){
            if(updatedelement.indexOf("<") > -1 && updatedelement.indexOf(">") > -1){
                // the element is enclosed with bracket.
                if(updatedelement.indexOf(":") > -1){
                    // the element contains namespace;
                	updatedelement = updatedelement.substring(updatedelement.indexOf(":")+1, updatedelement.indexOf(">"));
                }else{
                	updatedelement = updatedelement.substring(updatedelement.indexOf("<")+1, updatedelement.indexOf(">"));
                }
            }
            updatedelement = updatedelement.toLowerCase();
        }
        return updatedelement;
    }
    /**
     * This method used to check whether the number is a card number or not
     *
     * @return boolean
     */
    private boolean isCardNumber(String text){

         if(text != null && text.trim().length() == CARD_NUMBER_LENGTH){
        	 return true;
         }

         return false;
    }

    private boolean isNIB(String text){

        if(text != null && text.trim().length() == NIB_NUMBER_LENGTH){
       	 return true;
        }

        return false;
   }
    /**
     * @return the fullMaskElements
     */
    public List<String> getFullMaskElements() {
        return fullMaskElements;
    }

    /**
     * @param fullMaskElements the fullMaskElements to set
     */
    public void setFullMaskElements(List<String> fullMaskElements) {
        this.fullMaskElements = fullMaskElements;
    }

    /**
     * @return the partialMaskElements
     */
    public List<String> getPartialMaskElements() {
        return partialMaskElements;
    }

    /**
     * @param partialMaskElements the partialMaskElements to set
     */
    public void setPartialMaskElements(List<String> partialMaskElements) {
        this.partialMaskElements = partialMaskElements;
    }

    public List<String> getNumberMaskElements() {
		return numberMaskElements;
	}

	public void setNumberMaskElements(List<String> numberMaskElements) {
		this.numberMaskElements = numberMaskElements;
	}

	/**
     * @return
     * @see com.barclays.mcfe.ccs.handler.MaskingRule#isRequired()
     */
    public boolean isMaskingRequired() {
        return maskingRrequired;
    }

    /**
     * @param maskingRrequired the maskingRrequired to set
     */
    public void setMaskingRrequired(boolean maskingRrequired) {
        this.maskingRrequired = maskingRrequired;
    }

    /**
     * @return the unmaskNumber
     */
    public int getUnmaskNumber() {
        return unmaskNumber;
    }

    /**
     * @param unmaskNumber the unmaskNumber to set
     */
    public void setUnmaskNumber(int unmaskNumber) {
        this.unmaskNumber = unmaskNumber;
    }

}

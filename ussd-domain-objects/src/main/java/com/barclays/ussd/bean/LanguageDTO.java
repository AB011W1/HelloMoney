package com.barclays.ussd.bean;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class LanguageDTO.
 */
public class LanguageDTO {

    /** The languages. */
    private List<String> languages;

    /** The display label id. */
    private String displayLabelId;

    /**
     * Gets the display label id.
     * 
     * @return the display label id
     */
    public String getDisplayLabelId() {
	return displayLabelId;
    }

    /**
     * Sets the display label id.
     * 
     * @param displayLabelId
     *            the new display label id
     */
    public void setDisplayLabelId(String displayLabelId) {
	this.displayLabelId = displayLabelId;
    }

    /**
     * Gets the languages.
     * 
     * @return the languages
     */
    public List<String> getLanguages() {
	return languages;
    }

    /**
     * Sets the languages.
     * 
     * @param languages
     *            the new languages
     */
    public void setLanguages(List<String> languages) {
	this.languages = languages;
    }

}

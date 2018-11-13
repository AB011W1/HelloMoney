package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class UpdateLanguagePrefServiceRequest extends RequestContext {

    private String prefLang;

    /**
     * @return String prefLang
     */
    public String getPrefLang() {
	return prefLang;
    }

    /**
     * @param prefLang
     */
    public void setPrefLang(String prefLang) {
	this.prefLang = prefLang;
    }

}

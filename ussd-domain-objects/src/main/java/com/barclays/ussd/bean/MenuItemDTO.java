package com.barclays.ussd.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.utils.PaginationEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuItemDTO.
 */
/* Note: In order to display a field in a localized/non-localized forma then do make the changed in the localeScreenBuilder too */
public class MenuItemDTO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The menu item list. */
    private List<MenuItem> menuItemList;

    /** The next screen id. */
    private String nextScreenId;

    /** The page header. */
    private String pageHeader;

    /** The page body. */
    private String pageBody;

    /** The tran id. */
    private String tranId;

    /** The tran node id. */
    private String tranNodeId;

    /** The status. */
    private String status;

    /** The next screen node id. */
    private String nextScreenNodeId;

    /** The user auth resp map. */
    private Map<String, Object> userAuthRespMap;

    /** The page footer. */
    private String pageFooter;

    /** The page error. */
    private String pageError;

    /** The page error parameters. */
    private List<String> errorParams;

    /** The access type. */
    private String accessType;

    private PaginationEnum paginationType;

    /** The page scrollers **/
    private String scrollers;

    /** The error page flag. */
    private boolean errorPage = false;

    /** The error code placeholder. */
    private String errorCode;

    /** The tran node id. */
    private String labelId;

    private int nextScreenSequenceNumber;

    /**
     * Gets the tran id.
     * 
     * @return the tran id
     */
    public String getTranId() {
	return tranId;
    }

    /**
     * Sets the tran id.
     * 
     * @param tranId
     *            the new tran id
     */
    public void setTranId(String tranId) {
	this.tranId = tranId;
    }

    /**
     * Gets the page header.
     * 
     * @return the page header
     */
    public String getPageHeader() {
	return pageHeader;
    }

    /**
     * Sets the page header.
     * 
     * @param pageHeader
     *            the new page header
     */
    public void setPageHeader(String pageHeader) {
	this.pageHeader = pageHeader;
    }

    /**
     * Gets the menu item list.
     * 
     * @return the menu item list
     */
    public List<MenuItem> getMenuItemList() {
	return menuItemList;
    }

    /**
     * Sets the menu item list.
     * 
     * @param menuItemList
     *            the new menu item list
     */
    public void setMenuItemList(List<MenuItem> menuItemList) {
	this.menuItemList = menuItemList;
    }

    /**
     * Gets the next screen id.
     * 
     * @return the next screen id
     */
    public String getNextScreenId() {
	return nextScreenId;
    }

    /**
     * Sets the next screen id.
     * 
     * @param nextScreenId
     *            the new next screen id
     */
    public void setNextScreenId(String nextScreenId) {
	this.nextScreenId = nextScreenId;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public String getStatus() {
	return status;
    }

    /**
     * Sets the status.
     * 
     * @param status
     *            the new status
     */
    public void setStatus(String status) {
	this.status = status;
    }

    /**
     * Gets the next screen node id.
     * 
     * @return the next screen node id
     */
    public String getNextScreenNodeId() {
	return nextScreenNodeId;
    }

    /**
     * Sets the next screen node id.
     * 
     * @param nextScreenNodeId
     *            the new next screen node id
     */
    public void setNextScreenNodeId(String nextScreenNodeId) {
	this.nextScreenNodeId = nextScreenNodeId;
    }

    /**
     * Gets the tran node id.
     * 
     * @return the tran node id
     */
    public String getTranNodeId() {
	return tranNodeId;
    }

    /**
     * Sets the tran node id.
     * 
     * @param tranNodeId
     *            the new tran node id
     */
    public void setTranNodeId(String tranNodeId) {
	this.tranNodeId = tranNodeId;
    }

    /**
     * Gets the page body.
     * 
     * @return the page body
     */
    public String getPageBody() {
	return pageBody;
    }

    /**
     * Sets the page footer.
     * 
     * @param pageFooter
     *            the pageFooter to set
     */
    public void setPageFooter(String pageFooter) {
	this.pageFooter = pageFooter;
    }

    /**
     * Gets the page footer.
     * 
     * @return the pageFooter
     */
    public String getPageFooter() {
	return pageFooter;
    }

    /**
     * Sets the page error.
     * 
     * @param pageError
     *            the pageError to set
     */
    public void setPageError(String pageError) {
	this.pageError = pageError;
    }

    /**
     * Gets the page error.
     * 
     * @return the pageError
     */
    public String getPageError() {
	return pageError;
    }

    /**
     * Gets the user auth resp map.
     * 
     * @return the user auth resp map
     */
    public Map<String, Object> getUserAuthRespMap() {
	return userAuthRespMap;
    }

    /**
     * Sets the user auth resp map.
     * 
     * @param userAuthRespMap
     *            the user auth resp map
     */
    public void setUserAuthRespMap(Map<String, Object> userAuthRespMap) {
	this.userAuthRespMap = userAuthRespMap;
    }

    /**
     * Sets the page body.
     * 
     * @param pageBody
     *            the new page body
     */
    public void setPageBody(String pageBody) {
	this.pageBody = pageBody;
    }

    /**
     * Gets the access type.
     * 
     * @return the access type
     */
    public String getAccessType() {
	return accessType;
    }

    /**
     * Sets the access type.
     * 
     * @param accessType
     *            the new access type
     */
    public void setAccessType(String accessType) {
	this.accessType = accessType;
    }

    /**
     * @return the paginationType
     */
    public PaginationEnum getPaginationType() {
	return paginationType;
    }

    /**
     * @param paginationType
     *            the paginationType to set
     */
    public void setPaginationType(PaginationEnum paginationType) {
	this.paginationType = paginationType;
    }

    /**
     * @return the scrollers
     */
    public String getScrollers() {
	return scrollers;
    }

    /**
     * @param scrollers
     *            the scrollers to set
     */
    public void setScrollers(String scrollers) {
	this.scrollers = scrollers;
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    @Override
    public String toString() {

	return new StringBuffer("nextScreenId: ").append(nextScreenId).append(" || pageHeader : ").append(pageHeader).append(" ||pageBody: ").append(
		pageBody).toString();
    }

    /**
     * @return the errorParams
     */
    public List<String> getErrorParams() {
	return errorParams;
    }

    /**
     * @param errorParams
     *            the errorParams to set
     */
    public void setErrorParams(List<String> errorParams) {
	this.errorParams = errorParams;
    }

    /**
     * @return the errorPage
     */
    public boolean isErrorPage() {
	return errorPage;
    }

    /**
     * @param errorPage
     *            the errorPage to set
     */
    public void setErrorPage(boolean errorPage) {
	this.errorPage = errorPage;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
	return errorCode;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }

    public String getLabelId() {
	return labelId;
    }

    public void setLabelId(String labelId) {
	this.labelId = labelId;
    }

    public int getNextScreenSequenceNumber() {
	return nextScreenSequenceNumber;
    }

    public void setNextScreenSequenceNumber(int nextScreenSequenceNumber) {
	this.nextScreenSequenceNumber = nextScreenSequenceNumber;
    }

}

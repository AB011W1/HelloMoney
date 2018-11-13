package com.barclays.ussd.svc.context;

import java.io.Serializable;

import com.barclays.ussd.bean.MenuItemDTO;


// TODO: Auto-generated Javadoc
/**
 * The Class USSDBaseResponse.
 */
public class USSDBaseResponse implements Serializable {

	/** The menu item dto. */
	private MenuItemDTO menuItemDTO;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3136229468955391934L;
	
	/** The context. */
	private Context context;
    
    /** The success. */
    private boolean success = true;
    
    /** The res cde. */
    private String resCde;
    
    /** The res msg. */
    private String resMsg;
    
    /** The txn typ. */
    private String txnTyp;
    
    /** The err typ. */
    private String errTyp;

	/**
     * Gets the context.
     * 
     * @return the context
     */
	public Context getContext(){
        return context;
    }

    /**
     * Sets the context.
     * 
     * @param context
     *            the new context
     */
    public void setContext(Context context){

    	this.context = context;
    }



	/**
     * Gets the txn typ.
     * 
     * @return the txn typ
     */
	public String getTxnTyp() {
		return txnTyp;
	}

	/**
     * Sets the txn typ.
     * 
     * @param txnTyp
     *            the new txn typ
     */
	public void setTxnTyp(String txnTyp) {
		this.txnTyp = txnTyp;
	}

	/**
     * Checks if is success.
     * 
     * @return true, if is success
     */
	public boolean isSuccess() {
		return success;
	}

	/**
     * Sets the success.
     * 
     * @param success
     *            the new success
     */
	public void setSuccess(boolean success) {
		this.success = success;
	}


	/**
     * Gets the res cde.
     * 
     * @return the res cde
     */
	public String getResCde() {
		return resCde;
	}

	/**
     * Sets the res cde.
     * 
     * @param resCde
     *            the new res cde
     */
	public void setResCde(String resCde) {
		this.resCde = resCde;
	}

	/**
     * Gets the res msg.
     * 
     * @return the res msg
     */
	public String getResMsg() {
		return resMsg;
	}

	/**
     * Sets the res msg.
     * 
     * @param resMsg
     *            the new res msg
     */
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	/**
     * Gets the err typ.
     * 
     * @return the err typ
     */
	public String getErrTyp() {
		return errTyp;
	}

	/**
     * Sets the err typ.
     * 
     * @param errTyp
     *            the new err typ
     */
	public void setErrTyp(String errTyp) {
		this.errTyp = errTyp;
	}

	/**
     * Gets the menu item dto.
     * 
     * @return the menu item dto
     */
	public MenuItemDTO getMenuItemDTO() {
		return menuItemDTO;
	}

	/**
     * Sets the menu item dto.
     * 
     * @param menuItemDTO
     *            the new menu item dto
     */
	public void setMenuItemDTO(MenuItemDTO menuItemDTO) {
		this.menuItemDTO = menuItemDTO;
	}

}

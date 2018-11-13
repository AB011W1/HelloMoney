package com.barclays.bmg.mvc.validatotion.util;



public class ValidationUtil {


	/**
	 * Validate Transaction amount.
	 * return true if amount is matches
	 * return false if amount is not matches
	 * @param txnAmt
	 * @return
	 */
	public static boolean validateAmount(String txnAmt){
		//if(txnAmt!=null){
			if(!txnAmt.matches("^\\d+$|^\\d*\\.\\d{1,2}$")){

				return false;
			}

			return true;
		//}
	}


	/**
	 * validate mobile number, entered mobile number only digis
	 * @param mobileNo
	 * @return
	 */
	public static boolean validateMobileNo(String mobileNo){

		if(mobileNo.matches("\\d")){

				return false;
			}

			return true;
	}
}

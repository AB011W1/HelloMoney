package com.barclays.bmg.ussd.dao.operation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.bem.RetrieveIndividualCustCardList.AccountList;
import com.barclays.bem.RetrieveIndividualCustCardList.CardList;
import com.barclays.bem.RetrieveIndividualCustCardList.RetrieveIndividualCustCardListResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperation;
import com.barclays.bmg.dto.DebitCardDTO;
import com.barclays.bmg.service.request.RetrieveindividualCustCardListServiceRequest;
import com.barclays.bmg.service.response.RetrieveindividualCustCardListServiceResponse;
import com.barclays.bmg.utils.DateTimeUtil;

public class RetrieveindividualCustCardListResAdptOperation extends AbstractResAdptOperation {

	private static final Logger LOGGER = Logger.getLogger(RetrieveindividualCustCardListResAdptOperation.class);

	/**
	 *
	 * @param workContext
	 * @param obj
	 * @return RetrieveindividualCustCardListServiceResponse
	 */
	public RetrieveindividualCustCardListServiceResponse adaptResponse(WorkContext workContext,Object obj){

		 LOGGER.info(" Entry RetrieveindividualCustCardListResAdptOperation adaptResponse");
		RetrieveindividualCustCardListServiceResponse response = new RetrieveindividualCustCardListServiceResponse();
		RetrieveIndividualCustCardListResponse bemResponse = (RetrieveIndividualCustCardListResponse)obj;

		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();

		RetrieveindividualCustCardListServiceRequest request = (RetrieveindividualCustCardListServiceRequest)args[0];
		Calendar currentBusinessDate=DateTimeUtil.getCurrentBusinessCalendar(request.getContext());

		response.setSuccess(checkResponseHeader(bemResponse.getResponseHeader()));

		if(checkResponseHeader(bemResponse.getResponseHeader())){
			response.setDebitCardDTOList(populateDebitCardList(bemResponse.getCustomerCardListResp().getAccountList(),currentBusinessDate));
			LOGGER.info(" Entry RetrieveindividualCustCardListResAdptOperation adaptResponse checkResponseHeader");
			if(response.getDebitCardDTOList().size()==0){
				 if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" Entry RetrieveindividualCustCardListResAdptOperation adaptResponse debit card list size 0");
				 }
				response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
				response.setResCde("BEM08901");
				response.setSuccess(false);
			}

		}else{
			 if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" Entry RetrieveindividualCustCardListResAdptOperation adaptResponse checkResponseHeader false");
			}
			for (com.barclays.bem.BEMServiceHeader.Error error : bemResponse.getResponseHeader().getErrorList()) {
				response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
				response.setResCde("BEM"+error.getErrorCode());
				response.setSuccess(false);
				}
		}
		return response;
	}
	/**
	 *
	 * @param accountListArray
	 * @return DebitCardDTO list
	 */
	private List<DebitCardDTO> populateDebitCardList(AccountList[] accountListArray,Calendar businessDate){
		if (LOGGER.isDebugEnabled()) {
			if(accountListArray!=null)
			LOGGER.debug(" Entry RetrieveindividualCustCardListResAdptOperation populateDebitCardList accountListArray size"+accountListArray.length);
			}
		Date businessDateFormat=businessDate.getTime();//2018-02-28
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String businessDateFormatString=dateFormat.format(businessDateFormat);
		List<DebitCardDTO> debitCardList= new ArrayList<DebitCardDTO>();
		for(AccountList account:accountListArray){
			for(CardList card:account.getCardList()){
				String cardExpiryDateFormatString=dateFormat.format(card.getCardExpiryDate().getTime());
				//As per req we have to include
				if(card.getCardLifecycleStatusCode().equalsIgnoreCase("C") &&(card.getCardExpiryDate().after(businessDate) || cardExpiryDateFormatString.equals(businessDateFormatString))){
					DebitCardDTO debitCardDTO=new DebitCardDTO();
					debitCardDTO.setCardExpiryDate(card.getCardExpiryDate().getTime());
					debitCardDTO.setCardLifecycleStatusCode(card.getCardLifecycleStatusCode());
					//added since debit card no coming from BEM is of 19 digit so esscaping first 3 digit to make it of 16 digit
					debitCardDTO.setCardNumber(card.getCardNumber().substring(3));
					debitCardList.add(debitCardDTO);				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" Exit RetrieveindividualCustCardListResAdptOperation populateDebitCardList debitCardList size"+debitCardList.size());
			}

		return debitCardList;
	}
}

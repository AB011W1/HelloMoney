package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.selfreg.RetrieveindividualCustCardListResponse;

public class SelfRegisterDebitCardVerificationParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

    private static final Logger LOGGER = Logger.getLogger(SelfRegisterDebitCardVerificationParser.class);
    private static final String DEBIT_CARD_INVALID_EXP_DATE_LABEL = "label.selfreg.debitcard.invalid.expirydate";
    private static final String BMG_ERRCODE_REG0118 = "REG0118"; // CR35 validation and check if the user entered incorrect debit card no/expiry date
    private static final String  FORGOT_PIN_DEBIT_CARD_VALIDATION_SUCCESS="label.forgot.pin.debit.card.validation.success";
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	// TODO Auto-generated method stub
	return renderMenuOnScreen(responseBuilderParamsDTO);

    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException,USSDBlockingException{

	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	ObjectMapper mapper = new ObjectMapper();
	try {


	RetrieveindividualCustCardListResponse retrieveindividualCustCardListResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
			RetrieveindividualCustCardListResponse.class);

	   if (retrieveindividualCustCardListResponse != null) {
			if (retrieveindividualCustCardListResponse.getPayHdr() != null
				&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(retrieveindividualCustCardListResponse.getPayHdr().getResCde())) {

				USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
				String successDebitCardLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(FORGOT_PIN_DEBIT_CARD_VALIDATION_SUCCESS,
							new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

				pageBody.append(successDebitCardLabel);
			} else if (retrieveindividualCustCardListResponse.getPayHdr() != null
				&& (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), retrieveindividualCustCardListResponse.getPayHdr().getResCde()))) {
			    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
			else if(retrieveindividualCustCardListResponse.getPayHdr() != null
					&& USSDExceptions.BEM08901.getBmgCode().equalsIgnoreCase(retrieveindividualCustCardListResponse.getPayHdr().getResCde())){
				throw new USSDNonBlockingException(USSDExceptions.BEM08901.getBmgCode());
			}else if(retrieveindividualCustCardListResponse.getPayHdr() != null
					&& BMG_ERRCODE_REG0118.equalsIgnoreCase(retrieveindividualCustCardListResponse.getPayHdr().getResCde())){
				//Forgot Pin Change
				String tranDataId=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
				if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
					throw new USSDNonBlockingException(USSDExceptions.DEBIT_CARD_INVA_RAN_LABEL_FORGOT_PIN.getBmgCode());
				}else
				throw new USSDBlockingException(USSDExceptions.DEBIT_CARD_INVA_RAN_LABEL.getBmgCode());
			}else if (retrieveindividualCustCardListResponse.getPayHdr() != null) {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(retrieveindividualCustCardListResponse.getPayHdr().getResCde());
			}

			else {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		    } else {
			LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		    }}catch (Exception e) {
			    LOGGER.error("Exception : ", e);
			    if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			    } else if (e instanceof USSDBlockingException) {
				throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
			    } else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			    }
			}
		//pageBody.append(debitCardLabel);
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	menuItemDTO.setPageBody(pageBody.toString());
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ELEVEN.getSequenceNo());

    }
    @Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ELEVEN.getSequenceNo();
		String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
			seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWELVE.getSequenceNo();
		}
		return seqNo;
	}

}

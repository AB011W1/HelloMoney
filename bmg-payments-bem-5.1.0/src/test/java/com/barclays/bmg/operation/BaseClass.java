package com.barclays.bmg.operation;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class BaseClass {
    protected List<BillerDTO> getBillerDTOListMock() {

	List<BillerDTO> billerList = new ArrayList<BillerDTO>();
	BillerDTO billerDTO = new BillerDTO();
	billerDTO.setBillerId("Barclaycard");
	billerDTO.setBillerAccountNumber("1000004");
	billerDTO.setBillerCategoryId("BarclaycardBill");
	billerDTO.setBillerName("Barclaycard");
	billerList.add(billerDTO);

	return billerList;

    }

    public static Context getContextMock() {

	Context context = new Context();
	context.setBusinessId("TZBRB");
	context.setCountryCode("TZ");
	context.setLanguageId("EN");
	context.setSystemId("UB");
	context.setCustomerId("07995733");
	context.setLoginId("BIRTZ001");
	context.setLocalCurrency("TZS");
	context.setTimezoneOffset("3");
	context.setSessionId("A009F6935024429917046897359A248C");
	context.setServiceVersion("2.0");
	context.setActivityRefNo("13642082008013");
	context.setOrgRefNo("13642082014262");
	return context;
    }

    protected BeneficiaryDTO getBeneficiaryDTOMock() {
	BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
	beneficiaryDTO.setBeneficiaryName("BENF ABC");
	beneficiaryDTO.setPayeeId("BENF_0000ABC");
	return beneficiaryDTO;
    }

    protected SystemParameterDTO getSystemParameterDTOMock() {
	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
	systemParameterDTO.setActivityId("COMMON");
	systemParameterDTO.setParameterId("BeneficiaryManagement_EndPoint");
	systemParameterDTO.setParameterValue("http://mb4t.wload.global:7080/bem/bxx5_gateway");
	systemParameterDTO.setBusinessId("TZBRB");
	systemParameterDTO.setSystemId("UB");
	return systemParameterDTO;
    }

    protected SystemParameterListServiceResponse getSystemParameterListServiceResponseMock() {
	SystemParameterListServiceResponse systemParameterListServiceResponse = new SystemParameterListServiceResponse();
	List<SystemParameterDTO> sysParamDtoList = new ArrayList<SystemParameterDTO>();
	sysParamDtoList.add(getSystemParameterDTOMock());
	systemParameterListServiceResponse.setSystemParameterDTOList(sysParamDtoList);
	systemParameterListServiceResponse.setSuccess(true);
	return systemParameterListServiceResponse;
    }

    protected MessageResourceServiceResponse getMessageResourceServiceResponseMock() {
	MessageResourceServiceResponse messageResourceServiceResponse = new MessageResourceServiceResponse();
	messageResourceServiceResponse.setMessageDesc("Mssg Descr");
	messageResourceServiceResponse.setErrTyp("errTyp");
	return messageResourceServiceResponse;
    }

    protected SystemParameterServiceResponse getSysParameServiceRespSecAuthTypeMock() {
	SystemParameterServiceResponse systemParameterServiceResponse = new SystemParameterServiceResponse();
	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
	systemParameterDTO.setActivityId("COMMON");
	systemParameterDTO.setParameterId(SystemParameterConstant.SECOND_AUTH_TYPE);
	systemParameterDTO.setParameterValue("OTP");
	systemParameterServiceResponse.setSystemParameterDTO(systemParameterDTO);
	return systemParameterServiceResponse;
    }

    protected CASADetailsServiceResponse getCASADetailsServiceResponseEmptyMock() {
	CASADetailsServiceResponse casaDetailsServiceResponse = new CASADetailsServiceResponse();
	CASAAccountDTO acct = new CASAAccountDTO();
	casaDetailsServiceResponse.setCasaAccountDTO(acct);
	return casaDetailsServiceResponse;
    }

    protected CASADetailsServiceResponse getCASADetailsServiceResponseMock() {
	CASADetailsServiceResponse casaDetailsServiceResponse = new CASADetailsServiceResponse();
	CASAAccountDTO acct = new CASAAccountDTO();
	acct.setAccountNumber("111111111111");
	casaDetailsServiceResponse.setCasaAccountDTO(acct);
	return casaDetailsServiceResponse;
    }

}

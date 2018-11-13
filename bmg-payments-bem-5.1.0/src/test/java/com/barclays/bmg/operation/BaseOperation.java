package com.barclays.bmg.operation;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;

public class BaseOperation {

	public static Context getContextMock(){

		Context context = new Context();
		//Map<String, String> map = new HashMap<String, String>();
		//map.put(ProductProcessorTypeCode._BK, "0001000000000000000782");
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

	  protected BeneficiaryDTO getBeneficiaryDTOMock(){
	    	BeneficiaryDTO beneficiaryDTO=new BeneficiaryDTO();
	    	beneficiaryDTO.setBeneficiaryName("BENF ABC");
	    	beneficiaryDTO.setPayeeId("BENF_0000ABC");
			return beneficiaryDTO;
	  }

	  protected SystemParameterDTO getSystemParameterDTOMock(){
			SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
			systemParameterDTO.setActivityId("COMMON");
			systemParameterDTO.setParameterId("BeneficiaryManagement_EndPoint");
			systemParameterDTO.setParameterValue("http://mb4t.wload.global:7080/bem/bxx5_gateway");
			systemParameterDTO.setBusinessId("TZBRB");
			systemParameterDTO.setSystemId("UB");

			return systemParameterDTO;
	  }

	  protected SystemParameterListServiceResponse getSystemParameterListServiceResponseMock(){
			 SystemParameterListServiceResponse systemParameterListServiceResponse=new SystemParameterListServiceResponse();
			 List<SystemParameterDTO> sysParamDtoList=new ArrayList<SystemParameterDTO>();
			 sysParamDtoList.add(getSystemParameterDTOMock());
			 systemParameterListServiceResponse.setSystemParameterDTOList(sysParamDtoList);
			 systemParameterListServiceResponse.setSuccess(true);
			 return systemParameterListServiceResponse;
	  }

	  protected MessageResourceServiceResponse getMessageResourceServiceResponseMock(){
		  MessageResourceServiceResponse messageResourceServiceResponse=new MessageResourceServiceResponse();
		  messageResourceServiceResponse.setMessageDesc("Mssg Descr");
		  messageResourceServiceResponse.setErrTyp("errTyp");
		  return messageResourceServiceResponse;
	  }



}

package com.barclays.ussd.bmg.otbp.request;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.taglibs.standard.lang.jpath.expression.ParseException;
import org.apache.taglibs.standard.lang.jpath.expression.Parser;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillerArea;
import com.barclays.ussd.bean.BillerAttributes;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class OneTimeBillPayBillAreaCodeJsonParser implements BmgBaseJsonParser {
    ObjectMapper mapper = new ObjectMapper();

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		populateTxSession(responseBuilderParamsDTO);
		List<BillerArea> billerAreaList = (List<BillerArea>) ussdSessionMgmt.getTxSessions().get(
			USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getTranId());
		return renderMenuOnScreen(billerAreaList, responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(List<BillerArea> billerAreaList, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		int index = 1;
		StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();

		for (BillerArea area : billerAreaList) {
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(index);
		    pageBody.append(USSDConstants.DOT_SEPERATOR);
		    pageBody.append(area.getAreaName());
		    index++;
		}

		menuItemDTO.setPageBody(pageBody.toString());
		// insert the back and home options
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
    }

    @SuppressWarnings("unchecked")
	private void populateTxSession(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		if (StringUtils.equalsIgnoreCase(USSDConstants.BUSINESS_ID_UGBRB, businessId)) {
		    List<BillersListDO> blrsLstDO = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
			    USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
		    BillersListDO billersListDO = blrsLstDO.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST
			    .getParamName())) - 1);
		    if (StringUtils.equalsIgnoreCase(billersListDO.getBillerId(), "NWSC-4")) {

		    	  String areaList = "{\"areaList\":[{\"areaName\":\"Kampala\"},{\"areaName\":\"Jinja\"},{\"areaName\":\"Entebbe\"},{\"areaName\":\"Iganga\"},{\"areaName\":\"Lugazi\"},{\"areaName\":\"Mukono\"},{\"areaName\":\"Kajjansi\"},{\"areaName\":\"Kawuku\"},{\"areaName\":\"Others\"}]}";

				List<BillerArea> billerArea = extractBillerAttr(areaList, userInputMap);
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getTranId(), billerArea);
		    }
		}
    }

    private List<BillerArea> extractBillerAttr(String billerAttributesJson, Map<String, String> userInputMap) throws USSDNonBlockingException {
		try {
		  //  List<BillerArea> searchedBillerArea = new ArrayList<BillerArea>();

		    BillerAttributes billerAttributes = mapper.readValue(billerAttributesJson, BillerAttributes.class);
		//    String searchChars = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_SEARCHER.getParamName());
		    List<BillerArea> areaList = billerAttributes.getAreaList();
		   /* for (BillerArea area : areaList) {
			//if (StringUtils.containsIgnoreCase(area.getAreaName(), searchChars))
		    {
			    searchedBillerArea.add(area);
			}
		    }*/
		    if (areaList== null || areaList.isEmpty()) {
		    	throw new USSDNonBlockingException(USSDExceptions.USSD_NO_AREA_FOUND.getBmgCode());
		    }
		    return areaList;
		} catch (Exception e) {
		    if (e instanceof USSDNonBlockingException) {
		    	throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
		    } else {
		    	throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		    }
		}
    }

}
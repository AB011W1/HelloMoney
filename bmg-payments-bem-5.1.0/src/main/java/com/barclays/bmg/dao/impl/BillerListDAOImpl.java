package com.barclays.bmg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dao.BillerListDAO;
import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.PilotUserDTO;
import com.barclays.bmg.dto.UBPBusinessIdsDTO;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.ussd.utils.USSDSessionConstants;

public class BillerListDAOImpl extends BaseDAOImpl implements BillerListDAO {

	private static final String BILLER_ID = "billerId";
	private static final String BUSINESS_ID = "businessId";
	private static final String BILLER_CATEGORY_ID = "billerCategoryId";
	private static final String PILOT_USER = "getPilotUser";

	// UBP Optimisation - start
	private static final String MOBILE_NUMBER = "mobilenumber";
	private static final String PILOT_MODE = "pilotmode";
	private static final String STATUS = "status";
	private static final String BILLER_LIST_GET_UBP = "getAllBilelrListUBP";
	private static final String BILLER_LIST_GET_NOT_TZ_UBP = "getAllBilelrListNotTZUBP";
	private static final String BILLER_LIST_FOR_CATEGORY_UBP = "getBilerListAsPerCategIdUBP";
	private static final String BILLER_FOR_BILLER_ID_UBP = "getBillerAsPerBillerIdUBP";
	private static final String BILLER_FOR_BILLER_ID = "getBillerAsPerBillerId";
 	private static final String ACTION_CODE_FOR_BILLER_ID = "getActionCodeForBillerId";
	private static final String BILLER_LIST_FOR_CATEGORY = "getBilerListAsPerCategId";
	private static final String BILLER_LIST_GET = "getAllBilelrList";
	private static final String BILLER_LIST_GET_NOT_TZ = "getAllBilelrListNotTZ";
	private static final String UBP_BUSINESS_IDS_LIST = "getUBPBusinessIds";
	private static final String MOBILE_WALLET = "MobileWalletMobileMoney";

	private String UBP_BUSINESS_IDS=null;
	//Other Countries Biller queries Start
	private static final String OTHER_COUNTRIES_GET_ALL_BILELR_LIST="otherCountriesGetAllBilelrList";
	private static final String OTHER_COUNTRIES_GET_ALL_BILELR_LIST_NOTTZ="otherCountriesGetAllBilelrListNotTZ";
	private static final String OTHER_COUNTRIES_GET_BILLER_ASPER_BILLERID="otherCountriesGetBillerAsPerBillerId";
	private static final String OTHER_COUNTRIES_GET_BILER_LISTAS_PER_CATEGID="otherCountriesGetBilerListAsPerCategId";
	private static final String OTHER_COUNTRIES_GET_BILER_LISTAS_PER_CATEGID_FOR_GHANAANDZAMBIA="otherCountriesGetBilerListAsPerCategIdForGhanaandZambia";
	private static final String PROBASE_GET_BILLER_ASPER_BILLERID="probaseGetBillerAsPerBillerId";
	//Other Countries Biller End
	
	
	//Ghana data bundle change
	private static final String DATABUNDLE_GET_BILLERS="getDataBundleBilelrList";

	private static final Logger LOGGER = Logger.getLogger(BillerListDAOImpl.class);
	// End

	@SuppressWarnings("unchecked")
	@Override
	public BillerServiceResponse getAllBillerList(BillerServiceRequest request) {
		// UBP Optimisation - Start
		String businessId = request.getContext().getBusinessId();
		String mobilenumber = request.getContext().getMobilePhone();
		List<PilotUserDTO> pilotUserDTOList= null;
		List<UBPBusinessIdsDTO> ubpBusinessIdsDTO=this.queryForList(UBP_BUSINESS_IDS_LIST);
		if(ubpBusinessIdsDTO!=null && ubpBusinessIdsDTO.size()>0)
			UBP_BUSINESS_IDS=ubpBusinessIdsDTO.get(0).getParamValue();
		if(UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)&& request.getBillerCategoryId()!=null
				&&(!MOBILE_WALLET.contains(request.getBillerCategoryId()))){
		Map<String, String> pparameterMap = new HashMap<String, String>();
		pparameterMap.put(BUSINESS_ID, businessId);
		pparameterMap.put(MOBILE_NUMBER, mobilenumber);
		pilotUserDTOList = this.queryForList(PILOT_USER,
				pparameterMap);
		}
		// End
		BillerServiceResponse response = new BillerServiceResponse();
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put(BUSINESS_ID, businessId);
		List<BillerDTO> billerList = null;

		// UBP Optimisation - Start
		if (pilotUserDTOList!=null && pilotUserDTOList.size() > 0 && request.getBillerCategoryId()!=null
				&&(!MOBILE_WALLET.contains(request.getBillerCategoryId()))
				&& UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)) {

			//parameterMap.put(PILOT_MODE, "Y");
			parameterMap.put(STATUS, "ACTIVE");
			if (request.isShowAllBillers()) {
				billerList = this.queryForList(BILLER_LIST_GET_UBP,
						parameterMap);
			} else {
				if (!CommonConstants.TZBRB_BUSINESS_ID.equals(businessId)) {
					billerList = this.queryForList(BILLER_LIST_GET_NOT_TZ_UBP,
							parameterMap);
				} else {
					billerList = this.queryForList(BILLER_LIST_GET_UBP,
							parameterMap);
				}
			}
			// End
		} else {

			if(UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)&& request.getBillerCategoryId()!=null &&
					(!MOBILE_WALLET.contains(request.getBillerCategoryId()))){
				parameterMap.put(PILOT_MODE, "N");
				parameterMap.put(STATUS, "ACTIVE");
			if (request.isShowAllBillers()) {
				billerList = this.queryForList(BILLER_LIST_GET, parameterMap);
			} else {
				if (!CommonConstants.TZBRB_BUSINESS_ID.equals(businessId)) {
					billerList = this.queryForList(BILLER_LIST_GET_NOT_TZ,
							parameterMap);
				} else {
					billerList = this.queryForList(BILLER_LIST_GET,
							parameterMap);
				}
			}
			}else{
				if (request.isShowAllBillers()) {
					billerList = this.queryForList(OTHER_COUNTRIES_GET_ALL_BILELR_LIST, parameterMap);
				} else {
					if (!CommonConstants.TZBRB_BUSINESS_ID.equals(businessId)) {
						billerList = this.queryForList(OTHER_COUNTRIES_GET_ALL_BILELR_LIST_NOTTZ,
								parameterMap);
					} else {
						billerList = this.queryForList(OTHER_COUNTRIES_GET_ALL_BILELR_LIST,
								parameterMap);
					}
				}
			}

		}

		response.setBillerList(billerList);
		return response;
	}

	@Override
	public BillerServiceResponse getBillPaymentsBillerList(
			BillerServiceRequest request) {
		List<UBPBusinessIdsDTO> ubpBusinessIdsDTO=this.queryForList(UBP_BUSINESS_IDS_LIST);
		if(ubpBusinessIdsDTO!=null && ubpBusinessIdsDTO.size()>0)
			UBP_BUSINESS_IDS=ubpBusinessIdsDTO.get(0).getParamValue();
		BillerServiceResponse response = new BillerServiceResponse();
		Map<String, String> parameterMap = new HashMap<String, String>();
		String businessId=request.getContext().getBusinessId();
		parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
		// UBP Optimisation - Start
		List<PilotUserDTO> pilotUserDTOList = null;
		if(UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)
				&&(!MOBILE_WALLET.contains(request.getBillerCategoryId()))){
		String mobilenumber = request.getContext().getMobilePhone();
		Map<String, String> pparameterMap = new HashMap<String, String>();
		pparameterMap.put(MOBILE_NUMBER, mobilenumber);
		pparameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
		pilotUserDTOList = this.queryForList(PILOT_USER,
				pparameterMap);
		}
		List<BillerDTO> billerList = null;
		if (pilotUserDTOList != null && pilotUserDTOList.size() > 0&& request.getBillerCategoryId()!=null
				&&(!MOBILE_WALLET.contains(request.getBillerCategoryId()))
				&& UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)) {
			parameterMap.put(BILLER_CATEGORY_ID, request.getBillerCategoryId());
			//parameterMap.put(PILOT_MODE, "Y");
			parameterMap.put(STATUS, "ACTIVE");
			billerList = this.queryForList(BILLER_LIST_FOR_CATEGORY_UBP,
					parameterMap);
			// End
		} else {
			// BarclaycardBill
			parameterMap.put(BILLER_CATEGORY_ID, request.getBillerCategoryId());

			if(UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)&& request.getBillerCategoryId()!=null
					&&(!MOBILE_WALLET.contains(request.getBillerCategoryId()))){
			parameterMap.put(PILOT_MODE, "N");
			parameterMap.put(STATUS, "ACTIVE");
			billerList = this.queryForList(BILLER_LIST_FOR_CATEGORY,
					parameterMap);
			}else
				if(CommonConstants.ZMBRB_BUSINESS_ID.equals(businessId)|| CommonConstants.GHBRB_BUSINESS_ID.equals(businessId))
				{
					parameterMap.put(BILLER_CATEGORY_ID, "MobileMoney");
					billerList = this.queryForList(OTHER_COUNTRIES_GET_BILER_LISTAS_PER_CATEGID_FOR_GHANAANDZAMBIA,
							parameterMap);
				}else
				{
				billerList = this.queryForList(OTHER_COUNTRIES_GET_BILER_LISTAS_PER_CATEGID,
						parameterMap);
				}
		}


		response.setBillerList(billerList);
		return response;
	}

	@Override
	public BillerServiceResponse getBillerForBillerId(
			BillerServiceRequest request) {
		// TODO Auto-generated method stub
		BillerServiceResponse response = new BillerServiceResponse();
		Map<String, String> parameterMap = new HashMap<String, String>();
		String businessId=request.getBusinessId();
		parameterMap.put(BILLER_ID, request.getBillerId());
		parameterMap.put(BUSINESS_ID, request.getBusinessId());

		// UBP Optimisation - Start
		List<UBPBusinessIdsDTO> ubpBusinessIdsDTO=this.queryForList(UBP_BUSINESS_IDS_LIST);
		if(ubpBusinessIdsDTO!=null && ubpBusinessIdsDTO.size()>0)
			UBP_BUSINESS_IDS=ubpBusinessIdsDTO.get(0).getParamValue();

		List<PilotUserDTO> pilotUserDTOList = null;
		if(UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)&& request.getBillerCategoryId()!=null &&
				(!MOBILE_WALLET.contains(request.getBillerCategoryId()))){
		String mobilenumber = request.getContext().getMobilePhone();
		Map<String, String> pparameterMap = new HashMap<String, String>();
		pparameterMap.put(MOBILE_NUMBER, mobilenumber);
		pparameterMap.put(BUSINESS_ID, request.getBusinessId());
		pilotUserDTOList = this.queryForList(PILOT_USER,
				pparameterMap);
		}
		BillerDTO biller = null;
		if (pilotUserDTOList!=null && pilotUserDTOList.size() > 0&& request.getBillerCategoryId()!=null  &&
				(!MOBILE_WALLET.contains(request.getBillerCategoryId()))
				&& UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)) {
			//parameterMap.put(PILOT_MODE, "Y");
			parameterMap.put(STATUS, "ACTIVE");
			biller = (BillerDTO) this.queryForObject(BILLER_FOR_BILLER_ID_UBP,
					parameterMap);

		} else {
			if(UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)&& request.getBillerCategoryId()!=null &&
					(!MOBILE_WALLET.contains(request.getBillerCategoryId()))){
			parameterMap.put(PILOT_MODE, "N");
			parameterMap.put(STATUS, "ACTIVE");
			// End
			biller = (BillerDTO) this.queryForObject(BILLER_FOR_BILLER_ID,
					parameterMap);
			}else if(null!=request && request.getBusinessId().equalsIgnoreCase("ZMBRB") && (request.getBillerId().endsWith("-9"))){
				biller = (BillerDTO) this.queryForObject(PROBASE_GET_BILLER_ASPER_BILLERID,
						parameterMap);
			}else
				biller = (BillerDTO) this.queryForObject(OTHER_COUNTRIES_GET_BILLER_ASPER_BILLERID,
						parameterMap);
		}

		response.setBillerDTO(biller);
		return response;
	}


	@Override
	public BillerServiceResponse getActionCodeForBillerId(
			BillerServiceRequest request) {
	BillerServiceResponse response = new BillerServiceResponse();
	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BILLER_ID, request.getBillerId());
	parameterMap.put(BUSINESS_ID, request.getBusinessId());
	BillerCreditDTO creditBiller = (BillerCreditDTO) this.queryForObject(ACTION_CODE_FOR_BILLER_ID, parameterMap);
	response.setBillerCreditDTO(creditBiller);
	return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BillerServiceResponse getDataBundleBillerList(BillerServiceRequest request) {
		// TODO Auto-generated method stub
		BillerServiceResponse response = new BillerServiceResponse();
		Map<String, String> parameterMap = new HashMap<String, String>();
		String businessId=request.getContext().getBusinessId();
		parameterMap.put(BUSINESS_ID, businessId);
		parameterMap.put(STATUS, "ACTIVE");
		List<BillerDTO> billerList = null;
		billerList = this.queryForList(DATABUNDLE_GET_BILLERS,
				parameterMap);
		response.setBillerList(billerList);
		return response;
	}

}

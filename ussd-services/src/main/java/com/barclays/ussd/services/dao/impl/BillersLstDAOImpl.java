/**
 * BillersLstDAOImpl.java
 */
package com.barclays.ussd.services.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.bmg.dto.PilotUserDTO;
import com.barclays.bmg.dto.UBPBusinessIdsDTO;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;

/**
 * @author BTCI
 *
 */
public class BillersLstDAOImpl extends SqlMapClientDaoSupport implements IBillersLstDAO {

    /**
     * COUNTRY_CD
     */
    private static final String COUNTRY_CD = "countryCd";
    private static final String BILLER_ID = "billerId";
	private static final String PILOT_MODE = "pilotmode";
	private static final String STATUS = "status";
	private static final String PILOT_USER = "getPilotUser";
	private static final String BUSINESS_ID = "businessId";
	private static final String MOBILE_NUMBER = "mobilenumber";
	private static final String GET_BILLERS = "otherCountriesGetBillers";
	private static final String UBP_BUSINESS_IDS_LIST = "getUBPBusinessIds";
	private static final String BILLER_CATEGORY_ID ="billerCategoryId";
	private String UBP_BUSINESS_IDS=null;
	private static final Logger LOGGER = Logger.getLogger(BillersLstDAOImpl.class);
    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.services.dao.impl.IBillersLstDAO#getBillersList(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<BillersListDO> getBillersList(String countryCd, String mobileNumber,String businessId) throws USSDNonBlockingException {
    List<UBPBusinessIdsDTO> ubpBusinessIdsDTO=this.getSqlMapClientTemplate().queryForList(UBP_BUSINESS_IDS_LIST);
	if(ubpBusinessIdsDTO!=null && ubpBusinessIdsDTO.size()>0)
			UBP_BUSINESS_IDS=ubpBusinessIdsDTO.get(0).getParamValue();

    List<PilotUserDTO> pilotUserDTOList = null;
    if(businessId!=null && UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)){
	Map<String, String> pparameterMap = new HashMap<String, String>();

	pparameterMap.put(BUSINESS_ID, businessId);
	pparameterMap.put(MOBILE_NUMBER, mobileNumber);
	pilotUserDTOList = this.getSqlMapClientTemplate().queryForList(PILOT_USER, pparameterMap);
    }

	Map<String, String> params = new HashMap<String, String>(1);
	params.put(COUNTRY_CD, countryCd);

	List<BillersListDO> billersList;
	params.put(BUSINESS_ID, businessId);

	if (pilotUserDTOList!=null && pilotUserDTOList.size() > 0&&businessId!=null && UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)) {
		//params.put(PILOT_MODE, "Y");
		params.put(STATUS, "ACTIVE");
	String statementName = USSDConstants.GET_BILLERS_LST;
	try {
   //Modified for TZNBC Bill Pay Functionality
		if(countryCd.equals("TZN"))
	    {
	    	params.put(COUNTRY_CD, "TZ");
	    	statementName = statementName + "TZN";
	    }
		else{
			if (!countryCd.equals("TZ")) {
			statementName = statementName + "NotTZ";
		    }
		}
		statementName = statementName + "UBP" ;
	    billersList = this.getSqlMapClientTemplate().queryForList(statementName, params);
	} catch (Exception e) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}
	}
	else{
		params.put(PILOT_MODE, "N");
		params.put(STATUS, "ACTIVE");

		String statementName = null;
		if(businessId!=null && UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId))
			statementName = USSDConstants.GET_BILLERS_LST;
		else
			statementName = GET_BILLERS;
		try {
	   //Modified for TZNBC Bill Pay Functionality
			if(countryCd.equals("TZN"))
		    {
		    	params.put(COUNTRY_CD, "TZ");
		    	statementName = statementName + "TZN";
		    }
			else{
				if (!countryCd.equals("TZ")) {
				statementName = statementName + "NotTZ";
			    }
			}
		    billersList = this.getSqlMapClientTemplate().queryForList(statementName, params);
		} catch (Exception e) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
		}
	}

	return billersList;
    }

    @SuppressWarnings("unchecked")
	@Override
    public BillersListDO getBillerInfo(String countryCd, String billerId, String mobileNumber,String businessId) throws USSDNonBlockingException {
	    List<UBPBusinessIdsDTO> ubpBusinessIdsDTO=this.getSqlMapClientTemplate().queryForList(UBP_BUSINESS_IDS_LIST);
	    List<PilotUserDTO> pilotUserDTOList = null;
	    List<BillersListDO> billersList;
	    String statementName = USSDConstants.GET_BILLER_INFO;
	    

	    if(ubpBusinessIdsDTO!=null && ubpBusinessIdsDTO.size()>0) {
    		UBP_BUSINESS_IDS=ubpBusinessIdsDTO.get(0).getParamValue();
	    }

	    if(businessId!=null && UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)){
		    Map<String, String> pparameterMap = new HashMap<String, String>();
		    pparameterMap.put(BUSINESS_ID, countryCd);
		    pparameterMap.put(MOBILE_NUMBER, mobileNumber);
		    pilotUserDTOList = this.getSqlMapClientTemplate().queryForList(PILOT_USER, pparameterMap);
	    }

		Map<String, String> params = new HashMap<String, String>(2);
		params.put(COUNTRY_CD, countryCd);
		params.put(BILLER_ID, billerId);

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put(BUSINESS_ID, countryCd);

		if (pilotUserDTOList!=null && pilotUserDTOList.size() > 0&& businessId!=null && UBP_BUSINESS_IDS!=null && UBP_BUSINESS_IDS.contains(businessId)) {
			//parameterMap.put(PILOT_MODE, "Y");
			params.put(STATUS, "ACTIVE");
			try {
				//Modified for TZNBC Bill Pay Functionality
			    if(countryCd.equals("TZN")) {
			    	params.put(COUNTRY_CD, "TZ");
			    	statementName = statementName+ "TZN";
			    }
				statementName = statementName + "UBP" ;
			    billersList = this.getSqlMapClientTemplate().queryForList(statementName, params);
			} catch (Exception e) {
			    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
			}
		} else {
			params.put(PILOT_MODE, "N");
			params.put(STATUS, "ACTIVE");
			try {
				//Modified for TZNBC Bill Pay Functionality
			    if(countryCd.equals("TZN"))
			    {
			    	params.put(COUNTRY_CD, "TZ");
			    	statementName = statementName+ "TZN";
			    }
			    billersList = this.getSqlMapClientTemplate().queryForList(statementName, params);
			} catch (Exception e) {
			    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
			}
		}
		return (billersList == null || billersList.isEmpty()) ? null : billersList.get(0);
    }

  //TZNBC Menu Optimization - to fetch category and billers based on category
  	@SuppressWarnings("unchecked")
  	@Override
  	public List<BillersListDO> getCategoryList(String countryCd, String mobileNumber, String businessId)
  			throws USSDNonBlockingException {
  		Map<String, String> params = new HashMap<String, String>(1);

  		List<BillersListDO> categoryList;
  		params.put(COUNTRY_CD, "TZ");
  		params.put(BUSINESS_ID, businessId);
  		params.put(STATUS, "ACTIVE");
  		String statementName = USSDConstants.GET_CATEGORY_LST;
  		try {
  			categoryList=this.getSqlMapClientTemplate().queryForList(statementName, params);

  		}catch (Exception e) {
  			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
  		}
  		return categoryList;
  	}
  	@SuppressWarnings("unchecked")
  	@Override
  	public List<BillersListDO> getBillerPerCategory(String mobileNumber, String businessId,
  			String category) throws USSDNonBlockingException {
  		Map<String, String> params = new HashMap<String, String>(1);
  		List<BillersListDO> billerPerCategoryList;
  		params.put(COUNTRY_CD, "TZ");
  		params.put(BUSINESS_ID, businessId);
  		params.put(STATUS, "ACTIVE");
  		params.put(BILLER_CATEGORY_ID, category);
  		String statementName = USSDConstants.GET_BILLER_PER_CATEGORY;

  		try {
  			billerPerCategoryList=this.getSqlMapClientTemplate().queryForList(statementName, params);

  		}catch (Exception e) {
  			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
  		}
  		return billerPerCategoryList;
  	}
  	
  	//Ghana Data Bundle
	@Override
	public BillersListDO getBillerInfoDataBundleAcc(String billerId, String businessId)
			throws USSDNonBlockingException {
		// TODO Auto-generated method stub
		BillersListDO biller=null;
		Map<String, String> params = new HashMap<String, String>(1);
		params.put(COUNTRY_CD, "GH");
  		params.put(BILLER_ID, billerId);
  		params.put(BUSINESS_ID, businessId);
  		params.put(STATUS, "ACTIVE");
  		
  		biller=(BillersListDO) this.getSqlMapClientTemplate().queryForObject(USSDConstants.GET_BILLER_INFO, params);
		return biller;
	}
  	 	
  	
}

package com.barclays.ussd.adapter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.barclays.bmg.constants.ViewConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.json.response.BMBPayload;

public class BMGAdapter {
    private static final Logger LOGGER = Logger.getLogger(BMGAdapter.class);
    private static final String LOGIN_OPCODE_NEW = "OP0202";
    private static final String LOGIN_OPCODE = "OP0109";
    private Map<String, AbstractController> bmgControllerMap;
    private Map<String, Map<String, AbstractController>> countryMap;
    private Map<String, AbstractController> countryControllerMap;

    public String handleBMGRequest(HttpServletRequest request, HttpServletResponse response, Map<String, String> queryParamMap) throws Exception {
	String opcode = LOGIN_OPCODE;
	String result = null;

	opcode = queryParamMap.get("opCde");
	LOGGER.debug(" Entering into BMGAdapter.handleBMGRequest() class with BMG OPCODE :   " + opcode);
	USSDRequestWrapper reqWrp = new USSDRequestWrapper(request, queryParamMap);

	String BusinessId = BMBContextHolder.getContext().getBusinessId();
	if (null != countryMap) {
	    countryControllerMap = countryMap.get(BusinessId);
	    if (null == countryControllerMap) {
		result = makeControllerMap(request, response, queryParamMap, opcode, reqWrp, bmgControllerMap);
	    } else {
		if (countryControllerMap.containsKey(opcode)) {
		    result = makeControllerMap(request, response, queryParamMap, opcode, reqWrp, countryControllerMap);
		} else {
		    result = makeControllerMap(request, response, queryParamMap, opcode, reqWrp, bmgControllerMap);
		}
	    }
	}

	return result;
    }

    private String makeControllerMap(HttpServletRequest request, HttpServletResponse response, Map<String, String> queryParamMap, String opcode,
	    USSDRequestWrapper reqWrp, Map<String, AbstractController> bmgControllerMap) throws Exception, IOException {
	ModelAndView modelAndViewObj;

	if (bmgControllerMap.containsKey(opcode)) {
	    modelAndViewObj = bmgControllerMap.get(opcode).handleRequest(reqWrp, response);

	    if (LOGIN_OPCODE.equalsIgnoreCase(opcode)) {
		if (modelAndViewObj != null) {
		    final String viewName = modelAndViewObj.getViewName();
		    Map<String, Object> modelObject = modelAndViewObj.getModel();
		    String jsonResStr1 = getResponseJsonString(modelObject);
		    if (jsonResStr1 != null && (jsonResStr1.contains("ATH00200") || jsonResStr1.contains("THM00")) && jsonResStr1.contains("RES0109")) {
			return jsonResStr1;
		    }
		    if (viewName != null && viewName.equalsIgnoreCase(ViewConstant.FAILURE_VIEW) || jsonResStr1.contains("BMB99999")) {
			return jsonResStr1;
		    }

		}

		opcode = LOGIN_OPCODE_NEW;
		queryParamMap.put("opCde", opcode);
		reqWrp = new USSDRequestWrapper(request, queryParamMap);
		modelAndViewObj = bmgControllerMap.get(opcode).handleRequest(reqWrp, response);
	    }
	    Map<String, Object> modelObject = modelAndViewObj.getModel();
	    String jsonResStr = getResponseJsonString(modelObject);
	    LOGGER.debug("Json Recieved from BMG for opcode = " + opcode + " \t" + jsonResStr);
	    LOGGER.debug("Exit from BMGAdapter.handleBMGRequest()");
	    return jsonResStr;
	} else {
	    String temporary = "No controller found for opcode " + opcode;
	    // System.out.println(temporary);

	    return temporary;
	}
    }

    private String getResponseJsonString(Map<String, Object> model) throws IOException {
	// put into request
	ObjectMapper objectMapper = new ObjectMapper();
	Writer stringWriter = new StringWriter();
	String jsonStringSentToClient = null;

	for (Map.Entry<String, Object> entry : model.entrySet()) {
	    Object payload = entry.getValue();
	    if (payload instanceof BMBPayload) {
		JsonGenerator generator1 = objectMapper.getJsonFactory().createJsonGenerator(stringWriter);
		objectMapper.writeValue(generator1, payload);
		jsonStringSentToClient = stringWriter.toString();
		break;
	    }
	}

	return jsonStringSentToClient;
    }

    public void setBmgControllerMap(Map<String, AbstractController> bmgControllerMap) {
	this.bmgControllerMap = bmgControllerMap;
    }

    public Map<String, AbstractController> getBmgControllerMap() {
	return bmgControllerMap;
    }

    public Map<String, Map<String, AbstractController>> getCountryMap() {
	return countryMap;
    }

    public void setCountryMap(Map<String, Map<String, AbstractController>> countryMap) {
	this.countryMap = countryMap;
    }

}

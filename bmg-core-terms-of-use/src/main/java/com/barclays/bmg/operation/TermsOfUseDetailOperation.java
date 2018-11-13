package com.barclays.bmg.operation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.TermsOfUseOperationRequest;
import com.barclays.bmg.operation.response.TermsOfUseOperationResponse;
import com.barclays.bmg.service.TermsOfUseService;
import com.barclays.bmg.service.request.TermsOfUseAcceptServiceRequest;
import com.barclays.bmg.service.response.TermsOfUseAcceptServiceResponse;

public class TermsOfUseDetailOperation extends BMBCommonOperation {

	private TermsOfUseService termsOfUseService;
	private Resource resources[];
	private String termsOfUseFileName = "TermsOfUse";

	public TermsOfUseOperationResponse retrieveTermsAndCondition(
			TermsOfUseOperationRequest request) {

		TermsOfUseOperationResponse returnTermsOfUseOperationResp = new TermsOfUseOperationResponse();

		Context context = request.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);

		String termsOfUseVer = (String) context.getContextMap().get(
				"TERMS_OF_USE_VERSION_NO");

		TermsOfUseAcceptServiceRequest termsOfUseAcceptServiceReq = new TermsOfUseAcceptServiceRequest();
		termsOfUseAcceptServiceReq.setContext(context);
		termsOfUseAcceptServiceReq.setTermsOfUseVersionNo(termsOfUseVer);
		termsOfUseAcceptServiceReq.setAcceptFlag("Y");

		TermsOfUseAcceptServiceResponse termsOfUseAcceptServiceResp = termsOfUseService
				.checkTermsOfUseAccept(termsOfUseAcceptServiceReq);

		if (!termsOfUseAcceptServiceResp.isSuccess()) {
			try {

				returnTermsOfUseOperationResp
						.setTermsAndCondition(readFileData(request,
								termsOfUseVer));

				returnTermsOfUseOperationResp
						.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
				returnTermsOfUseOperationResp.setSuccess(true);

			} catch (Exception e) {
				
				returnTermsOfUseOperationResp.setSuccess(false);
			}

		} else {
			returnTermsOfUseOperationResp.setSuccess(false);
		}

		return returnTermsOfUseOperationResp;

	}

	private String readFileData(TermsOfUseOperationRequest request,
			String versionNo) throws Exception {

		StringBuffer termsDetails = new StringBuffer();

		String fileName = null;
		if (this.resources != null && resources.length > 0) {
			for (Resource resource : resources) {
				URL url = resource.getURL();
				fileName = url.getFile();

				if (fileName.contains(request.getContext().getBusinessId())) {
					break;
				}
			}
		}

		String newFileName = request.getContext().getBusinessId() + "_"
				+ termsOfUseFileName + "_" + versionNo + ".txt";

		if (fileName != null && fileName.contains(newFileName)) {

			FileReader fileReader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fileReader);

			String line = "";

			while ((line = br.readLine()) != null) {
				termsDetails.append(line);
			}

			br.close();
			fileReader.close();
		}

		String tcdtails = "";
		if (StringUtils.isNotBlank(termsDetails.toString())) {

			tcdtails = replaceBackSpaceChars(termsDetails.toString());
		}

		return tcdtails;

	}

	private String replaceBackSpaceChars(String tcDetl) {
		// TODO need to replace the back space character.
		String updatedtcDetl = tcDetl;
		updatedtcDetl = updatedtcDetl.replaceAll("\t", " ");

		return updatedtcDetl;
	}

	public void setTermsOfUseService(TermsOfUseService termsOfUseService) {
		this.termsOfUseService = termsOfUseService;
	}

	public Resource[] getResources() {
		return resources.clone();
	}

	public void setResources(Resource[] resources) {
		this.resources = resources.clone();
	}

}
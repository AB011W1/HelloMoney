package com.barclays.bmg.json.model.builder.intrates;

import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.IntrateDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.TDIntratesTenureJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.intrates.IntRatesSlabJSONModel;
import com.barclays.bmg.json.model.intrates.InterestRatesJSONModel;
import com.barclays.bmg.json.model.intrates.InterestRatesListJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.intrates.InterestRatesOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class InterestRatesJSONBldr extends BMBCommonJSONBuilder implements
		BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {


		if(responseContext instanceof InterestRatesOperationResponse) {
			InterestRatesOperationResponse response = (InterestRatesOperationResponse) responseContext;
			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			populatePayload(response, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}

	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");

		}else{
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}



		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.TD_INT_RATES_RESPONSE_ID);

		return header;
	}

	protected void populatePayload(InterestRatesOperationResponse response,
			BMBPayload bmbPayload) {
		InterestRatesListJSONModel intRatesListResponseModel = new InterestRatesListJSONModel();

		if (response != null && response.isSuccess()) {

			List<IntrateDTO> intRatesDTOList = response.getIntrateDTOList();

			if (intRatesDTOList != null && intRatesDTOList.size() > 0) {

				String prevPrdCode = "";
				Double prevFromSlab = 0.0;
				Double prevToSlab = 0.0;
				InterestRatesJSONModel interestRatesJSONModel = null;
				IntRatesSlabJSONModel intRatesSlabJSONModel = null;
				TDIntratesTenureJSONModel tdIntratesTenureJSONModel = null;

				for (IntrateDTO intratesDTO : intRatesDTOList) {

					if (prevPrdCode.equals(intratesDTO.getProductCode())) {

						if ((intratesDTO.getFrom().doubleValue() != prevFromSlab)
								|| (intratesDTO.getTo().doubleValue() != prevToSlab)) {

							intRatesSlabJSONModel = new IntRatesSlabJSONModel();
							prevFromSlab = intratesDTO.getFrom().doubleValue();
							prevToSlab = intratesDTO.getTo().doubleValue();

							intRatesSlabJSONModel
									.setFrom(BMGFormatUtility
											.getJSONAmount(
													intratesDTO.getCcy(),
													BMGFormatUtility
															.getFormattedAmount(intratesDTO
																	.getFrom())));

							intRatesSlabJSONModel
									.setTo(BMGFormatUtility
											.getJSONAmount(
													intratesDTO.getCcy(),
													BMGFormatUtility
															.getFormattedAmount(intratesDTO
																	.getTo())));

							tdIntratesTenureJSONModel = new TDIntratesTenureJSONModel();
							tdIntratesTenureJSONModel.setIntrate(intratesDTO
									.getIntrate().toString());
							tdIntratesTenureJSONModel.setTenDay(intratesDTO
									.getTenureDay().toString());
							tdIntratesTenureJSONModel.setTenMonth(intratesDTO
									.getTenureMonth().toString());

							intRatesSlabJSONModel.getTenure().add(tdIntratesTenureJSONModel);
							interestRatesJSONModel.getIntRateSlab().add(intRatesSlabJSONModel);



						} else {

							tdIntratesTenureJSONModel = new TDIntratesTenureJSONModel();
							tdIntratesTenureJSONModel.setIntrate(intratesDTO
									.getIntrate().toString());
							tdIntratesTenureJSONModel.setTenDay(intratesDTO
									.getTenureDay().toString());
							tdIntratesTenureJSONModel.setTenMonth(intratesDTO
									.getTenureMonth().toString());

							intRatesSlabJSONModel.getTenure().add(tdIntratesTenureJSONModel);


						}

					} else {
						prevFromSlab = 0.0;
						prevToSlab = 0.0;
						prevPrdCode = intratesDTO.getProductCode();
						interestRatesJSONModel = new InterestRatesJSONModel();
						interestRatesJSONModel.setProdCode(intratesDTO
								.getProductCode());
						interestRatesJSONModel.setProdDesc(intratesDTO
								.getProdDesc());
						if ((intratesDTO.getFrom().doubleValue() != prevFromSlab)
								|| (intratesDTO.getTo().doubleValue() != prevToSlab)) {

							intRatesSlabJSONModel = new IntRatesSlabJSONModel();
							prevFromSlab = intratesDTO.getFrom().doubleValue();
							prevToSlab = intratesDTO.getTo().doubleValue();

							intRatesSlabJSONModel
									.setFrom(BMGFormatUtility
											.getJSONAmount(
													intratesDTO.getCcy(),
													BMGFormatUtility
															.getFormattedAmount(intratesDTO
																	.getFrom())));

							intRatesSlabJSONModel
									.setTo(BMGFormatUtility
											.getJSONAmount(
													intratesDTO.getCcy(),
													BMGFormatUtility
															.getFormattedAmount(intratesDTO
																	.getTo())));



							tdIntratesTenureJSONModel = new TDIntratesTenureJSONModel();
							tdIntratesTenureJSONModel.setIntrate(intratesDTO
									.getIntrate().toString());
							tdIntratesTenureJSONModel.setTenDay(intratesDTO
									.getTenureDay().toString());
							tdIntratesTenureJSONModel.setTenMonth(intratesDTO
									.getTenureMonth().toString());


							intRatesSlabJSONModel.getTenure().add(tdIntratesTenureJSONModel);
							interestRatesJSONModel.getIntRateSlab().add(intRatesSlabJSONModel);


						} else {

							tdIntratesTenureJSONModel = new TDIntratesTenureJSONModel();
							tdIntratesTenureJSONModel.setIntrate(intratesDTO
									.getIntrate().toString());
							tdIntratesTenureJSONModel.setTenDay(intratesDTO
									.getTenureDay().toString());
							tdIntratesTenureJSONModel.setTenMonth(intratesDTO
									.getTenureMonth().toString());

							intRatesSlabJSONModel.getTenure().add(tdIntratesTenureJSONModel);

						}
						intRatesListResponseModel.getIntRatesList().add(interestRatesJSONModel);




					}
					// intRatesList.add(new
					// InterestRatesJSONModel(intratesDTOList));


				}
			}



		}
		bmbPayload.setPayData(intRatesListResponseModel);
	}
}
